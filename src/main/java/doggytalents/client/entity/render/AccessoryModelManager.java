package doggytalents.client.entity.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class AccessoryModelManager {

    private static ArrayList<Entry> entryArray = new ArrayList<Entry>();
    private static List<Entry> entryArraySyncedView = Collections.synchronizedList(entryArray);

    public static void register(Entry entry) {
        if (entryArraySyncedView.contains(entry)) return;
        entryArraySyncedView.add(entry);
    }

    public static void resolve(EntityRendererProvider.Context ctx) {
        for (var x : entryArraySyncedView) {
            x.initModel(ctx);
        }
    }

    public static void registerLayerDef(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (var x : entryArraySyncedView) {
            x.registerLayerDef(event);
        }
    }

    public static abstract class Entry {

        public abstract void initModel(EntityRendererProvider.Context ctx);
        public abstract SyncedAccessoryModel getModel();

        public void renderAccessory(RenderLayer<DogRenderState, DogModel> layer,
            PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight,
            DogRenderState renderState, AccessoryInstance inst) {

            var model = this.getModel();
            var dogModel = layer.getParentModel();
            dogModel.copyPropertiesTo(model);
            model.setupAnim(renderState);
            model.sync(dogModel);

            float[] color = new float[]{1.0f, 1.0f, 1.0f};
            if (this.isDyable() && (inst instanceof IColoredObject coloredObject))
                color = coloredObject.getColor();

            if (isTranslucent()) {
                DefaultAccessoryRenderer.renderTranslucentModel(model, getResources(inst),
                    poseStack, submitNodeCollector, packedLight, renderState, color[0], color[1], color[2], 1f);
            } else {
                AccessoryModelManager.renderColoredCutoutModel(model, getResources(inst),
                    poseStack, submitNodeCollector, packedLight, renderState, color[0], color[1], color[2]);
            }
        }

        public abstract void registerLayerDef(final EntityRenderersEvent.RegisterLayerDefinitions event);
        public abstract Identifier getResources(AccessoryInstance inst);
        public boolean isDyable() { return false; }
        public boolean isTranslucent() { return false; }
    }

    public static void renderColoredCutoutModel(SyncedAccessoryModel model, Identifier texture,
            PoseStack poseStack, SubmitNodeCollector collector, int light,
            DogRenderState renderState, float r, float g, float b) {
        collector.submitModel(model, renderState, poseStack,
            RenderTypes.entityCutout(texture), light, OverlayTexture.NO_OVERLAY,
            ARGB.colorFromFloat(1, r, g, b), null);
    }

}
