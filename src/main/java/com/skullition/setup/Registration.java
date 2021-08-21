package com.skullition.setup;

import com.skullition.items.WaterBottle;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.skullition.testmod.TestMod.MODID;

public class Registration {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> WATER_BOTTLE = ITEMS.register("waterbottle", () -> new WaterBottle(new Item.Properties()
    .tab(CreativeModeTab.TAB_FOOD)
    .food(new FoodProperties.Builder().nutrition(10).alwaysEat().saturationMod(1).build())));
}
