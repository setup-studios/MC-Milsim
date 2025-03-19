package at.setup_studios.mc_milsim.datagen;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.ModBlocks;
import at.setup_studios.mc_milsim.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> SMELTABLES = List.of(ModItems.RAW_BEISPIEL.get(),
            ModBlocks.STEVE_BLOCK.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreBlasting(consumer, SMELTABLES, RecipeCategory.MISC, ModItems.BEISPIEL.get(), 0.3F, 100, "beispiel");
        oreSmelting(consumer, SMELTABLES, RecipeCategory.MISC, ModItems.BEISPIEL.get(), 0.3F, 200, "beispiel");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BEISPIEL_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BEISPIEL.get())
                .unlockedBy(getHasName(ModItems.BEISPIEL.get()), has(ModItems.BEISPIEL.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BEISPIEL.get(), 9)
                .requires(ModBlocks.BEISPIEL_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.BEISPIEL_BLOCK.get()), has(ModBlocks.BEISPIEL_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GRAVITY_BLOCK.get())
                .pattern("AB")
                .pattern("BA")
                .define('B', ModBlocks.BEISPIEL_BLOCK.get())
                .define('A', ModItems.BEISPIEL.get())
                .unlockedBy(getHasName(ModItems.BEISPIEL.get()), has(ModItems.BEISPIEL.get()))
                .unlockedBy(getHasName(ModBlocks.BEISPIEL_BLOCK.get()), has(ModBlocks.BEISPIEL_BLOCK.get()))
                .save(consumer);
    }
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Mc_milsim.MOD_ID + ":" + (pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
