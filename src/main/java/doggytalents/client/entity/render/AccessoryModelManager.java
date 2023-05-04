package doggytalents.client.entity.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.client.event.EntityRenderersEvent;

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

    public interface Entry {
        
        public void initModel(EntityRendererProvider.Context ctx);
        public ListModel<Dog> getModel();
        public void renderAccessory(RenderLayer<Dog, DogModel<Dog>> layer, 
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, 
            Dog dog, float limbSwing, float limbSwingAmount, float partialTicks, 
        float ageInTicks, float netHeadYaw, float headPitch, AccessoryInstance inst);
        public void registerLayerDef(final EntityRenderersEvent.RegisterLayerDefinitions event);
    }


}
