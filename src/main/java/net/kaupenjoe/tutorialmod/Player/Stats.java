package net.kaupenjoe.tutorialmod.Player;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.kaupenjoe.tutorialmod.DnDSystem.Dice;
import net.minecraft.text.Text;

public class Stats {
    private int
            strength,
            dexterity,
            constitution,
    critThreshold,
    currentCursedEnergy,
    maxCursedEnergy,
    level,MaxHP,AC;

    public Stats(int strength, int dexterity, int constitution, int level) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.level = level;
        initializeMaxHp();
        initializeAC();
        currentCursedEnergy = 100;
        maxCursedEnergy = 100;
    }

    public int getCurrentCursedEnergy() {
        return currentCursedEnergy;
    }

    public void setCurrentCursedEnergy(int currentCursedEnergy) {
        this.currentCursedEnergy = currentCursedEnergy;
    }

    public int getMaxCursedEnergy() {
        return maxCursedEnergy;
    }

    public void setMaxCursedEnergy(int maxCursedEnergy) {
        this.maxCursedEnergy = maxCursedEnergy;
    }

    public Stats(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public int getCritThreshold() {
        return critThreshold;
    }

    public void setCritThreshold(int critThreshold) {
        this.critThreshold = critThreshold;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        int difference = constitution - this.constitution;
        this.constitution = constitution;

        if (difference != 0) {
            MaxHP+=level*difference;
        }
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

    /**
     * Should be used for base HP only!
     */
    public int calculateMaxHp() {
        return 10 + getModifier(StatType.CONSTITUTION);
    }

    public int calculateAC() {
        return 10 + getModifier(StatType.DEXTERITY);
    }

    public int getAC() {
        return AC;
    }

    public void initializeMaxHp() {
        int roll = (int)(Math.random() * 8) + 1;
        MaxHP = calculateMaxHp() + roll;
    }

    public void initializeAC() {
        AC = calculateAC();
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void addMaxHP(int amount) {
        this.MaxHP+=amount;
    }
    public int addMaxHP() {
        int roll = Math.max(Dice.rollD8(getModifier(StatType.CONSTITUTION)),1);
        this.MaxHP+=roll;
        return roll;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void addLevel(int amount) {
        this.level+=amount;
    }

    public void applyMaxHP(ServerPlayerEntity player, int hp){
        var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if( attr!=null){
            attr.setBaseValue(hp);
        }
    }
    public void levelUp(CommandContext<ServerCommandSource> context) {
        int roll = addMaxHP();
        context.getSource().sendFeedback(() -> Text.literal("Your HP is added by: "+roll+" Your new HP is "+MaxHP),false);
        addLevel(1);

        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        applyMaxHP(player, MaxHP);
    }

    public int getModifier(StatType stat){
        int value = switch (stat){
            case STRENGTH -> strength;
            case CONSTITUTION -> constitution;
            case DEXTERITY -> dexterity;
            default -> 0;
        };
        return (int) Math.floor((value-10.0)/2.0);
    }
    public int getStat(StatType stat){
        return switch (stat){
            case STRENGTH -> strength;
            case CONSTITUTION -> constitution;
            case DEXTERITY -> dexterity;
            case LEVEL ->  level;

            default -> 0;
        };
    }
    public void setStat(StatType stat,int amount){
        switch (stat){
            case STRENGTH -> strength = amount;
            case CONSTITUTION -> constitution = amount;
            case DEXTERITY -> dexterity = amount;
            case LEVEL -> level = amount;
        };
    }
    public enum StatType{
        STRENGTH,
        DEXTERITY,
        CONSTITUTION,
        LEVEL,
        ;
    }

}
