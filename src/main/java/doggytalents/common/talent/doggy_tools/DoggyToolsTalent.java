package doggytalents.common.talent.doggy_tools;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DoggyTalents;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.impl.IDogRangedAttackManager;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.Screens;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.DoggyToolsItemHandler;
import doggytalents.common.network.packet.data.DoggyToolsPickFirstData;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.talent.doggy_tools.tool_actions.ToolAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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

        var mainHandItem = dog.getMainHandItem();
        boolean mainHandNotEmpty = mainHandItem != null && !mainHandItem.isEmpty();

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
            var item = stack.getItem();
            if (item instanceof SwordItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            if (item instanceof AxeItem) {
                dog.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
            if (item instanceof BowItem && 
                (isInfinityBow(stack) || findArrowsInInventory(dog).isPresent())) {
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

    private static Optional<Pair<ItemStackHandler, Integer>> findArrowsInInventory(AbstractDog dog) {
        var talentInstOptional = dog.getTalent(DoggyTalents.DOGGY_TOOLS);
        if (!talentInstOptional.isPresent())
            return Optional.empty();
        if (!(talentInstOptional.get() instanceof DoggyToolsTalent tools))
            return Optional.empty();

        ItemStackHandler inv = tools.getTools();
        int id = findArrowStackInInventory(inv);
        if (id >= 0)
            return Optional.of(Pair.of(inv, id));

        talentInstOptional = dog.getTalent(DoggyTalents.PACK_PUPPY);
        if (!talentInstOptional.isPresent())
            return Optional.empty();
        if (!(talentInstOptional.get() instanceof PackPuppyTalent packPup))
            return Optional.empty();
        
        inv = packPup.inventory();
        id = findArrowStackInInventory(inv);
        if (id >= 0)
            return Optional.of(Pair.of(inv, id));
        return Optional.empty();
    }

    private static int findArrowStackInInventory(ItemStackHandler inv) {
        if (inv == null)
            return -1;
        int selected_id = -1;
        for (int i = 0; i < inv.getSlots(); ++i) {
            var stack = inv.getStackInSlot(i);
            if (!(stack.getItem() instanceof ArrowItem))
                continue;
            selected_id = i;
            break;
        }
        return selected_id;
    }

    public static boolean isInfinityBow(ItemStack bowStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bowStack) > 0;
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
        item.hurtEnemy(stack, living, dogIn);
    }

    public int getMaxOwnerDistSqr() {
        return 8*8;
    } 


    @Override
    public void writeToBuf(FriendlyByteBuf buf) {
        super.writeToBuf(buf);
        buf.writeBoolean(this.alwaysPickSlot0);
    }

    @Override
    public void readFromBuf(FriendlyByteBuf buf) {
        super.readFromBuf(buf);
        alwaysPickSlot0 = buf.readBoolean();
    }

    @Override
    public void updateOptionsFromServer(TalentInstance fromServer) {
        if (!(fromServer instanceof DoggyToolsTalent tools))
            return;
        this.alwaysPickSlot0 = tools.alwaysPickSlot0;
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

    private static class DoggyToolsRangedAttack implements IDogRangedAttackManager {

        @Override
        public boolean isApplicable(AbstractDog dog) {
            return dog.getMainHandItem().getItem() instanceof BowItem
                && findArrowsInInventory(dog).isPresent();
        }

        @Override
        public boolean updateUsingWeapon(UsingWeaponContext ctx) {
            var dog = ctx.dog;
            if (!dog.isUsingItem()) {
                mayStartUsingWeapon(ctx);
                return false;
            }
    
            boolean should_stop_using = 
                !ctx.canSeeTarget && ctx.seeTime < -60;
    
            if (should_stop_using) {
                dog.stopUsingItem();
                return false;
            }
    
            if (ctx.canSeeTarget) {
                int i = dog.getTicksUsingItem();
                if (i >= 20) {
                    dog.stopUsingItem();
                    shoot(dog, ctx.target, BowItem.getPowerForTime(i));
                    return true;
                }
            }
            return false;
        }
    
        private void mayStartUsingWeapon(UsingWeaponContext ctx) {
            if (ctx.cooldown <= 0 && ctx.seeTime >= -60) {
                ctx.dog.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }
    
        private void shoot(AbstractDog dog, LivingEntity target, float damage) {
            var arrowOptional = getAndConsumeDogArrow(dog, damage);
            if (!arrowOptional.isPresent())
                return;

            var arrow = arrowOptional.get();
            final double aim_y_offset_l_xz_influence = 0.2;
            final double aim_y_offset_l_xz_influence_down = 0.1;
    
            double dx = target.getX() - dog.getX();
            double dz = target.getZ() - dog.getZ();
            double l_xz = Math.sqrt(dx * dx + dz * dz);
    
            double aim_y = target.getY()
                + 0.5 * target.getBbHeight(); 
            double dy = aim_y - arrow.getY();
            if (dy > 0) {
                dy += l_xz * aim_y_offset_l_xz_influence;
            } else {
                dy += l_xz * aim_y_offset_l_xz_influence_down;
            }
            
            double shoot_dir_x = dx;
            double shoot_dir_y = dy;
            double shoot_dir_z = dz;
            float power = 1.6f;
            float error_window = 2;
            arrow.shoot(shoot_dir_x, shoot_dir_y, shoot_dir_z, 
                power, error_window);
    
            dog.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (dog.getRandom().nextFloat() * 0.4F + 0.8F));
            dog.level().addFreshEntity(arrow);
        }

        public Optional<AbstractArrow> getAndConsumeDogArrow(AbstractDog dog, float damage) {
            var bowStack = dog.getMainHandItem();
            if (!(bowStack.getItem() instanceof BowItem)) {
                return Optional.empty();
            }
            
            var arrowInvOptional = findArrowsInInventory(dog);
            ItemStackHandler inv = null;
            int id = -1;
            if (arrowInvOptional.isPresent()) {
                inv = arrowInvOptional.get().getLeft();
                id = arrowInvOptional.get().getRight();
            }
            
            var arrow_stack = ItemStack.EMPTY;

            if (inv != null && id >= 0) {
                arrow_stack = inv.getStackInSlot(id).copy();
            }
            var projArrowOptional = getArrowFromBow(dog, bowStack, arrow_stack, damage);
            if (!projArrowOptional.isPresent())
                return Optional.empty();
            
            consumeArrow(dog, bowStack, arrow_stack);
            if (inv != null && id >= 0)
                inv.setStackInSlot(id, arrow_stack);

            bowStack.hurtAndBreak(1, dog, (dog_1) -> {
                dog_1.broadcastBreakEvent(InteractionHand.MAIN_HAND);
            });

            return projArrowOptional;
        }

        private Optional<AbstractArrow> getArrowFromBow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack, float power) {
            if (!(bow_stack.getItem() instanceof BowItem bow))
                return Optional.empty();
            boolean is_infinity_bow = isInfinityBow(bow_stack);
    
            ArrowItem arrow = null;
    
            if (is_infinity_bow)
                arrow = (ArrowItem) Items.ARROW;
    
            if (arrowStack.getItem() instanceof ArrowItem arrowItem)
                arrow = arrowItem;
            
            if (arrow == null)  
                return Optional.empty();
    
            var arrow_proj = arrow.createArrow(dog.level(), arrowStack, dog);
            if (arrow_proj == null)
                return Optional.empty();
    
            arrow_proj = bow.customArrow(arrow_proj);
            if (power >= 1.0F) {
                arrow_proj.setCritArrow(true);
            }
    
            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow_stack);
            if (j > 0) {
                arrow_proj.setBaseDamage(arrow_proj.getBaseDamage() + (double)j * 0.5D + 0.5D);
            }
    
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow_stack);
            if (k > 0) {
                arrow_proj.setKnockback(k);
            }
    
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow_stack) > 0) {
                arrow_proj.setSecondsOnFire(100);
            }
    
            if (is_infinity_bow) {
                arrow_proj.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            } else {
                arrow_proj.pickup = AbstractArrow.Pickup.ALLOWED;
            }
    
            return Optional.of(arrow_proj);
        }
    
        private void consumeArrow(AbstractDog dog, ItemStack bow_stack, ItemStack arrowStack) {
            boolean is_infinity_bow = isInfinityBow(bow_stack);
            
            if (!is_infinity_bow)
                arrowStack.shrink(1);
    
        }

        @Override
        public void onStop(AbstractDog dog) {
            dog.stopUsingItem();
        }

        
        
    }
    
}
