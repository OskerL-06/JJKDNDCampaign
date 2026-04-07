package net.kaupenjoe.tutorialmod.JJKSystem;

import net.kaupenjoe.tutorialmod.util.CursedTechniqueContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;



public abstract class CursedTechnique {
    int baseCE;
    CTs CTEnum;

    public abstract void onUse(CursedTechniqueContext context);

    public abstract void activate(CursedTechniqueContext context);

}
