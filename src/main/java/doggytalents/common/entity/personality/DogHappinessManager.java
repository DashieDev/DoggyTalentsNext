package doggytalents.common.entity.personality;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogHungerManager;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;

public class DogHappinessManager {

    public static final int MAX_HAPPINESS = 100;
    public static final int STABLE_HAPPINESS = 70;
    public static final int LOW_HAPPINESS = 40;
    public static final int DEPRESSED = 10;
    
    private final Dog dog;
    private float happiness;

    public DogHappinessManager(Dog dog) {
        this.dog = dog;
    }

    public void tick() {
        syncDogHappiness();
        
    }

    private void syncDogHappiness() {
        happiness = Mth.clamp(happiness, 0, MAX_HAPPINESS);
        var synced_happiness = this.dog.getDogHappiness();
        if (synced_happiness != Mth.floor(happiness)) {
            this.dog.setDogHappiness(Mth.floor(happiness));
        }
    }

    public void onHurt(DamageSource source) {
        
    }

    

}
