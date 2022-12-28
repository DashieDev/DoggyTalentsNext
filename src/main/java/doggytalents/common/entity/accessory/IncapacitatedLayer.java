package doggytalents.common.entity.accessory;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class IncapacitatedLayer extends Accessory {

    public IncapacitatedLayer() {
        super(DoggyAccessoryTypes.INCAPACITATED, () -> ItemStack.EMPTY, 0);
    }

    public void tickClient(AbstractDog dog) {
        
    }

    public static class BURN extends IncapacitatedLayer {
        
        @Override
        public void tickClient(AbstractDog dog) {
            if (dog.getDogHunger() <= 10) {
                for (int i = 0; i < 2; ++i) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    dog.level.addParticle(ParticleTypes.ASH,
                    dog.getX() + f1,
                    dog.getY() + 0.4,
                    dog.getZ() + f2,
                    0, -0.05 , 0 );
                }
    
                if (dog.getRandom().nextInt(3) == 0) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.5F;
                    dog.level.addParticle(ParticleTypes.SMOKE,
                    dog.getX() + f1,
                    dog.getY() + dog.getEyeHeight(),
                    dog.getZ() + f2,
                    0, 0.05 , 0 );
                }
            }
        }
        
    }

    public static class BLOOD extends IncapacitatedLayer {
        @Override
        public void tickClient(AbstractDog dog) {
            if (dog.getDogHunger() <= 10 && dog.tickCount % 8 == 0) {
                for (int i = 0; i < 2; ++i) {
                    float f1 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    float f2 = (dog.getRandom().nextFloat() * 2.0F - 1.0F) * dog.getBbWidth() * 0.8F;
                    dog.level.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.NETHER_WART)),
                        dog.getX() + f1,
                        dog.getY() + 0.4,
                        dog.getZ() + f2,
                        0, -0.05 , 0 
                    );
                }
            }
        }
    }

    public static class POISON extends IncapacitatedLayer {
    }

}

