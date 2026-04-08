package net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java;

import net.kaupenjoe.tutorialmod.WeaponsTypes;
import net.minecraft.item.ItemStack;

public class ClickableGUI {

    public final int size;
    public ClickableGUI( int size) {
        this.size = size;
    }

    public boolean isOver(double mouseX, double mouseY,int x,int y){
        return (mouseX >= x && mouseX <= x+size)&&
                (mouseY >= y && mouseY <= y+size);

    }

    public void onClick(){
    }

}
