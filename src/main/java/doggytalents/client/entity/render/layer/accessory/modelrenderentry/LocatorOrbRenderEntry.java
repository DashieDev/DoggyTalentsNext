package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.kusa.LocatorOrbModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class LocatorOrbRenderEntry extends Entry {

    public static final ModelLayerLocation LOCATOR_ORB = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "locator_orb"), "main");

    public LocatorOrbModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new LocatorOrbModel(ctx.bakeLayer(LOCATOR_ORB));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LOCATOR_ORB, LocatorOrbModel::createOrbLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }
    
}
