package doggytalents.common.effects;

import java.util.function.Consumer;

import doggytalents.common.entity.Dog;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

public class NattoBiteEffect extends MobEffect {
    
    public NattoBiteEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffb36729);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        return true;
    }

    public void doAdditionalAttackEffects(Dog dog, LivingEntity target) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 3));
    }

    //@Override
    public static IClientMobEffectExtensions initializeClient() {
        return (new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return false;
            }
        });
    }

}
