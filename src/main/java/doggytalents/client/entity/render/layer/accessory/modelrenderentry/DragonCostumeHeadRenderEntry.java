package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.kusa.DeerAntlersModel;
import doggytalents.client.entity.model.dog.kusa.DragonCostumeHeadModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DragonCostumeHeadRenderEntry extends Entry{
    
    public static final ModelLayerLocation DRAGON_COSTUME_HEAD = new ModelLayerLocation(Util.getResource("dragon_costume_head"), "main");

    public DragonCostumeHeadModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new DragonCostumeHeadModel(ctx.bakeLayer(DRAGON_COSTUME_HEAD));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DRAGON_COSTUME_HEAD, DragonCostumeHeadModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DRAGON_COSTUME_HEAD;
    }
    
}
