package net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java;

import net.kaupenjoe.tutorialmod.JJKSystem.CTs;
import net.kaupenjoe.tutorialmod.JJKSystem.CursedTechnique;
import net.kaupenjoe.tutorialmod.util.Context.CursedTechniqueContext;

public class SevenDeadlySins extends CursedTechnique {
    CTs CTEnum = CTs.Seven_Deadly_Sins;

    @Override
    public void onUse(CursedTechniqueContext context) {

    }

    @Override
    public void activate(CursedTechniqueContext context) {
        PrideEffect.activate(context.getPlayer());
    }
}
