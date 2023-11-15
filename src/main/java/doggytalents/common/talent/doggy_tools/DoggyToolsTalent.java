package doggytalents.common.talent.doggy_tools;

import java.util.Map;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.Screens;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import doggytalents.common.network.packet.data.DoggyToolsPickFirstData;
import doggytalents.common.talent.doggy_tools.tool_actions.ToolAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class DoggyToolsTalent extends TalentInstance  {

    private DoggyToolsItemHandler tools;
    private Map<Item, ToolAction> TOOL_ACTION_MAP;
    private boolean alwaysPickSlot0;

    public DoggyToolsTalent(Talent talentIn, int level) {
        super(talentIn, level);
        this.tools = new DoggyToolsItemHandler();
    }

    public static int getSize(int level) {
        return Mth.clamp(level, 0, 5);
    }

    @Override
    public void init(AbstractDog dog) {
        TOOL_ACTION_MAP = ToolActionEntries.getToolActionMapFor((Dog) dog, this);
    }

    @Override
    public boolean hasRenderer() {
        return true;
    }

    @Override
    public void remove(AbstractDog dog) {
        dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public void set(AbstractDog dog, int levelBefore) {
        if (dog.level.isClientSide) return;
        if (levelBefore > 0 && this.level() <= 0) {
            this.dropAllToolbar(dog);
        }
    }

    private void dropAllToolbar(AbstractDog dog) {
        for (int i = 0; i < this.tools.getSlots(); ++i) {
            Containers.dropItemStack(dog.level, dog.getX(), dog.getY(), dog.getZ(), 
                this.tools.getStackInSlot(i));
            this.tools.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void tick(AbstractDog d) {
        if (d.level.isClientSide) return;

        validateAndSync(d);
        
        if (!(d instanceof Dog dog)) return;

        if (this.alwaysPickSlot0) {
            var firstTool = this.tools.getStackInSlot(0);
            if (!firstTool.isEmpty()) {
                if (dog.getMainHandItem() != firstTool) {
                    dog.setItemInHand(InteractionHand.MAIN_HAND, firstTool);
                }
                return;
            }
        }

        if (dog.isOrderedToSit() || !dog.isDoingFine())  {
            var mainHandItem = dog.getMainHandItem();
            if (mainHandItem != null && !mainHandItem.isEmpty())
                dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            return;
        };

        if (dog.isBusy())
            return;

        if (dog.getTarget() != null) {
            pickTargetTool(dog);
            return;
        }

        var mainHandItem = dog.getMainHandItem();
        if (mainHandItem != null && !mainHandItem.isEmpty())
            dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        
        var owner = dog.getOwner();
        if (owner == null || dog.distanceToSqr(owner) > getMaxOwnerDistSqr()) {
            return;
        }

        pickActionTool(dog);
        
    }

    private void validateAndSync(AbstractDog d) {
        var stack = d.getMainHandItem();
        if (!this.tools.hasStackRef(stack)) {
            d.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }
    
    private void pickTargetTool(Dog dog) {
        for (int i = 0; i < getSize(this.level()); ++i) {
            var stack = this.tools.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            var item = stack.getItem();
            if (item instanceof SwordItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            if (item instanceof AxeItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
        }
    }

    private void pickActionTool(Dog dog) {
        for (int i = 0; i < getSize(this.level()); ++i) {
            var stack = this.tools.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            var item = stack.getItem();
            var action = TOOL_ACTION_MAP.get(item);
            if (action == null) continue;
            if (action.shouldUse()) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                dog.triggerAction(action);
                break; 
            }
        }
    }

    public DoggyToolsItemHandler getTools() {
        return this.tools;
    }

    @Override
    public InteractionResult processInteract(AbstractDog d, Level levek, Player player,
            InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof PickaxeItem)) 
            return InteractionResult.PASS;
        if (!(d instanceof Dog dog)) 
            return InteractionResult.PASS;
        if (!dog.level.isClientSide && player instanceof ServerPlayer sP) {
            Screens.openDoggyToolsScreen(sP, dog);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        var tag = new CompoundTag();
        tag.put("tool_inv", tools.serializeNBT());
        tag.putBoolean("pickFirstTool", this.alwaysPickSlot0);
        compound.put("doggy_tools", tag);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        var tag = compound.getCompound("doggy_tools");
        if (tag == null) return;
        alwaysPickSlot0 = tag.getBoolean("pickFirstTool");
        var inv_tag = tag.getCompound("tool_inv");
        if (inv_tag != null) {
            this.tools.deserializeNBT(inv_tag);
        }
    }

    @Override
    public void doAdditionalAttackEffects(AbstractDog dogIn, Entity target) {
        var stack = dogIn.getMainHandItem();
        var item = stack.getItem();
        if (!(target instanceof LivingEntity living))
            return;
        item.hurtEnemy(stack, living, dogIn);
    }

    public int getMaxOwnerDistSqr() {
        return 8*8;
    } 


    @Override
    public void writeToBuf(FriendlyByteBuf buf) {
        buf.writeBoolean(this.alwaysPickSlot0);
    }

    @Override
    public void readFromBuf(FriendlyByteBuf buf) {
        alwaysPickSlot0 = buf.readBoolean();
    }

    @Override
    public TalentInstance copy() {
        var ret = super.copy();
        if (!(ret instanceof DoggyToolsTalent tools))
            return ret;
        tools.setPickFirstTool(this.alwaysPickSlot0);
        return tools;
    }

    public void updateFromPacket(DoggyToolsPickFirstData data) {
        alwaysPickSlot0 = data.val;
    }

    public boolean pickFirstTool() { return this.alwaysPickSlot0; }
    public void setPickFirstTool(boolean val) { this.alwaysPickSlot0 = val; }
    
}
