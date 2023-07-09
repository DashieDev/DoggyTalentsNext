package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogPathNavigation extends GroundPathNavigation {

    private Dog dog;

    public DogPathNavigation(Dog dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    protected void followThePath() {
        if (invalidateIfNextNodeIsTooHigh()) return;

        var currentPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = 
            this.mob.getBbWidth() > 0.75F ? 
                this.mob.getBbWidth() / 2.0F 
                : 0.75F - this.mob.getBbWidth() / 2.0F;

        var nextPos = this.path.getNextNodePos();
        double dx = Math.abs(this.mob.getX() - ((double)nextPos.getX() + 0.5));
        double dy = Math.abs(this.mob.getY() - (double)nextPos.getY());
        double dz = Math.abs(this.mob.getZ() - ((double)nextPos.getZ() + 0.5));

        boolean isCloseEnough = 
            dx <= (double)this.maxDistanceToWaypoint 
            && dy < 1.0D
            && dz <= (double)this.maxDistanceToWaypoint;
        boolean canCutCorner = 
            this.canCutCorner(this.path.getNextNode().type) 
            && this.shouldTargetNextNodeInDirection(currentPos);

        if (isCloseEnough || canCutCorner) {
            this.path.advance();
        }
  
        this.doStuckDetection(currentPos);
    }

    protected boolean invalidateIfNextNodeIsTooHigh() {
        var path = this.path;
        if (path != null) {
            var nextPos = path.getNextNodePos();
            var dy = this.mob.getY() - (double)nextPos.getY();
                    
            if (dy < -1.75) {
                this.stop();
                return true;
            }
        }
        return false;
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 current_pos) {
        var path = this.path;
        if (path == null) return false;
        if (path.getNextNodeIndex() + 1 >= path.getNodeCount()) {
           return false;
        }
        
        var next_pos = Vec3.atBottomCenterOf(path.getNextNodePos());
        if (!current_pos.closerThan(next_pos, 2.0D)) {
            return false;
        }

        Vec3 next2th_node = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
        Vec3 v_next_next2th = next2th_node.subtract(next_pos);
        Vec3 v_next_current = current_pos.subtract(next_pos);
        //small alpha
        if (v_next_next2th.dot(v_next_current) <= 0.0D) return false;

        Vec3 v_current_next2th = next2th_node.subtract(current_pos);
        double v_current_next2th_lSqr = v_current_next2th.lengthSqr();
        if (v_current_next2th_lSqr < 1) return true;
        Vec3 v_add = v_current_next2th.normalize();
        var check_b0 = BlockPos.containing(current_pos.add(v_add));
        var type = WalkNodeEvaluator
            .getBlockPathTypeStatic(level, check_b0.mutable());
        return type == BlockPathTypes.WALKABLE;
    }
    
}
