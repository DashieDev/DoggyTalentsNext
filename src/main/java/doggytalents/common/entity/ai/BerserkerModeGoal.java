package doggytalents.common.entity.ai;

import doggytalents.DoggyTags;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.fabric_helper.util.FabricUtil;
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
            if (targetingOwnerCheck(dog, e))
                return true;
            if (!(e instanceof Enemy)) return false;
            
            return true;
        });
        this.dog = dog;
    }

    private static boolean targetingOwnerCheck(Dog dog, LivingEntity e) {
        if (!ConfigHandler.SERVER.BG_MODE_LESS_STRICT.get())
            return false;
        if (!(e instanceof Mob mob))
            return false;
        var owner = dog.getOwner();
        return mob.getTarget() == owner;
    }

    @Override
    public boolean canUse() {
        return (
            this.dog.isMode(EnumMode.BERSERKER, EnumMode.BERSERKER_MINOR, EnumMode.PATROL)
        ) && super.canUse();
    }
}