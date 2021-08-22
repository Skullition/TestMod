package com.skullition.datagen;

import com.skullition.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> craftingRecipes) {
        ShapedRecipeBuilder.shaped(Registration.WATER_BOTTLE.get())
                .pattern("xxx")
                .pattern(" y ")
                .pattern(" y ")
                .define('x', Items.STICK)
                .define('y', Tags.Items.INGOTS_NETHERITE)
                .group("testmod")
                .unlockedBy("sticks", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                .save(craftingRecipes);
    }
}
