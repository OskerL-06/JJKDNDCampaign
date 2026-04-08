package net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class PrideEffect {
    Sins sin;

    static void activate(PlayerEntity player){

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10 * 20, 5));
    }
}
