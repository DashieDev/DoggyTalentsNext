package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.DoggyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class StarterBundleItem extends Item {

    public static List<Supplier<? extends Item>> STARTER_ITEMS = List.of(
        DoggyItems.DOGGY_CHARM,
        DoggyItems.WHISTLE,
        DoggyItems.RADIO_COLLAR,
        DoggyItems.CANINE_TRACKER,
        () -> Items.STICK,
        () -> Items.BONE
    );

    public StarterBundleItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (level.isClientSide())
            return InteractionResult.SUCCESS;
        
        var inv = player.getInventory();
        var items = inv.getNonEquipmentItems();
        boolean hasEnoughSpace = false; 
        int freeSlot = 0;
        for (var item : items) {
            if (item.isEmpty())
                ++freeSlot;
            if (freeSlot >= STARTER_ITEMS.size()) {
                hasEnoughSpace = true;
                break;
            }
        }
        if (!hasEnoughSpace) {
            player.sendOverlayMessage(
                Component.translatable("item.doggytalents.starter_bundle.fail")
                    .withStyle(ChatFormatting.RED));
            return InteractionResult.SUCCESS;
        }
           
        
        for (var regItem : STARTER_ITEMS) {
            var starterStack = new ItemStack(regItem.get());
            inv.add(starterStack);
        }
        player.setItemInHand(hand, ItemStack.EMPTY);

        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        components.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
        for (var item : STARTER_ITEMS) {
            var c1 = Component.translatable("item.doggytalents.starter_bundle.contains", 
                1, Component.translatable(item.get().getDescriptionId()));
            components.accept(c1);
        }
    }

}
