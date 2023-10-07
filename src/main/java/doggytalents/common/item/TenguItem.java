package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;

public class TenguItem extends AccessoryItem{

    public TenguItem(Supplier<? extends TenguAccessory> type, Properties properties) {
        super(type, properties);
    }
    
}
