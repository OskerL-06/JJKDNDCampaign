package net.kaupenjoe.tutorialmod.Player;

import net.kaupenjoe.tutorialmod.DnDSystem.Weapon;
import net.kaupenjoe.tutorialmod.DnDSystem.Weapons;
import net.kaupenjoe.tutorialmod.JJKSystem.CursedTechnique;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

public class DNDCharacter {
    Weapon equippedWeapon = Weapons.FIST;
    Stats stats;
    CursedTechnique CT;
    public DNDCharacter(){
        this.stats= new Stats(5,5,5,1);
    }

    public Stats getStats() {
        return stats;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public void applyMaxHP(PlayerEntity player){
        var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if( attr!=null){
            attr.setBaseValue(stats.getMaxHP());
        }
    }

    public void setCT(CursedTechnique CT) {
        this.CT = CT;
    }

    public CursedTechnique getCT() {
        return CT;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
}
