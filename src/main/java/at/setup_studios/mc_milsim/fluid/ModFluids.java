package at.setup_studios.mc_milsim.fluid;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.block.ModBlocks;
import at.setup_studios.mc_milsim.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Mc_milsim.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_CHOCOLATE_FLUID = FLUIDS.register("chocolate_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.CHOCOLATE_FLOWING_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CHOCOLATE_FLUID = FLUIDS.register("flowing_chocolate_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.CHOCOLATE_FLOWING_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_MILK_FLUID = FLUIDS.register("source_reproductive_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MILK_FLOWING_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MILK_FLUID = FLUIDS.register("flowing_reproductive_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MILK_FLOWING_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CHOCOLATE_FLOWING_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.CHOCOLATE_FLUID_TYPE, SOURCE_CHOCOLATE_FLUID, FLOWING_CHOCOLATE_FLUID)
            .slopeFindDistance(2).tickRate(20).levelDecreasePerBlock(2).block(ModBlocks.CHOCOLATE_FLUID_BLOCK)
            .bucket(ModItems.CHOCOLATE_FLUID_BUCKET);

    public static final ForgeFlowingFluid.Properties MILK_FLOWING_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MILK_FLUID_TYPE, SOURCE_MILK_FLUID, FLOWING_MILK_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.MILK_FLUID_BLOCK)
            .bucket(ModItems.MILK_FLUID_BUCKET).tickRate(1);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
