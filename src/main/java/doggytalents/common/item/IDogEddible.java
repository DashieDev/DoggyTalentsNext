package doggytalents.common.item;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

public interface IDogEddible {
    
    default ItemStack getReturnStackAfterDogConsume(ItemStack useStack, AbstractDog dog) {
        return ItemStack.EMPTY;
    };

    public float getAddedHunger(ItemStack useStack, AbstractDog dog);

    default List<Pair<MobEffectInstance, Float>> getAdditionalEffects(ItemStack useStack, AbstractDog dog) {
        return List.of();
    }

    default boolean alwaysEat(AbstractDog dog) {
        return false;
    };

}
