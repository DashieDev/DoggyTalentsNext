package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.BachWigModel;
import doggytalents.client.entity.model.BowTieModel;
import doggytalents.client.entity.model.SmartyGlassesModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.WigModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.SmartyGlasses;
import doggytalents.common.entity.accessory.Wig;
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

public class BachWigRenderEntry extends AccessoryModelManager.Entry {

    public static final ModelLayerLocation DOG_BACH_WIG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_bach_wig"), "main");
    
    public BachWigModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BachWigModel(ctx.bakeLayer(DOG_BACH_WIG));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG_BACH_WIG, BachWigModel::createWigLayerDefinition);
    }

    @Override
    public ResourceLocation getResources() {
        return Resources.BACH_WIG;
    }
}
