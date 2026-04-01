package net.kaupenjoe.tutorialmod.Player;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class Stats {
    private int strength,dexterity,constitution,level,MaxHP;

    public Stats(int strength, int dexterity, int constitution, int level) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.level = level;
        initializeMaxHp();
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

    public int calculateMaxHp() {
        return 10 + constitution; // base HP only
    }

    public void initializeMaxHp() {
        int roll = (int)(Math.random() * 8) + 1; // d8
        MaxHP = calculateMaxHp() + roll;
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

    public void applyMaxHP(ServerPlayerEntity player, int hp){
        var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if( attr!=null){
            attr.setBaseValue(hp);
        }
    }
    public void levelUp(CommandContext<ServerCommandSource> context) {
        int roll = (int)(Math.random() * 8) + 1;
        int hpGain = roll + constitution;

        addMaxHP(hpGain);
        addLevel(1);

        ServerPlayerEntity player = context.getSource().getPlayer();
        assert player != null;
        applyMaxHP(player, MaxHP);
    }

}
