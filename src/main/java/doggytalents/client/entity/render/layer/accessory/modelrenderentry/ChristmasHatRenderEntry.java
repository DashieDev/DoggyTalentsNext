package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ChristmasHatModel;
import doggytalents.client.entity.model.FireFighterHatModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class ChristmasHatRenderEntry extends Entry {
    
    public static final ModelLayerLocation DOG_CHRISTMAS_HAT = new ModelLayerLocation(Util.getResource("dog_christmas_hat"), "main");
    
    public ChristmasHatModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new ChristmasHatModel(ctx.bakeLayer(DOG_CHRISTMAS_HAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_CHRISTMAS_HAT, ChristmasHatModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }
}