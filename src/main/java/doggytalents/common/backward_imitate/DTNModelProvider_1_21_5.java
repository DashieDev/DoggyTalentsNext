package doggytalents.common.backward_imitate;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import doggytalents.DoggyBlocks;
import doggytalents.client.data.DTItemModelProvider;
import doggytalents.common.block.crops.DogCropBlock;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.generators.template.ElementBuilder;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.neoforged.neoforge.client.model.generators.template.FaceBuilder;

public class DTNModelProvider_1_21_5 extends ModelProvider {

    //Dog Bed
    public static final TextureSlot BEDDING = TextureSlot.create("bedding");
    public static final TextureSlot CASING = TextureSlot.create("casing");

    //Dog Bath
    public static final TextureSlot WATER = TextureSlot.create("water");


    private static final String RENDERTYPE_CUTOUT = "cutout";

    public DTNModelProvider_1_21_5(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlocks(blockModels);
        registerItem(itemModels);
    }

    //Block ===================================

    private void registerBlocks(BlockModelGenerators blockModels) {
        registerDogBed(blockModels);
        registerDogBath(blockModels);
        registerFoodBowl(blockModels);
        registerDoggyCrops(blockModels, DoggyBlocks.RICE_CROP);
        registerDoggyCrops(blockModels, DoggyBlocks.SOY_CROP);
        registerParticleOnly(blockModels, DoggyBlocks.RICE_MILL.get(), Blocks.OAK_PLANKS);
    }

    private void registerDogBed(BlockModelGenerators blockModels) {
        var template_builder = ExtendedModelTemplateBuilder.builder();
        template_builder.parent(vanillaBlockModelParent())
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(BEDDING)
            .requiredTextureSlot(CASING)
            .ambientOcclusion(false);
        
        template_builder.element(
            b -> b
            .from(1.6F, 3.2F, 1.6F)
            .to(14.4F, 6.4F, 14.4F)
            .face(Direction.UP, b1 -> b1.texture(BEDDING))
            .face(Direction.NORTH, b1 -> b1.texture(BEDDING))
        );

        template_builder.element(
            b -> b
            .from(0, 0, 0)
            .to(16, 3.2F, 16)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.DOWN))
        );

        template_builder.element(
            b -> b
            .from(11.2F, 3.2F, 0)
            .to(16, 9.6F, 1.6F)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.NORTH))
        );

        template_builder.element(
            b -> b
            .from(0, 3.2F, 0)
            .to(4.8F, 9.6F, 1.6F)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.NORTH))
        );

        template_builder.element(
            b -> b
            .from(14.4F, 3.2F, 0)
            .to(16, 9.6F, 16)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.EAST))
        );

        template_builder.element(
            b -> b
            .from(0, 3.2F, 14.4F)
            .to(16, 9.6F, 16)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.SOUTH))
        );

        template_builder.element(
            b -> b
            .from(0, 3.2F, 0)
            .to(1.6F, 9.6F, 16)
            .allFaces(textureAndCullForDirectionConsumer(CASING, Direction.WEST))
        );
        var template = template_builder.build();

        Function<Block, TextureMapping> default_texture_provider = 
            block -> new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.OAK_PLANKS))
                .put(BEDDING, TextureMapping.getBlockTexture(Blocks.WHITE_WOOL))
                .put(CASING, TextureMapping.getBlockTexture(Blocks.OAK_PLANKS));


        var template_provider = TexturedModel.createDefault(default_texture_provider, template);
        

        blockModels.createTrivialBlock(DoggyBlocks.DOG_BED.get(), template_provider);
    }

    private void registerDogBath(BlockModelGenerators blockModels) {
        var template_builder = ExtendedModelTemplateBuilder.builder();
        template_builder.parent(vanillaBlockModelParent())
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(TextureSlot.SIDE)
            .requiredTextureSlot(TextureSlot.BOTTOM)
            .requiredTextureSlot(WATER)
            .ambientOcclusion(false);

        template_builder.element(
            b -> b
            .from(1, 0, 1)
            .to(15, 6, 15)
            .face(Direction.UP, b1 -> b1.texture(WATER).tintindex(0))
        );

        template_builder.element(
            b -> b
            .from(1, 0, 1)
            .to(15, 6, 15)
            .face(Direction.DOWN, b1 -> b1.texture(TextureSlot.BOTTOM))
        );

        template_builder.element(
            b -> b
            .from(0, 0, 0)
            .to(16, 8, 1)
            .allFaces(textureAndCullForDirectionConsumer(TextureSlot.SIDE, Direction.NORTH))
        );

        template_builder.element(
            b -> b
            .from(15, 0, 0)
            .to(16, 8, 16)
            .allFaces(textureAndCullForDirectionConsumer(TextureSlot.SIDE, Direction.EAST))
        );

        template_builder.element(
            b -> b
            .from(0, 0, 15)
            .to(16, 8, 16)
            .allFaces(textureAndCullForDirectionConsumer(TextureSlot.SIDE, Direction.SOUTH))
        );

        template_builder.element(
            b -> b
            .from(0, 0, 0)
            .to(1, 8, 16)
            .allFaces(textureAndCullForDirectionConsumer(TextureSlot.SIDE, Direction.WEST))
        );

        var template = template_builder.build();

        Function<Block, TextureMapping> texture_provider = 
            block -> new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK))
                .put(WATER, dogBathWaterTexture());

        var template_provider = TexturedModel.createDefault(texture_provider, template);

        blockModels.createTrivialBlock(DoggyBlocks.DOG_BATH.get(), template_provider);
    }

    private ResourceLocation dogBathWaterTexture() {
        return blockTextureWithExtent(Blocks.WATER, "_still");
    }

    private void registerFoodBowl(BlockModelGenerators blockModels) {
        var bb = new AABB(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
        var block = DoggyBlocks.FOOD_BOWL.get();
        var template_builder = ExtendedModelTemplateBuilder.builder();
        template_builder.parent(vanillaBlockModelParent())
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(TextureSlot.BOTTOM)
            .requiredTextureSlot(TextureSlot.TOP)
            .requiredTextureSlot(TextureSlot.SIDE);
        
        BiConsumer<Direction, FaceBuilder> foreach_face = (dir, builder) -> {
            builder
                .cullface(dir == Direction.DOWN ? dir : null)
                .texture(
                    dir.getAxis().isHorizontal() ? TextureSlot.SIDE 
                    : dir == Direction.DOWN ? TextureSlot.BOTTOM 
                    : TextureSlot.TOP
                );
        };

        template_builder.element(
            b -> b
            .from((float) bb.minX, (float) bb.minY, (float) bb.minZ)
            .to((float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
            .allFaces(foreach_face)
        );

        var template = template_builder.build();

        Function<Block, TextureMapping> texture_provider = 
            $ -> new TextureMapping()
                .put(TextureSlot.PARTICLE, blockTextureWithExtent(block, "_bottom"))
                .put(TextureSlot.BOTTOM, blockTextureWithExtent(block, "_bottom"))
                .put(TextureSlot.TOP, blockTextureWithExtent(block, "_top"))
                .put(TextureSlot.SIDE, blockTextureWithExtent(block, "_side"));

        var template_provider = TexturedModel.createDefault(texture_provider, template);

        blockModels.createTrivialBlock(block, template_provider);
    }

    private void registerDoggyCrops(BlockModelGenerators blockModels, 
        Supplier<? extends DogCropBlock> block_supplier) {
        
        var crop_block = block_supplier.get();
        var age_props = crop_block.getAgeProperty();
        var model = MultiVariantGenerator.dispatch(crop_block)
            .with(PropertyDispatch.initial(age_props).generate(
                age -> {
                    var variant_rl = blockModels.createSuffixedVariant(
                        crop_block, "/stage_" + age, 
                        ModelTemplates.CROP.extend()
                            .renderType(RENDERTYPE_CUTOUT).build(), 
                        TextureMapping::crop
                    );
                    return BlockModelGenerators.plainVariant(variant_rl);
                }                
            ));
        blockModels.blockStateOutput.accept(model);
    }

    private void registerParticleOnly(BlockModelGenerators blockModels, Block block, Block particle_block) {
        Function<Block, TextureMapping> texture_provider = 
            $ -> new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle_block));
        var template_provider = TexturedModel.createDefault(texture_provider, ModelTemplates.PARTICLE_ONLY);
        blockModels.createTrivialBlock(block, template_provider);
    }
    
    private ResourceLocation blockTextureWithExtent(Block block, String extend) {
        return blockTextureWithModifyPath(block, x -> x + extend);
    }

    private ResourceLocation blockTextureWithModifyPath(Block block, 
        Function<String, String> path_modifier) {
        var block_texture = TextureMapping.getBlockTexture(block);
        return Util.modifyPath(block_texture, path_modifier);
    }

    private ResourceLocation vanillaBlockModelParent() {
        return ModelLocationUtils.decorateBlockModelLocation(mcLocation("block").toString());
    }

    private BiConsumer<Direction, FaceBuilder> textureAndCullForDirectionConsumer(
        TextureSlot texture, Direction cull_direction) {
        
        return (dir, builder) -> {
            builder
                .cullface(cull_direction == dir ? cull_direction : null)
                .texture(texture);
        };
    }

    //Item ===================================

    private void registerItem(ItemModelGenerators itemModels) {
        var item_model_prov = new DTItemModelProvider();
        item_model_prov.itemGenerators_1_21_5 = itemModels;
        item_model_prov.registerModels();
        item_model_prov.itemGenerators_1_21_5 = null;
    }

    public static void generated(ItemModelGenerators itemModels, Supplier<? extends Item> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    public static void generated2(ItemModelGenerators itemModels, Supplier<? extends Item> item, String layer0, String layer1) {
        var layer0_rl = ModelLocationUtils.decorateItemModelLocation(Util.getResource(layer0).toString());
        var layer1_rl = ModelLocationUtils.decorateItemModelLocation(Util.getResource(layer1).toString());
        generated2(itemModels, item, layer0_rl, layer1_rl);
    }

    public static void generated2(ItemModelGenerators itemModels, Supplier<? extends Item> item, ResourceLocation layer0, ResourceLocation layer1) {
        var model = itemModels.generateLayeredItem(item.get(), layer0, layer1);
        itemModels.itemModelOutput.accept(item.get(), ItemModelUtils.plainModel(model));
    }

    public static void handheld(ItemModelGenerators itemModels, Supplier<? extends Item> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public static void dogBathItem(ItemModelGenerators itemModels, Supplier<? extends Block> block_supplier) {
        var block = block_supplier.get();
        var block_item = block.asItem();
        var block_model = ModelLocationUtils.getModelLocation(block);
        var item_model = ItemModelUtils.tintedModel(block_model, new Constant(4159204));
        itemModels.itemModelOutput.accept(block_item, item_model);
    }
}
