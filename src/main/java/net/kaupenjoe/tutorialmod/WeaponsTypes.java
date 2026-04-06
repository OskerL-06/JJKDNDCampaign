package net.kaupenjoe.tutorialmod;

import net.kaupenjoe.tutorialmod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum WeaponsTypes {
    CURSED_SWORD(ModItems.CREATED_CURSED_SWORD),
    DIAMOND_SWORD(Items.DIAMOND_SWORD)
    ;
    private final Item weapon;

    WeaponsTypes(Item weapon){
        this.weapon = weapon;
    }

    public Item getWeapon(){
        return weapon;
    }

    public static int getCECost(WeaponsTypes weapon){
        return switch (weapon){
            case CURSED_SWORD -> 5;
            case DIAMOND_SWORD -> 0;
        };
    }
}
