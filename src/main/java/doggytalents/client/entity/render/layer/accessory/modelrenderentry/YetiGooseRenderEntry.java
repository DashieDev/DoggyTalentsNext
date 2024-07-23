package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.YetiGooseModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;

public class YetiGooseRenderEntry extends Entry{
    public static final ModelLayerLocation YETI_GOOSE = new ModelLayerLocation(Util.getResource("goose"), "main");
    
    public YetiGooseModel model;
    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.YETI_GOOSE;
    }

    @Override
    public void initModel(Context ctx) {
        this.model = new YetiGooseModel(ctx.bakeLayer(YETI_GOOSE));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(YETI_GOOSE, YetiGooseModel::createBodyLayer);
    }
    
}
