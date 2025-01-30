package doggytalents.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager.NaniBedding;
import doggytalents.common.block.DogBedMaterialManager.NaniCasing;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.fabric_helper.block.dogbed.DogBedModelData;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.Util;
import net.fabricmc.fabric.api.blockview.v2.FabricBlockView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.renderer.VanillaModelEncoder;
import net.minecraft.client.particle.TerrainParticle;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

//@OnlyIn(Dist.CLIENT)
public class DogBedModel implements BakedModel {

    //public static DogBedItemOverride ITEM_OVERIDE = new DogBedItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = Util.getVanillaResource("missingno");

    private final ModelBakery modelLoader;
    private final BlockModel unbakedModel;
    private final BakedModel defaultModelVariant;
    private final Map<Direction, BakedModel> defaultModelVariants = new ConcurrentHashMap<>(Direction.values().length);
    private final Map<Direction, BakedModel> missingModelVariant = new ConcurrentHashMap<>(Direction.values().length);

    private final Map<Triple<ICasingMaterial, IBeddingMaterial, Direction>, BakedModel> cache = Maps.newConcurrentMap();
    private final int maxCacheSize;

    public DogBedModel(ModelBakery modelLoader, BlockModel model, BakedModel defaultModelVariant, int maxCacheSize) {
        this.modelLoader = modelLoader;
        this.unbakedModel = model;
        this.defaultModelVariant = defaultModelVariant;
        this.maxCacheSize = maxCacheSize;
    }

    public BakedModel getModelVariant(@Nonnull DogBedModelData data) {
        return this.getModelVariant(data.casing(), data.bedding(), data.direction());
    }

    public BakedModel getModelVariant(ICasingMaterial casing, IBeddingMaterial bedding, Direction facing) {
        if (facing == null)
            facing = Direction.NORTH;
        
        if (casing == null || bedding == null)
            return getDefaultVariant(facing);
        if (casing.isNani() || bedding.isNani())
            return getMissingVariant(facing);
        
        var key = ImmutableTriple.of(casing, bedding, facing);
        var model_variant = this.cache.get(key);
        if (model_variant != null)
            return model_variant;
        
        if (this.cache.size() >= this.maxCacheSize)
            return getDefaultVariant(facing);

        model_variant = bakeModelVariant(casing, bedding, facing);
        this.cache.put(key, model_variant);
        return model_variant;
    }

    private BakedModel getMissingVariant(Direction dir) {
        var missing = this.missingModelVariant.get(dir);
        if (missing != null)
            return missing;
        missing = bakeModelVariant(NaniCasing.NULL, NaniBedding.NULL, dir);
        this.missingModelVariant.put(dir, missing);
        return missing;
    }

    private BakedModel getDefaultVariant(Direction dir) {
        var default_variant = this.defaultModelVariants.get(dir);
        if (default_variant != null)
            return default_variant;
        
        default_variant = bakeModel(unbakedModel, dir);
        this.defaultModelVariants.put(dir, default_variant);
        return default_variant;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
        //!!!!!!
        return this.getModelVariant(null, null, Direction.NORTH).getQuads(state, side, rand);
    }

    // @Override
    // public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
    //     return this.getModelVariant(data).getQuads(state, side, rand, renderType);
    // }

    // @Override
    // public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, ModelData data) {
    //     return this.getModelVariant(data).getQuads(state, side, rand, data);
    // }

    // @Override
    // public TextureAtlasSprite getParticleIcon(@Nonnull DogBedModelData data) {
    //     return this.getModelVariant(data).getParticleIcon(data);
    // }

    public BakedModel bakeModelVariant(@Nullable ICasingMaterial casing, @Nullable IBeddingMaterial bedding, @Nonnull Direction facing) {
        var new_model = deepCopyBlockModel(this.unbakedModel);

        var casing_texture = findCasingTexture(casing);
        var bedding_texture = findBeddingTexture(bedding);
        new_model.textureMap.put("bedding", bedding_texture);
        new_model.textureMap.put("casing", casing_texture);
        new_model.textureMap.put("particle", casing_texture);

        var ret = bakeModel(new_model, facing);
        return ret;
    }

    private static BlockModel deepCopyBlockModel(BlockModel model) {
        var elements_old = model.getElements();
        var elements_new = new ArrayList<BlockElement>(elements_old.size());
        for (var element : elements_old) {
            var element_copy = new BlockElement(element.from, element.to, 
                Maps.newHashMap(element.faces), element.rotation, element.shade);
            elements_new.add(element_copy);
        }

        var ret = new BlockModel(model.parentLocation, elements_new,
            Maps.newHashMap(model.textureMap), model.hasAmbientOcclusion(), model.getGuiLight(),
            model.getTransforms(), new ArrayList<>(model.getOverrides()));
        ret.name = model.name;
        ret.parent = model.parent;
        return ret;
    }

    private static BakedModel bakeModel(BlockModel to_bake, Direction dir) {
        var baker = (new ModelBaker() {

            @Override
            public @Nullable BakedModel bake(ResourceLocation location, ModelState state,
                    Function<Material, TextureAtlasSprite> sprites) {
                return to_bake.bake(this, to_bake, Material::sprite, 
                    getModelRotation(dir),
                    true
                );
            }

            @Override
            public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
                return Material::sprite;
            }

            @Override
            public UnbakedModel getModel(ResourceLocation p_252194_) {
                return to_bake;
            }

            @Override
            @javax.annotation.Nullable
            public BakedModel bake(ResourceLocation p_250776_, ModelState p_251280_) {
                return this.bake(p_250776_, p_251280_, getModelTextureGetter());
            }

            @Override
            public @org.jetbrains.annotations.Nullable UnbakedModel getTopLevelModel(ModelResourceLocation location) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public @org.jetbrains.annotations.Nullable BakedModel bakeUncached(UnbakedModel model, ModelState state,
                    Function<Material, TextureAtlasSprite> sprites) {
                return null;
            }
            
        });
        return baker.bake(null, null, null);
    }

    private static BlockModelRotation getModelRotation(@Nonnull Direction dir) {
        switch (dir) {
        default:    return BlockModelRotation.X0_Y0;
        case EAST:  return BlockModelRotation.X0_Y90;
        case SOUTH: return BlockModelRotation.X0_Y180;
        case WEST:  return BlockModelRotation.X0_Y270;
        }
    }

    private static Either<Material, String> findCasingTexture(@Nullable ICasingMaterial resource) {
        return findTexture(resource != null ? resource.getTexture() : null);
    }

    private static Either<Material, String> findBeddingTexture(@Nullable IBeddingMaterial resource) {
        return findTexture(resource != null ? resource.getTexture() : null);
    }

    private static Either<Material, String> findTexture(ResourceLocation resource) {
        if (resource == null) {
            resource = MISSING_TEXTURE;
        }

        return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resource));
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.defaultModelVariant.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.defaultModelVariant.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.defaultModelVariant.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.defaultModelVariant.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.defaultModelVariant.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return this.defaultModelVariant.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.bakedModel.getOverrides();
    }

    
    //Fabric
    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos,
            Supplier<RandomSource> randomSupplier, RenderContext context) {
        var dataGetter = (FabricBlockView) blockView;
        var data = dataGetter.getBlockEntityRenderData(pos);
        BakedModel bakedModel = null;
        if (data instanceof DogBedModelData dogBedData) {
            bakedModel = this.getModelVariant(dogBedData);
        } else {
            bakedModel = this.getModelVariant(DogBedModelData.EMPTY);
        }
        
        VanillaModelEncoder.emitBlockQuads(bakedModel, state, randomSupplier, context);
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        var bedMaterial = DogBedUtil.getMaterials(stack);
        var bakedModel = this.getModelVariant(bedMaterial.getLeft(), bedMaterial.getRight(), Direction.NORTH);

        VanillaModelEncoder.emitItemQuads(bakedModel, null, randomSupplier, context);
    }
}
