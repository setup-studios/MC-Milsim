package at.setup_studios.mc_milsim.item;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.fluid.ModFluids;
import at.setup_studios.mc_milsim.item.custom.FuelItem;
import net.minecraft.world.item.*;
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
    public static final RegistryObject<Item> MUSIC_SHARD = ITEMS.register("music_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_FOOD = ITEMS.register("dirt_food",
            () -> new Item(new Item.Properties().food(ModFoods.DIRT_FOOD).durability(10)));

    public static final RegistryObject<Item> EMERALD_SWORD = ITEMS.register("emerald_sword",
            () -> new SwordItem(ModToolTiers.EMERALD, 124, 2065, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe",
            () -> new PickaxeItem(ModToolTiers.EMERALD, 2, 416, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_AXE = ITEMS.register("emerald_axe",
            () -> new AxeItem(ModToolTiers.EMERALD, 251, 996, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_SHOVEL = ITEMS.register("emerald_shovel",
            () -> new ShovelItem(ModToolTiers.EMERALD, 1, 996, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_HOE = ITEMS.register("emerald_hoe",
            () -> new HoeItem(ModToolTiers.EMERALD, 0, 68996, new Item.Properties()));


    public static final RegistryObject<Item> EMERALD_HELMET = ITEMS.register("emerald_helmet",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_BOOTS = ITEMS.register("emerald_boots",
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> CHOCOLATE_FLUID_BUCKET = ITEMS.register("chocolate_fluid_bucket",
            () -> new BucketItem(ModFluids.SOURCE_CHOCOLATE_FLUID, new Item.Properties().stacksTo(1)
                    .craftRemainder(Items.BUCKET)));
    public static final RegistryObject<Item> MILK_FLUID_BUCKET = ITEMS.register("milk_fluid_bucket",
            () -> new BucketItem(ModFluids.SOURCE_MILK_FLUID, new Item.Properties().stacksTo(1)
                    .craftRemainder(Items.BUCKET)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
