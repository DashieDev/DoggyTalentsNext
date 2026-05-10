package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.accessories.ChristmasTreeModel;
import doggytalents.client.entity.model.accessories.DemonHornsModel;
import doggytalents.client.entity.model.accessories.PresentCostumeModel;
import doggytalents.client.entity.model.accessories.SnorkelModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.accessory.DemonHornsAccessory;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class PresentCostumeRenderEntry extends DoubleDyableRenderEntry {
    public static final ModelLayerLocation PRESENT_COSTUME = new ModelLayerLocation(Util.getResource("dog_gift_costume"), "main");
    
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
    protected Identifier getFgResource(AccessoryInstance inst) {
        return Resources.DOG_GIFT_COSTUME_OVERLAY;
    }

    @Override
    protected Identifier getBgResource(AccessoryInstance inst) {
        return Resources.DOG_GIFT_COSTUME;
    }
}
