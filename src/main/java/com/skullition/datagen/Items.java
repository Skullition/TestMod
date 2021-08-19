package com.skullition.datagen;

import com.skullition.setup.Registration;
import com.skullition.testmod.TestMod;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {
    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TestMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(
                Registration.WATER_BOTTLE.get().getRegistryName().getPath(),
                new ResourceLocation("item/handheld"),
                new ResourceLocation(TestMod.MODID, "item/waterbottle"));
    }
}
