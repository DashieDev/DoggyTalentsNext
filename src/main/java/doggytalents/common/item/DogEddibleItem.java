package doggytalents.common.item;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class DogEddibleItem extends Item implements IDogEddible {

    private final FoodProperties foodProps;

    public DogEddibleItem(Properties itemProps, FoodProperties foodProps) {
        super(itemProps.food(foodProps));
        this.foodProps = foodProps;
    }

    // public DogEddibleItem(FoodProperties foodProperties) {
    //     this(new Properties(), foodProperties);
    // }

    public DogEddibleItem(Properties itemProps, Function<FoodProperties.Builder, FoodProperties.Builder> propsCreator) {
        this(
            itemProps, 
            propsCreator.apply(new FoodProperties.Builder())
                .build()
        );
    }

    public DogEddibleItem(Properties itemProps, Function<Item.Properties, Item.Properties> itemPropsCreator,
        Function<FoodProperties.Builder, FoodProperties.Builder> propsCreator) {
    
        this(itemPropsCreator.apply(itemProps),
            propsCreator.apply(new FoodProperties.Builder()).build());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        return !dog.isDefeated() && isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        var dogEddible = this;
        
        if (!dogEddible.alwaysEatWhenDogConsume(dog) && !dog.canStillEat()) {
            return InteractionResult.FAIL;
        }

        if (!dog.level().isClientSide) {    
            float heal = dogEddible.getAddedHungerWhenDogConsume(stack, dog);

            dog.addHunger(heal);
            dog.consumeItemFromStack(entityIn, stack);

            for(var pair : dogEddible.getAdditionalEffectsWhenDogConsume(stack, dog)) {
                if (dog.getRandom().nextFloat() < pair.probability()) {
                   dog.addEffect(pair.effect());
                }
             }

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(this));
            }
            dog.playSound(
                dogEddible.getDogEatingSound(dog), 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );

            var returnStack = dogEddible.getReturnStackAfterDogConsume(stack, dog);
            if (!returnStack.isEmpty()) {
                dog.spawnAtLocation(returnStack);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return this.foodProps.nutrition() * 5;
    }

    @Override
    public List<PossibleEffect> getAdditionalEffectsWhenDogConsume(ItemStack useStack,
            AbstractDog dog) {
        return this.foodProps.effects();
    }
}
