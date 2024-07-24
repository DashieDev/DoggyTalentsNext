package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.GiantStickModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import doggytalents.forge_imitate.event.client.EntityRenderersEvent.RegisterLayerDefinitions;


public class GiantStickRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation GIANT_STICK = new ModelLayerLocation(Util.getResource("giant_stick"), "main");
    
    private GiantStickModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new GiantStickModel(ctx.bakeLayer(GIANT_STICK));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GIANT_STICK, GiantStickModel::createGiantStickLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.GIANT_STICK;
    }
    
}
