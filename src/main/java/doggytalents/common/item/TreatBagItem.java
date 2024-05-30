package doggytalents.common.item;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.feature.FoodHandler;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.Screens;
import doggytalents.common.entity.DogFoodProjectile;
import doggytalents.common.entity.DogGunpowderProjectile;
import doggytalents.common.inventory.TreatBagItemHandler;
import doggytalents.common.util.Cache;
import doggytalents.common.util.InventoryUtil;
import doggytalents.common.util.ItemUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TreatBagItem extends Item implements IDogFoodHandler {

    private Cache<String> contentsTranslationKey = Cache.make(() -> this.getDescriptionId() + ".contents");

    public TreatBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (worldIn.isClientSide) {
            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
        else {
            if (!playerIn.isShiftKeyDown()) {
                if (playerIn instanceof ServerPlayer sP) {
                    findFoodAndShootOut(sP, stack);
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            if (playerIn instanceof ServerPlayer && !(playerIn instanceof FakePlayer)) {
                ServerPlayer serverPlayer = (ServerPlayer) playerIn;

                Screens.openTreatBagScreen(serverPlayer, stack, playerIn.getInventory().selected);
            }

            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
    }

    private void findFoodAndShootOut(ServerPlayer player, ItemStack stack) {
        var itemHandler = new TreatBagItemHandler(stack);
        //if (itemHandler == null) return;
        int foodStackId = findThrowableInItemHandler(itemHandler);
        if (foodStackId < 0)
            return;
        var foodStack = itemHandler.getStackInSlot(foodStackId);
        if (foodStack.isEmpty())
            return;
        if (foodStack.is(Items.GUNPOWDER)) {
            throwGunpowder(player);
        } else {
            throwFood(player, foodStack.getItem());
        }
        foodStack = foodStack.copy();
        foodStack.shrink(1);
        itemHandler.setStackInSlot(foodStackId, foodStack);

        player.getCooldowns().addCooldown(this, 20);
    }

    private void throwFood(Player player, Item item) {
        var dogFoodProj = new DogFoodProjectile(player.level, player);
        dogFoodProj.setDogFoodStack(new ItemStack(item));
        dogFoodProj.setOwner(player);
        dogFoodProj.shootFromRotation(player, 
            player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
        player.level.addFreshEntity(dogFoodProj);
    }

    private void throwGunpowder(Player player) {
        var dogGunpowderProj = new DogGunpowderProjectile(player.level, player);
        dogGunpowderProj.setOwner(player);
        dogGunpowderProj.shootFromRotation(player, 
            player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
        player.level.addFreshEntity(dogGunpowderProj);
    }

    private int findThrowableInItemHandler(IItemHandler itemHandler) {
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            var stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty())
                continue;
            if (FoodHandler.isFood(stack).isPresent()) {
                return i;
            }
            if (stack.is(Items.GUNPOWDER))
                return i;
        }
        return -1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        tooltip.add(ComponentUtil.translatable("item.doggytalents.treat_bag.help").withStyle(
            Style.EMPTY.withItalic(true)
        ));

        displayContents(stack, tooltip, flagIn);
        
        
    }

    private void displayContents(ItemStack stack, List<Component> tooltip, TooltipFlag flagIn) {
        var inv = new TreatBagItemHandler(stack);
        // if (inv == null)
        //     return; 
        var contentsOverview = ItemUtil.getContentOverview(inv);
        var contentsMap = contentsOverview.contents();
        if (contentsMap.isEmpty())
            return;
        tooltip.add(ComponentUtil.translatable("item.doggytalents.treat_bag.contents"));
        for (var entry : contentsMap.entrySet()) {
            var c1 = ComponentUtil.translatable("item.doggytalents.starter_bundle.contains",
                entry.getValue(), entry.getKey().getDescription()).withStyle(
                    Style.EMPTY.withColor(0xffa3a3a3)
                );
            tooltip.add(c1);
        }
        if (contentsOverview.isMore() > 0) {
            tooltip.add(ComponentUtil.translatable("item.doggytalents.treat_bag.contents.more",
                contentsOverview.isMore()).withStyle(
                Style.EMPTY.withColor(0xffa3a3a3)
            ));
        }
    
        
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSame(oldStack, newStack);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return false;
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, Entity entityIn) {
        if (dogIn.isDefeated()) return false;
        return entityIn instanceof LivingEntity ? dogIn.canInteract((LivingEntity) entityIn) : false;
    }

    @Override
    public InteractionResult consume(AbstractDog dogIn, ItemStack stackIn, Entity entityIn) {
        if (dogIn.level().isClientSide)
            return InteractionResult.SUCCESS;

        IItemHandlerModifiable treatBag = (IItemHandlerModifiable) stackIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(EmptyHandler.INSTANCE);
        return InventoryUtil.feedDogFrom(dogIn, entityIn, treatBag);
    }
}
