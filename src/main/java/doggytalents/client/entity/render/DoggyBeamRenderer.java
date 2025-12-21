package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.backward_imitate.EntityRenderer_1_21_9;
import doggytalents.client.backward_imitate.EntityRenderer_21_3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DoggyBeamRenderer<T extends Entity> extends EntityRenderer_1_21_9<T> {

    private final net.minecraft.client.renderer.entity.ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public DoggyBeamRenderer(EntityRendererProvider.Context ctx, float p_i226035_3_, boolean p_i226035_4_) {
        super(ctx);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        this.scale = p_i226035_3_;
        this.fullBright = p_i226035_4_;
    }

    public DoggyBeamRenderer(EntityRendererProvider.Context ctx) {
        this(ctx, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T entityIn, BlockPos posIn) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entityIn, posIn);
    }

    @Override
    public void submit(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, RenderContext_1_21_9<T> context_1_21_9, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(this.scale, this.scale, this.scale);
        matrixStackIn.mulPose(context_1_21_9.cameraState().orientation);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
        this.submitItemStack(new ItemStack(Items.SNOWBALL), 
            ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, 
            matrixStackIn, context_1_21_9);
        matrixStackIn.popPose();
        //super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public Identifier getTextureLocation(Entity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
