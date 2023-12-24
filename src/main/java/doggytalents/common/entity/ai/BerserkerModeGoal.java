package doggytalents.common.entity.ai;

import doggytalents.DoggyTags;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Mob;
import net.minecraft.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.EnderMan;
import net.minecraft.entity.monster.Enemy;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.ZombifiedPiglin;
import net.minecraft.entity.monster.piglin.AbstractPiglin;
import net.minecraft.entity.monster.piglin.PiglinBrute;

public class BerserkerModeGoal extends NearestAttackableTargetGoal<Mob> {

    private final Dog dog;

    public BerserkerModeGoal(Dog dog) {
        super(dog, Mob.class, false , (e) -> {
            if (!(e instanceof Enemy)) return false;
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
                if (e.getType().is(DoggyTags.DOG_SHOULD_IGNORE))
                    return false;
            }
            return true;
        });
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        return (
            this.dog.isMode(EnumMode.BERSERKER, EnumMode.BERSERKER_MINOR, EnumMode.PATROL)
        ) && super.canUse();
    }
}