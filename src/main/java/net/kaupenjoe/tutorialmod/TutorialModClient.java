package net.kaupenjoe.tutorialmod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.ClientModInitializer;
import net.kaupenjoe.tutorialmod.Player.Stats;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TutorialModClient implements ClientModInitializer {

    public static void openScreen(){
        MinecraftClient.getInstance().setScreen(new ModdedScreen(Text.of("Weapon")));

        System.out.println("Got to the Open Screen Function");

    }

    @Override
    public void onInitializeClient() {
        KeyBinding openScreenKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.dndjjk.openscreen", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K,"category.dndjjk"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openScreenKey.wasPressed()){
                openScreen();
            }
        });
    }
}
