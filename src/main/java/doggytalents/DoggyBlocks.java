package doggytalents;

import doggytalents.common.block.DogBathBlock;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.FoodBowlBlock;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.block.crops.RiceCropBlock;
import doggytalents.common.block.crops.SoyCropBlock;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DoggyItems.ITEMS;

    public static final Supplier<DogBedBlock> DOG_BED = registerWithItem("dog_bed", DogBedBlock::new, (prop) -> prop);
    public static final Supplier<DogBathBlock> DOG_BATH = registerWithItem("dog_bath", DogBathBlock::new);
    public static final Supplier<FoodBowlBlock> FOOD_BOWL = registerWithItem("food_bowl", FoodBowlBlock::new);
    public static final Supplier<RiceMillBlock> RICE_MILL = registerWithItem("rice_mill", RiceMillBlock::new);

    public static final Supplier<RiceCropBlock> RICE_CROP = register("rice_crop", RiceCropBlock::new);
    public static final Supplier<SoyCropBlock> SOY_CROP = register("soy_crop", SoyCropBlock::new);

    private static Item.Properties createInitialProp() {
        return new Item.Properties();
    }

    private static BlockItem makeItemBlock(Block block) {
        return makeItemBlock(block, null);
    }

    private static BlockItem makeItemBlock(Block block, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Item.Properties prop = createInitialProp();
        return new BlockItem(block, extraPropFunc != null ? extraPropFunc.apply(prop) : prop);
    }

    private static <T extends Block> Supplier<T> registerWithItem(final String name, final Supplier<T> blockSupplier, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        return register(name, blockSupplier, (b) -> makeItemBlock(b.get(), extraPropFunc));
    }

    private static <T extends Block> Supplier<T> registerWithItem(final String name, final Supplier<T> blockSupplier) {
        return register(name, blockSupplier, (b) -> makeItemBlock(b.get()));
    }

    private static <T extends Block> Supplier<T> register(final String name, final Supplier<T> blockSupplier, final Function<Supplier<T>, Item> itemFunction) {
        Supplier<T> blockObj = register(name, blockSupplier);
        ITEMS.register(name, () -> itemFunction.apply(blockObj));
        return blockObj;
    }

    private static <T extends Block> Supplier<T> register(final String name, final Supplier<T> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    public static void registerBlockColours(final RegisterColorHandlersEvent.Block event) {
        //BlockColors blockColors = event.getBlockColors();

        //Util.acceptOrElse(DoggyBlocks.DOG_BATH, (block) -> {
            event.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : -1;
             }, DoggyBlocks.DOG_BATH.get());
        //}, DoggyBlocks::logError);
    }

    public static void logError() {
        // Only try to register if blocks were successfully registered
        // Trying to avoid as reports like DoggyTalents#242, where it says
        // DoggyTalents crashed but is not the CAUSE of the crash

         DoggyTalentsNext.LOGGER.info("Items/Blocks were not registered for some reason... probably beacuse we are c...r..a..s.hing");
    }
}
