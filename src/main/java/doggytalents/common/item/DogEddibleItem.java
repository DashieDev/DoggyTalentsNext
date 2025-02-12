package doggytalents.common.item;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import doggytalents.api.backward_imitate.DogInteractionResult;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.backward_imitate.DogFoodProperties_21_3;
import doggytalents.common.backward_imitate.DogFoodProperties_21_3.PossibleEffect_1_21_3;
import doggytalents.common.network.packet.ParticlePackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class DogEddibleItem extends Item implements IDogEddible {

    // private static FoodProperties NULL_PROPS = 
    //     (new FoodProperties.Builder())
    //         .nutrition(0)
    //         .build();

    // private final FoodProperties nullProps;
    // private final FoodProperties actualFoodProps;
    //private FoodProperties currentFoodProps;

    public DogEddibleItem(Properties itemProps, DogFoodProperties_21_3 foodProps) {
        super(itemProps.food(foodProps.getVanillaProps().build()));
        // if (foodProps != null)
        //     actualFoodProps = foodProps;
        // else 
        //     actualFoodProps = NULL_PROPS;

        // var nullPropsBuilder = (new FoodProperties.Builder())
        //     .nutrition(0);
        // boolean changed = false;
        // if (actualFoodProps.canAlwaysEat()) {
        //     changed = true;
        //     nullPropsBuilder.alwaysEdible();
        // }
        // if (changed)
        //     nullProps = nullPropsBuilder.build();
        // else
        //     nullProps = NULL_PROPS;
            
        //currentFoodProps = foodProps;
        init_1_21_3(foodProps);
    }

    // public DogEddibleItem(DogFoodProperties_21_3 foodProperties) {
    //     this(new Properties(), foodProperties);
    // }

    public DogEddibleItem(Properties itemProps, Function<DogFoodProperties_21_3, DogFoodProperties_21_3> propsCreator) {
        this(
            itemProps, 
            propsCreator.apply(new DogFoodProperties_21_3())
                //.build()
        );
    }

    public DogEddibleItem(Properties itemProps, Function<Item.Properties, Item.Properties> itemPropsCreator,
        Function<DogFoodProperties_21_3, DogFoodProperties_21_3> propsCreator) {
    
        this(itemPropsCreator.apply(itemProps),
            propsCreator.apply(new DogFoodProperties_21_3()));
    }

    // @Override
    // @Nullable
    // public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
    //     return this.currentFoodProps;
    // }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == this;
    }

    @Override
    public boolean canConsume(AbstractDog dog, ItemStack stackIn, @Nullable Entity entityIn) {
        return !dog.isDefeated() && isFood(stackIn);
    }

    @Override
    public DogInteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        if (dog.level().isClientSide)
            return DogInteractionResult.SUCCESS;
        
        var dogEddible = this;
        
        if (!dogEddible.alwaysEatWhenDogConsume(dog) && !dog.canStillEat()) {
            return DogInteractionResult.FAIL;
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
                dog.spawnAtLocation((ServerLevel)dog.level(), returnStack);
            }
        }

        return DogInteractionResult.SUCCESS;
    }

    @Override
    public float getAddedHungerWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        return this.vanillaDogProps_21_3.nutrition() * 5;
    }

    @Override
    public List<PossibleEffect_1_21_3> getAdditionalEffectsWhenDogConsume(ItemStack useStack,
            AbstractDog dog) {
        return this.dogEffects_21_3;
    }

    // @Override
    // public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    //     if (entity instanceof Player)
    //         currentFoodProps = actualFoodProps;
    //     var ret = super.finishUsingItem(stack, level, entity);
    //     currentFoodProps = nullProps;
    //     return ret;
    // }



    //1_21_3+
    private List<PossibleEffect_1_21_3> dogEffects_21_3;
    private FoodProperties vanillaDogProps_21_3;
    public void init_1_21_3(DogFoodProperties_21_3 props) {
        this.dogEffects_21_3 = props.dogEffects();
        this.vanillaDogProps_21_3 = props.getVanillaProps().build();
    }
    
}
