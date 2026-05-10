package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.client.screen.CanineTrackerScreen;
import doggytalents.common.storage.DogLocationData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.ItemUtil;
import net.minecraft.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class CanineTrackerItem extends Item {

    public CanineTrackerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        var stack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide()) {
            if (stack.getItem() instanceof CanineTrackerItem && ItemUtil.hasTag(stack)) {
                ItemUtil.clearTag(stack);
            }
        } else {
            if (!ItemUtil.hasTag(stack))
                CanineTrackerScreen.open();
        }
        return InteractionResult.FAIL; // stack: stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (ItemUtil.hasTag(stack)) {
            var text = getStatusText(ItemUtil.getTag(stack));
            if (text != null) return text; 
        }
        return Component.translatable(this.getDescriptionId());
    }

    private @Nullable Component getStatusText(CompoundTag tag) {
        if (tag == null)
            return null;
        if (!tag.contains("name"))
            return null;
        var ret = Component.translatable("item.doggytalents.radar.status", tag.getStringOr("name", ""));
        int ret_color = 0xffffea2e;
        if (tag.contains("locateColor")) {
            int tag_color = tag.getIntOr("locateColor", 0);
            ret_color = tag_color != 0 ? tag_color : ret_color;
        }
        return ret.withStyle(
            Style.EMPTY.withColor(ret_color)
        );
    }
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> componentConsumer, TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        componentConsumer.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
}
