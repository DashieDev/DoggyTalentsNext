package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.DoggyItemGroups;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class ScentTreatItem extends Item {

    public static String SCENT_BLOCK_ID = "DTN_scented_block_id";
    
    public ScentTreatItem() {
        super((new Properties().stacksTo(1)).tab(DoggyItemGroups.GENERAL));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        if (handleClearScent(state, context).shouldSwing())
            return InteractionResult.SUCCESS;
        if (handleAddScent(state, context).shouldSwing())
            return InteractionResult.SUCCESS;

        return InteractionResult.FAIL;
    }

    private InteractionResult handleClearScent(BlockState state, UseOnContext context) {
        if (!state.is(Blocks.WATER_CAULDRON))
            return InteractionResult.PASS;
        
        var stack = context.getItemInHand();
        var tag = stack.getOrCreateTag();
        if (!tag.contains(SCENT_BLOCK_ID))
            return InteractionResult.PASS;

        if (context.getLevel().isClientSide)
            return InteractionResult.SUCCESS;
        
        stack.setTag(new CompoundTag());

        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleAddScent(BlockState state, UseOnContext context) {
        if (state.is(Blocks.WATER_CAULDRON))
            return InteractionResult.PASS;
        
        var stack = context.getItemInHand();
        var tag = stack.getOrCreateTag();
        if (tag.contains(SCENT_BLOCK_ID))
            return InteractionResult.PASS;

        if (context.getLevel().isClientSide)
            return InteractionResult.SUCCESS;

        var block = state.getBlock();
        var id = ForgeRegistries.BLOCKS.getKey(block);
        NBTUtil.putResourceLocation(tag, SCENT_BLOCK_ID, id);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(ComponentUtil.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
        var tag = stack.getOrCreateTag();
        if (!tag.contains(SCENT_BLOCK_ID))
            return;
        var block = NBTUtil.getRegistryValue(tag, SCENT_BLOCK_ID, ForgeRegistries.BLOCKS);
        if (block == null)
            return;
        components.add(Component.translatable(this.getDescriptionId() + ".scented_block")
            .withStyle(Style.EMPTY.withColor(0xffffea2e).withBold(true)));
        components.add(
            Component.translatable(block.asItem().getDescriptionId()).withStyle(
                Style.EMPTY.withItalic(true).withColor(0xff6fe86b)
            )
        );
    }
}
