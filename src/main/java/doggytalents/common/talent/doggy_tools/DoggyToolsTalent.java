package doggytalents.common.talent.doggy_tools;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.TalentsOptions;
import doggytalents.DoggyTags;
import doggytalents.DoggyTalents;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.impl.IDogRangedAttackManager;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.Screens;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.misc.DogThrownTrident;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.talent.doggy_tools.tool_actions.ToolAction;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

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
        if (dog.level().isClientSide)
            return;
        dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public void set(AbstractDog dog, int levelBefore) {
        if (dog.level().isClientSide) return;
        if (levelBefore > 0 && this.level() <= 0) {
            this.dropAllToolbar(dog);
        }
    }

    private void dropAllToolbar(AbstractDog dog) {
        for (int i = 0; i < this.tools.getSlots(); ++i) {
            Containers.dropItemStack(dog.level(), dog.getX(), dog.getY(), dog.getZ(), 
                this.tools.getStackInSlot(i));
            this.tools.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void tick(AbstractDog d) {
        if (d.level().isClientSide) return;

        validateAndSync(d);
        
        if (!(d instanceof Dog dog)) return;

        updateMainHandItem(dog);
    }

    private void updateMainHandItem(Dog dog) {
        if (this.alwaysPickSlot0) {
            var firstTool = this.tools.getStackInSlot(0);
            if (!firstTool.isEmpty()) {
                if (dog.getMainHandItem() != firstTool && !isItemBlacklisted(firstTool)) {
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

        var mainHandItem = dog.getMainHandItem();

        if (dog.isBusy() || dog.getTarget() != null)
            return;

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
            if (isItemBlacklisted(stack))
                continue;
            var item = stack.getItem();
            if (item instanceof SwordItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            if (item instanceof AxeItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            if (stack.is(Items.TRIDENT)) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            var shoot_handler = DoggyToolsRangedAttack.getShootHandler(stack, dog);
            if (shoot_handler != ShootHandler.NONE) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }   
        }
    }

    private void pickActionTool(Dog dog) {
        for (int i = 0; i < getSize(this.level()); ++i) {
            var stack = this.tools.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (isItemBlacklisted(stack))
                continue;
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
        if (!dog.level().isClientSide && player instanceof ServerPlayer sP) {
            Screens.openDoggyToolsScreen(sP, dog);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onDogSetTarget(AbstractDog dogIn, @Nullable LivingEntity newTarget, @Nullable LivingEntity oldTarget) {
        if (!(dogIn instanceof Dog dog))
            return;
        if (dog.level().isClientSide)
            return;
        if (this.alwaysPickSlot0)
            return;
        
        if (newTarget == null) {
            var mainHandItem = dog.getMainHandItem();
            if (mainHandItem != null && !mainHandItem.isEmpty())
                dog.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        } else {
            pickTargetTool(dog);
        }
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setCanUseTools();
    }

    @Override
    public Optional<IDogRangedAttackManager> getRangedAttack() {
        return Optional.of(new DoggyToolsRangedAttack());
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
        if (isItemBlacklisted(stack))
            return;
        item.hurtEnemy(stack, living, dogIn);
        //item.postHurtEnemy(stack, living, dogIn);
    }

    public int getMaxOwnerDistSqr() {
        return 8*8;
    } 

    private boolean isItemBlacklisted(ItemStack stack) {
        return stack.is(DoggyTags.DOGGY_TOOLS_BLACKLIST);
    }

    @Override
    public Object getTalentOption(TalentOption<?> entry) {
        if (entry == TalentsOptions.DOGGY_TOOLS_EXC.get()) {
            return this.alwaysPickSlot0;
        }
        return null;
    }

    @Override
    public void setTalentOption(TalentOption<?> entry, Object data) {
        if (entry == TalentsOptions.DOGGY_TOOLS_EXC.get()) {
            this.alwaysPickSlot0 = (Boolean) data;
        }
    }

    @Override
    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of(TalentsOptions.DOGGY_TOOLS_EXC.get());
    }

    public boolean pickFirstTool() { return this.alwaysPickSlot0; }
    public void setPickFirstTool(boolean val) { this.alwaysPickSlot0 = val; }
    
}
