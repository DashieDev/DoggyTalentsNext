package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BDHatModel;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.HotDogModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BDHatRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation BD_HAT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "bd_hat"), "main");

    private BDHatModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new BDHatModel(ctx.bakeLayer(BD_HAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BD_HAT, BDHatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }
    
}
