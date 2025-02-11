package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {
    
    @Inject(at = @At("TAIL"),  method = "setupAnim")
    protected void dtn__setupAnim(PlayerRenderState state, CallbackInfo info) {
        //1_21_3+ check
        if (PlayerRenderPrep_21_3.player == null) return;
        
        var self = (PlayerModel)(Object) this;
        DTNClientDogSleepOnManager.get().afterPlayerModelSetupAnim(PlayerRenderPrep_21_3.player, state, self);
    }

}
