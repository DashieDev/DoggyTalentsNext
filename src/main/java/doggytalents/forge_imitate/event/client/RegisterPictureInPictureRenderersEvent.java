package doggytalents.forge_imitate.event.client;

import java.util.List;
import java.util.function.Function;

import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;

/*
 * Do not extends Event for now, since we are directly invoke the listener
 * (which is only GuiDoggySpinRenderer_1_21_7) in the mixin, and we want to prevent
 * attempts to subscribe to this event on the bus for now. 
 */
public class RegisterPictureInPictureRenderersEvent {
    
    private final List<RegistrationEntry<?>> renderers;

    public RegisterPictureInPictureRenderersEvent(List<RegistrationEntry<?>> renderers) {
        this.renderers = renderers;
    }
    
    public <T extends PictureInPictureRenderState> void register(Class<T> stateClass,
            Function<MultiBufferSource.BufferSource, PictureInPictureRenderer<T>> factory) {
        this.renderers.add(new RegistrationEntry<>(stateClass, factory));
    }

    public static record RegistrationEntry<T extends PictureInPictureRenderState>(
        Class<T> stateClass,
        Function<MultiBufferSource.BufferSource, PictureInPictureRenderer<T>> factory
    ) {}

}
