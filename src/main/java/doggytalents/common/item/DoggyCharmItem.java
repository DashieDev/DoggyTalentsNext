package doggytalents.common.item;

import doggytalents.DoggyEntityTypes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.ClassicalVar;
import doggytalents.common.entity.Dog;
import doggytalents.common.event.EventHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DoggyCharmItem extends Item implements IDogItem {

    public DoggyCharmItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide || !(world instanceof ServerLevel)) {
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


            Entity entity = DoggyEntityTypes.DOG.get().spawn((ServerLevel) world, itemstack, context.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, !Objects.equals(blockpos, blockpos1) && enumfacing == Direction.UP, false);
            if (entity instanceof Dog) {
               Dog dog = (Dog)entity;
               if (player != null) {
                   dog.setTame(true);
                   dog.setOwnerUUID(player.getUUID());
                   dog.maxHealth();
                   if (ConfigHandler.SERVER.RANDOM_VAR_WITH_CHARM.get()) {
                        dog.setClassicalVar(ClassicalVar.random(dog));
                   }
               }
               itemstack.shrink(1);
               if (player instanceof ServerPlayer sP) {
                   CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sP, blockpos1, itemstack);
                   sP.getCooldowns().addCooldown(this, 30);
               }
            
           }

           return InteractionResult.SUCCESS;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (playerIn.isShiftKeyDown())
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        
        if (worldIn.isClientSide || !(worldIn instanceof ServerLevel)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        } else {
            if (playerIn == null)
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);;
            if (!EventHandler.isWithinTrainWolfLimit(playerIn))
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
            
            HitResult raytraceresult = Item.getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
            if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult)raytraceresult).getBlockPos();
                if (!(worldIn.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                    return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
                } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, ((BlockHitResult)raytraceresult).getDirection(), itemstack)) {
                    Entity entity = DoggyEntityTypes.DOG.get().spawn((ServerLevel) worldIn, itemstack, playerIn, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                    if (entity instanceof Dog) {
                        Dog dog = (Dog)entity;
                           dog.setTame(true);
                           dog.setOwnerUUID(playerIn.getUUID());
                           dog.maxHealth();
                           if (ConfigHandler.SERVER.RANDOM_VAR_WITH_CHARM.get()) {
                                dog.setClassicalVar(ClassicalVar.random(dog));
                           }
                           itemstack.shrink(1);

                        if (playerIn instanceof ServerPlayer sP)
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sP, blockpos, itemstack);
                        playerIn.awardStat(Stats.ITEM_USED.get(this));
                        
                        playerIn.getCooldowns().addCooldown(this, 30);
                        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
                    } else {
                        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
                    }
                } else {
                    return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
                }
            } else {
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
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
        var complsList = Arrays.asList(ClassicalVar.getCompls());
        if (complsList.isEmpty())
            return InteractionResult.FAIL;
        
        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        var current_indx = complsList.indexOf(dog.getClassicalVar());
        int next_indx = 0;
        if (current_indx >= 0)
            next_indx = (current_indx + 1) % complsList.size();
        var next_compl = complsList.get(next_indx);
        dog.setClassicalVar(next_compl);
        return InteractionResult.SUCCESS;
    }
}
