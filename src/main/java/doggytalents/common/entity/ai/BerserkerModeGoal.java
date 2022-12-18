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

    public BerserkerModeGoal(Dog dogIn) {
        super(dogIn, Monster.class, false , (e) -> {
            if (dogIn.isMode(EnumMode.BERSERKER_MINOR)) {
                if (e instanceof AbstractPiglin) return false;
                if (e instanceof ZombifiedPiglin) return false;
                if (e instanceof EnderMan) return false;
            }
            return true;
        });
        this.dog = dogIn;
    }

    @Override
    public boolean canUse() {
        return (
            this.dog.isMode(EnumMode.BERSERKER, EnumMode.BERSERKER_MINOR)
        ) && super.canUse();
    }
}