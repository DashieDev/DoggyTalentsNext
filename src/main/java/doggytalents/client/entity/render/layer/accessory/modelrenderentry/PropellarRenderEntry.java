package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.PropellerHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class PropellarRenderEntry extends AccessoryModelManager.Entry {

    public static final ModelLayerLocation DOG_PROPELLAR = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_propellar"), "main");
    
    public PropellerHatModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new PropellerHatModel(ctx.bakeLayer(DOG_PROPELLAR));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_PROPELLAR, PropellerHatModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DOG_PROPELLAR;
    }
}