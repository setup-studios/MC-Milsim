package at.setup_studios.mc_milsim.datagen;

import at.setup_studios.mc_milsim.block.ModBlocks;
import at.setup_studios.mc_milsim.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
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
    }
}
