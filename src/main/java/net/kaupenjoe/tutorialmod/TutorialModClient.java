package net.kaupenjoe.tutorialmod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kaupenjoe.tutorialmod.JJKSystem.CTs;
import net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java.SDSModdedScreen;
import net.kaupenjoe.tutorialmod.networking.Payload.UseCursedTechniquePayload;
import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TutorialModClient implements ClientModInitializer {

    public static void openScreen(){
        MinecraftClient.getInstance().setScreen(new SDSModdedScreen(Text.of("Weapon")));

        System.out.println("Got to the Open Screen Function");

    }
    public static void useCT(){
        ClientPlayNetworking.send(new UseCursedTechniquePayload(CTs.Seven_Deadly_Sins));
    }
    @Override
    public void onInitializeClient() {
        KeyBinding openScreenKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.dndjjk.openscreen", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K,"category.dndjjk"
        ));
        KeyBinding useCursedTechniqueKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.dndjjk.usecursedtechnique", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,"category.dndjjk"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openScreenKey.wasPressed()){
                openScreen();
            }
            while (useCursedTechniqueKey.wasPressed()){
                useCT();
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(UseCursedTechniquePayload.ID,(payload, context) -> {
            openScreen();
        });
    }
}
