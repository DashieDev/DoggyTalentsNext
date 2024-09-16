package doggytalents.common.talent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.Maps;

import doggytalents.TalentsOptions;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class GatePasserTalent extends TalentInstance {

    private boolean allowPassingGate = true;

    private int openDoorCooldown = 0;
    private int keepOpenDoorTick = 0;
    private Optional<BlockPos> currentOpenedDoor = Optional.empty();
    private int navTickLeftTillCanOpenDoor = 20;
    private boolean canApplyBeginNavCooldown = true;
    
    public GatePasserTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void tick(AbstractDog dog) {
        if (dog.level().isClientSide)
            return;

        if (!this.allowPassingGate) {
            if (this.currentOpenedDoor.isPresent())
                this.currentOpenedDoor = Optional.empty();
            return;
        }

        updateNavTickLeftTillCanOpenDoor(dog);

        if (openDoorCooldown > 0) {
            --openDoorCooldown;
        }

        if (keepOpenDoorTick > 0) {
            --keepOpenDoorTick;
        }
        
        if (currentOpenedDoor.isPresent()) {
            invalidateAndMayCloseOpenedDoor(dog);
        } else {
            checkPathForDoorAndTryOpen(dog);
        }

    }

    private void updateNavTickLeftTillCanOpenDoor(AbstractDog dog) {
        var nav = dog.getNavigation();
        if (canApplyBeginNavCooldown && nav.isDone()) {
            this.navTickLeftTillCanOpenDoor = 20;
            canApplyBeginNavCooldown = false;
            return;
        }
        --this.navTickLeftTillCanOpenDoor;
        if (this.navTickLeftTillCanOpenDoor < 0)
            this.navTickLeftTillCanOpenDoor = 0;
    }
    
    private List<Dog> getNearbyGatePasser(AbstractDog dog) {
        Predicate<Dog> is_passer_and_not_dog = filter_dog ->
            filter_dog != dog
            && filter_dog.isDoingFine()
            && !filter_dog.getNavigation().isDone()
            && filter_dog.getDogLevel(DoggyTalents.GATE_PASSER) > 0;
        var passers = dog.level().getEntitiesOfClass(Dog.class, dog.getBoundingBox().inflate(1),
        is_passer_and_not_dog);
        return passers;
    }

    private void checkPathForDoorAndTryOpen(AbstractDog dog) {
        if (this.openDoorCooldown > 0)
            return;
        if (this.navTickLeftTillCanOpenDoor > 0)
            return;
        var nav = dog.getNavigation();
        if (nav.isDone()) {
            return;
        }

        var path = nav.getPath();
        if (path == null || path.isDone())
            return;
        
        BlockPos doorPos = null;
        BlockState doorState = null;
        Block doorBlock = null;
        
        var prevNode = path.getPreviousNode();
        if (prevNode != null) {
            var prevPos = prevNode.asBlockPos();
            var state = dog.level().getBlockState(prevPos);
            var block = state.getBlock();
            if (isValidDoorBlock(block) && !isDoorOpen(state, prevPos)) {
                doorState = state;
                doorPos = prevPos;
                doorBlock = block;
            }
        }

        var nextNode = path.getNextNode();
        if (doorState == null) {
            var nextPos = nextNode.asBlockPos();
            var state = dog.level().getBlockState(nextPos);
            var block = state.getBlock();
            if (isValidDoorBlock(block) && !isDoorOpen(state, nextPos)) {
                doorState = state;
                doorPos = nextPos;
                doorBlock = block;
            }
        }

        if (doorState == null || doorPos == null || doorBlock == null) 
            return;
        
        this.currentOpenedDoor = Optional.of(doorPos);
        setOpenDoor(dog, doorState, doorPos, true);
        this.canApplyBeginNavCooldown = true;
        keepOpenDoorTick = 5;
    }

    private void invalidateAndMayCloseOpenedDoor(AbstractDog dog) {
        if (keepOpenDoorTick > 0)
            return;
        if (!currentOpenedDoor.isPresent())
            return;
        var currentOpened = currentOpenedDoor.get();
        if (dog.distanceToSqr(Vec3.atBottomCenterOf(currentOpened)) > 16) {
            currentOpenedDoor = Optional.empty();
            return;
        }
        var state = dog.level().getBlockState(currentOpened);
        var block = state.getBlock();
        if (!isValidDoorBlock(block)) {
            currentOpenedDoor = Optional.empty();
            return;
        } 
        if (!isDoorOpen(state, currentOpened)) {
            currentOpenedDoor = Optional.empty();
            return;
        }
        if (isDogStillPassingThruDoor(dog, currentOpened))
            return;
        if (mayDelegateClosingDoorToStillPassingDog(dog, currentOpened))
            return;
        setOpenDoor(dog, state, currentOpened, false);
        this.openDoorCooldown = 5;
        this.currentOpenedDoor = Optional.empty();
        return;
    }

    private boolean isDogStillPassingThruDoor(AbstractDog dog, BlockPos doorPos) {
        var nav = dog.getNavigation();
        if (nav.isDone())
            return false;
        var path = nav.getPath();
        if (path == null || path.isDone())
            return false;
        var nextNode = path.getNextNode();
        if (nextNode.asBlockPos().equals(doorPos))
            return true;
        var prevNextNode = path.getPreviousNode();
        if (prevNextNode != null && prevNextNode.asBlockPos().equals(doorPos))
            return true;
        return false;
    }

    private boolean mayDelegateClosingDoorToStillPassingDog(AbstractDog delegator, BlockPos doorPos) {
        var passers = getNearbyGatePasser(delegator);
        if (passers.isEmpty())
            return false;
        Dog delegate = null;
        GatePasserTalent delegateInst = null;
        for (var dog : passers) {
            if (!dog.isDoingFine())
                continue;
            if (!isDogStillPassingThruDoor(dog, doorPos))
                continue;
            var instOptional = dog.getTalent(DoggyTalents.GATE_PASSER);
            if (!instOptional.isPresent())
                continue;
            var inst = instOptional.get();
            if (!(inst instanceof GatePasserTalent gate))
                continue;
            delegate = dog;
            delegateInst = gate;
            break;
        }
        if (delegate == null || delegateInst == null)
            return false;
        delegateInst.currentOpenedDoor = Optional.of(doorPos);
        this.currentOpenedDoor = Optional.empty();
        this.openDoorCooldown = 5;
        return true;
    }

    private boolean isValidDoorBlock(Block block) {
        return (block instanceof DoorBlock door
                && block.builtInRegistryHolder().is(BlockTags.WOODEN_DOORS)
            )
            || block instanceof FenceGateBlock;
    }

    private void setOpenDoor(AbstractDog dog, BlockState state, BlockPos pos, boolean open) {
        var block = state.getBlock();
        if (block instanceof DoorBlock door) {
            door.setOpen(dog, dog.level(), state, pos, open);
            return;
        }
        if (block instanceof FenceGateBlock fenceGate) {
            setOpenFenceGate(dog.level(), state, pos, dog, fenceGate, open);
            return;
        }
    }

    private void setOpenFenceGate(Level level, BlockState state, BlockPos pos, AbstractDog dog, FenceGateBlock fence, boolean open) {
        boolean open0 = state.getValue(FenceGateBlock.OPEN);
        boolean hasUpdate = false;
        if (!open && open0) {
            state = state.setValue(FenceGateBlock.OPEN, Boolean.valueOf(false));
            level.setBlock(pos, state, 10);
            hasUpdate = true;
        }
        if (open && !open0) {
            var direction = dog.getDirection();
            if (state.getValue(FenceGateBlock.FACING) == direction.getOpposite()) {
                state = state.setValue(FenceGateBlock.FACING, direction);
            }

            state = state.setValue(FenceGateBlock.OPEN, Boolean.valueOf(true));
            level.setBlock(pos, state, 10);
            hasUpdate = true;
        }

        if (hasUpdate) {
            boolean still_open = state.getValue(FenceGateBlock.OPEN);
            level.playSound(null, pos, still_open ? SoundEvents.FENCE_GATE_OPEN : SoundEvents.FENCE_GATE_CLOSE, 
                SoundSource.BLOCKS, 1.0F, dog.getRandom().nextFloat() * 0.1F + 0.9F);
            level.gameEvent(dog, still_open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    private boolean isDoorOpen(BlockState state, BlockPos pos) {
        var block = state.getBlock();
        if (block instanceof DoorBlock door) {
            return door.isOpen(state);
        }
        if (block instanceof FenceGateBlock fenceGate) {
            return state.getValue(FenceGateBlock.OPEN);
        }
        return false;
    }

    @Override
    public InteractionResult canDogPassGate(AbstractDog dogIn) {
        if (this.allowPassingGate)
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.allowPassingGate = compound.getBoolean("DogGatePasser_allowPassingGate");
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.putBoolean("DogGatePasser_allowPassingGate", allowPassingGate);
    }

    @Override
    public Object getTalentOption(TalentOption<?> entry) {
        if (entry == TalentsOptions.GATE_PASSER_ENABLE.get()) {
            return this.allowPassingGate;
        }
        return null;
    }

    @Override
    public void setTalentOption(TalentOption<?> entry, Object data) {
        if (entry == TalentsOptions.GATE_PASSER_ENABLE.get()) {
            this.allowPassingGate = (Boolean) data;
        }
    }

    @Override
    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of(TalentsOptions.GATE_PASSER_ENABLE.get());
    }

    public boolean allowPassingGate() { return this.allowPassingGate; }
    public void setAllowPassingGate(boolean val) { this.allowPassingGate = val; }

}