package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.FireFighterHatModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class FIreFighterHatRenderEntry extends Entry {
    
    public static final ModelLayerLocation DOG_FIREFIGHTER_HAT = new ModelLayerLocation(Util.getResource("dog_firefighter_hat"), "main");
    
    public FireFighterHatModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new FireFighterHatModel(ctx.bakeLayer(DOG_FIREFIGHTER_HAT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_FIREFIGHTER_HAT, FireFighterHatModel::createLayer);
    }

    @Override
    public Identifier getResources(AccessoryInstance inst) {
        return inst.getAccessory().getModelTexture();
    }

}
