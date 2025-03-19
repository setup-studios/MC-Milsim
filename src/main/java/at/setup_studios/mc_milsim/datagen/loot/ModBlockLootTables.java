package at.setup_studios.mc_milsim.datagen.loot;

import at.setup_studios.mc_milsim.block.ModBlocks;
import at.setup_studios.mc_milsim.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.BEISPIEL_BLOCK.get());
        this.dropSelf(ModBlocks.FIRE_BLOCK.get());
        //this.dropSelf(ModBlocks.NEUER_BLOCK.get());
        this.add(ModBlocks.STEVE_BLOCK.get(),
                block -> createOreDrops(ModBlocks.STEVE_BLOCK.get(), ModItems.RAW_BEISPIEL.get()));

        this.dropOther(ModBlocks.GRAVITY_BLOCK.get(), ModItems.BEISPIEL.orElse(ModItems.RAW_BEISPIEL.get()));
    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
