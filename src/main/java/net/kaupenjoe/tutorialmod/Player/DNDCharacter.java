package net.kaupenjoe.tutorialmod.Player;

import net.kaupenjoe.tutorialmod.DnDSystem.Weapon;
import net.kaupenjoe.tutorialmod.DnDSystem.Weapons;

public class DNDCharacter {
    Weapon equippedWeapon = Weapons.FIST;
    Stats stats;
    public DNDCharacter(){
        this.stats= new Stats(5,5,5,1);
    }

    public Stats getStats() {
        return stats;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
}
