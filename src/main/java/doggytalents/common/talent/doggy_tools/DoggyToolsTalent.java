package doggytalents.common.talent.doggy_tools;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class DoggyToolsTalent extends TalentInstance  {

    private DoggyToolsItemHandler tools;
    private byte selectSlot = -1;

    public DoggyToolsTalent(Talent talentIn, int level) {
        super(talentIn, level);
        this.tools = new DoggyToolsItemHandler(getSize(level));
    }

    private int getSize(int level) {
        return level;
    }

    public void selectSlot(int slot) {
        Inventory
    }

    public void deselectSlot() {
        this.selectedSlot = -1;
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        var tag = new CompoundTag();
        
        tag.put("tool_inv", tools.serializeNBT());

        compound.put("doggy_tools", tag);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        var tag = compound.getCompound("doggy_tools");
        if (tag == null) return;
        var inv_tag = tag.getCompound("tool_inv");
        if (inv_tag != null) {
            this.tools.deserializeNBT(inv_tag);
        }
    }
}
