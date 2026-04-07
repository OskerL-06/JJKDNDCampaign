package net.kaupenjoe.tutorialmod.util.ActionContext;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ActionContext{
    private final ServerPlayerEntity player;
    private final MinecraftServer server;
    private String data;

    public ActionContext(
            ServerPlayerEntity player,
            MinecraftServer server,
            String data){
        this.player = player;
        this.server = server;
        this.data = data;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public String getData() {
        return data;
    }


}
