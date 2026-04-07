package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UseCursedTechniquePayload() implements CustomPayload {
    public static final Id<UseCursedTechniquePayload> ID = new Id<>(Identifier.of(TutorialMod.MOD_ID, "use_cursed_technique"));

    public static final PacketCodec<RegistryByteBuf, UseCursedTechniquePayload> CODEC = PacketCodec.unit(new UseCursedTechniquePayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}