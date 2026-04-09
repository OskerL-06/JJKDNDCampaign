package net.kaupenjoe.tutorialmod.JJKSystem;

import net.kaupenjoe.tutorialmod.util.Context.CursedTechniqueContext;


public abstract class CursedTechnique {
    int baseCE;
    CTs CTEnum;


    public abstract void onUse(CursedTechniqueContext context);

    public abstract void activate(CursedTechniqueContext context);

}
