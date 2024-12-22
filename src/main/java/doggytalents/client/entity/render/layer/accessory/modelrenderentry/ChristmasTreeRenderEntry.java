package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.ChristmasTreeModel;
import doggytalents.client.entity.model.DemonHornsModel;
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

public class ChristmasTreeRenderEntry extends AccessoryModelManager.Entry {
    public static final ModelLayerLocation CHRISTMAS_TREE = new ModelLayerLocation(Util.getResource("dog_christmas_tree"), "main");
    
    public ChristmasTreeModel model;
    @Override
    public void initModel(Context ctx) {
        this.model = new ChristmasTreeModel(ctx.bakeLayer(CHRISTMAS_TREE));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CHRISTMAS_TREE, ChristmasTreeModel::createBodyLayer);
    }

    @Override
    public ResourceLocation getResources(AccessoryInstance inst) {
        return Resources.DOG_CHRISTMAS_TREE;
    }
    
}
