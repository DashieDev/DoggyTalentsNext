package doggytalents.common.entity.accessory;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
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
import net.minecraft.world.level.Level;

public class DragonCostumeSuit extends Clothing implements IAccessoryHasModel {

    public DragonCostumeSuit(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DRAGON_COSTUME_SUIT;
    }
    public static class DragonCostumeSuitItem extends AccessoryItem {

        public DragonCostumeSuitItem(Supplier<? extends Accessory> type, Properties properties) {
            super(type, properties);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level context, List<Component> components,
                TooltipFlag flags) {
            var desc_id = this.getDescriptionId(stack) + ".description";
            components.add(ComponentUtil.translatable(desc_id).withStyle(
                Style.EMPTY.withItalic(true)
            ));
        }
    }
}
