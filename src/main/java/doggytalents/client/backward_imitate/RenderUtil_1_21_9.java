package doggytalents.client.backward_imitate;

import org.joml.Matrix4f;

import doggytalents.mixin.NameTagFeatureRendererStorageAccessorMixin_1_21_9;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;

public class RenderUtil_1_21_9 {
    
    public static record RenderContext_1_21_9(SubmitNodeCollector nodeCollector, CameraRenderState cameraState, DogRenderState_21_3 dogRenderState) {}

    public static void submitNameTagRaw(RenderContext_1_21_9 ctx, Component text, float tX, float tY, int txtcolor, Matrix4f pose, Font.DisplayMode display_mode, int bkg_color) {
        pose = new Matrix4f(pose);
        int light = ctx.dogRenderState.lightCoords;
        double camera_dist_sqr = ctx.dogRenderState.distanceToCameraSq;
        if (!(ctx.nodeCollector() instanceof SubmitNodeStorage submit_storage))
            return;
        var nametag_storage = (NameTagFeatureRendererStorageAccessorMixin_1_21_9) submit_storage.order(0).getNameTagSubmits();
        var submit_target = display_mode == Font.DisplayMode.SEE_THROUGH ?
            nametag_storage.dtn__getNameTagSubmitsSeethrough()
            : nametag_storage.dtn__getNameTagSubmitsNormal();
        var submit_entry = new SubmitNodeStorage.NameTagSubmit(pose, tX, tY, text, light, txtcolor, bkg_color, camera_dist_sqr);
        submit_target.add(submit_entry);
    }

    public static DeltaTracker getDeltaTracker() {
        return Minecraft.getInstance().getDeltaTracker();
    } 

}
