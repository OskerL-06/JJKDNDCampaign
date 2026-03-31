package net.kaupenjoe.tutorialmod.Player;

public class Stats {
    private int strength,dexterity,constitution,level,MaxHP;

    public Stats(int strength, int dexterity, int constitution, int level) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.level = level;
    }

    public Stats(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getLevel() {
        return level;
    }

    public int intalizeMaxHp(){
        return MaxHP +((int)(Math.random()*8)+1+constitution+10);
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void addMaxHP(int amount) {
        this.MaxHP+=amount;
    }


    public void setLevel(int level) {
        this.level = level;
    }
    public void addLevel(int amount) {
        this.level+=amount;
    }
    public void levelUp() {

        int roll = (int) (Math.random()*8)+1;
        int total = roll+constitution;

        addMaxHP(total);
        addLevel(1);
    }
}
