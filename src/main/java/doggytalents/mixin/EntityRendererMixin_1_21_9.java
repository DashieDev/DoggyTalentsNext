package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin_1_21_9 {

    @Inject(at = @At("RETURN"),  method = "createRenderState(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;")
    public void dtn__createRenderState(Entity entity, float pticks, CallbackInfoReturnable<EntityRenderState> info) {
        if (entity.getType() != EntityType.PLAYER)
            return;
        if (!(entity instanceof AbstractClientPlayer player))
            return;

        PlayerRenderUtil_1_21_9.setPlayerToState(info.getReturnValue(), player);
    }

}
