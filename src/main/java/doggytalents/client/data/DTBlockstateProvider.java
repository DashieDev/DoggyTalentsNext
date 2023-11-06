package doggytalents.client.data;

import doggytalents.DoggyBlocks;
import doggytalents.common.lib.Constants;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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

public class DTBlockstateProvider extends BlockStateProvider {

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
        waterBowl(DoggyBlocks.WATER_BOWL);
        createFromShape(DoggyBlocks.FOOD_BOWL, new AABB(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D));
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

    protected void waterBowl(Supplier<? extends Block> blockIn) {
      BlockModelBuilder model = this.models()
        .getBuilder(name(blockIn.get()))
        .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
        .texture("particle", blockTexture(Blocks.IRON_BLOCK))
        .texture("1", blockTexture(DoggyBlocks.WATER_BOWL.get()))
        .ao(false);
      
        var element0 = model.element()
        .from(1f, 0f, 1f)
        .to(15f, 1f, 15f);
        element0.face(Direction.NORTH).texture("#1").uvs(4.25f, 7.75f, 7.75f, 8f);
        element0.face(Direction.EAST).texture("#1").uvs(0.75f, 7.75f, 4.25f, 8f);
        element0.face(Direction.SOUTH).texture("#1").uvs(11.25f, 7.75f, 14.75f, 8f);
        element0.face(Direction.WEST).texture("#1").uvs(7.75f, 7.75f, 11.25f, 8f);
        element0.face(Direction.UP).texture("#1").uvs(7.75f, 7.75f, 4.25f, 4.25f);
        element0.face(Direction.DOWN).texture("#1").uvs(11.25f, 4.25f, 7.75f, 7.75f);
        
        var element1 = model.element()
        .from(1.75f, 0.5f, 1.75f)
        .to(14.25f, 2f, 14.25f);
        element1.face(Direction.NORTH).texture("#1").uvs(5.75f, 11f, 8.75f, 11.25f);
        element1.face(Direction.EAST).texture("#1").uvs(2.75f, 11f, 5.75f, 11.25f);
        element1.face(Direction.SOUTH).texture("#1").uvs(11.75f, 11f, 14.75f, 11.25f);
        element1.face(Direction.WEST).texture("#1").uvs(8.75f, 11f, 11.75f, 11.25f);
        element1.face(Direction.UP).texture("#1").uvs(8.75f, 11f, 5.75f, 8f);
        element1.face(Direction.DOWN).texture("#1").uvs(11.75f, 8f, 8.75f, 11f);
        
        var element2 = model.element()
        .from(2.5f, 1.25f, 2.5f)
        .to(13.5f, 2.25f, 13.5f);
        element2.face(Direction.NORTH).texture("#1").uvs(3f, 3f, 6f, 3.5f);
        element2.face(Direction.EAST).texture("#1").uvs(0f, 3f, 3f, 3.5f);
        element2.face(Direction.SOUTH).texture("#1").uvs(9f, 3f, 12f, 3.5f);
        element2.face(Direction.WEST).texture("#1").uvs(6f, 3f, 9f, 3.5f);
        element2.face(Direction.UP).texture("#1").uvs(6f, 3f, 3f, 0f);
        element2.face(Direction.DOWN).texture("#1").uvs(9f, 0f, 6f, 3f);
        
        var element3 = model.element()
        .from(2.25f, 1.5f, 2.25f)
        .to(13.75f, 3f, 3.75f);
        element3.face(Direction.NORTH).texture("#1").uvs(4.25f, 11.25f, 7.25f, 11.75f);
        element3.face(Direction.EAST).texture("#1").uvs(3.75f, 11.25f, 4.25f, 11.75f);
        element3.face(Direction.SOUTH).texture("#1").uvs(7.75f, 11.25f, 10.75f, 11.75f);
        element3.face(Direction.WEST).texture("#1").uvs(7.25f, 11.25f, 7.75f, 11.75f);
        element3.face(Direction.UP).texture("#1").uvs(7.25f, 11.25f, 4.25f, 10.75f);
        element3.face(Direction.DOWN).texture("#1").uvs(10.25f, 10.75f, 7.25f, 11.25f);
        
        var element4 = model.element()
        .from(2.2501f, 1.5001f, 2.2501f)
        .to(3.7499f, 2.9999f, 13.7499f);
        element4.face(Direction.NORTH).texture("#1").uvs(6.75f, 13.75f, 7.25f, 14.25f);
        element4.face(Direction.EAST).texture("#1").uvs(3.75f, 13.75f, 6.75f, 14.25f);
        element4.face(Direction.SOUTH).texture("#1").uvs(10.25f, 13.75f, 10.75f, 14.25f);
        element4.face(Direction.WEST).texture("#1").uvs(7.25f, 13.75f, 10.25f, 14.25f);
        element4.face(Direction.UP).texture("#1").uvs(7.25f, 13.75f, 6.75f, 10.75f);
        element4.face(Direction.DOWN).texture("#1").uvs(7.75f, 10.75f, 7.25f, 13.75f);
        
        var element5 = model.element()
        .from(12.2501f, 1.5001f, 2.2501f)
        .to(13.7499f, 2.9999f, 13.7499f);
        element5.face(Direction.NORTH).texture("#1").uvs(6.75f, 13.75f, 7.25f, 14.25f);
        element5.face(Direction.EAST).texture("#1").uvs(3.75f, 13.75f, 6.75f, 14.25f);
        element5.face(Direction.SOUTH).texture("#1").uvs(10.25f, 13.75f, 10.75f, 14.25f);
        element5.face(Direction.WEST).texture("#1").uvs(7.25f, 13.75f, 10.25f, 14.25f);
        element5.face(Direction.UP).texture("#1").uvs(7.25f, 13.75f, 6.75f, 10.75f);
        element5.face(Direction.DOWN).texture("#1").uvs(7.75f, 10.75f, 7.25f, 13.75f);
        
        var element6 = model.element()
        .from(2.25f, 1.5f, 12.25f)
        .to(13.75f, 3f, 13.75f);
        element6.face(Direction.NORTH).texture("#1").uvs(4.25f, 11.25f, 7.25f, 11.75f);
        element6.face(Direction.EAST).texture("#1").uvs(3.75f, 11.25f, 4.25f, 11.75f);
        element6.face(Direction.SOUTH).texture("#1").uvs(7.75f, 11.25f, 10.75f, 11.75f);
        element6.face(Direction.WEST).texture("#1").uvs(7.25f, 11.25f, 7.75f, 11.75f);
        element6.face(Direction.UP).texture("#1").uvs(7.25f, 11.25f, 4.25f, 10.75f);
        element6.face(Direction.DOWN).texture("#1").uvs(10.25f, 10.75f, 7.25f, 11.25f);
        element6.rotation().angle(0f).axis(Axis.Y).origin(0f, 0f, 16f);

        this.simpleBlock(blockIn.get(), model);
    }

    private String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
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
