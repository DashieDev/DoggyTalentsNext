package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ElytraCapeModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;


public class BatWingsRenderEntry extends Entry {
    
    public static final ModelLayerLocation BAT_WINGS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "bat_wings"), "main");

    private ElytraCapeModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new ElytraCapeModel(ctx.bakeLayer(BAT_WINGS));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    
    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BAT_WINGS, ElytraCapeModel::bat);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.BAT_WINGS;
    }

}
