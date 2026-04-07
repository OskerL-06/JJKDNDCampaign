package net.kaupenjoe.tutorialmod.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class CursedTechniqueContext {
    private final ServerPlayerEntity player;
    private final MinecraftServer server;
    public CursedTechniqueContext(
            ServerPlayerEntity player,
            MinecraftServer server){
        this.player = player;
        this.server = server;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public MinecraftServer getServer() {
        return server;
    }


}
