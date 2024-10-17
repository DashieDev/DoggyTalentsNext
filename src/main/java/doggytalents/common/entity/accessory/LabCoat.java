package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.datafixers.types.templates.List;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class LabCoat extends DyeableAccessory implements IAccessoryHasModel{

    public LabCoat(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.CLOTHING, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.LAB_COAT;
    }
    public static class LabCoatItem extends DyeableAccessoryItem {

        public LabCoatItem(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }
    }
}
