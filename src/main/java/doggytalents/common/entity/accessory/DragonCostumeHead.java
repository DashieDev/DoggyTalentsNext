package doggytalents.common.entity.accessory;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;

public class DragonCostumeHead extends Glasses implements IAccessoryHasModel {

    public DragonCostumeHead(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DRAGON_COSTUME_HEAD;
    }
    public static class DragonCostumeHeadItem extends AccessoryItem {


        public DragonCostumeHeadItem(Supplier<? extends Accessory> type, Properties properties) {
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