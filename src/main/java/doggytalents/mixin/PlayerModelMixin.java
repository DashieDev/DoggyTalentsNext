package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {
    
    @Inject(at = @At("TAIL"),  method = "setupAnim")
    protected void dtn__setupAnim(AvatarRenderState state, CallbackInfo info) {
        //1.21.9+
        var player_1_21_9 = PlayerRenderUtil_1_21_9.getPlayerFromState(state, false);
        if (!player_1_21_9.isPresent()) return;
        
        var self = (PlayerModel)(Object) this;
        DTNClientDogSleepOnManager.get().afterPlayerModelSetupAnim(player_1_21_9.get(), state, self);
    }

}
