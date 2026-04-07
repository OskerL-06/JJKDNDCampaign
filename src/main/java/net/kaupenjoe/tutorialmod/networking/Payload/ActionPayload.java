package net.kaupenjoe.tutorialmod.networking.Payload;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.kaupenjoe.tutorialmod.util.ActionContext.ActionTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ActionPayload(ActionTypes action, WeaponsTypes weapon) implements CustomPayload {

    public static final Id<ActionPayload> ID =
            new Id<>(Identifier.of(TutorialMod.MOD_ID,"action"));

    public static final PacketCodec<PacketByteBuf, ActionPayload> CODEC =
    PacketCodec.of(
            ((payload,buf) -> {
                buf.writeEnumConstant(payload.action);
                buf.writeEnumConstant(payload.weapon);
            }),
            buf -> new ActionPayload(buf.readEnumConstant(ActionTypes.class),buf.readEnumConstant(WeaponsTypes.class))

    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
