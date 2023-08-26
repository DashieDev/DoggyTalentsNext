package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.SmartyGlasses;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class SmartyGlassesRenderEntry extends AccessoryModelManager.Entry {

    public static final ModelLayerLocation DOG_SMARTY_GLASSES = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_smarty_glasses"), "main");
    
    public SmartyGlassesModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new SmartyGlassesModel(ctx.bakeLayer(DOG_SMARTY_GLASSES));
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_SMARTY_GLASSES, SmartyGlassesModel::createGlassesLayer);
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getResources() {
        return Resources.SMARTY_GLASSES;
    }
}