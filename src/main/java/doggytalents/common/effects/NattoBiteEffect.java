package doggytalents.common.effects;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.EffectRenderer;

public class NattoBiteEffect extends MobEffect {
    
    public NattoBiteEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffb36729);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    public void doAdditionalAttackEffects(Dog dog, LivingEntity target) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 3));
    }

    @Override
    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(new EffectRenderer() {
            @Override
            public boolean shouldRender(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean shouldRenderInvText(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean shouldRenderHUD(MobEffectInstance effect) {
                return false;
            }

            @Override
            public void renderInventoryEffect(MobEffectInstance effectInstance, EffectRenderingInventoryScreen<?> gui,
                    PoseStack poseStack, int x, int y, float z) {
            }

            @Override
            public void renderHUDEffect(MobEffectInstance effectInstance, GuiComponent gui, PoseStack poseStack, int x,
                    int y, float z, float alpha) {
            }
        });
    }

}
