package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;

import doggytalents.common.lib.Constants;

@Mixin(net.minecraft.Util.class)
public class Minecraft_UtilMixin {
    
    @Inject(
        method = "fetchChoiceType(Lcom/mojang/datafixers/DSL$TypeReference;Ljava/lang/String;)Lcom/mojang/datafixers/types/Type;", 
        at = @At("HEAD"),
        cancellable = true    
    )
    private static void dtn__fetchChoiceType(DSL.TypeReference type, String id, CallbackInfoReturnable<Type<?>> info) {
        //It already return null for DTN, since we don't use any vanilla's datafixers for now.
        //Return early to avoid unecessary logging. 
        if (id.startsWith(Constants.MOD_ID)) {
            info.setReturnValue(null);
        }
    }

}
