package net.kaupenjoe.tutorialmod.JJKSystem.WeaponCreationCT;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.tutorialmod.JJKSystem.CTs;
import net.kaupenjoe.tutorialmod.JJKSystem.CursedTechnique;
import net.kaupenjoe.tutorialmod.util.Context.CursedTechniqueContext;
import net.kaupenjoe.tutorialmod.networking.Payload.UseCursedTechniquePayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class WeaponCreatorCT extends CursedTechnique {
    CTs CTEnum = CTs.Weapon_Creation;

    @Override
    public void onUse(CursedTechniqueContext context) {
        ServerPlayerEntity player = context.getPlayer();

    }

    @Override
    public void activate(CursedTechniqueContext context) {
        ServerPlayNetworking.send(context.getPlayer(), new UseCursedTechniquePayload(CTEnum));
    }
}
