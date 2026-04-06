package net.kaupenjoe.tutorialmod;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class ClickableItem{

    public final ItemStack stack;
    public final  WeaponsTypes weapon;
    public final int size;
    public ClickableItem(ItemStack stack, WeaponsTypes weapon, int size) {
        this.stack = stack;
        this.weapon = weapon;
        this.size = size;
    }

    public boolean isOver(double mouseX, double mouseY,int x,int y){
        return (mouseX >= x && mouseX <= x+size)&&
                (mouseY >= y && mouseY <= y+size);

    }

}
