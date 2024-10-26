package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.kusa.DeerAntlersModel;
import doggytalents.client.entity.model.dog.kusa.DragonCostumeSuitModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DragonCostumeSuitRenderEntry extends Entry{
    
    public static final ModelLayerLocation DRAGON_COSTUME_SUIT = new ModelLayerLocation(Util.getResource("dragon_costume_suit"), "main");

    public DragonCostumeSuitModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new DragonCostumeSuitModel(ctx.bakeLayer(DRAGON_COSTUME_SUIT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DRAGON_COSTUME_SUIT, DragonCostumeSuitModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DRAGON_COSTUME_SUIT;
    }
}
