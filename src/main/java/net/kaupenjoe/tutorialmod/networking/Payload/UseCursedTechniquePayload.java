package net.kaupenjoe.tutorialmod.networking.Payload;

import net.kaupenjoe.tutorialmod.JJKSystem.CTs;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UseCursedTechniquePayload(CTs CursedTechnique) implements CustomPayload {
    public static final Id<UseCursedTechniquePayload> ID = new Id<>(Identifier.of(TutorialMod.MOD_ID, "use_cursed_technique"));


    public static final PacketCodec<PacketByteBuf,UseCursedTechniquePayload> CODEC =
            PacketCodec.of(
            ((payload, buf) -> {
                buf.writeEnumConstant(payload.CursedTechnique);
            }),
              buf -> new UseCursedTechniquePayload(buf.readEnumConstant(CTs.class))
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}