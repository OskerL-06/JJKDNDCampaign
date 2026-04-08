package net.kaupenjoe.tutorialmod.JJKSystem;

import net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java.SevenDeadlySins;
import net.kaupenjoe.tutorialmod.JJKSystem.WeaponCreationCT.WeaponCreatorCT;

public enum CTs {
    Weapon_Creation,
    Seven_Deadly_Sins,
    ;

    public CursedTechnique getCT(CTs ct){
        return switch (ct){
            case Weapon_Creation -> new WeaponCreatorCT();
            case Seven_Deadly_Sins -> new SevenDeadlySins();
        };
    }
}
