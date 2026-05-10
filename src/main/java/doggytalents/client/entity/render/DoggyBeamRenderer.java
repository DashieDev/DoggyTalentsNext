package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DoggyBeamRenderer<T extends Entity> extends EntityRenderer<T, DoggyBeamRenderer.DoggyBeamRenderState> {

    public static class DoggyBeamRenderState extends EntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
    }

    private final ItemModelResolver itemModelResolver;
    private final float scale;
    private final boolean fullBright;

    public DoggyBeamRenderer(EntityRendererProvider.Context ctx, float p_i226035_3_, boolean p_i226035_4_) {
        super(ctx);
        this.itemModelResolver = ctx.getItemModelResolver();
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
    public DoggyBeamRenderState createRenderState() {
        return new DoggyBeamRenderState();
    }

    @Override
    public void extractRenderState(T entity, DoggyBeamRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        this.itemModelResolver.updateForNonLiving(state.item, new ItemStack(Items.SNOWBALL), ItemDisplayContext.GROUND, entity);
    }

    public Identifier getTextureLocation(DoggyBeamRenderState state) {
        return net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public void submit(DoggyBeamRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.scale(this.scale, this.scale, this.scale);
        stack.mulPose(cameraState.orientation);
        stack.mulPose(Axis.YP.rotationDegrees(180.0F));
        state.item.submit(stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, -1);
        stack.popPose();
    }
}
