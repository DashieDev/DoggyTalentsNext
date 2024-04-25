package doggytalents.api.impl;

import java.util.Optional;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

public interface IDogRangedAttackManager {
    
    public static IDogRangedAttackManager NONE = new IDogRangedAttackManager() {
        @Override
        public boolean isApplicable(AbstractDog dog) {
            return false;
        }

        @Override
        public boolean updateUsingWeapon(UsingWeaponContext ctx) {
            return false;
        }
    };

    public boolean isApplicable(AbstractDog dog);

    public boolean updateUsingWeapon(UsingWeaponContext ctx);

    default void onStart(AbstractDog dog) {

    }

    default void onStop(AbstractDog dog) {
        
    }

    public static class UsingWeaponContext {
        
        public final AbstractDog dog;
        public final boolean canSeeTarget;
        public final int seeTime;
        public final int cooldown;
        public final LivingEntity target;

        public UsingWeaponContext(AbstractDog dog, boolean canSeeTarget, int seeTime, int cooldown, LivingEntity target) {
            this.dog = dog;
            this.canSeeTarget = canSeeTarget;
            this.seeTime = seeTime;
            this.cooldown = cooldown;
            this.target = target;
        }

    }

}
