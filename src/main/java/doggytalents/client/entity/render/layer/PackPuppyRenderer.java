package doggytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.DoggyTalents;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.DogBackpackModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderState;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.lib.Resources;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.Optional;

public class PackPuppyRenderer extends RenderLayer<DogRenderState, DogModel> {

    private DogBackpackModel model;

    public PackPuppyRenderer(RenderLayerParent<DogRenderState, DogModel> parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new DogBackpackModel(ctx.bakeLayer(ClientSetup.DOG_BACKPACK));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, DogRenderState renderState, float yRot, float xRot) {
        if (renderState.isInvisible) {
            return;
        }

        var dog = renderState.dog;
        if (dog == null) return;

        if (!ConfigHandler.CLIENT.RENDER_CHEST.get())
            return;

        var dogSkin = renderState.activeSkin;
        if (dogSkin == null) return;
        if (dogSkin.useCustomModel()) {
            var model = dogSkin.getCustomModel().getValue();
            if (!model.armorShouldRender(dog))
                return;
        }

        Optional<PackPuppyTalent> inst = dog.getTalent(DoggyTalents.PACK_PUPPY)
            .map(x -> x.cast(PackPuppyTalent.class));
        if (inst.isPresent() && inst.get().renderChest()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(renderState);
            this.model.sync(getParentModel());

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_CHEST, poseStack, submitNodeCollector, packedLight, renderState, OverlayTexture.NO_OVERLAY, 0xffffffff);
        }

    }
}
