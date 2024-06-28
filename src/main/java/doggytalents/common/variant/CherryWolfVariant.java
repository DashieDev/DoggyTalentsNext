package doggytalents.common.variant;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;

public class CherryWolfVariant extends DogVariant implements IDogAlteration {

    public CherryWolfVariant(String name) {
        super(DogVariant.props(name).guiColor(0xfffa9de5));       
    }

    @Override
    public void tick(AbstractDog dog) {

        if (!dog.level().isClientSide)
            return;
        if (!ConfigHandler.CLIENT.DOG_VARIANT_CLIENT_EFFECT.get())
            return;
        if (dog.isDefeated() || !dog.isDogVariantRenderEffective())
            return;
        
        var random = dog.getRandom();
        var level = dog.level();
        double dx = dog.getX() - dog.xo;
        double dz = dog.getZ() - dog.zo;
        var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (is_moving && random.nextInt(3) == 0) {
            double d0 = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            double d1 = (double)dog.getY() + random.nextFloat() * (dog.getBbHeight() * 0.8);
            double d2 = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            level.addParticle(ParticleTypes.CHERRY_LEAVES, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        // if (random.nextInt(200) == 0) {
        //     level.playLocalSound((double)dog.getX(), (double)dog.getY(), (double)dog.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.15f, 0.9F + random.nextFloat() * 0.15F, false);
        // }
    }
    
}
