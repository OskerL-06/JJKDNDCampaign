package net.kaupenjoe.tutorialmod.DnDSystem;

public class Dice {

    public static int rollD20(){
        return (int) (Math.random()*20)+1;
    }

    public static int rollD20(int modifier){
        return (int) (Math.random()*20)+1+modifier;
    }

    public static int rollD20(int modifier, boolean advantage){
        if(advantage){
            return Math.max((int) (Math.random()*20)+1+modifier,(int) (Math.random()*20)+1+modifier);
        }else {
            return Math.min((int) (Math.random()*20)+1+modifier,(int) (Math.random()*20)+1+modifier);
        }
    }

    public static int rollD6(){
        return (int) (Math.random()*6)+1;
    }

    public static int rollD6(int modifier){
        return (int) (Math.random()*6)+1+modifier;
    }

    public static int rollD6(int modifier, boolean advantage){
        if(advantage){
            return Math.max((int) (Math.random()*6)+1+modifier,(int) (Math.random()*6)+1+modifier);
        }else {
            return Math.min((int) (Math.random()*6)+1+modifier,(int) (Math.random()*6)+1+modifier);
        }
    }
    public static int rollD8(){
        return (int) (Math.random()*8)+1;
    }

    public static int rollD8(int modifier){
        return (int) (Math.random()*8)+1+modifier;
    }

    public static int rollD8(int modifier, boolean advantage){
        if(advantage){
            return Math.max((int) (Math.random()*8)+1+modifier,(int) (Math.random()*8)+1+modifier);
        }else {
            return Math.min((int) (Math.random()*8)+1+modifier,(int) (Math.random()*8)+1+modifier);
        }
    }
}
