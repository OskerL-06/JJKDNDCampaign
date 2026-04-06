package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class GiveWeaponContext extends ActionContext {
    private final WeaponsTypes weapon;

    public GiveWeaponContext(
            ServerPlayerEntity player,
            MinecraftServer server,WeaponsTypes weapon,String data){
        super(player,server,data);
        this.weapon = weapon;
    }

    public WeaponsTypes getWeapon() {
        return weapon;
    }

}
