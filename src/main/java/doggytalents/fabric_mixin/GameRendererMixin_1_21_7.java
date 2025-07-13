package doggytalents.fabric_mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import doggytalents.client.backward_imitate.GuiDoggySpinRenderer_1_21_7;
import doggytalents.forge_imitate.event.client.RegisterPictureInPictureRenderersEvent;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

@Mixin(GameRenderer.class)
public class GameRendererMixin_1_21_7 {
    
    @WrapOperation(
        method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/ItemInHandRenderer;Lnet/minecraft/client/renderer/RenderBuffers;)V", 
        at = @At(
            value = "NEW", 
            target = "(Lnet/minecraft/client/gui/render/state/GuiRenderState;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Ljava/util/List;)Lnet/minecraft/client/gui/render/GuiRenderer;"
        )
    )
    private GuiRenderer dtn__GuiRenderer_constructor(GuiRenderState guiRenderState, MultiBufferSource.BufferSource bufferSource, List<PictureInPictureRenderer<?>> list, Operation<GuiRenderer> original) {
        var modify_list = new ArrayList<>(list);
        
        var gather_list = new ArrayList
            <RegisterPictureInPictureRenderersEvent.RegistrationEntry<?>>();  
        var event = new RegisterPictureInPictureRenderersEvent(gather_list);
        GuiDoggySpinRenderer_1_21_7.onRegisterPIPRenderers(event); // Directly invoke from the mixin for now.
        
        if (!gather_list.isEmpty()) {
            gather_list.forEach(entry -> 
                modify_list.add(entry.factory().apply(bufferSource)));
            list = List.copyOf(modify_list);
        }

        return original.call(guiRenderState, bufferSource, list);
    }

}
