package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.LivingEntity;

@Mixin(PlayerModel.class)
public class PlayerModelMixin {
    
    @Inject(at = @At("TAIL"),  method = "setupAnim")
    protected void dtn__setupAnim(LivingEntity player, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYRot, float headPitch, CallbackInfo info) {
        var self = (PlayerModel<?>)(Object) this;
        DTNClientDogSleepOnManager.get().afterPlayerModelSetupAnim(player, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYRot, headPitch, self);
    }

}
