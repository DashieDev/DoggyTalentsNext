package doggytalents.common.talent.doggy_tools;

import java.util.Map;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import doggytalents.common.talent.doggy_tools.tool_actions.ToolAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class DoggyToolsTalent extends TalentInstance  {

    private DoggyToolsItemHandler tools;
    private Map<Item, ToolAction> TOOL_ACTION_MAP;

    public DoggyToolsTalent(Talent talentIn, int level) {
        super(talentIn, level);
        this.tools = new DoggyToolsItemHandler(getSize(level));
    }

    private int getSize(int level) {
        return level;
    }

    @Override
    public void init(AbstractDog dog) {
        TOOL_ACTION_MAP = ToolActionEntries.getToolActionMapFor((Dog) dog, this);
    }

    @Override
    public void tick(AbstractDog d) {
        if (d.level.isClientSide) return;
        
        if (!(d instanceof Dog dog)) return;

        if (dog.isBusy()) return;

        for (int i = 0; i < this.tools.getSlots(); ++i) {
            var stack = this.tools.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            var item = stack.getItem();
            var action = TOOL_ACTION_MAP.get(item);
            if (action.shouldUse()) {
                dog.triggerAction(action);
                break; 
            }
        }
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level levek, Player player,
            InteractionHand hand) {
        
        var stack = player.getItemInHand(hand);
        if (stack.getItem() == Items.STONE_HOE) {
            this.tools.setStackInSlot(0, new ItemStack(Items.STONE_HOE));
        } else if (stack.getItem() == Items.STONE_AXE) {
            this.tools.setStackInSlot(0, ItemStack.EMPTY);
        }
        return InteractionResult.PASS;
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
