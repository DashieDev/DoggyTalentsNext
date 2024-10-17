package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.common.entity.accessory.DoubleDyeableAccessory;

public class StripedScarfItem extends DoubleDyeableAccessoryItem {
    
    public StripedScarfItem(Supplier<? extends DoubleDyeableAccessory> type, Properties properties) {
        super(type, properties);
    }
    
}