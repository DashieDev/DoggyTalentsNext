package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ChristmasTreeModel;
import doggytalents.client.entity.model.DemonHornsModel;
import doggytalents.client.entity.model.PresentCostumeModel;
import doggytalents.client.entity.model.SnorkelModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.accessory.DemonHornsAccessory;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class PresentCostumeRenderEntry extends DoubleDyableRenderEntry {
    public static final ModelLayerLocation PRESENT_COSTUME = new ModelLayerLocation(Util.getResource("dog_present_costume"), "main");
    
    public PresentCostumeModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new PresentCostumeModel(ctx.bakeLayer(PRESENT_COSTUME));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PRESENT_COSTUME, PresentCostumeModel::createBodyLayer);
    }

    
    @Override
    protected ResourceLocation getFgResource(AccessoryInstance inst) {
        return Resources.DOG_PRESENT_COSTUME_OVERLAY;
    }

    @Override
    protected ResourceLocation getBgResource(AccessoryInstance inst) {
        return Resources.DOG_PRESENT_COSTUME;
    }
}
