package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;

public interface IDogEddible extends IDogFoodHandler {
    
    default ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return ItemStack.EMPTY;
    };

    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog);

    default List<PossibleEffect> getAdditionalEffectsWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return List.of();
    }

    default boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return false;
    };

    default SoundEvent getDogEatingSound(AbstractDog dog) {
        return SoundEvents.GENERIC_EAT;
    }
}
