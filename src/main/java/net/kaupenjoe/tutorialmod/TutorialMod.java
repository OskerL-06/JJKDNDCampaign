package net.kaupenjoe.tutorialmod;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.tutorialmod.DnDSystem.Dice;
import net.kaupenjoe.tutorialmod.DnDSystem.Weapon;
import net.kaupenjoe.tutorialmod.DnDSystem.Weapons;
import net.kaupenjoe.tutorialmod.Player.DNDCharacter;
import net.kaupenjoe.tutorialmod.Player.SorcererCharacter;
import net.kaupenjoe.tutorialmod.Player.Stats;
import net.kaupenjoe.tutorialmod.item.ModItemGroups;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.kaupenjoe.tutorialmod.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;


public class TutorialMod implements ModInitializer {
	public static final Map<UUID, DNDCharacter> PLAYER_CHARACTERS = new HashMap<>();

	public static final Map<UUID, SorcererCharacter> SORCERER_CHARACTER = new HashMap<>();
	public static final Map<EntityType,Integer> ENTITY_AC = new HashMap<>();
	public static Map<ActionTypes, Consumer<ActionContext>> actions = new HashMap<>();
//	public static final Identifier USECT = Identifier.of(TutorialMod.MOD_ID,"useCT");

	public static final String MOD_ID = "dndjjk";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static Stats getPlrStats(CommandContext<ServerCommandSource> context){
		UUID uuid = Objects.requireNonNull(context.getSource().getPlayer()).getUuid();
		DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
		if(plrCharacter == null){
			plrCharacter = new DNDCharacter();
			applyMaxHP(context.getSource().getPlayer(), plrCharacter.getStats().getMaxHP());
			PLAYER_CHARACTERS.put(uuid,plrCharacter);
		}
        return plrCharacter.getStats();

	}


	public static Stats getPlrStats(PlayerEntity player){
		UUID uuid = Objects.requireNonNull(player).getUuid();
		DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
		if(plrCharacter == null){
			plrCharacter = new DNDCharacter();
			applyMaxHP(player, plrCharacter.getStats().getMaxHP());
			PLAYER_CHARACTERS.put(uuid,plrCharacter);
		}
		return plrCharacter.getStats();
	}

	public static Stats getPlrStats(ServerCommandSource context){
		UUID uuid = Objects.requireNonNull(context.getPlayer()).getUuid();
		DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
		if(plrCharacter == null){
			plrCharacter = new DNDCharacter();
			applyMaxHP(context.getPlayer(), plrCharacter.getStats().getMaxHP());
			PLAYER_CHARACTERS.put(uuid,plrCharacter);
		}
		return plrCharacter.getStats();
	}
	public static DNDCharacter getPlrCharacter(ServerCommandSource context){
		UUID uuid = Objects.requireNonNull(context.getPlayer()).getUuid();
		DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
		if(plrCharacter == null){
			plrCharacter = new DNDCharacter();
			applyMaxHP(context.getPlayer(), plrCharacter.getStats().getMaxHP());
			PLAYER_CHARACTERS.put(uuid,plrCharacter);
		}
		return plrCharacter;
	}

	public static DNDCharacter getPlrCharacter(PlayerEntity player){
		UUID uuid = Objects.requireNonNull(player).getUuid();
		DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
		if(plrCharacter == null){
			plrCharacter = new DNDCharacter();
			applyMaxHP(player, plrCharacter.getStats().getMaxHP());
			PLAYER_CHARACTERS.put(uuid,plrCharacter);
		}
		return plrCharacter;
	}

	public static int getStat(CommandContext<ServerCommandSource> context, String stat) {
		Stats plrStats = getPlrStats(context);

        return switch (stat) {
            case "strength" -> plrStats.getStrength();
            case "dexterity" -> 5;
            default -> 0;
        };
	}

	public static int getStat(CommandContext<ServerCommandSource> context, Stats.StatType stat) {
		Stats plrStats = getPlrStats(context);

		return  plrStats.getStat(stat);
	}

	public static void setStat(CommandContext<ServerCommandSource> context, Stats.StatType stat,int amount) {
		Stats plrStats = getPlrStats(context);

		plrStats.setStat(stat,amount);
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

	public static int doRoll(ServerCommandSource context, String modifier, String label){
		int baseRoll = (int)(Math.random() * 20) + 1;
		Stats plrStats = getPlrStats(context);

		int mod = switch (modifier){
			case "strength" -> plrStats.getStrength();
			case "dexterity" -> 5;
			default -> throw new IllegalStateException("Unexpected value: " + modifier);
		};

		int total = baseRoll + mod;
		context.sendFeedback(
				() -> Text.literal(label + " " + baseRoll+ " + "+ mod + " = "+ total),
				false
		);

		return total;
	}


	public static int doRoll(PlayerEntity player, Stats.StatType modifier, String label){
		int baseRoll = (int)(Math.random() * 20) + 1;
		Stats plrStats = getPlrStats(player);

		int mod = plrStats.getModifier(modifier);

		int total = baseRoll + mod;
		player.sendMessage(
				Text.literal(label + " " + baseRoll+ " + "+ mod + " = "+ total),
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
	public static void applyMaxHP(PlayerEntity player, int hp){
		var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if( attr!=null){
			attr.setBaseValue(hp);
		}
	}

	public static boolean isFalling(PlayerEntity player){
		return (!player.isOnGround()&& player.fallDistance > 0);
	}
	public static ActionResult attack(PlayerEntity player, Entity target, World world){

		if(!(target instanceof LivingEntity)) return ActionResult.PASS;

		Stats plrStats = getPlrStats(player);
		DNDCharacter plrCharacter = getPlrCharacter(player);
		Weapon currentWeapon = plrCharacter.getEquippedWeapon();
		return currentWeapon.attack(plrCharacter,player,(LivingEntity) target);

	}





	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(
				ActionPayload.ID,
				ActionPayload.CODEC
		);
		PayloadTypeRegistry.playC2S().register(
				GiveWeaponPayload.ID,
				GiveWeaponPayload.CODEC
		);

		PayloadTypeRegistry.playC2S().register(
				UseCursedTechniquePayload.ID,
				UseCursedTechniquePayload.CODEC
		);


		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ActionManager.registerActions(actions);

		ENTITY_AC.put(EntityType.PIG,5);
		ENTITY_AC.put(EntityType.PIGLIN,10);
		ENTITY_AC.put(EntityType.PIGLIN_BRUTE,13);


		ServerPlayNetworking.registerGlobalReceiver(ActionPayload.ID,
				(payload,context)->{
					ServerPlayerEntity player = context.player();

					System.out.println("Action Payload");
					context.server().execute(() -> {

						System.out.println("Got to the Server Execute");
//						ActionContext ctx = new ActionContext(player,context.server(),payload.data());
//
//						Consumer<ActionContext> action = actions.get(payload.action());
//
//						if(action!= null){
//							action.accept(ctx);
//							player.closeHandledScreen();
//						}
					});
				});
		ServerPlayNetworking.registerGlobalReceiver(GiveWeaponPayload.ID,
				(payload,context)->{
					ServerPlayerEntity player = context.player();

					System.out.println("Give Weapon Payload");
					context.server().execute(() -> {

						System.out.println("Got to the Server Execute");
						GiveWeaponContext ctx = new GiveWeaponContextBuilder(player,context.server(),payload.weapon()).build();
						System.out.println("Got Berfore the Give Weapon function");
//						Consumer<ActionContext> action = actions.get(payload.action());

						System.out.println("Got to the Give Weapon Function");

						WeaponsTypes weapon = ctx.getWeapon();
						ItemStack itemStack = new ItemStack(weapon.getWeapon());
						player.giveItemStack(itemStack);
						player.closeHandledScreen();
//						GiveWeaponPayload;
					});
				});
		ServerPlayNetworking.registerGlobalReceiver(UseCursedTechniquePayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				System.out.println("Received payload from: " + context.player().getName().getString());
			});
		});
		AttackEntityCallback.EVENT.register(
				(player, world, hand, entity, hitResult)
						-> attack(player,entity,world)

				);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("modifystat")

			);
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
										()->Text.literal("You hit Osker dealing "+ (roll+2)+ " damage"),
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
						context.getSource().sendFeedback(
								()-> Text.literal("You got "+roll),false);
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
								setStat(context, Stats.StatType.STRENGTH,Amount);
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
										() -> Text.literal("Your strength is " + getStat(context, Stats.StatType.STRENGTH)),
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

									setStat(context, Stats.StatType.CONSTITUTION,Amount);
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
														() -> Text.literal("Your constitution is " + getStat(context, Stats.StatType.CONSTITUTION)),
														false
												);
												return 1;
											}))

				).then(CommandManager.literal("level")
						.then(CommandManager.literal("set")

								.then(CommandManager.argument("amount",IntegerArgumentType.integer(0,20))
										.executes(context -> {
											int Amount = IntegerArgumentType.getInteger(context,"amount");

											setStat(context, Stats.StatType.LEVEL,Amount);
											context.getSource().sendFeedback(
													() -> Text.literal("You have set your Level to " + Amount ),
													false
											);
											return 1;
										}))
						)
						.then(CommandManager.literal("get")
								.executes(context -> {
									context.getSource().sendFeedback(
											() -> Text.literal("Your Level is " + getStat(context, Stats.StatType.LEVEL)),
											false
									);
									return 1;
								}))

				)
		);
		});
	}
}