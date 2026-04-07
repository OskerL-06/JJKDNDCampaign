package net.kaupenjoe.tutorialmod.networking.Payload;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GiveWeaponPayload(WeaponsTypes weapon) implements CustomPayload {

    public static final Id<GiveWeaponPayload> ID = new Id<>(Identifier.of(TutorialMod.MOD_ID,"give_weapon"));

    public static final PacketCodec<PacketByteBuf,GiveWeaponPayload> CODEC =
    PacketCodec.of(
            ((payload,buf) -> buf.writeEnumConstant(payload.weapon())),
            buf -> new GiveWeaponPayload(buf.readEnumConstant(WeaponsTypes.class))

    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
