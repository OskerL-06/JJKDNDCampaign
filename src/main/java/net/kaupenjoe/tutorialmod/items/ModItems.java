//package net.kaupenjoe.tutorialmod.items;
//
//import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//import net.kaupenjoe.tutorialmod.TutorialMod;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemGroups;
//import net.minecraft.registry.Registry;
//
//import net.minecraft.registry.Registries;
//import net.minecraft.util.Identifier;
//
//public class ModItems {
//    public static final Item CREATED_CURSED_GUN = registerItem("Created_Cursed_Gun", new Item(new Item.Settings()));
//
//    public static Item registerItem(String name, Item item){
//        return Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID,name), item);
//    }
//
//    public static void registerModItems() {
//        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);
//
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
//            entries.add(CREATED_CURSED_GUN);
//        });
//    }
//}
