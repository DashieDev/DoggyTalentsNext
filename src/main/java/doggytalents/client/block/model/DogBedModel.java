package doggytalents.client.block.model;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.block.DogBedMaterialManager.NaniBedding;
import doggytalents.common.block.DogBedMaterialManager.NaniCasing;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.lib.Constants;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class DogBedModel implements BakedModel {

    public static DogBedItemOverride ITEM_OVERIDE = new DogBedItemOverride();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    private ForgeModelBakery modelLoader;
    private BlockModel unbakedModel;
    private BakedModel defaultModelVariant;
    private final Map<Direction, BakedModel> missingModelVariant = new ConcurrentHashMap<>(Direction.values().length);

    private final Map<Triple<ICasingMaterial, IBeddingMaterial, Direction>, BakedModel> cache = Maps.newConcurrentMap();
    private final int maxCacheSize;

    public DogBedModel(ForgeModelBakery modelLoader, BlockModel model, BakedModel bakedModel, int maxCacheSize) {
        this.modelLoader = modelLoader;
        this.unbakedModel = model;
        this.defaultModelVariant = bakedModel;
        this.maxCacheSize = maxCacheSize;
    }

    public BakedModel getModelVariant(@Nonnull IModelData data) {
        return this.getModelVariant(data.getData(DogBedTileEntity.CASING), data.getData(DogBedTileEntity.BEDDING), data.getData(DogBedTileEntity.FACING));
    }

    public BakedModel getModelVariant(ICasingMaterial casing, IBeddingMaterial bedding, Direction facing) {
        if (casing == null || bedding == null)
            return defaultModelVariant;
        if (casing.isNani() || bedding.isNani())
            return getMissingVariant(facing);
        
        if (facing == null)
            facing = Direction.NORTH;
        var key = ImmutableTriple.of(casing, bedding, facing);
        var model_variant = this.cache.get(key);
        if (model_variant != null)
            return model_variant;
        
        if (this.cache.size() >= this.maxCacheSize)
            return defaultModelVariant;

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

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
        return this.getModelVariant(null, null, Direction.NORTH).getQuads(state, side, rand, EmptyModelData.INSTANCE);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        return this.getModelVariant(data).getQuads(state, side, rand, data);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull IModelData data) {
        return this.getModelVariant(data).getParticleIcon(data);
    }

    public BakedModel bakeModelVariant(@Nullable ICasingMaterial casingResource, @Nullable IBeddingMaterial beddingResource, @Nonnull Direction facing) {
        List<BlockElement> parts = this.unbakedModel.getElements();
        List<BlockElement> elements = new ArrayList<>(parts.size()); //We have to duplicate this so we can edit it below.
        for (BlockElement part : parts) {
            elements.add(new BlockElement(part.from, part.to, Maps.newHashMap(part.faces), part.rotation, part.shade));
        }

        BlockModel newModel = new BlockModel(this.unbakedModel.getParentLocation(), elements,
            Maps.newHashMap(this.unbakedModel.textureMap), this.unbakedModel.hasAmbientOcclusion(), this.unbakedModel.getGuiLight(),
            this.unbakedModel.getTransforms(), new ArrayList<>(this.unbakedModel.getOverrides()));
        newModel.name = this.unbakedModel.name;
        newModel.parent = this.unbakedModel.parent;


        Either<Material, String> casingTexture = findCasingTexture(casingResource);
        newModel.textureMap.put("bedding", findBeddingTexture(beddingResource));
        newModel.textureMap.put("casing", casingTexture);
        newModel.textureMap.put("particle", casingTexture);

        return newModel.bake(this.modelLoader, newModel, ForgeModelBakery.defaultTextureGetter(), getModelRotation(facing), createResourceVariant(casingResource, beddingResource, facing), true);
    }

    private ResourceLocation createResourceVariant(@Nonnull ICasingMaterial casingResource, @Nonnull IBeddingMaterial beddingResource, @Nonnull Direction facing) {
        String beddingKey = beddingResource != null
                ? DogBedMaterialManager.getKey(beddingResource).toString().replace(':', '.')
                : "doggytalents.dogbed.bedding.missing";
        String casingKey = beddingResource != null
                ? DogBedMaterialManager.getKey(casingResource).toString().replace(':', '.')
                : "doggytalents.dogbed.casing.missing";
        return new ModelResourceLocation(Constants.MOD_ID, "block/dog_bed#bedding=" + beddingKey + ",casing=" + casingKey + ",facing=" + facing.getName());
    }

    private Either<Material, String> findCasingTexture(@Nullable ICasingMaterial resource) {
        return findTexture(resource != null ? resource.getTexture() : null);
    }

    private Either<Material, String> findBeddingTexture(@Nullable IBeddingMaterial resource) {
        return findTexture(resource != null ? resource.getTexture() : null);
    }

    private Either<Material, String> findTexture(@Nullable ResourceLocation resource) {
        if (resource == null) {
            resource = MISSING_TEXTURE;
        }

        return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resource));
    }

    private BlockModelRotation getModelRotation(@Nonnull Direction dir) {
        switch (dir) {
        default:    return BlockModelRotation.X0_Y0; // North
        case EAST:  return BlockModelRotation.X0_Y90;
        case SOUTH: return BlockModelRotation.X0_Y180;
        case WEST:  return BlockModelRotation.X0_Y270;
        }
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
        return ITEM_OVERIDE;
    }
}
