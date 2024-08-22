package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.fabric_helper.util.FabricUtil;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

/**
 * @author DashieDev
 */
public class DogAvoidPushWhenIdleGoal extends Goal {

    private Dog dog;
    private int cooldown = 0;
    private int grace = 0;
    
    public DogAvoidPushWhenIdleGoal(Dog dog) {
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }

        if (!dog.canDogResistPush())
            return false;

        if (!checkIfShouldBeginResisting()) return false;

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.canDogResistPush())
            return false;
        if (this.grace <= 0)
            return false;
        
        return true;
    }

    @Override
    public void start() {
        //grace period 5 tick
        this.grace = 3;
        this.dog.setDogResistingPush(true);
    }

    @Override
    public void tick() {
        if (grace > 0) --this.grace;
    }

    @Override
    public void stop() {
        this.grace = 0;
        this.dog.setDogResistingPush(false);
    }

    private boolean checkIfShouldBeginResisting() {
        var delta = dog.getDeltaMovement();
        var delta_l = delta.x*delta.x + delta.z*delta.z;
        var xz_cap = this.dog.getDogPushResistXZCap();
        if (delta_l <= xz_cap * xz_cap)
            return false;

        var dog_pos = dog.position();
        var dog_pos_1 = new Vec3(
            dog_pos.x + delta.x,
            dog_pos.y,
            dog_pos.z + delta.z
        );
        
        var dog_b0 = BlockPos.containing(dog_pos);
        var dog_b1 = BlockPos.containing(dog_pos_1);

        var blockType = dog.getBlockPathTypeViaAlterations(dog_b0);
        boolean currently_damaging = 
            FabricUtil.getDanger(blockType) != null    
            && dog.getPathfindingMalus(blockType) < 0;
        if (currently_damaging) {
            this.cooldown = 10;
            return false;
        }

        if (!dog_b0.equals(dog_b1))
            blockType = dog.getBlockPathTypeViaAlterations(dog_b1);
        if (FabricUtil.getDanger(blockType) != null)
            return true;

        if (blockType != BlockPathTypes.OPEN)
            return false;
        
        boolean noWalkable = true;
        for (int i = 1; i <= dog.getMaxFallDistance(); ++i) {
            var type = dog.getBlockPathTypeViaAlterations(dog_b1.below(i));
            if (type == BlockPathTypes.OPEN)
                continue;
            else {
                noWalkable = type != BlockPathTypes.WALKABLE;
                break;
            }
        }

        return noWalkable;

    }
}
