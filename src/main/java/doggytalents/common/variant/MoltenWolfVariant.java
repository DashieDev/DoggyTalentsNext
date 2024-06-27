package doggytalents.common.variant;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.RandomUtil;
import doggytalents.common.util.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class MoltenWolfVariant extends DogVariant implements IDogAlteration {

    public MoltenWolfVariant(String name) {
        super(
            DogVariant.props(name)
            .guiColor(0xffcb4e2c)
            .glowingOverlay(
                Util.getResource("textures/entity/dog/classical/compl/overlay/wolf_molten_overlay.png")
            )
            .customInjuredTexture(
                Util.getResource("textures/entity/dog/classical/compl/overlay/wolf_molten_injured.png")
            )
        );
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setFallImmune();
        props.setFireImmune();
    }

    @Override
    public void tick(AbstractDog dog) {

        if (!dog.level().isClientSide)
            return;
        if (!ConfigHandler.CLIENT.DOG_VARIANT_CLIENT_EFFECT.get())
            return;
        if (dog.isDefeated())
            return;
        
        var random = dog.getRandom();
        var level = dog.level();
        if (random.nextInt(100) == 0) {
            double d0 = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            double d1 = (double)dog.getY() + random.nextFloat() * (dog.getBbHeight());
            double d2 = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
            level.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            level.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.AMBIENT, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }

        double dx = dog.getX() - dog.xo;
        double dz = dog.getZ() - dog.zo;
        var is_moving = dx * dx + dz * dz > (double)2.5000003E-7F;
        if (is_moving && dog.getRandom().nextInt(3) == 0) {
            int r = dog.getRandom().nextInt(3);
            SimpleParticleType type = null;
            if (r == 0) 
                type = ParticleTypes.FLAME;
            else
                type = ParticleTypes.LANDING_LAVA;
            if (type != null) {
                double d0 = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
                double d1 = (double)dog.getY() + random.nextFloat() * (dog.getBbHeight() * 0.8);
                double d2 = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2);
                level.addParticle(type, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
            
        }

        // if (random.nextInt(200) == 0) {
        //     level.playLocalSound((double)wolf.getX(), (double)wolf.getY(), (double)wolf.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.15f, 0.9F + random.nextFloat() * 0.15F, false);
        // }
    }
    

    
}
