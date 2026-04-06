package net.kaupenjoe.tutorialmod.util;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEEDS_WEAK_CURSED_TOOL = createTag("needs_weak_cursed_tool");
        public static final TagKey<Block> INCORRECT_FOR_CURSED_TOOL = createTag("incorrect_for_cursed_tool");

        public static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(TutorialMod.MOD_ID,name));
        }
    }
}
