package net.kaupenjoe.tutorialmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

//import net.kaupenjoe.tutorialmod.items.ModItems;
import net.kaupenjoe.tutorialmod.Player.Stats;
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

		switch (stat.toLowerCase()){
			case "strength":
				plrStats.setStrength(amount);
				break;
		}
	}
		@Override
	public void onInitialize() {
		System.out.println("MY MOD IS RUNNING");
//		ModItems.registerModItems();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			System.out.println("REGISTERING COMMAND");

			dispatcher.register(CommandManager.literal("roll")
				.then(CommandManager.literal("attack")
					.then(CommandManager.argument("modifier", IntegerArgumentType.integer(-10,20))
						.executes(context -> {
							Stats plrStats = getPlrStats(context);
							int strMod = plrStats.getStrength();
							int roll = (int)(Math.random() * 20)+1;
							int total = roll + strMod;
							context.getSource().sendFeedback(
									() -> Text.literal("Attack Roll: " + roll + " + " + strMod + " = " + total),
									false
							);
							return 1;
						})
					)
					.executes(context -> {
						int roll = (int)(Math.random() * 20) + 1;

						context.getSource().sendFeedback(
								() -> Text.literal("Attack Roll: " + roll),
								false
						);

						return 1;
					})
				)
				.then(CommandManager.literal("skill")
					.then(CommandManager.argument("modifier", IntegerArgumentType.integer())
						.executes(context -> {
							int roll = (int)(Math.random() * 20) + 1;
							int modifier = IntegerArgumentType.getInteger(context, "modifier");
							int total = roll + modifier;

							context.getSource().sendFeedback(
									() -> Text.literal("Skill Check: " + roll + " + " + modifier + " = " + total),
									false
							);

							return 1;
						})
					)
					.executes(context -> {
						int roll = (int)(Math.random() * 20) + 1;

						context.getSource().sendFeedback(
							() -> Text.literal("Skill Check: " + roll),
							false
						);

						return 1;
					})
				)
			);
			dispatcher.register(CommandManager.literal("stat")
				.then(CommandManager.argument("statName", StringArgumentType.string())
					.then(CommandManager.argument("newAmount", IntegerArgumentType.integer())
						.executes(context -> {
							String statToChange = StringArgumentType.getString(context,"statName").toLowerCase();
							int amountTo = IntegerArgumentType.getInteger(context,"newAmount");
							Stats plrStats = getPlrStats(context);


							return  1;
						})
					)
				)
			);
		});
	}
}