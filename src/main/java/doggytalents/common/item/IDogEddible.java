package doggytalents.common.item;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

public interface IDogEddible {
    
    default ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return ItemStack.EMPTY;
    };

    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog);

    default List<Pair<MobEffectInstance, Float>> getAdditionalEffectsWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return List.of();
    }

    default boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return false;
    };

    default SoundEvent getDogEatingSound(AbstractDog dog) {
        return SoundEvents.GENERIC_EAT;
    }

}
