package doggytalents.common.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.client.screen.AmnesiaBoneScreen.AmneisaBoneScreen;
import doggytalents.common.entity.Dog;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class AmnesiaBoneItem extends Item implements IDogItem  {

    public AmnesiaBoneItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level level, Player player,
        InteractionHand hand) {
        var ownerUUID = dog.getOwnerUUID();
        if (ownerUUID == null) {
            return InteractionResult.FAIL;
        }
        if (level.isClientSide && hand == InteractionHand.MAIN_HAND && dog instanceof Dog d) {
            if (ownerUUID.equals(player.getUUID())) {
                AmneisaBoneScreen.open(d);
            }
        }
        requestOwnership(level, ownerUUID, player, hand, dog);
        return InteractionResult.SUCCESS;
    }

    private void requestOwnership(Level level, UUID ownerUUID, Player player,
        InteractionHand hand, AbstractDog dog) {
        if (level.isClientSide) return;
        var playerUUID = player.getUUID();
        if (ownerUUID.equals(playerUUID)) {
            return;
        };
        if (!(dog instanceof Dog)) return;
        var d = (Dog)dog;
        if (!d.willObeyOthers()) {
            player.displayClientMessage(Component.translatable("item.doggytalents.amnesia_bone.reject")
                .withStyle(ChatFormatting.RED) ,true);
            return;
        }
        var stack = player.getItemInHand(hand);
        if (stack.getItem() != this) return;
        var tag = stack.getOrCreateTag();
        if (tag.hasUUID("request_uuid")
            && tag.getUUID("request_uuid").equals(playerUUID)) 
                return;
        tag.putUUID("request_uuid", player.getUUID());
        tag.putString("request_str", player.getName().getString());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flag) {
        var tag = stack.getTag();
        if (tag == null) return;
        if (tag.contains("amnesia_bone_used_time", Tag.TAG_INT)) {
            components.add(
                Component.translatable("item.doggytalents.amnesia_bone.use_status", 
                getUseCap() - tag.getInt("amnesia_bone_used_time"), getUseCap())
                .withStyle(ChatFormatting.RED));
        }
        if (tag.contains("request_str", Tag.TAG_STRING)) {
            components.add(
                Component.translatable("item.doggytalents.amnesia_bone.status", tag.getString("request_str"))
                .withStyle(
                    ChatFormatting.GRAY
                )
            );
        }
    }

    public static int getUseCap() { return 8; }
    public static int getUntameXPost() { return 3; }
    public static int getMigrateOwnerXPCost() { return 5; }
}
