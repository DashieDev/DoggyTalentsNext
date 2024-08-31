package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.AngelHaloModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class AngelHaloRenderEntry extends Entry {
    public static final ModelLayerLocation ANGEL_HALO = new ModelLayerLocation(Util.getResource("angel_halo"), "main");
    
    public AngelHaloModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new AngelHaloModel(ctx.bakeLayer(ANGEL_HALO));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ANGEL_HALO, AngelHaloModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.ANGEL_HALO;
    }
    
}
