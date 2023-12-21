package doggytalents.client.data;

import doggytalents.DoggyBlocks;
import doggytalents.common.block.crops.DogCropBlock;
import doggytalents.common.lib.Constants;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;

import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import java.util.stream.Collectors;


public class DTBlockstateProvider extends BlockStateProvider {

    private static final String RENDERTYPE_CUTOUT = "cutout";

    public DTBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    public ExistingFileHelper getExistingHelper() {
        return this.models().existingFileHelper;
    }

    @Override
    public String getName() {
        return "DoggyTalents Blockstates/Block Models";
    }

    @Override
    protected void registerStatesAndModels() {
        dogBath(DoggyBlocks.DOG_BATH);
        dogBed(DoggyBlocks.DOG_BED);
        createFromShape(DoggyBlocks.FOOD_BOWL, new AABB(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D));

        doggyCrops(DoggyBlocks.RICE_CROP);
    }

    // Applies texture to all faces and for the input face culls that direction
    private static BiFunction<String, Direction, BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder>> cullFaceFactory = (texture, input) -> (d, b) -> b.texture(texture).cullface(d == input ? d : null);

    protected void createFromShape(Supplier<? extends Block> blockIn, AABB bb) {
        BlockModelBuilder model = this.models()
                .getBuilder(name(blockIn.get()))
                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                .texture("particle", extend(blockTexture(blockIn.get()), "_bottom"))
                .texture("bottom", extend(blockTexture(blockIn.get()), "_bottom"))
                .texture("top", extend(blockTexture(blockIn.get()), "_top"))
                .texture("side", extend(blockTexture(blockIn.get()), "_side"));

        model.element()
          .from((float) bb.minX, (float) bb.minY, (float) bb.minZ)
            .to((float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
            .allFaces((d, f) -> f.cullface(d == Direction.DOWN ? d : null).texture(d.getAxis().isHorizontal() ? "#side" : d == Direction.DOWN ? "#bottom" : "#top"));

        this.simpleBlock(blockIn.get(), model);
    }


    protected void dogBed(Supplier<? extends Block> blockIn) {
        BlockModelBuilder model = this.models()
                .getBuilder(name(blockIn.get()))
                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                .texture("particle", blockTexture(Blocks.OAK_PLANKS))
                .texture("bedding", blockTexture(Blocks.WHITE_WOOL))
                .texture("casing", blockTexture(Blocks.OAK_PLANKS))
                .ao(false);

        model.element()
          .from(1.6F, 3.2F, 1.6F)
          .to(14.4F, 6.4F, 14.4F)
          .face(Direction.UP).texture("#bedding").end()
          .face(Direction.NORTH).texture("#bedding");

        model.element() //base
          .from(0, 0, 0)
          .to(16, 3.2F, 16)
          .allFaces(cullFaceFactory.apply("#casing", Direction.DOWN));

        model.element()
          .from(11.2F, 3.2F, 0)
          .to(16, 9.6F, 1.6F)
          .allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));

        model.element()
          .from(0, 3.2F, 0)
          .to(4.8F, 9.6F, 1.6F)
          .allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));

        model.element()
          .from(14.4F, 3.2F, 0)
          .to(16, 9.6F, 16)
          .allFaces(cullFaceFactory.apply("#casing", Direction.EAST));

        model.element()
          .from(0, 3.2F, 14.4F)
          .to(16, 9.6F, 16)
          .allFaces(cullFaceFactory.apply("#casing", Direction.SOUTH));

        model.element()
          .from(0, 3.2F, 0)
          .to(1.6F, 9.6F, 16)
          .allFaces(cullFaceFactory.apply("#casing", Direction.WEST));

        this.simpleBlock(blockIn.get(), model);
    }

    protected void dogBath(Supplier<? extends Block> blockIn) {
        BlockModelBuilder model = this.models()
                .getBuilder(name(blockIn.get()))
                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                .texture("particle", blockTexture(Blocks.IRON_BLOCK))
                .texture("water", extend(blockTexture(Blocks.WATER), "_still"))
                .texture("side", blockTexture(Blocks.IRON_BLOCK))
                .texture("bottom", blockTexture(Blocks.IRON_BLOCK))
                .ao(false);

        model.element()
          .from(1, 0, 1)
          .to(15, 6, 15)
          .face(Direction.UP).texture("#water").tintindex(0);

        model.element()
          .from(1, 0, 1)
          .to(15, 6, 15)
          .face(Direction.DOWN).texture("#bottom");

        model.element()
          .from(0, 0, 0)
          .to(16, 8, 1)
          .allFaces(cullFaceFactory.apply("#side", Direction.NORTH));

        model.element()
          .from(15, 0, 0)
          .to(16, 8, 16)
          .allFaces(cullFaceFactory.apply("#side", Direction.EAST));

        model.element()
          .from(0, 0, 15)
          .to(16, 8, 16)
          .allFaces(cullFaceFactory.apply("#side", Direction.SOUTH));

        model.element()
          .from(0, 0, 0)
          .to(1, 8, 16)
          .allFaces(cullFaceFactory.apply("#side", Direction.WEST));

        this.simpleBlock(blockIn.get(), model);
    }

    private void doggyCrops(Supplier<? extends DogCropBlock> cropBlockSup) {
      var cropBlock = cropBlockSup.get();
      var variantBuilder = this.getVariantBuilder(cropBlock);
      var ageProp = cropBlock.getAgeProperty();
      var possibleAges = ageProp.getAllValues().collect(Collectors.toList());

      for (var age : possibleAges) {
        var partialState = variantBuilder.partialState()
          .with(ageProp, age.value());
        var modelName = cropState(cropBlock, age.value());
        var modelTexture = cropTexture(cropBlock, age.value());
        var model = this.models()
          .crop(modelName, modelTexture)
          .renderType(RENDERTYPE_CUTOUT);
        
        variantBuilder.addModels(partialState, new ConfiguredModel(model));
      }
    }

    private String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private String cropState(DogCropBlock block, int age) {
      return name(block) + "/stage_" + age;
    }

    private ResourceLocation cropTexture(DogCropBlock block, int age) {
      return extend(blockTexture(block), "/stage_" + age);
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation base = ForgeRegistries.BLOCKS.getKey(block);
        return prextend(base, ModelProvider.BLOCK_FOLDER + "/");
    }

    public ModelFile cross(Supplier<? extends Block> block) {
        return this.models().cross(name(block.get()), blockTexture(block.get()));
    }


    protected void makeSimple(Supplier<? extends Block> blockIn) {
        this.simpleBlock(blockIn.get());
    }

    private ResourceLocation prextend(ResourceLocation rl, String prefix) {
        return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
