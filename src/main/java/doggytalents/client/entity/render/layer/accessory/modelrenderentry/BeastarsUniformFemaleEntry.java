package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.BeastarsUniformFemaleAugmentModel;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class BeastarsUniformFemaleEntry extends AccessoryModelManager.Entry {
    
    public static final ModelLayerLocation BEASTARS_UNIFORM_F_AUGMENT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "beastars_uniform_f_augment"), "main");

    private BeastarsUniformFemaleAugmentModel model;

    @Override
    public void initModel(Context ctx) {
        this.model = new BeastarsUniformFemaleAugmentModel(ctx.bakeLayer(BEASTARS_UNIFORM_F_AUGMENT));
    }

    @Override
    public SyncedAccessoryModel getModel() {
        return this.model;
    }

    @Override
    public void registerLayerDef(RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BEASTARS_UNIFORM_F_AUGMENT, BeastarsUniformFemaleAugmentModel::createLayer);
    }

    @Override
    public ResourceLocation getResources() {
        return Resources.BEASTARS_UNIFORM_FEMALE;
    }


}
