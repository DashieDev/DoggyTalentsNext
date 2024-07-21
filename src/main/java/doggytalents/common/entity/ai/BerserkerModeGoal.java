package doggytalents.common.entity.ai;

import doggytalents.DoggyTags;
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

public class BerserkerModeGoal extends NearestAttackableTargetGoal<Mob> {

    private final Dog dog;

    public BerserkerModeGoal(Dog dog) {
        super(dog, Mob.class, false , (e) -> {
            if (!(e instanceof Enemy)) return false;
            
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