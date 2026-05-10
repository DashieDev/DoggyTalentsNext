package doggytalents.common.item;

import net.minecraft.world.effect.MobEffectInstance;

/**
 * Replacement for the removed FoodProperties.PossibleEffect class.
 * Used for dog-specific mob effects when dogs consume food items.
 */
public record DogMobEffectEntry(MobEffectInstance effect, float probability) {
}
