package doggytalents;

import doggytalents.common.block.DogBathBlock;
import doggytalents.common.block.DogBedBlock;
import doggytalents.common.block.FoodBowlBlock;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.block.crops.RiceCropBlock;
import doggytalents.common.block.crops.SoyCropBlock;
import doggytalents.common.lib.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DoggyItems.ITEMS;

    public static final Supplier<DogBedBlock> DOG_BED = registerWithCustomItem("dog_bed", DogBedBlock::new,
        (block, props) -> new BlockItem(block, props) {
            @Override
            public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
                super.appendHoverText(stack, ctx, display, tooltip, flag);
                DogBedBlock.addBedTooltip(stack, tooltip, flag);
            }
        });

    public static final Supplier<DogBathBlock> DOG_BATH = registerWithCustomItem("dog_bath", DogBathBlock::new,
        (block, props) -> new BlockItem(block, props) {
            @Override
            public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
                super.appendHoverText(stack, ctx, display, tooltip, flag);
                tooltip.accept(Component.translatable("block.doggytalents.dog_bath.description")
                    .withStyle(Style.EMPTY.withItalic(true)));
            }
        });

    public static final Supplier<FoodBowlBlock> FOOD_BOWL = registerWithCustomItem("food_bowl", FoodBowlBlock::new,
        (block, props) -> new BlockItem(block, props) {
            @Override
            public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
                super.appendHoverText(stack, ctx, display, tooltip, flag);
                tooltip.accept(Component.translatable("block.doggytalents.food_bowl.pro_tip.desc")
                    .withStyle(Style.EMPTY.withItalic(true)));
            }
        });

    public static final Supplier<RiceMillBlock> RICE_MILL = registerWithItem("rice_mill", RiceMillBlock::new, null);

    public static final Supplier<RiceCropBlock> RICE_CROP = register("rice_crop", RiceCropBlock::new);
    public static final Supplier<SoyCropBlock> SOY_CROP = register("soy_crop", SoyCropBlock::new);

    private static <T extends Block> Supplier<T> registerWithCustomItem(final String name,
            final Function<BlockBehaviour.Properties, T> blockConstructor,
            BiFunction<T, Item.Properties, BlockItem> itemFactory) {
        Supplier<T> blockObj = register(name, blockConstructor);
        ITEMS.registerItem(name, props -> itemFactory.apply(blockObj.get(), props));
        return blockObj;
    }

    private static <T extends Block> Supplier<T> registerWithItem(final String name,
            final Function<BlockBehaviour.Properties, T> blockConstructor,
            @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Supplier<T> blockObj = register(name, blockConstructor);
        ITEMS.registerItem(name, props -> {
            Item.Properties p = extraPropFunc != null ? extraPropFunc.apply(props) : props;
            return new BlockItem(blockObj.get(), p);
        });
        return blockObj;
    }

    private static <T extends Block> Supplier<T> register(final String name, final Function<BlockBehaviour.Properties, T> blockConstructor) {
        return BLOCKS.registerBlock(name, blockConstructor, BlockBehaviour.Properties::of);
    }

    public static void logError() {
        DoggyTalentsNext.LOGGER.info("Items/Blocks were not registered for some reason... probably beacuse we are c...r..a..s.hing");
    }
}
