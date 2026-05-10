package doggytalents.common.entity.misc;

import doggytalents.DoggyItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SamoyedPlushie extends BaseDogPlushie {

    public SamoyedPlushie(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public ItemStack getDogPlusieItemDrop() {
        var item = DoggyItems.SAMOYED_PLUSHIE_TOY.get();
        var stack = new ItemStack(item);
        return stack;
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
    }
}
