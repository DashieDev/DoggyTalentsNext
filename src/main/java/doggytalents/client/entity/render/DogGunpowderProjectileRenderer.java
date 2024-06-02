package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.common.entity.misc.DogGunpowderProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogGunpowderProjectileRenderer extends EntityRenderer<DogGunpowderProjectile> {
    
    private ItemRenderer itemRenderer;
    private ItemStack renderStack;

    public DogGunpowderProjectileRenderer(Context ctx) {
        super(ctx);
        itemRenderer = ctx.getItemRenderer();
        renderStack = new ItemStack(Items.GUNPOWDER);
    }

    @Override
    public void render(DogGunpowderProjectile dogFood, float yRot, float pTick, PoseStack stack,
            MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        this.itemRenderer.renderStatic(renderStack, 
            ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, 
            stack, buffer, null, dogFood.getId());
        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DogGunpowderProjectile proj) {
        return InventoryMenu.BLOCK_ATLAS;
    }

}
