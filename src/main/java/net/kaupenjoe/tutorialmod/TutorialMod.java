package net.kaupenjoe.tutorialmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.kaupenjoe.tutorialmod.Player.Stats;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;



public class TutorialMod implements ModInitializer {
	public static final Map<UUID, Stats> PLAYER_STATS = new HashMap<>();
	public static final String MOD_ID = "dndjjk";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Stats getPlrStats(CommandContext<ServerCommandSource> context){
		UUID uuid = Objects.requireNonNull(context.getSource().getPlayer()).getUuid();
		Stats plrStats = PLAYER_STATS.get(uuid);
		if(plrStats == null){
			plrStats = new Stats(5,5,5,1);
			applyMaxHP(context.getSource().getPlayer(), plrStats.getMaxHP());
			PLAYER_STATS.put(uuid,plrStats);
		}
        return plrStats;
	}
	public static int getStat(CommandContext<ServerCommandSource> context, String stat) {
		Stats plrStats = getPlrStats(context);

        return switch (stat) {
            case "strength" -> plrStats.getStrength();
            case "dexterity" -> 5;
            default -> 0;
        };
	}
	public static int doRoll(CommandContext<ServerCommandSource> context, String modifier, String label){
		int baseRoll = (int)(Math.random() * 20) + 1;
		Stats plrStats = getPlrStats(context);

		int mod = switch (modifier){
			case "strength" -> plrStats.getStrength();
			case "dexterity" -> 5;
			default -> throw new IllegalStateException("Unexpected value: " + modifier);
        };

		int total = baseRoll + mod;
		context.getSource().sendFeedback(
				() -> Text.literal(label + " " + baseRoll+ " + "+ mod + " = "+ total),
				false
		);

		return total;
	}
	public static int doRoll(CommandContext<ServerCommandSource> context, int modifier, String label){
		int baseRoll = (int)(Math.random() * 20) + 1;
		Stats plrStats = getPlrStats(context);

		int total = baseRoll + modifier;
		context.getSource().sendFeedback(
				() -> Text.literal(label + " " + baseRoll+ " + "+ modifier + " = "+ total),
				false
		);

		return total;
	}
	public static void applyMaxHP(ServerPlayerEntity player, int hp){
		var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if( attr!=null){
			attr.setBaseValue(hp);
		}
	}
	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("levelup")
					.executes(context -> {
						Stats plrStats = getPlrStats(context);
						plrStats.levelUp(context);
						return 1;
					})
			);
			dispatcher.register(CommandManager.literal("update")
					.executes(context -> {
						Stats plrStats = getPlrStats(context);
						ServerPlayerEntity player = context.getSource().getPlayer();
						int baseHP = plrStats.getMaxHP();
						int HP = baseHP > 0? baseHP:  plrStats.calculateMaxHp();
                           assert player != null;
                           TutorialMod.applyMaxHP(player,HP);
						player.sendMessage(
								Text.of("HP set to" + HP)
						);
						return 1;
					})
			);
			dispatcher.register(CommandManager.literal("roll")
				.then(CommandManager.literal("attack")
					.executes(context -> {
							int roll = doRoll(context,"strength","Attack Roll: ");
							ServerCommandSource source = context.getSource();

							source.sendFeedback(()->Text.literal("Attempting to attack Osker. Their AC is 13"),false);

							source.sendFeedback(()->Text.literal("You got "+roll),false);

							if(roll>=12){
								source.sendFeedback(
										()->Text.literal("You hit Osker dealing "+ (roll+2)+ "damage"),
										false
								);
							}else{
								source.sendFeedback(
										()->Text.literal("You miss"),
										false
								);
							}

							return 1;
					})
				)
				.then(CommandManager.literal("skill")
						.executes(context -> {

						int roll = doRoll(context, "strength","Skill Check: ");
							return 1;
					})
				)
			);
		dispatcher.register(CommandManager.literal("stat")
			.then(CommandManager.literal("strength")
				.then(CommandManager.literal("set")
					.then(CommandManager.argument("amount",IntegerArgumentType.integer(0,20))
							.executes(context -> {
								int Amount = IntegerArgumentType.getInteger(context,"amount");
								Stats plrStats = getPlrStats(context);
								plrStats.setStrength(Amount);
								context.getSource().sendFeedback(
										() -> Text.literal("You have set your strength to " + Amount ),
										false
								);
								return 1;
							}))
					)
					.then(CommandManager.literal("get")
							.executes(context -> {
								context.getSource().sendFeedback(
										() -> Text.literal("Your strength is " + getPlrStats(context).getStrength()),
										false
								);
								return 1;
							}))
				)
					.then(CommandManager.literal("constitution")
						.then(CommandManager.literal("set")
							.then(CommandManager.argument("amount",IntegerArgumentType.integer(0,20))
								.executes(context -> {
									int Amount = IntegerArgumentType.getInteger(context,"amount");

									getPlrStats(context).setConstitution(Amount);
										context.getSource().sendFeedback(
											() -> Text.literal("You have set your constitution to " + Amount ),
											false
											);
											return 1;
										}))
								)
									.then(CommandManager.literal("get")
											.executes(context -> {
												context.getSource().sendFeedback(
														() -> Text.literal("Your constitution is " + getPlrStats(context).getConstitution()),
														false
												);
												return 1;
											}))
							)
				);
		});
	}
}