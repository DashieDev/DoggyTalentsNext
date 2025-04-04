package doggytalents.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.client.backward_imitate.WrappedDogBedItemOverride_21_3;
import doggytalents.common.block.DogBedMaterialManager.NaniBedding;
import doggytalents.common.block.DogBedMaterialManager.NaniCasing;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.SpriteGetter;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelBaker;
import net.neoforged.neoforge.model.data.ModelData;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class DogBedModel implements BlockStateModel {

    public static DogBedItemOverride ITEM_OVERIDE = new DogBedItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = Util.getVanillaResource("missingno");

    private final ModelBakery modelLoader;
    private final BlockModel unbakedModel;
    private final BlockStateModel defaultModelVariant;
    private final Map<Direction, SimpleModelWrapper> defaultModelVariants = new ConcurrentHashMap<>(Direction.values().length);
    private final Map<Direction, SimpleModelWrapper> missingModelVariant = new ConcurrentHashMap<>(Direction.values().length);

    private final Map<Triple<ICasingMaterial, IBeddingMaterial, Direction>, SimpleModelWrapper> cache = Maps.newConcurrentMap();
    private final int maxCacheSize;

    public DogBedModel(ModelBakery modelLoader, BlockModel model, BlockStateModel defaultModelVariant, int maxCacheSize) {
        this.modelLoader = modelLoader;
        this.unbakedModel = model;
        this.defaultModelVariant = defaultModelVariant;
        this.maxCacheSize = maxCacheSize;
        this.initItemOverride_21_3();
    }

    public SimpleModelWrapper getModelVariant(@Nonnull ModelData data) {
        return this.getModelVariant(data.get(DogBedTileEntity.CASING), data.get(DogBedTileEntity.BEDDING), data.get(DogBedTileEntity.FACING));
    }

    public SimpleModelWrapper getModelVariant(ICasingMaterial casing, IBeddingMaterial bedding, Direction facing) {
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

    private SimpleModelWrapper getMissingVariant(Direction dir) {
        var missing = this.missingModelVariant.get(dir);
        if (missing != null)
            return missing;
        missing = bakeModelVariant(NaniCasing.NULL, NaniBedding.NULL, dir);
        this.missingModelVariant.put(dir, missing);
        return missing;
    }

    private SimpleModelWrapper getDefaultVariant(Direction dir) {
        var default_variant = this.defaultModelVariants.get(dir);
        if (default_variant != null)
            return default_variant;
        
        default_variant = bakeModel(unbakedModel, dir);
        this.defaultModelVariants.put(dir, default_variant);
        return default_variant;
    }

    // @Override
    // public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
    //     //!!!!!!
    //     return this.getModelVariant(null, null, Direction.NORTH).getQuads(state, side, rand,ModelData.EMPTY, null);
    // }

    // @Override
    // public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
    //     return this.getModelVariant(data).getQuads(state, side, rand, ModelData.EMPTY, renderType);
    // }

    // @Override
    // public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, ModelData data) {
    //     return this.getModelVariant(data).getQuads(state, side, rand, data);
    // }

    //@Override
    public TextureAtlasSprite getParticleIcon(@Nonnull ModelData data) {
        return this.getModelVariant(data).particleIcon();
    }

    public SimpleModelWrapper bakeModelVariant(@Nullable ICasingMaterial casing, @Nullable IBeddingMaterial bedding, @Nonnull Direction facing) {
        // var new_model = deepCopyBlockModel(this.unbakedModel);

        // var casing_texture = findCasingTexture(casing);
        // var bedding_texture = findBeddingTexture(bedding);
        // new_model.textureMap.put("bedding", bedding_texture);
        // new_model.textureMap.put("casing", casing_texture);
        // new_model.textureMap.put("particle", casing_texture);
        var new_model = prepareVariantBlockModelForBaking_1_21_5(unbakedModel, casing, bedding);

        var ret = bakeModel(new_model, facing);
        return ret;
    }

    // private static BlockModel deepCopyBlockModel(BlockModel model) {
    //     var elements_old = model.getElements();
    //     var elements_new = new ArrayList<BlockElement>(elements_old.size());
    //     for (var element : elements_old) {
    //         var element_copy = new BlockElement(element.from, element.to, 
    //             Maps.newHashMap(element.faces), element.rotation, element.shade, element.lightEmission);
    //         elements_new.add(element_copy);
    //     }

    //     var ret = new BlockModel(model.getParentLocation(), elements_new,
    //         Maps.newHashMap(model.textureMap), model.hasAmbientOcclusion(), model.getGuiLight(),
    //         model.getTransforms(), new ArrayList<>(model.getOverrides()));
    //     ret.name = model.name;
    //     ret.parent = model.parent;
    //     return ret;
    // }

    private static SimpleModelWrapper bakeModel(BlockModel to_bake, Direction dir) {
        // var baker = (new ModelBaker() {

        //     @Override
        //     public @Nullable BakedModel bake(ResourceLocation location, ModelState state,
        //             Function<Material, TextureAtlasSprite> sprites) {
        //         return to_bake.bake(Material::sprite, 
        //             getModelRotation(dir),
        //             true
        //         );
        //     }

        //     @Override
        //     public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
        //         return Material::sprite;
        //     }

        //     // @Override
        //     // public UnbakedModel getModel(ResourceLocation p_252194_) {
        //     //     return to_bake;
        //     // }

        //     @Override
        //     @javax.annotation.Nullable
        //     public BakedModel bake(ResourceLocation p_250776_, ModelState p_251280_) {
        //         return this.bake(p_250776_, p_251280_, getModelTextureGetter());
        //     }

        //     @Override
        //     public @org.jetbrains.annotations.Nullable UnbakedModel getTopLevelModel(ModelResourceLocation location) {
        //         // TODO Auto-generated method stub
        //         return null;
        //     }

        //     @Override
        //     public @org.jetbrains.annotations.Nullable BakedModel bakeUncached(UnbakedModel model, ModelState state,
        //             Function<Material, TextureAtlasSprite> sprites) {
        //         return null;
        //     }
            
        // });
        // return baker.bake(null, null, null);
        return bakeModel_1_21_5(to_bake, dir);
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

        return Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, resource));
    }

    // @Override
    // public boolean useAmbientOcclusion() {
    //     return this.defaultModelVariant.useAmbientOcclusion();
    // }

    // @Override
    // public boolean isGui3d() {
    //     return this.defaultModelVariant.isGui3d();
    // }

    // @Override
    // public boolean usesBlockLight() {
    //     return this.defaultModelVariant.usesBlockLight();
    // }

    // @Override
    // public boolean isCustomRenderer() {
    //     return this.defaultModelVariant.isCustomRenderer();
    // }

    @Override
    public TextureAtlasSprite particleIcon() {
        return this.defaultModelVariant.particleIcon();
    }

    // @Override
    // public ItemTransforms getTransforms() {
    //     return this.defaultModelVariant.getTransforms();
    // }

    @Override
    public BakedOverrides overrides() {
        return override_21_3;
    }




    //1.20.3+
    private WrappedDogBedItemOverride_21_3 override_21_3;
    private void initItemOverride_21_3() {
        this.override_21_3 = new WrappedDogBedItemOverride_21_3(this);
    }

    //1.21.5+
    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random,
            List<BlockModelPart> parts) {
        var model_data = level.getModelData(pos);
        var part = this.getModelVariant(model_data);
        if (part != null) parts.add(part);
    }

    @Override
    public void collectParts(RandomSource random, List<BlockModelPart> parts) {
        var part = this.getModelVariant(null, null, Direction.NORTH);
        if (part != null) parts.add(part);
    }

    @Override
    public TextureAtlasSprite particleIcon(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        var model_data = level.getModelData(pos);
        return this.getParticleIcon(model_data);
    }

    private static List<BlockElement> getElements_1_21_5(BlockModel model) {
        if (!(model.geometry() instanceof SimpleUnbakedGeometry element_getter))
            return List.of();
        return element_getter.elements();
    }

    private BlockModel prepareVariantBlockModelForBaking_1_21_5(BlockModel model, @Nullable ICasingMaterial casing, @Nullable IBeddingMaterial bedding) {
        var elements_old = getElements_1_21_5(model);
        var elements_new = new ArrayList<BlockElement>(elements_old.size());
        for (var element : elements_old) {
            var element_copy = new BlockElement(element.from(), element.to(), 
                Maps.newHashMap(element.faces()), element.rotation(), element.shade(), element.lightEmission());
            elements_new.add(element_copy);
        }

        var casing_texture = findCasingTexture(casing);
        var bedding_texture = findBeddingTexture(bedding);
        var builder = new TextureSlots.Data.Builder();
        addMaterialToBuilder_1_21_5(builder, "bedding", bedding_texture);
        addMaterialToBuilder_1_21_5(builder, "casing", casing_texture);
        addMaterialToBuilder_1_21_5(builder, "particle", casing_texture);
        var texture_map = builder.build();

        var ret = new BlockModel(new SimpleUnbakedGeometry(elements_new), model.guiLight(), 
            model.ambientOcclusion(), model.transforms(), texture_map, 
            model.parent(), model.rootTransform(), model.renderTypeGroup(), new HashMap<>(model.partVisibility()));
        return ret;
    }

    private static void addMaterialToBuilder_1_21_5(TextureSlots.Data.Builder builder, String name,
        Either<Material, String> material) {
        
        if (material.left().isPresent()) {
            builder.addTexture(name, material.left().get());
            return;
        }
        if (material.right().isPresent()) {
            builder.addReference(name, material.right().get());
            return;
        }
        
    }

    public ResolvedModel resolvedDefaultUnbakedModel_1_21_5() {
        return resolvedModel_1_21_5(this.unbakedModel);
    }

    public BlockStateModel defaultBakedModel_1_21_5() {
        return this.defaultModelVariant;
    }

    private static SimpleModelWrapper bakeModel_1_21_5(BlockModel model, Direction dir) {
        var resolved_model = resolvedModel_1_21_5(model);
        var baker = modelBaker_1_21_5(resolved_model);
        return SimpleModelWrapper.bake(baker, resolved_model, getModelRotation(dir));
    }

    public static ResolvedModel resolvedModel_1_21_5(UnbakedModel model) {
        return new ResolvedModel() {

            @Override
            public String debugName() {
                return "DTN Dog Bed Variant Model";
            }

            @Override
            public UnbakedModel wrapped() {
                return model;
            }

            @Override
            @Nullable
            public ResolvedModel parent() {
                return null;
            }
            
        };
    }

    public static ModelBaker modelBaker_1_21_5(ResolvedModel resolved_model) {
        return new ModelBaker() {

            @Override
            public ResolvedModel getModel(ResourceLocation p_405736_) {
                return resolved_model;
            }

            @Override
            public SpriteGetter sprites() {
                return new SpriteGetter() {

                    @Override
                    public TextureAtlasSprite get(Material material, ModelDebugName p_404904_) {
                        return material.sprite();
                    }

                    @Override
                    public TextureAtlasSprite reportMissingReference(String p_387031_, ModelDebugName p_405621_) {
                        return new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation()).sprite();
                    }
                    
                };
            }

            @Override
            public <T> T compute(SharedOperationKey<T> p_410340_) {
                return p_410340_.compute(this);
            }
            
        };
    }

}
