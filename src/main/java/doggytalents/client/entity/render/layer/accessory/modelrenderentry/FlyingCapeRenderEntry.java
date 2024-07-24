package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ElytraCapeModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;


public class FlyingCapeRenderEntry extends Entry {

    public static final ModelLayerLocation FLYING_CAPE = new ModelLayerLocation(Util.getResource("flying_cape"), "main");

    private ElytraCapeModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new ElytraCapeModel(ctx.bakeLayer(FLYING_CAPE));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    
    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FLYING_CAPE, ElytraCapeModel::cape);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.FLYING_CAPE;
    }

    @Override
    public boolean isDyable() {
        return true;
    }
    
}
