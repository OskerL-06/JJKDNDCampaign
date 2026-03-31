package net.kaupenjoe.tutorialmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

//import net.kaupenjoe.tutorialmod.items.ModItems;
import net.kaupenjoe.tutorialmod.Player.Stats;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class TutorialMod implements ModInitializer {
	public static final Map<UUID, Stats> PLAYER_STATS = new HashMap<>();
	public static final String MOD_ID = "dndjjk";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Stats getPlrStats(CommandContext<ServerCommandSource> context){
		UUID uuid = context.getSource().getPlayer().getUuid();

        return PLAYER_STATS.computeIfAbsent(uuid, k -> new Stats(5));
	}
	public static void setStat(CommandContext<ServerCommandSource> context, String stat, int amount) {
		Stats plrStats = getPlrStats(context);

		switch (stat){
			case "strength":
				plrStats.setStrength(amount);
				break;
		}
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
		@Override
	public void onInitialize() {
//		ModItems.registerModItems();
			CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
				dispatcher.register(CommandManager.literal("roll")
					.then(CommandManager.literal("attack")
						.executes(context -> {

							int roll = doRoll(context,"strength","Attack Roll: ");

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
									setStat(context,"strength",Amount);
									context.getSource().sendFeedback(
											() -> Text.literal("You have set your strength to " + Amount ),
											false
									);
									return 1;
								}))
						)
						.then(CommandManager.literal("get")
								.executes(context -> {
									int Strength = getStat(context,"strength");
									context.getSource().sendFeedback(
											() -> Text.literal("Your strength is " + Strength),
											false
									);
									return 1;
								}))

					)
				);
		});
	}
}