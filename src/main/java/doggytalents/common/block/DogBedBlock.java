package doggytalents.common.block;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
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
import net.minecraft.network.chat.Style;
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
import java.util.function.Predicate;

public class DogBedBlock extends BaseEntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape SHAPE_COLLISION = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public DogBedBlock() {
        super(Block.Properties.of(Material.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        var tile = WorldUtil.getTileEntity(level, pos, DogBedTileEntity.class);
        if (tile == null) 
            return InteractionResult.SUCCESS;

        var stack = player.getItemInHand(handIn);

        if (handleNameTagBed(player, level, state, pos, tile, stack)
            .shouldSwing())
            return InteractionResult.SUCCESS;
        
        if (handleDogClaimBed(player, level, state, pos, tile, stack)
            .shouldSwing())
            return InteractionResult.SUCCESS;

        if (handleDogRespawn(player, level, state, pos, tile, stack)
            .shouldSwing())
            return InteractionResult.SUCCESS;

        if (handleDogReclaim(player, level, state, pos, tile, stack)
            .shouldSwing())
            return InteractionResult.SUCCESS;
        
    
        player.sendSystemMessage(Component.translatable("block.doggytalents.dog_bed.set_owner_help"));
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleDogReclaim(Player player, Level level, 
        BlockState state, BlockPos pos, DogBedTileEntity tile, ItemStack stack) {
        var owner_id = tile.getOwnerUUID();
        if (owner_id == null)
            return InteractionResult.PASS;
        
        if (!player.isShiftKeyDown())
            return InteractionResult.PASS;

        if (!reclaimBed(player, (ServerLevel) level, tile, pos)) {
            Component name = tile.getOwnerName();
            player.sendSystemMessage(Component.translatable("block.doggytalents.dog_bed.owner", name != null ? name : "someone"));
        }  

        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleDogRespawn(Player player, Level level, 
        BlockState state, BlockPos pos, DogBedTileEntity tile, ItemStack stack) {
        var owner_id = tile.getOwnerUUID();
        if (owner_id == null)
            return InteractionResult.PASS;
        
        var storage = DogRespawnStorage.get(level);
        var data = storage.remove(owner_id);
        if (data == null)
            return InteractionResult.PASS;

        var dog = data.respawn((ServerLevel) level, player, pos.above());
        if (dog == null)
            return InteractionResult.SUCCESS;

        tile.setOwner(dog);
        dog.setBedPos(dog.level().dimension(), pos);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleDogClaimBed(Player player, Level level, 
        BlockState state, BlockPos pos, DogBedTileEntity tile, ItemStack stack) {
        if (tile.getOwnerUUID() != null)
            return InteractionResult.PASS;

        boolean isAssign = 
            player.isShiftKeyDown() || stack.is(Items.BONE);
        if (!isAssign)
            return InteractionResult.PASS;
        
        Predicate<Dog> isValidDog = valid_dog -> 
            valid_dog.isDoingFine()
            && valid_dog.isOwnedBy(player)
            && !valid_dog.isOrderedToSit();
        List<Dog> dogs = level.getEntities(
            DoggyEntityTypes.DOG.get(), 
            new AABB(pos).inflate(10D), 
            isValidDog
        );
        Collections.sort(dogs, new EntityUtil.Sorter(Vec3.atBottomCenterOf(pos)));

        Dog closest = dogs.isEmpty() ? null : dogs.get(0);
        if (pos != null && closest != null 
            && closest.readyForNonTrivialAction()) {
            closest.triggerAction(new DogMoveToBedAction(closest, pos, true));
        }

        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleNameTagBed(Player player, Level level, 
        BlockState state, BlockPos pos, DogBedTileEntity tile, ItemStack stack) {
        if (!stack.is(Items.NAME_TAG))
            return InteractionResult.PASS;
        if (!stack.hasCustomHoverName())
            return InteractionResult.PASS;
        
        tile.setBedName(stack.getHoverName());

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        
        return InteractionResult.SUCCESS;
    }

    private boolean reclaimBed(Player player, ServerLevel level, DogBedTileEntity bedEntity, BlockPos pos) {
        var e = level.getEntity(bedEntity.getOwnerUUID());
        if (!(e instanceof Dog dog))
            return false;
        var bedPosOptional = dog.getBedPos();
        if (!bedPosOptional.isPresent())
            return false;
        var bedPos = bedPosOptional.get();
        if (bedPos.equals(pos))
            return false;
        
        dog.setBedPos(pos);
        player.sendMessage(
            ComponentUtil.translatable("block.doggytalents.dog_bed.reclaim", 
                dog.getName().getString(), 
                dog.getGenderPossessiveAdj()), Util.NIL_UUID);
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        Pair<ICasingMaterial, IBeddingMaterial> materials = DogBedUtil.getMaterials(stack);

        tooltip.add(ComponentUtil.translatable("dogbed.casing.title"));
        tooltip.add(materials.getLeft() != null
                ? materials.getLeft().getTooltip()
                : ComponentUtil.translatable("dogbed.casing.null").withStyle(ChatFormatting.RED));
        tooltip.add(ComponentUtil.translatable("dogbed.bedding.title"));
        tooltip.add(materials.getRight() != null
            ? materials.getRight().getTooltip()
            : ComponentUtil.translatable("dogbed.bedding.null").withStyle(ChatFormatting.RED));

        if (materials.getLeft() == null && materials.getRight() == null) {
            tooltip.add(ComponentUtil.translatable("dogbed.explain.missing").withStyle(ChatFormatting.ITALIC));
        }

        CompoundTag tag = stack.getTagElement("doggytalents");
        if (tag != null) {
            UUID ownerId = NBTUtil.getUniqueId(tag, "ownerId");
            Component name = NBTUtil.getTextComponent(tag, "name");
            Component ownerName = NBTUtil.getTextComponent(tag, "ownerName");

            if (name != null) {
                tooltip.add(ComponentUtil.literal("Bed Name: ").withStyle(ChatFormatting.WHITE).append(name));
            }

            if (ownerName != null) {
                tooltip.add(ComponentUtil.literal("Name: ").withStyle(ChatFormatting.DARK_AQUA).append(ownerName));

            }

            if (ownerId != null && (flagIn.isAdvanced() || Screen.hasShiftDown())) {
                tooltip.add(ComponentUtil.literal("UUID: ").withStyle(ChatFormatting.AQUA).append(ComponentUtil.literal(ownerId.toString())));
            }
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        final int maxBeddingEntries = 13;
        final int maxCasingEntries = 13;
        var beddingList = DogBedMaterialManager.getBeddings().entrySet().stream()
            .map(x -> x.getValue())
            .filter(x -> !(x instanceof DogBedMaterialManager.NaniBedding))
            .collect(Collectors.toList());
        var casingList = DogBedMaterialManager.getCasings().entrySet().stream()
            .map(x -> x.getValue())
            .filter(x -> !(x instanceof DogBedMaterialManager.NaniCasing))
            .collect(Collectors.toList());
        
        Collections.shuffle(beddingList);
        Collections.shuffle(casingList);
        for (int i = 0; i < Math.min(maxCasingEntries, casingList.size()); ++i) {
            for (int j = 0; j < Math.min(maxBeddingEntries, beddingList.size()); ++j) {
                var beddingId = beddingList.get(j);
                var casingId = casingList.get(i);
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
