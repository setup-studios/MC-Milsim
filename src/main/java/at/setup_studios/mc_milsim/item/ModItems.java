package at.setup_studios.mc_milsim.item;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.item.custom.FuelItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Mc_milsim.MOD_ID);

    public static final RegistryObject<Item> BEISPIEL = ITEMS.register("beispiel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BEISPIEL = ITEMS.register("raw_beispiel",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BEISPIEL_FOOD = ITEMS.register("beispiel_food",
            () -> new Item(new Item.Properties().food(ModFoods.BEISPIEL_FOOD)));

    public static final RegistryObject<Item> BEISPIEL_FUEL = ITEMS.register("beispiel_fuel",
            () -> new FuelItem(new Item.Properties(),400));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
