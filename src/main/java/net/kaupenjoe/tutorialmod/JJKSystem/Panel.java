package net.kaupenjoe.tutorialmod.JJKSystem;

import net.minecraft.client.gui.screen.Screen;

public class Panel {
    int x,y,width, height;
    int padding = 10;
    int columns = 4;
    int spacing = 20;
    int totalWidth = columns*spacing;

    public Panel(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Panel(int width, int height, Screen moddedScreen) {
        this.width = width;
        this.height = height;
        x = (moddedScreen.width-width)/2;
        y = (moddedScreen.height-height)/2;
    }

    void updatePanel(Screen moddedScreen){
        int checkX = (moddedScreen.width-width)/2;
        int checkY = (moddedScreen.height-height)/2;
        if(x!=checkX||y!=checkY){
            x = checkX;
            y = checkY;
        }
    }


}
