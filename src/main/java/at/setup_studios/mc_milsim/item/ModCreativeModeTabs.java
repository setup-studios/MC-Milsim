package at.setup_studios.mc_milsim.item;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mc_milsim.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BEISPIEL_TAB = CREATIVE_MODE_TABS.register("beispiel_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BEISPIEL.get()))
                    .title(Component.translatable("creativetab.beispiel_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                            output.accept(ModItems.BEISPIEL.get());
                            output.accept(ModItems.RAW_BEISPIEL.get());
                            output.accept(ModItems.BEISPIEL_FOOD.get());
                            output.accept(ModItems.BEISPIEL_FUEL.get());
                            output.accept(ModItems.MUSIC_SHARD.get());
                            output.accept(ModItems.DIRT_FOOD.get());
                            output.accept(ModItems.CHOCOLATE_FLUID_BUCKET.get());
                            output.accept(ModItems.MILK_FLUID_BUCKET.get());

                            output.accept(ModItems.EMERALD_SWORD.get());
                            output.accept(ModItems.EMERALD_PICKAXE.get());
                            output.accept(ModItems.EMERALD_AXE.get());
                            output.accept(ModItems.EMERALD_SHOVEL.get());
                            output.accept(ModItems.EMERALD_HOE.get());

                            output.accept(ModItems.EMERALD_HELMET.get());
                            output.accept(ModItems.EMERALD_CHESTPLATE.get());
                            output.accept(ModItems.EMERALD_LEGGINGS.get());
                            output.accept(ModItems.EMERALD_BOOTS.get());

                            output.accept(ModBlocks.BEISPIEL_BLOCK.get());
                            output.accept(ModBlocks.STEVE_BLOCK.get());
                            output.accept(ModBlocks.GRAVITY_BLOCK.get());
                            output.accept(ModBlocks.FIRE_BLOCK.get());
                            output.accept(ModBlocks.MUSIC_BLOCK.get());
                            //output.accept(ModBlocks.NEUER_BLOCK.get());
                            output.accept(ModBlocks.CURSED_DIRT_BLOCK.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
