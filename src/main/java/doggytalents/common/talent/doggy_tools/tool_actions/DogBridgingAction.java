package doggytalents.common.talent.doggy_tools.tool_actions;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.DogBridging;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class DogBridgingAction extends TriggerableAction {

    private static enum Phase { GO_TO_INITIAL_POS, BRIDGE }
    private Phase phase = Phase.GO_TO_INITIAL_POS;

    private final DoggyToolsTalent toolsTalent;
    private final LivingEntity owner;

    private BlockPos initialPos;

    private Direction bridgingDir;
    private int bridgingY = 0;
    private BlockPos nextPos = null;
    private BlockPos prevPos = null;
    private static enum NextPosAction { GO, STOP, BRIDGE }
    private int bridgeCooldown;

    public DogBridgingAction(Dog dog, LivingEntity owner, float bridgingRot, BlockPos initialPos, DoggyToolsTalent talent) {
        super(dog, true, false);
        this.owner = owner;
        this.toolsTalent = talent;
        this.initialPos = initialPos;
        this.bridgingDir = Direction.fromYRot(bridgingRot);
    }

    @Override
    public void onStart() {
        var path = dog.getNavigation().createPath(initialPos, 1);
        if (path == null || !DogUtil.canPathReachTargetBlock(dog, path, initialPos, 1, dog.getMaxFallDistance())) {
            this.setState(ActionState.FINISHED);
            return;
        }
        phase = Phase.GO_TO_INITIAL_POS;
        dog.getNavigation().moveTo(path, 1);
        DogBridging.equipBridgingStack(toolsTalent, dog);
    }

    @Override
    public void tick() {
        if (!DogBridging.isValidBridgingDog(toolsTalent, dog)) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (this.phase == Phase.GO_TO_INITIAL_POS) {
            goToInitialPos();
        } else {
            doBridge();
        }
    }

    private void goToInitialPos() {
        if (this.dog.getNavigation().isDone()) {
            this.phase = Phase.BRIDGE;
            this.nextPos = getNextPos();
            this.bridgingY = dog.blockPosition().getY();
            return;
        }
    }

    private void doBridge() {
        if (this.bridgeCooldown > 0)
            --this.bridgeCooldown;
        if (dog.blockPosition().getY() != this.bridgingY) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (!owner.isAlive() || owner.isSpectator() 
            || owner.distanceToSqr(dog) >= 12 * 12) {
            
            this.setState(ActionState.FINISHED);
            return;
        }
        // if (owner.isShiftKeyDown()) {
        //     this.bridgingDir = Direction.fromYRot(owner.getYHeadRot());
        // }

        if (this.nextPos == null)
            this.nextPos = getNextPos();
        var next_pos_action = this.getNextPosAction(this.nextPos);
        if (next_pos_action == NextPosAction.STOP) {
            this.setState(ActionState.FINISHED);
            return;
        }
        dog.getLookControl().setLookAt(Vec3.atBottomCenterOf(nextPos));
        if (next_pos_action == NextPosAction.GO) {
            var go_state = getGoState(nextPos);
            if (go_state == GoState.TOO_FAR) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (go_state == GoState.NOT_REACHED) {
                var go_pos = Vec3.atBottomCenterOf(nextPos);
                this.dog.getMoveControl().setWantedPosition(go_pos.x, go_pos.y, go_pos.z, 0.5);
            } else {
                this.nextPos = null;
            }
        } else if (next_pos_action == NextPosAction.BRIDGE) {
            if (bridgeCooldown <= 0 && !tryBridge(this.nextPos)) {
                this.setState(ActionState.FINISHED);
                return;
            }
        }
    }

    private BlockPos getNextPos() {
        return this.dog.blockPosition().relative(bridgingDir);
    }

    private NextPosAction getNextPosAction(BlockPos pos) {
        var path_type = dog.getBlockPathTypeViaAlterations(pos);
        if (path_type == PathType.OPEN)
            return NextPosAction.BRIDGE;
        if (path_type == PathType.WALKABLE)
            return NextPosAction.GO;
        return NextPosAction.STOP;
    }

    private static enum GoState { REACHED, NOT_REACHED, TOO_FAR }
    private GoState getGoState(BlockPos pos) {
        final float near_dist = 0.45f;
        final float far_dist = 2f;
        var dist_sqr = dog.distanceToSqr(Vec3.atBottomCenterOf(pos));
        if (dist_sqr >= far_dist * far_dist)
            return GoState.TOO_FAR;
        if (dist_sqr <= near_dist * near_dist)
            return GoState.REACHED;
        return GoState.NOT_REACHED;
    }

    private boolean tryBridge(BlockPos pos) {
        pos = pos.below();
        if (this.prevPos != null && this.prevPos.equals(pos))
            return false;
        var state = dog.level().getBlockState(pos);
        if (!state.isAir())
            return false;
        var pair_optional = DogBridging.getBridgingMaterial(toolsTalent, dog);
        if (!pair_optional.isPresent())
            return false;
        var pair = pair_optional.get();
        pair.getRight().place(getBlockPlaceContext(pos, pair.getLeft()));
        if (pair.getLeft().isEmpty()) {
            DogBridging.getBridgingMaterial(toolsTalent, dog)
                .ifPresent(x -> dog.setItemInHand(InteractionHand.MAIN_HAND, x.getLeft()));
        }
        this.bridgeCooldown = 15;
        this.prevPos = pos;
        return true;
    }

    private BlockPlaceContext getBlockPlaceContext(BlockPos place_pos, ItemStack stack) {
        return new BlockPlaceContext(this.dog.level(), null, InteractionHand.MAIN_HAND, stack, 
            new BlockHitResult(Vec3.atBottomCenterOf(place_pos), Direction.UP, place_pos, false)
        );
    } 

    @Override
    public void onStop() {
        if (this.isStarted())
            dog.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        DogBridging.onBridgingActionStop(owner);
    }
}
