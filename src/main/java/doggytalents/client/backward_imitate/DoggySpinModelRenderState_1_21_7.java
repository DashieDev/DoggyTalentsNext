package doggytalents.client.backward_imitate;

import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.MultiBufferSource;

public record DoggySpinModelRenderState_1_21_7  (
    BiConsumer<PoseStack, MultiBufferSource> renderer,
    Vector3f offset,
    Quaternionf rotation,
    int x0,
    int y0,
    int x1,
    int y1,
    float scale,
    @Nullable ScreenRectangle scissorArea,
    @Nullable ScreenRectangle bounds
) implements PictureInPictureRenderState {

    public DoggySpinModelRenderState_1_21_7(
        BiConsumer<PoseStack, MultiBufferSource> renderer,
        Vector3f offset,
        Quaternionf rotation,
        int x0,
        int y0,
        int x1,
        int y1,
        float scale,
        @Nullable ScreenRectangle scissorArea
    ) {
        this(renderer, offset, rotation, x0, y0, x1, y1, scale, scissorArea,
            PictureInPictureRenderState.getBounds(x0, y0, x1, y1, scissorArea));
    }
    
}