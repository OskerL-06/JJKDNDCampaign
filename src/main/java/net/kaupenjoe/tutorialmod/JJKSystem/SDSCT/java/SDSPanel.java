package net.kaupenjoe.tutorialmod.JJKSystem.SDSCT.java;

import net.minecraft.client.gui.screen.Screen;

public class SDSPanel {
    public int x,y,width, height;
    public int padding = 10;
    public int columns = 4;
    public int spacing = 20;
    public int totalWidth = columns*spacing;

    public SDSPanel(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public SDSPanel(int width, int height, Screen moddedScreen) {
        this.width = width;
        this.height = height;
        x = (moddedScreen.width-width)/2;
        y = (moddedScreen.height-height)/2;
    }

    public void updatePanel(Screen moddedScreen){
        int checkX = (moddedScreen.width-width)/2;
        int checkY = (moddedScreen.height-height)/2;
        if(x!=checkX||y!=checkY){
            x = checkX;
            y = checkY;
        }
    }


}
