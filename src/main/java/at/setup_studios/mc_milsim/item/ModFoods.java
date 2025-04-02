package at.setup_studios.mc_milsim.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties BEISPIEL_FOOD = new FoodProperties.Builder().nutrition(15)
            .saturationMod(20).effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 10000), 0.999999F)
            .meat().build();
    public static final FoodProperties DIRT_FOOD = new FoodProperties.Builder().nutrition(500)
            .saturationMod(500).fast().meat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, (int)(Math.random()*5000), 10), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int)(Math.random()*5000), 50), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, (int)(Math.random()*5000), 5), 0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int)(Math.random()*5000), (int)(1+Math.random()*10)), 0.5F).build();
}
