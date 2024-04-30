package doggytalents.common.block;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.common.block.tileentity.FoodBowlTileEntity;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RiceMillBlock extends BaseEntityBlock implements WorldlyContainerHolder {

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public RiceMillBlock() {
        super(Block.Properties.of().mapColor(MapColor.WOOD).strength(1.0F, 5.0F).sound(SoundType.WOOD));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    
    }

    public RiceMillBlock(BlockBehaviour.Properties props) {
        this();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide)
            return InteractionResult.SUCCESS;
        if (player instanceof ServerPlayer sP)
            RiceMillBlockEntity.openContainer(sP, level, pos);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RiceMillBlockEntity(pos, state);
    }
    
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, DoggyTileEntityTypes.RICE_MILL.get(), RiceMillBlockEntity::tick);
    }

    @Override
    public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof RiceMillBlockEntity mill))
            return null;
        return mill.getWorldlyContainer();
    }

    public static Direction getFacing(BlockState state) {
        if (state.getBlock() != DoggyBlocks.RICE_MILL.get())
            return Direction.NORTH;
        var facing = state.getValue(FACING);
        if (facing.getAxis() == Axis.Y)
            return Direction.NORTH;
        return facing;
    }

    public static final MapCodec<RiceMillBlock> CODEC = simpleCodec(RiceMillBlock::new);
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
