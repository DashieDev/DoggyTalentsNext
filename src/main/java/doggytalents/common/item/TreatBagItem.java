package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.api.feature.FoodHandler;
import doggytalents.api.forge_imitate.inventory.ItemStackHandler;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.Screens;
import doggytalents.common.entity.misc.DogFoodProjectile;
import doggytalents.common.entity.misc.DogGunpowderProjectile;
import doggytalents.common.inventory.TreatBagItemHandler;
import doggytalents.common.util.Cache;
import doggytalents.common.util.InventoryUtil;
import doggytalents.common.util.ItemUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TreatBagItem extends Item implements IDogFoodHandler {

    private Cache<String> contentsTranslationKey = Cache.make(() -> this.getDescriptionId() + ".contents");

    public TreatBagItem(Properties properties) {
        super(
            properties
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
        );
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
            if (playerIn instanceof ServerPlayer) {
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
        var dogFoodProj = new DogFoodProjectile(player.level(), player);
        dogFoodProj.setDogFoodStack(new ItemStack(item));
        dogFoodProj.setOwner(player);
        dogFoodProj.shootFromRotation(player, 
            player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
        player.level().addFreshEntity(dogFoodProj);
    }

    private void throwGunpowder(Player player) {
        var dogGunpowderProj = new DogGunpowderProjectile(player.level(), player);
        dogGunpowderProj.setOwner(player);
        dogGunpowderProj.shootFromRotation(player, 
            player.getXRot(), player.getYRot(), 0.0F, 0.8F, 1.0F);
        player.level().addFreshEntity(dogGunpowderProj);
    }

    private int findThrowableInItemHandler(ItemStackHandler itemHandler) {
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
    //@OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);

        tooltip.add(Component.translatable("item.doggytalents.treat_bag.help").withStyle(
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
        tooltip.add(Component.translatable("item.doggytalents.treat_bag.contents"));
        for (var entry : contentsMap.entrySet()) {
            var c1 = Component.translatable("item.doggytalents.starter_bundle.contains",
                entry.getValue(), entry.getKey().getDescription()).withStyle(
                    Style.EMPTY.withColor(0xffa3a3a3)
                );
            tooltip.add(c1);
        }
        if (contentsOverview.isMore() > 0) {
            tooltip.add(Component.translatable("item.doggytalents.treat_bag.contents.more",
                contentsOverview.isMore()).withStyle(
                Style.EMPTY.withColor(0xffa3a3a3)
            ));
        }
        
        
    }

    public static List<ItemStack> inventory(ItemStack stack) {
        if (!stack.is(DoggyItems.TREAT_BAG.get()))
            return List.of();
        var itemList = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (itemList == ItemContainerContents.EMPTY)
            return List.of();
        return itemList.stream().collect(Collectors.toList());
    }

    public static void flushInveotory(ItemStack stack, NonNullList<ItemStack> inv) {
        if (!stack.is(DoggyItems.TREAT_BAG.get()))
            return;
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inv));
    }

    // @Override
    // public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    //     //TODO : do we want to matches the tag as well ? As this one is currently do...
    //     return !ItemStack.matches(oldStack, newStack);
    // }

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

        var treatBag = new TreatBagItemHandler(stackIn);
        return InventoryUtil.feedDogFrom(dogIn, entityIn, treatBag);
    }
}