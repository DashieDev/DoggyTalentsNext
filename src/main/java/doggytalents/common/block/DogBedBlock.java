package doggytalents.common.block;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.DogMoveToBedAction;
import doggytalents.common.storage.DogRespawnData;
import doggytalents.common.storage.DogRespawnStorage;
import doggytalents.common.util.DogBedUtil;
import doggytalents.common.util.EntityUtil;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.WorldUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import doggytalents.common.lib.Constants;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DogBedBlock extends BaseEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public DogBedBlock() {
        super(Block.Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE_COLLISION;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new DogBedTileEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        state = state.setValue(FACING, placer.getDirection().getOpposite());

        DogBedTileEntity dogBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, DogBedTileEntity.class);

        if (dogBedTileEntity != null) {
            DogBedUtil.setBedVariant(dogBedTileEntity, stack);

            dogBedTileEntity.setPlacer(placer);
            CompoundTag tag = stack.getTagElement("doggytalents");
            if (tag != null) {
                Component name = NBTUtil.getTextComponent(tag, "name");
                UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
                dogBedTileEntity.setBedName(name);
                dogBedTileEntity.setOwner(ownerId);
            }
        }

        worldIn.setBlock(pos, state, Block.UPDATE_CLIENTS);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            DogBedTileEntity dogBedTileEntity = WorldUtil.getTileEntity(worldIn, pos, DogBedTileEntity.class);

            if (dogBedTileEntity != null) {

                ItemStack stack = player.getItemInHand(handIn);
                if (stack.getItem() == Items.NAME_TAG && stack.hasCustomHoverName()) {
                    dogBedTileEntity.setBedName(stack.getHoverName());

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                } else if (player.isShiftKeyDown() && dogBedTileEntity.getOwnerUUID() == null) {
                    List<Dog> dogs = worldIn.getEntities(DoggyEntityTypes.DOG.get(), new AABB(pos).inflate(10D), (dog) -> dog.isAlive() && dog.isOwnedBy(player) && !dog.isOrderedToSit());
                    Collections.sort(dogs, new EntityUtil.Sorter(new Vec3(pos.getX(), pos.getY(), pos.getZ())));

                    // Dog closestStanding = null;
                    // Dog closestSitting = null;
                    // for (Dog dog : dogs) {
                    //     if (closestSitting != null && closestSitting != null) {
                    //         break;
                    //     }

                    //     if (closestSitting == null && dog.isInSittingPose()) {
                    //         closestSitting = dog;
                    //     } else if (closestStanding == null && !dog.isInSittingPose()) {
                    //         closestStanding = dog;
                    //     }
                    // }

                    Dog closest = dogs.isEmpty() ? null : dogs.get(0);
                    if (closest != null && closest.readyForNonTrivialAction()) {
                        closest.triggerAction(new DogMoveToBedAction(closest, pos, true));
                    }
                } else if (dogBedTileEntity.getOwnerUUID() != null) {
                    DogRespawnData storage = DogRespawnStorage.get(worldIn).remove(dogBedTileEntity.getOwnerUUID());

                    if (storage != null) {
                        Dog dog = storage.respawn((ServerLevel) worldIn, player, pos.above());

                        dogBedTileEntity.setOwner(dog);
                        dog.setBedPos(dog.level.dimension(), pos);
                        return InteractionResult.SUCCESS;
                    } else {
                        Component name = dogBedTileEntity.getOwnerName();
                        player.sendSystemMessage(Component.translatable("block.doggytalents.dog_bed.owner", name != null ? name : "someone"));
                        return InteractionResult.FAIL;
                    }
                } else {
                    player.sendSystemMessage(Component.translatable("block.doggytalents.dog_bed.set_owner_help"));
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        Pair<ICasingMaterial, IBeddingMaterial> materials = DogBedUtil.getMaterials(stack);

        tooltip.add(materials.getLeft() != null
                ? materials.getLeft().getTooltip()
                : Component.translatable("dogbed.casing.null").withStyle(ChatFormatting.RED));
        tooltip.add(materials.getRight() != null
                ? materials.getRight().getTooltip()
                : Component.translatable("dogbed.bedding.null").withStyle(ChatFormatting.RED));

        if (materials.getLeft() == null && materials.getRight() == null) {
            tooltip.add(Component.translatable("dogbed.explain.missing").withStyle(ChatFormatting.ITALIC));
        }

        CompoundTag tag = stack.getTagElement("doggytalents");
        if (tag != null) {
            UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
            Component name = NBTUtil.getTextComponent(tag, "name");
            Component ownerName = NBTUtil.getTextComponent(tag, "ownerName");

            if (name != null) {
                tooltip.add(Component.literal("Bed Name: ").withStyle(ChatFormatting.WHITE).append(name));
            }

            if (ownerName != null) {
                tooltip.add(Component.literal("Name: ").withStyle(ChatFormatting.DARK_AQUA).append(ownerName));

            }

            if (ownerId != null && (flagIn.isAdvanced() || Screen.hasShiftDown())) {
                tooltip.add(Component.literal("UUID: ").withStyle(ChatFormatting.AQUA).append(Component.literal(ownerId.toString())));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        for (IBeddingMaterial beddingId : DoggyTalentsAPI.BEDDING_MATERIAL.get().getValues()) {
            for (ICasingMaterial casingId : DoggyTalentsAPI.CASING_MATERIAL.get().getValues()) {
                items.add(DogBedUtil.createItemStack(casingId, beddingId));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        DogBedTileEntity dogBedTileEntity = WorldUtil.getTileEntity(world, pos, DogBedTileEntity.class);

        if (dogBedTileEntity != null) {
            return DogBedUtil.createItemStack(dogBedTileEntity.getCasing(), dogBedTileEntity.getBedding());
        }

        DoggyTalentsNext.LOGGER.debug("Unable to pick block on dog bed.");
        return ItemStack.EMPTY;
    }
}
