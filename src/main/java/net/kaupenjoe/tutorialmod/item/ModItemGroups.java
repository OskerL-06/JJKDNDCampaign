package net.kaupenjoe.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CURSED_TOOLS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TutorialMod.MOD_ID,"cursed_tools"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CREATED_CURSED_SWORD))
                    .displayName(Text.translatable("itemgroup.dndjjk.cursed_tools"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CREATED_CURSED_SWORD);
                        entries.add(ModItems.CREATED_CURSED_GUN);

                    }).build());
    public static final ItemGroup CURSED_ENERGY_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TutorialMod.MOD_ID,"cursed_energy"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.RAW_CURSE_ENERGY))
                    .displayName(Text.translatable("itemgroup.dndjjk.cursed_energy"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.RAW_CURSE_ENERGY);

                    }).build());

    public  static  void registerItemGroups(){
        TutorialMod.LOGGER.info("Registering Item Groups for "+TutorialMod.MOD_ID);
    }
}
