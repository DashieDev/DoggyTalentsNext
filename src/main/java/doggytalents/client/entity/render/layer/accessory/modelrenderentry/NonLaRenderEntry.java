package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.NonLaModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class NonLaRenderEntry extends Entry {

    public static final ModelLayerLocation NON_LA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "non_la"), "main");
    
    public NonLaModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new NonLaModel(ctx.bakeLayer(NON_LA));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NON_LA, NonLaModel::createNonLaLayerDefinition);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.NON_LA;
    }
}