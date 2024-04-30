package doggytalents.common.entity.accessory;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class KitsuneMask extends Glasses implements IAccessoryHasModel {

    public KitsuneMask(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.KITSUNE_MASK;
    }
    @Override
    public AccessoryInstance getDefault() {
        return new Inst(this);
    }

    public static class Inst extends AccessoryInstance implements IDogAlteration {

        public boolean unwear = false;

        public Inst(Accessory typeIn) {
            super(typeIn);
        }

        @Override
        public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
                InteractionHand handIn) {

            if (dogIn.level().isClientSide)
            if (playerIn.getItemInHand(handIn).getItem() == Items.STRING) {
                unwear = !unwear;
            }

            return InteractionResult.PASS;
        }

    }
    public static class KitsuneMaskItem extends AccessoryItem {


        public KitsuneMaskItem(Supplier<? extends Accessory> type, Properties properties) {
            super(type, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
                TooltipFlag flags) {
            var desc_id = this.getDescriptionId(stack) + ".description";
            components.add(Component.translatable(desc_id).withStyle(
                Style.EMPTY.withItalic(true)
            ));
        }
    }
    
    
}
