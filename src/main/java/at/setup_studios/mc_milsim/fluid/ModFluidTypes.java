package at.setup_studios.mc_milsim.fluid;

import at.setup_studios.mc_milsim.Mc_milsim;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(Mc_milsim.MOD_ID, "misc/in_chocolate_fluid");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Mc_milsim.MOD_ID);

    public static final RegistryObject<FluidType> CHOCOLATE_FLUID_TYPE =  register("chocolate_water_fluid",
            FluidType.Properties.create().canDrown(false).canSwim(true).fallDistanceModifier(0f).supportsBoating(true).canExtinguish(true)
                    .canPushEntity(true).viscosity(100).density(100).lightLevel(15), 0xFF612104, new Vector3f(97 / 255f, 33 / 255f, 4 / 255f));

    public static final RegistryObject<FluidType> MILK_FLUID_TYPE = register("reproductive_fluid",
            FluidType.Properties.create().canPushEntity(true).canSwim(true).canDrown(false).canExtinguish(true).viscosity(0).density(0)
                    .supportsBoating(true).rarity(Rarity.EPIC).canHydrate(true), 0xFFFFFFFF, new Vector3f(1f, 1f, 1f));


    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties, int tintColor, Vector3f fogColor) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL,
                tintColor, fogColor, properties));
    }

    public static void register(IEventBus eventBus) {
         FLUID_TYPES.register(eventBus);
    }
}
