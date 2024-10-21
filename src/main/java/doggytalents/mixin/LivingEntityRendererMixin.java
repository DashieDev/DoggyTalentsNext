package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogSleepOnManager;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.InputEvent;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(at = @At("HEAD"),  method = "setupRotations", cancellable = true)
    protected void dtn__setupRotation(LivingEntity living, PoseStack p_115318_, float p_115319_, float p_115320_,
        float p_115321_, float x, CallbackInfo info) {
        if (!living.hasPose(Pose.SLEEPING))
            return;
        if (!(living instanceof Player player))
            return;
        var sleep_on = DogSleepOnManager.getSleepingOnDog(living);
        if (!sleep_on.isPresent())
            return;
        ClientEventHandler.rotatePlayerToDogWhenSleepOn(sleep_on.get(), player, p_115318_, p_115319_, p_115320_, p_115321_, x);
        info.cancel();
    }

}
