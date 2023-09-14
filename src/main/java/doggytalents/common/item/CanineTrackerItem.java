package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.CanineTrackerScreen;
import doggytalents.common.storage.DogLocationData;
import doggytalents.common.storage.DogLocationStorage;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide) {
            if (playerIn.isShiftKeyDown()) {
                if (playerIn.getAbilities().instabuild) {
                    DogLocationStorage locationManager = DogLocationStorage.get(worldIn);
                    for (UUID uuid : locationManager.getAllUUID()) {
                        playerIn.sendMessage(ComponentUtil.literal(locationManager.getData(uuid).toString()), net.minecraft.Util.NIL_UUID);
                    }
                }
                return new InteractionResultHolder<>(InteractionResult.FAIL, playerIn.getItemInHand(handIn));
            }

            if (stack.getItem() instanceof CanineTrackerItem && stack.hasTag()) {
                stack.setTag(null);
            }
        } else {
            if (playerIn.isShiftKeyDown()) 
                return
                    new InteractionResultHolder<>(InteractionResult.FAIL, playerIn.getItemInHand(handIn));
            if (!stack.hasTag())
                CanineTrackerScreen.open();
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.hasTag()) {
            var text = getStatusText(stack.getTag());
            if (text != null) return text;
        }
        return ComponentUtil.translatable(this.getDescriptionId(stack));
    }

    private @Nullable Component getStatusText(CompoundTag tag) {
        if (tag == null)
            return null;
        if (!tag.contains("name", Tag.TAG_STRING))
            return null;
        var ret = ComponentUtil.translatable("item.doggytalents.radar.status", tag.getString("name"));
        int ret_color = 0xffffea2e;
        if (tag.contains("locateColor", Tag.TAG_INT)) {
            int tag_color = tag.getInt("locateColor");
            ret_color = tag_color != 0 ? tag_color : ret_color;
        }
        return ret.withStyle(
            Style.EMPTY.withColor(ret_color)
        );
    }
}
