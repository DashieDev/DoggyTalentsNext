package doggytalents.api.inferface;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class DogArmorItemHandler extends ItemStackHandler {

    protected final AbstractDog dog;

    public DogArmorItemHandler(AbstractDog dog) {
        super(4);
        this.dog = dog;
    }

    public abstract CompoundTag serializeNBT(HolderLookup.Provider prov);
    public abstract void deserializeNBT(HolderLookup.Provider prov, CompoundTag compound);

}
