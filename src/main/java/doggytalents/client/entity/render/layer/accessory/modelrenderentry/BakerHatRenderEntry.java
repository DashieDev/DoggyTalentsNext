package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BakerHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;

public class BakerHatRenderEntry extends AccessoryModelManager.Entry{
    public static final ModelLayerLocation DOG_BAKER = new ModelLayerLocation(Util.getResource("dog_baker"), "main");
    
    public BakerHatModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new BakerHatModel(ctx.bakeLayer(DOG_BAKER));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_BAKER, BakerHatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.BAKER_HAT;
    }

    @Override
    public boolean isDyable() {
        return true;
    }
}
