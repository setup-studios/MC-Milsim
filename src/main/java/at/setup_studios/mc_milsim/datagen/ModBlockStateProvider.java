package at.setup_studios.mc_milsim.datagen;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Mc_milsim.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BEISPIEL_BLOCK);
        blockWithItem(ModBlocks.STEVE_BLOCK);
        blockWithItem(ModBlocks.GRAVITY_BLOCK);
        blockWithItem(ModBlocks.FIRE_BLOCK);
        blockWithItem(ModBlocks.MUSIC_BLOCK);
        blockWithItem(ModBlocks.CURSED_DIRT_BLOCK);
        //blockWithItem(ModBlocks.NEUER_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
