package doggytalents.common.entity;

import java.util.Optional;

import doggytalents.api.impl.IDogRangedAttackManager;
import net.minecraft.world.entity.projectile.ThrownTrident;

public class DogProjectileManager {
    
    private final Dog dog;

    public DogProjectileManager(Dog dog) {
        this.dog = dog;
    }

    public void tick() {
        validateAwaitingTrident(dog);
    }

    

}
