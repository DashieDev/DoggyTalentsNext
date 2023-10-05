package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.KitsuneFrontModel;
import doggytalents.client.entity.model.KitsuneSideModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class KitsuneSideRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation DOG_KITSUNE_SIDE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kitsune_side"), "main");
    
    public KitsuneSideModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new KitsuneSideModel(ctx.bakeLayer(DOG_KITSUNE_SIDE));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_KITSUNE_SIDE, KitsuneSideModel::createKitsuneSideLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.KITSUNE_SIDE;
    }
    
}
