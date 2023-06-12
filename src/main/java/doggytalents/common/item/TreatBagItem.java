package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.Screens;
import doggytalents.common.inventory.TreatBagItemHandler;
import doggytalents.common.util.Cache;
import doggytalents.common.util.InventoryUtil;
import doggytalents.common.util.ItemUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
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
            if (playerIn instanceof ServerPlayer && !(playerIn instanceof FakePlayer)) {
                ServerPlayer serverPlayer = (ServerPlayer) playerIn;

                Screens.openTreatBagScreen(serverPlayer, stack, playerIn.getInventory().selected);
            }

            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        IItemHandler bagInventory = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        List<ItemStack> condensedContents = ItemUtil.getContentOverview(bagInventory);

        condensedContents.forEach((food) -> {
            tooltip.add(Component.translatable(this.contentsTranslationKey.get(), food.getCount(), Component.translatable(food.getDescriptionId())));
        });
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag nbt) {
        // https://github.com/MinecraftForge/MinecraftForge/issues/5989
        if (ForgeCapabilities.ITEM_HANDLER == null) {
            return null;
        }

        return new ICapabilityProvider() {
            final LazyOptional<IItemHandler> itemHandlerInstance = LazyOptional.of(() -> new TreatBagItemHandler(stack));

            @Override
            @Nonnull
            public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
                if (cap == ForgeCapabilities.ITEM_HANDLER) {
                    return (LazyOptional<T>) this.itemHandlerInstance;
                }
                return LazyOptional.empty();
            }
        };
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
        IItemHandlerModifiable treatBag = (IItemHandlerModifiable) stackIn.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        return InventoryUtil.feedDogFrom(dogIn, entityIn, treatBag);
    }
}
