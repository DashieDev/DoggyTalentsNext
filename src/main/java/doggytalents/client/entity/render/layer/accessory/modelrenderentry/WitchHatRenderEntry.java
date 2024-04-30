package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BirthdayHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WitchHatModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class WitchHatRenderEntry  extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DOG_WITCH_HAT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_witch_hat"), "main");
    
    public WitchHatModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new WitchHatModel(ctx.bakeLayer(DOG_WITCH_HAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_WITCH_HAT, WitchHatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.WITCH_HAT;
    }
    
}
