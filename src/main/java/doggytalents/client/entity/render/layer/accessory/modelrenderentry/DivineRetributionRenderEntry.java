package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.DivineRetributionModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DivineRetributionRenderEntry extends Entry {
    
    public static final ModelLayerLocation DIVINE_RETRIB = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "divine_retribution"), "main");

    private DivineRetributionModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new DivineRetributionModel(ctx.bakeLayer(DIVINE_RETRIB));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }
    
    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DIVINE_RETRIB, DivineRetributionModel::createLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DIVINE_RETRIBUTION;
    }

}
