package at.setup_studios.mc_milsim.block;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.custom.ExplodingBlock;
import at.setup_studios.mc_milsim.block.custom.SoundBlock;
import at.setup_studios.mc_milsim.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.logging.Level;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Mc_milsim.MOD_ID);

    public static final RegistryObject<Block> BEISPIEL_BLOCK = registerBlock("beispiel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> STEVE_BLOCK = registerBlock("steve_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> GRAVITY_BLOCK = registerBlock("gravity_block",
            () -> new SandBlock(255, BlockBehaviour.Properties.copy(Blocks.GRAVEL)));
    /*public static final RegistryObject<Block> CHEST_BLOCK = registerBlock("chest_block",
            () -> new ChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST), ));*/
    /*public static final RegistryObject<Block> NEUER_BLOCK = registerBlock("neuer_block",
            () -> new AbstractCandleBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_CANDLE)) {
                @Override
                protected Iterable<Vec3> getParticleOffsets(BlockState blockState) {
                    return null;
                }
            });*/
    public static final RegistryObject<Block> FIRE_BLOCK = registerBlock("feuer_block",
            () -> new BaseFireBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), 0.5f) {
                @Override
                protected boolean canBurn(BlockState blockState) {
                    return true;
                }
            });
    public static final RegistryObject<Block> MUSIC_BLOCK = registerBlock("music_block",
            () -> new SoundBlock(BlockBehaviour.Properties.copy(Blocks.NOTE_BLOCK), SoundEvents.ENDER_DRAGON_DEATH));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registryBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item > registryBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
