package net.kaupenjoe.tutorialmod.DnDSystem;

import net.kaupenjoe.tutorialmod.Player.DNDCharacter;
import net.kaupenjoe.tutorialmod.Player.Stats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import static net.kaupenjoe.tutorialmod.TutorialMod.ENTITY_AC;
import static net.kaupenjoe.tutorialmod.TutorialMod.PLAYER_CHARACTERS;

public class Weapon {
    String name;
    int diceSize;
    int diceCount;
    public Weapon(String name,int diceSize,int diceCount){
        this.name = name;
        this.diceSize = diceSize;
        this.diceCount = diceCount;
    }

    public static boolean isFalling(PlayerEntity player){
        return (!player.isOnGround()&& player.fallDistance > 0);
    }
    public ActionResult attack(DNDCharacter plrCharacter, PlayerEntity player, LivingEntity target){
        Stats plrStats = plrCharacter.getStats();
        int strMod = plrStats.getModifier(Stats.StatType.STRENGTH);
        int roll = Dice.rollD20(strMod);

        int targetAC = (target instanceof PlayerEntity)?PLAYER_CHARACTERS.computeIfAbsent(target.getUuid(),k ->new DNDCharacter()).getStats().getAC():ENTITY_AC.getOrDefault(target.getType(),10);
        player.sendMessage(Text.literal("Attempting to attack "+(target.getName().getString())+". Their AC is "+targetAC),false);

        player.sendMessage(Text.literal("You got "+roll),false);
        boolean crit = roll>=plrStats.getCritThreshold();
        if(roll>=targetAC){
            if(crit){
                if(isFalling(player)){
                    int dmg = Dice.roll(diceCount+1,diceSize+2)+strMod;
                    player.sendMessage(
                            Text.literal("You critically struck "+target.getName().getString()+" dealing "+ (dmg)+ " damage"),
                            false
                    );
                    target.damage(player.getDamageSources().playerAttack(player),(float)dmg);
                }else{

                    int dmg = Dice.roll(diceCount+1 ,diceSize)+strMod;
                    player.sendMessage(
                            Text.literal("You critically struck "+target.getName().getString()+" dealing "+ (dmg)+ " damage"),
                            false
                    );
                    target.damage(player.getDamageSources().playerAttack(player),(float)dmg);
                }
            }else{
                if(isFalling(player)){
                    int dmg = Dice.roll(diceCount,diceSize+2)+strMod;
                    player.sendMessage(
                            Text.literal("You hit "+target.getName().getString()+" dealing "+ (dmg)+ " damage"),
                            false
                    );
                    target.damage(player.getDamageSources().playerAttack(player),(float)dmg);
                }else{
                    int dmg = Dice.roll(diceCount,diceSize)+strMod;
                    player.sendMessage(
                            Text.literal("You hit "+target.getName().getString()+" dealing "+ (dmg)+ " damage"),
                            false
                    );
                    target.damage(player.getDamageSources().playerAttack(player),(float)dmg);
                }
            }
            return ActionResult.SUCCESS;
        }else{
            player.sendMessage(
                    Text.literal("You miss"),
                    false
            );
            return ActionResult.FAIL;
        }

    }
}
