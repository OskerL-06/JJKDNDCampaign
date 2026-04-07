package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.Player.DNDCharacter;
import net.kaupenjoe.tutorialmod.Player.Stats;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static net.kaupenjoe.tutorialmod.TutorialMod.PLAYER_CHARACTERS;

public class ActionManager {
    public static void registerActions(Map<ActionTypes, Consumer<ActionContext>> action){
        action.put(ActionTypes.GIVE_WEAPON, context -> {
            giveWeapon((GiveWeaponContext) context);
        });
    }
    private boolean hasEnoughCE(ServerPlayerEntity player, WeaponsTypes weapon){
        DNDCharacter plrCharacter = getPlrCharacter(player);
        Stats plrStats = plrCharacter.getStats();

        return (plrStats.getCurrentCursedEnergy()>=WeaponsTypes.getCECost(weapon));
    }

    private static void giveWeapon(GiveWeaponContext  context){
//        String weapon = context.getData();
        WeaponsTypes weapon = context.getWeapon();
        switch (weapon){
            case CURSED_SWORD ->  {
                ItemStack itemStack = new ItemStack(ModItems.CREATED_CURSED_SWORD);
                context.getPlayer().giveItemStack(itemStack);
            }
        }
        context.getPlayer().closeHandledScreen();
    }
    private static Stats getPlrStats(PlayerEntity player){
        UUID uuid = Objects.requireNonNull(player).getUuid();
        DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
        if(plrCharacter == null){
            plrCharacter = new DNDCharacter();
            plrCharacter.applyMaxHP(player);
            PLAYER_CHARACTERS.put(uuid,plrCharacter);
        }
        return plrCharacter.getStats();
    }
    private static DNDCharacter getPlrCharacter(PlayerEntity player){
        UUID uuid = Objects.requireNonNull(player).getUuid();
        DNDCharacter plrCharacter = PLAYER_CHARACTERS.get(uuid);
        if(plrCharacter == null){
            plrCharacter = new DNDCharacter();
            plrCharacter.applyMaxHP(player);
            PLAYER_CHARACTERS.put(uuid,plrCharacter);
        }
        return plrCharacter;
    }


}
