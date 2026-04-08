package net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kaupenjoe.tutorialmod.ClickableItem;
import net.kaupenjoe.tutorialmod.JJKSystem.CTs;
import net.kaupenjoe.tutorialmod.JJKSystem.WeaponCreationCT.Panel;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.networking.Payload.GiveWeaponPayload;
import net.kaupenjoe.tutorialmod.networking.Payload.UseCursedTechniquePayload;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModdedScreen extends Screen {
    public static final Identifier GIVE_WEAPON = Identifier.of(TutorialMod.MOD_ID,"give_weapon");
    public static final List<ClickableItem> sins = new ArrayList<>();
    public Panel panel;




    public ModdedScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {

        int panelWidth = 200;
        int panelHeight = 100;
        panel = new Panel(panelWidth,panelHeight,this);

        int size = 16;


        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        panel.updatePanel(this);
        this.renderInGameBackground(context);

        int panelY = (this.height-panel.height)/2;
        context.fill(panel.x,panel.y,panel.x+panel.width,panel.y+panel.height,0xAA000000);
        context.drawText(
                textRenderer,
                "Deadly Sin Use",
                (panel.width/2)+panel.x-30,
                panel.y-10,
                0xFFFFFF,
                false
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        ClientPlayNetworking.send(new UseCursedTechniquePayload(CTs.Seven_Deadly_Sins));

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        super.close();
    }
}
