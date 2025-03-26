package at.setup_studios.mc_milsim.item;

import at.setup_studios.mc_milsim.Mc_milsim;
import at.setup_studios.mc_milsim.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier EMERALD = TierSortingRegistry.registerTier(
            new ForgeTier(5, 3000, 10f, 4f, 25,
                    ModTags.Blocks.NEEDS_EMERALD_TOOL, () -> Ingredient.of(Items.EMERALD)),
            new ResourceLocation(Mc_milsim.MOD_ID, "emerald"), List.of(Tiers.NETHERITE), List.of());
}
