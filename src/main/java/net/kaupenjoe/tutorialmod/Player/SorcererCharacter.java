package net.kaupenjoe.tutorialmod.Player;

public class SorcererCharacter extends DNDCharacter{
    CursedEnergy cursedEnergy;
    public SorcererCharacter() {
        stats = new Stats(10,10,10,10,10,10,1);
        cursedEnergy = new CursedEnergy(100,100,20,.5);
    }


    public CursedEnergy getCursedEnergy() {
        return cursedEnergy;
    }
}
