package doggytalents.common.entity.accessory;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

public class DivineRetribution extends Accessory implements IAccessoryHasModel {
    
    public DivineRetribution(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.WINGS, itemIn);
        this.setAccessoryRenderType(AccessoryRenderType.MODEL);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DIVINE_RETRIB;
    }

    @Override
    public AccessoryInstance getDefault() {
        return new Inst(this);
    }

    public static class Inst extends AccessoryInstance implements IDogAlteration {

        public static final int MAX_COOKED = 10;
        public static final int COOK_RADIUS = 1;

        private final Map<ItemEntity, Integer> cooking = Maps.newHashMap();
        private int tickTillRefresh = 0;

        public Inst(Accessory typeIn) {
            super(typeIn);
        }

        @Override
        public void tick(AbstractDog dogIn) {
            if (--tickTillRefresh <= 0) {
                tickTillRefresh = 5;
                populateCooking(dogIn);
            }
            invalidateCooking(dogIn);
            cookAllCooking(dogIn);
            if (dogIn.level().isClientSide)
                addFlameParticles(dogIn);
        }

        private void addFlameParticles(AbstractDog dog) {
                var a1 = dog.yBodyRot;
                var dx1 = -Mth.sin(a1*Mth.DEG_TO_RAD);
                var dz1 = Mth.cos(a1*Mth.DEG_TO_RAD);
                float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                dog.level().addParticle(ParticleTypes.FLAME,
                dog.getX() + f1 - dx1*(dog.getBbWidth() * 1.8),
                dog.getY() + dog.getBbHeight() + 0.24,
                dog.getZ() + f2 - dz1*(dog.getBbWidth() * 1.8),
                -dx1*0.05, -0.01, -dz1*0.05);
        }

        private void populateCooking(AbstractDog dog) {
            if (this.cooking.size() >= MAX_COOKED)
                return;
            var cookingItems = dog.level()
                .getEntitiesOfClass(ItemEntity.class, dog.getBoundingBox().inflate(COOK_RADIUS));
            for (var e : cookingItems) {
                if (this.cooking.size() >= MAX_COOKED)
                    break;
                if (this.cooking.containsKey(e))
                    continue;
                var item = e.getItem();
                var recipe = getCookedRecipe(dog, item);
                if (recipe == null)
                    continue;
                this.cooking.put(e, recipe.getCookingTime());
            }
        }

        private void invalidateCooking(AbstractDog dog) {
            if (this.cooking.isEmpty())
                return;
            var removeList = new ArrayList<ItemEntity>();
            for (var entry : this.cooking.entrySet()) {
                if (stillValidCooking(dog, entry.getKey()))
                    continue;
                removeList.add(entry.getKey());
            }
            for (var e : removeList) {
                this.cooking.remove(e);
            }
        }

        private void cookAllCooking(AbstractDog dog) {
            if (this.cooking.isEmpty())
                return;
            if (dog.level().isClientSide) {
                if (dog.getRandom().nextDouble() < 0.1D) {
                    dog.level()
                        .playLocalSound(
                            dog.getX(), dog.getY(), dog.getZ(), 
                            SoundEvents.FURNACE_FIRE_CRACKLE, 
                            SoundSource.AMBIENT, 
                            1.0F, 1.0F, 
                            false
                        );
                }
            }
            var finished = new ArrayList<ItemEntity>();
            for (var entry : this.cooking.entrySet()) {
                if (dog.level().isClientSide) {
                    var e = entry.getKey();
                    if (dog.getRandom().nextInt(3) == 0) {
                        float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * e.getBbWidth() * 0.5F;
                        float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * e.getBbWidth() * 0.5F;
                        dog.level().addParticle(ParticleTypes.SMOKE,
                        e.getX() + f1,
                        e.getY(),
                        e.getZ() + f2,
                        0, 0.05 , 0 );
                    }
                } else {
                    entry.setValue(entry.getValue() - 1);
                    if (entry.getValue() > 0)
                        continue;
                    finishCooking(dog, entry.getKey());
                    finished.add(entry.getKey());
                }
            } 
            for (var e : finished) {
                this.cooking.remove(e);
            }
        }

        private void finishCooking(AbstractDog dog, ItemEntity e) {
            var uncookedItem = e.getItem().copy();
            var recipe = getCookedRecipe(dog, uncookedItem);
            if (recipe == null)
                return;
            var cookedItem = recipe
                .getResultItem(dog.level().registryAccess()).copy();
            var cookedItemEntity = new ItemEntity(dog.level(), 
                e.getX(), e.getY(), e.getZ(), cookedItem);
            cookedItemEntity.setDefaultPickUpDelay();
            dog.level().addFreshEntity(cookedItemEntity);

            uncookedItem.shrink(1);
            if (uncookedItem.isEmpty())
                e.discard();
            else
                e.setItem(uncookedItem);
        }

        private boolean stillValidCooking(AbstractDog dog, ItemEntity e) {
            var maxDist = dog.getBbWidth()/2 + COOK_RADIUS + 0.5;
            return e.isAlive() && e.distanceToSqr(dog) <= maxDist*maxDist;
        }

        private SmeltingRecipe getCookedRecipe(AbstractDog dog, ItemStack stack) {
            var recipeOptional = 
                dog.level().getRecipeManager().getRecipeFor(RecipeType.SMELTING, 
                    new SimpleContainer(stack), dog.level());
            if (!recipeOptional.isPresent())
                return null;
            var recipe = recipeOptional.get();
            return recipe;
        }
    }
}
