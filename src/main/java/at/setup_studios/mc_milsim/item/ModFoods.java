package at.setup_studios.mc_milsim.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties BEISPIEL_FOOD = new FoodProperties.Builder().nutrition(15)
            .saturationMod(20).effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 10000), 0.999999F)
            .meat().build();
}
