package net.kaupenjoe.tutorialmod.JJKSystem;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kaupenjoe.tutorialmod.ClickableItem;
import net.kaupenjoe.tutorialmod.JJKSystem.Panel;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.kaupenjoe.tutorialmod.util.GiveWeaponPayload;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class WeaponCreationScreen extends Screen {
    public static final Identifier GIVE_WEAPON = Identifier.of(TutorialMod.MOD_ID,"give_weapon");
    public static final List<ClickableItem> items = new ArrayList<>();
    public static final List<WeaponsTypes> weapons = List.of(
            WeaponsTypes.CURSED_SWORD,
            WeaponsTypes.DIAMOND_SWORD
     );
    public Panel panel;




    protected WeaponCreationScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        items.clear();

        int panelWidth = 200;
        int panelHeight = 100;
        int panelX = (this.width-panelWidth)/2;
        int panelY = (this.height-panelHeight)/2;
        panel = new Panel(panelWidth,panelHeight,this);

        System.out.println("Init Function: "+panelX+" : "+panelWidth);
        int size = 16;

        for (WeaponsTypes weapon : weapons) {
            System.out.println("Added an Item: " + weapon.name());
            items.add(new ClickableItem(
                    new ItemStack(weapon.getWeapon()),
                    weapon,
                    size
            ));
        }
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
                "Weapon Creator",
                (panel.width/2)+panel.x-30,
                panel.y-10,
                0xFFFFFF,
                false
        );

        for(int i = 0; i<weapons.size();i++){
            ClickableItem item = items.get(i);

            int x = (panel.padding+panel.x)+((i%panel.columns)* (item.size+ panel.spacing));
            int y = (panel.padding+panel.y)+((i/ panel.columns)* (item.size+ panel.spacing));

            context.drawItem(item.stack,x,y);
            if(item.isOver(mouseX,mouseY,x,y)){
                context.fill(x,y,x+item.size,y+item.size,0x80FFFFFF);
                context.drawTooltip(
                        textRenderer,
                        item.stack.getName(),
                        mouseX,
                        mouseY
                );
                context.drawBorder(x,y,item.size,item.size, Colors.GREEN);
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for(int i = 0; i<weapons.size();i++){
            ClickableItem item = items.get(i);
            int x = (panel.padding+panel.x)+((i%panel.columns)* panel.spacing);
            int y = (panel.padding+panel.y)+((i/ panel.columns)* panel.spacing);
            if(item.isOver(mouseX,mouseY,x,y)){
                ClientPlayNetworking.send(new GiveWeaponPayload(item.weapon));

                return true;
            }
        }
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
