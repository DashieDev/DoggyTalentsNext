package doggytalents.common.item;

import doggytalents.DoggyEntityTypes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.event.EventHandler;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.variant.util.DogVariantUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DoggyCharmItem extends Item implements IDogItem {

    public DoggyCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide() || !(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction enumfacing = context.getClickedFace();
            BlockState iblockstate = world.getBlockState(blockpos);

            if (player == null)
                return InteractionResult.SUCCESS;

            if (player.isShiftKeyDown())
                return InteractionResult.SUCCESS;

            if (!EventHandler.isWithinTrainWolfLimit(player))
                return InteractionResult.SUCCESS;

            BlockPos blockpos1;
            if (iblockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(enumfacing);
            }


            Entity entity = DoggyEntityTypes.DOG.get().spawn((ServerLevel) world, itemstack, context.getPlayer(), blockpos1, EntitySpawnReason.SPAWN_ITEM_USE, !Objects.equals(blockpos, blockpos1) && enumfacing == Direction.UP, false);
            if (entity instanceof Dog) {
               Dog dog = (Dog)entity;
               if (player != null) {
                   dog.setTame(true, true);
                   dog.setOwnerUUID(player.getUUID());
                   dog.maxHealth();
                   if (ConfigHandler.SERVER.RANDOM_VAR_WITH_CHARM.get()) {
                        dog.setDogVariant(DogVariantUtil.getRandom(dog.getRandom()));
                   }
               }
               itemstack.shrink(1);
               if (player instanceof ServerPlayer sP) {
                   CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sP, blockpos1, itemstack);
                   sP.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(this), 30);
               }
            
           }

           return InteractionResult.SUCCESS;
        }
    }

    @Override
    public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn.isShiftKeyDown())
            return InteractionResult.PASS; // stack: itemstack);
        
        if (worldIn.isClientSide() || !(worldIn instanceof ServerLevel)) {
            return InteractionResult.PASS; // stack: itemstack);
        } else {
            if (playerIn == null)
                return InteractionResult.PASS; // stack: itemstack);;
            if (!EventHandler.isWithinTrainWolfLimit(playerIn))
                return InteractionResult.PASS; // stack: itemstack);
            
            HitResult raytraceresult = Item.getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
            if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult)raytraceresult).getBlockPos();
                if (!(worldIn.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                    return InteractionResult.PASS; // stack: itemstack);
                } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, ((BlockHitResult)raytraceresult).getDirection(), itemstack)) {
                    Entity entity = DoggyEntityTypes.DOG.get().spawn((ServerLevel) worldIn, itemstack, playerIn, blockpos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);
                    if (entity instanceof Dog) {
                        Dog dog = (Dog)entity;
                           dog.setTame(true, true);
                           dog.setOwnerUUID(playerIn.getUUID());
                           dog.maxHealth();
                           if (ConfigHandler.SERVER.RANDOM_VAR_WITH_CHARM.get()) {
                                dog.setDogVariant(DogVariantUtil.getRandom(dog.getRandom()));
                           }
                           itemstack.shrink(1);

                        if (playerIn instanceof ServerPlayer sP)
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sP, blockpos, itemstack);
                        playerIn.awardStat(Stats.ITEM_USED.get(this));
                        
                        playerIn.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(this), 30);
                        return InteractionResult.SUCCESS; // stack: itemstack);
                    } else {
                        return InteractionResult.PASS; // stack: itemstack);
                    }
                } else {
                    return InteractionResult.FAIL; // stack: itemstack);
                }
            } else {
                return InteractionResult.PASS; // stack: itemstack);
            }
        }
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player player,
            InteractionHand handIn) {
        if (!(dogIn instanceof Dog dog))
            return InteractionResult.FAIL;
        if (!player.isCreative())
            return InteractionResult.FAIL;
        if (!player.isShiftKeyDown())
            return InteractionResult.FAIL;
        if (!dog.canInteract(player))
            return InteractionResult.FAIL;
        
        if (dog.level().isClientSide())
            return InteractionResult.SUCCESS;
        
        var current_variant = dog.dogVariant();
        var next_variant = DogVariantUtil.cycle(current_variant);
        dog.setDogVariant(next_variant);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isCharmForcedGlint(stack) || super.isFoil(stack);
    }

    public static boolean isCharmForcedGlint(ItemStack stack) {
        return ItemUtil.getWrappedTag(stack).contains("dtn_charm_forced_glint");
    }

    public static void setCharmForcedGlint(ItemStack stack, boolean glint) {
        ItemUtil.modifyTag(stack, tg -> {
            if (glint) {
                tg.putBoolean("dtn_charm_forced_glint", true);
            } else {
                tg.remove("dtn_charm_forced_glint");
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        components.accept(Component.translatable(desc_id));
    }
}
