package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class GiveWeaponContextBuilder {
    private final ServerPlayerEntity player;
    private final MinecraftServer server;
    private String data;
    private final WeaponsTypes weapon;

    public GiveWeaponContextBuilder(ServerPlayerEntity player, MinecraftServer server,WeaponsTypes weapon) {
        this.player = player;
        this.server = server;
        this.weapon = weapon;
    }

    public GiveWeaponContextBuilder addData(String data){
        this.data = data;
        return this;
    }

    public GiveWeaponContext build(){
        return new GiveWeaponContext(player,server,weapon,data);
    }
}
