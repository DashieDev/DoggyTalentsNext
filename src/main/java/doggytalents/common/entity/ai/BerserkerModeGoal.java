package doggytalents.common.entity.ai;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;

public class BerserkerModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final Dog dog;

    public BerserkerModeGoal(Dog dog) {
        super(dog, Monster.class, false , (e) -> {
            if (dog.isMode(EnumMode.BERSERKER_MINOR)) {
                if (e instanceof ZombifiedPiglin) return false;
                if (e instanceof AbstractPiglin) {
                    var owner = dog.getOwner();
                    if (owner != null) {
                        for (var stack : owner.getArmorSlots()) {
                            if (stack.makesPiglinsNeutral(owner)) {
                                return false;
                            }
                        }
                    }
                }
                if (e instanceof EnderMan) return false;
            }
            return true;
        });
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        return (
            this.dog.isMode(EnumMode.BERSERKER, EnumMode.BERSERKER_MINOR)
        ) && super.canUse();
    }
}