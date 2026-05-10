package doggytalents.client.block.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.mojang.math.OctahedralGroup;

import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager.NaniBedding;
import doggytalents.common.block.DogBedMaterialManager.NaniCasing;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.mixin.ModelBakeryMixinAccessor;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.common.extensions.IBlockGetterExtension;
import net.neoforged.neoforge.model.data.ModelData;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Vector3fc;

public class DogBedModel implements DynamicBlockStateModel {

    /** Default baked part for each facing direction when no material data is available */
    private final Map<Direction, BlockStateModelPart> defaultParts;

    /** Cache of baked variants per (casing, bedding, facing) key */
    private static final Map<Triple<ICasingMaterial, IBeddingMaterial, Direction>, BlockStateModelPart> cache
        = new ConcurrentHashMap<>();

    /** The resolved model used as the source geometry for variant baking */
    private final ResolvedModel resolvedModel;

    /** A ModelBaker wrapping the text getter for on-demand variant baking */
    private final ModelBaker variantBaker;

    /** Maximum cache size to prevent unbounded growth */
    private final int maxCacheSize;

    public DogBedModel(
        Map<Direction, BlockStateModelPart> defaultParts,
        ResolvedModel resolvedModel,
        ModelBaker variantBaker,
        int maxCacheSize
    ) {
        this.defaultParts = defaultParts;
        this.resolvedModel = resolvedModel;
        this.variantBaker = variantBaker;
        this.maxCacheSize = maxCacheSize;
    }

    public static void clearCache() {
        cache.clear();
    }

    @Override
    public void collectParts(
        BlockAndTintGetter level, BlockPos pos, BlockState state,
        RandomSource random, List<BlockStateModelPart> parts
    ) {
        ModelData modelData = ((IBlockGetterExtension) level).getModelData(pos);
        ICasingMaterial casing = modelData.get(DogBedTileEntity.CASING);
        IBeddingMaterial bedding = modelData.get(DogBedTileEntity.BEDDING);
        Direction facing = modelData.get(DogBedTileEntity.FACING);
        if (facing == null) facing = Direction.NORTH;

        parts.add(getModelPart(casing, bedding, facing));
    }

    /** Fallback for when no block context is available */
    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> parts) {
        parts.add(getModelPart(null, null, Direction.NORTH));
    }

    @Override
    public Material.Baked particleMaterial() {
        BlockStateModelPart part = defaultParts.get(Direction.NORTH);
        return part != null ? part.particleMaterial() : resolvedModel.resolveParticleMaterial(resolvedModel.getTopTextureSlots(), variantBaker);
    }

    @Override
    public int materialFlags() {
        BlockStateModelPart part = defaultParts.get(Direction.NORTH);
        return part != null ? part.materialFlags() : 0;
    }

    private BlockStateModelPart getModelPart(ICasingMaterial casing, IBeddingMaterial bedding, Direction facing) {
        // Return default if no materials specified or materials are the null placeholders
        if (casing == null || bedding == null || casing.isNani() || bedding.isNani()) {
            BlockStateModelPart defaultPart = defaultParts.get(facing);
            return defaultPart != null ? defaultPart : variantBaker.missingBlockModelPart();
        }

        Triple<ICasingMaterial, IBeddingMaterial, Direction> key = ImmutableTriple.of(casing, bedding, facing);
        BlockStateModelPart cached = cache.get(key);
        if (cached != null) return cached;

        if (cache.size() >= maxCacheSize) {
            // Cache full - fall back to default
            BlockStateModelPart defaultPart = defaultParts.get(facing);
            return defaultPart != null ? defaultPart : variantBaker.missingBlockModelPart();
        }

        BlockStateModelPart baked = bakeVariant(casing.getTexture(), bedding.getTexture(), facing);
        cache.put(key, baked);
        return baked;
    }

    private BlockStateModelPart bakeVariant(Identifier casingTexture, Identifier beddingTexture, Direction facing) {
        // Build custom TextureSlots that override casing, bedding, and particle
        TextureSlots.Data.Builder customOverride = new TextureSlots.Data.Builder();
        customOverride.addTexture("casing", new Material(casingTexture));
        customOverride.addTexture("particle", new Material(casingTexture));
        customOverride.addTexture("bedding", new Material(beddingTexture));
        TextureSlots.Data customData = customOverride.build();

        // Stack the custom override on top of the model's texture hierarchy
        TextureSlots.Resolver resolver = new TextureSlots.Resolver();
        ResolvedModel current = resolvedModel;
        while (current != null) {
            resolver.addLast(current.wrapped().textureSlots());
            current = current.parent();
        }
        resolver.addFirst(customData);
        TextureSlots customSlots = resolver.resolve(resolvedModel);

        ModelState modelState = getRotationForFacing(facing);
        boolean ambientOcclusion = resolvedModel.getTopAmbientOcclusion();
        Material.Baked particleMaterial = resolvedModel.resolveParticleMaterial(customSlots, variantBaker);
        QuadCollection geometry = resolvedModel.bakeTopGeometry(customSlots, variantBaker, modelState);
        return new SimpleModelWrapper(geometry, ambientOcclusion, particleMaterial);
    }

    private static ModelState getRotationForFacing(Direction facing) {
        return switch (facing) {
            case EAST  -> BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_90);
            case SOUTH -> BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_180);
            case WEST  -> BlockModelRotation.get(OctahedralGroup.BLOCK_ROT_Y_270);
            default    -> BlockModelRotation.IDENTITY; // NORTH
        };
    }

    /**
     * Creates a MaterialBaker that resolves sprites from the block atlas using the given texture getter.
     */
    public static MaterialBaker createMaterialBaker(Function<Identifier, TextureAtlasSprite> textureGetter) {
        return new MaterialBaker() {
            @Override
            public Material.Baked get(Material material, net.minecraft.client.resources.model.ModelDebugName name) {
                TextureAtlasSprite sprite = textureGetter.apply(material.sprite());
                return new Material.Baked(sprite, material.forceTranslucent());
            }

            @Override
            public Material.Baked reportMissingReference(String reference, net.minecraft.client.resources.model.ModelDebugName name) {
                // Return a dummy material using the missing texture
                TextureAtlasSprite sprite = textureGetter.apply(Identifier.withDefaultNamespace("missingno"));
                return new Material.Baked(sprite, false);
            }
        };
    }

    /**
     * Creates a ModelBaker that uses resolved models from the bakery and
     * sprites from the textureGetter, suitable for on-demand variant baking.
     */
    public static ModelBaker createVariantBaker(
        ModelBakery modelBakery,
        Function<Identifier, TextureAtlasSprite> textureGetter,
        BlockStateModelPart missingPart
    ) {
        MaterialBaker materialBaker = createMaterialBaker(textureGetter);
        Map<Identifier, ResolvedModel> resolvedModels = ((ModelBakeryMixinAccessor) modelBakery).dtn__getResolvedModels();

        return new ModelBaker() {
            @Override
            public ResolvedModel getModel(Identifier location) {
                return resolvedModels.get(location);
            }

            @Override
            public BlockStateModelPart missingBlockModelPart() {
                return missingPart;
            }

            @Override
            public MaterialBaker materials() {
                return materialBaker;
            }

            @Override
            public ModelBaker.Interner interner() {
                // No-op interner (no deduplication, acceptable for small mod)
                return new ModelBaker.Interner() {
                    @Override
                    public Vector3fc vector(Vector3fc v) { return v; }

                    @Override
                    public net.minecraft.client.resources.model.geometry.BakedQuad.MaterialInfo materialInfo(
                        net.minecraft.client.resources.model.geometry.BakedQuad.MaterialInfo m
                    ) { return m; }
                };
            }

            @Override
            public <T> T compute(ModelBaker.SharedOperationKey<T> key) {
                return key.compute(this);
            }
        };
    }
}
