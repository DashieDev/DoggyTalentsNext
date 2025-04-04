package doggytalents.client.backward_imitate;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import doggytalents.client.block.model.DogBedItemOverride;
import doggytalents.client.block.model.DogBedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DogBedItemModel_1_21_5 implements ItemModel {

    private final DogBedModel dogBedModel;
    private final DogBedItemOverride dogBedResolver = new DogBedItemOverride();
    private final ModelRenderProperties renderProps;
    private Vector3f[] extents = null;

    public DogBedItemModel_1_21_5(DogBedModel dogBedModel) {
        this.dogBedModel = dogBedModel;
        renderProps = renderPopsFromDogBedModel(dogBedModel);
    }

    @Override
    public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver,
            ItemDisplayContext context, @Nullable ClientLevel level, @Nullable LivingEntity entity,
            int p_387820_) {
        
        var quads = dogBedResolver.resolve(dogBedModel, stack, level, entity, p_387820_).getQuads(Direction.NORTH);
        if (extents == null) {
            extents = BlockModelWrapper.computeExtents(quads);
        }

        var layer = state.newLayer();
        layer.setExtents(() -> extents);
        layer.setRenderType(ItemBlockRenderTypes.getRenderType(stack));
        this.renderProps.applyToLayer(layer, context);
        layer.prepareQuadList().addAll(quads);
    }

    private ModelRenderProperties renderPopsFromDogBedModel(DogBedModel model) {
        var resolved = model.resolvedDefaultUnbakedModel_1_21_5();
        var baked = model.defaultBakedModel_1_21_5();
        return new ModelRenderProperties(resolved.getTopGuiLight().lightLikeBlock(), 
            baked.particleIcon(), resolved.getTopTransforms());
    }
    
}
