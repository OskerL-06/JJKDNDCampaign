package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ActionContextBuilder {
    private final ServerPlayerEntity player;
    private final MinecraftServer server;
    private String data;
    private WeaponsTypes weapon;

    public ActionContextBuilder(ServerPlayerEntity player, MinecraftServer server) {
        this.player = player;
        this.server = server;
    }

    public ActionContextBuilder addWeapon(WeaponsTypes weaponsTypes){
        weapon = weaponsTypes;
        return this;
    }

    public ActionContext build(){
        return new ActionContext(player,server,data);
    }
}
