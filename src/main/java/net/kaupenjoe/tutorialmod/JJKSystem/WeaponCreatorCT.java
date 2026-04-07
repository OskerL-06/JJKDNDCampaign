package net.kaupenjoe.tutorialmod.JJKSystem;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.kaupenjoe.tutorialmod.util.CursedTechniqueContext;
import net.kaupenjoe.tutorialmod.util.UseCursedTechniquePayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class WeaponCreatorCT extends CursedTechnique{
    CTs CTEnum = CTs.Weapon_Creation;

    @Override
    public void onUse(CursedTechniqueContext context) {
        ServerPlayerEntity player = context.getPlayer();

    }

    @Override
    public void activate(CursedTechniqueContext context) {
        ServerPlayNetworking.send(context.getPlayer(), new UseCursedTechniquePayload());
    }
}
