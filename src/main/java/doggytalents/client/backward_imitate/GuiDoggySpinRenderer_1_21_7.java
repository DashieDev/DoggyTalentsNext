package doggytalents.client.backward_imitate;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;

public class GuiDoggySpinRenderer_1_21_7 extends PictureInPictureRenderer<DoggySpinModelRenderState_1_21_7> {
    
    public GuiDoggySpinRenderer_1_21_7(MultiBufferSource.BufferSource buffer) {
        super(buffer);
    }

    @Override
    public Class<DoggySpinModelRenderState_1_21_7> getRenderStateClass() {
        return DoggySpinModelRenderState_1_21_7.class;
    }

    @Override protected void renderToTexture(DoggySpinModelRenderState_1_21_7 renderState, PoseStack stack) {
        var mc = Minecraft.getInstance();
        mc.gameRenderer.getLighting().setupFor(Lighting.Entry.ENTITY_IN_UI);
        var offset = renderState.offset();
        stack.translate(offset.x, offset.y, offset.z);
        stack.mulPose(renderState.rotation());
        stack.translate(0.0F, -1.501F, 0.0F);
        renderState.renderer().accept(stack, this.bufferSource);
    }

    @Override
    protected float getTranslateY(int sizeY, int guiScale) {
        return sizeY / 2.0F;
    }

    @Override
    protected String getTextureLabel() {
        return Constants.MOD_ID + "_doggySpin";
    }

    public static void doRenderDoggySpinModel(
        GuiGraphics graphics,
        float mid_x,
        float mid_y,
        float scale,
        Vector3f offset,
        Quaternionf rot,
        BiConsumer<PoseStack, MultiBufferSource> renderer
    ) {
        final int box_size = 128;
        int x0 = (int)(mid_x - box_size/2);
        int x1 = (int)(mid_x + box_size/2); 
        int y0 = (int)(mid_y - box_size/2);
        int y1 = (int)(mid_y + box_size/2);

        Function<ScreenRectangle, DoggySpinModelRenderState_1_21_7> creator = 
            scissor -> new DoggySpinModelRenderState_1_21_7(renderer, offset, rot, 
                x0, y0, x1, y1, scale, scissor);
        
        GuiRenderStateUtil_1_21_7.sumbitPIPRenderStateToGuiGraphics(graphics, creator);
    }

    public static void onRegisterPIPRenderers(RegisterPictureInPictureRenderersEvent event) {
        event.register(DoggySpinModelRenderState_1_21_7.class, GuiDoggySpinRenderer_1_21_7::new);
    }
}
