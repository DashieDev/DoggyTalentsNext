package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.common.entity.misc.DogFoodProjectile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DogFoodProjectileRenderer extends EntityRenderer<DogFoodProjectile, DogFoodProjectileRenderer.DogFoodRenderState> {

    public static class DogFoodRenderState extends EntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
    }

    private final ItemModelResolver itemModelResolver;
    private ItemStack placeholder;

    public DogFoodProjectileRenderer(Context ctx) {
        super(ctx);
        this.itemModelResolver = ctx.getItemModelResolver();
    }

    @Override
    public DogFoodRenderState createRenderState() {
        return new DogFoodRenderState();
    }

    @Override
    public void extractRenderState(DogFoodProjectile entity, DogFoodRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        var foodStack = entity.getDogFoodStack();
        if (foodStack.isEmpty()) {
            if (placeholder == null) placeholder = new ItemStack(Items.SNOWBALL);
            foodStack = placeholder;
        }
        this.itemModelResolver.updateForNonLiving(state.item, foodStack, ItemDisplayContext.GROUND, entity);
    }

    public Identifier getTextureLocation(DogFoodRenderState state) {
        return net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void submit(DogFoodRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.mulPose(cameraState.orientation);
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        state.item.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, -1);
        stack.popPose();
    }
}
