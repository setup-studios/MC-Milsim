package at.setup_studios.mc_milsim.item;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

                            output.accept(ModBlocks.BEISPIEL_BLOCK.get());
                            output.accept(ModBlocks.STEVE_BLOCK.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
