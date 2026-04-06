package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record UseCursedTechniquePayload(UUID plrUUID) implements CustomPayload {

    public static final Id<UseCursedTechniquePayload> ID = new Id<>(Identifier.of(TutorialMod.MOD_ID,"useCT"));

    public static final PacketCodec<PacketByteBuf, UseCursedTechniquePayload> CODEC =
    PacketCodec.of(
            ((payload,buf) -> buf.writeUuid(payload.plrUUID)),
            buf -> new UseCursedTechniquePayload(buf.readUuid())

    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
