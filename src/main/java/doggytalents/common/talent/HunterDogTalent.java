package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.event.LootingLevelEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class HunterDogTalent {

    // public static void onLootDrop(final LootingLevelEvent event) {
    //     DamageSource damageSource = event.getDamageSource();

    //     // Possible to be null #265
    //     if (damageSource != null) {
    //         Entity trueSource = damageSource.getEntity();
    //         if (trueSource instanceof Dog) {
    //             Dog dog = (Dog) trueSource;
    //             int level = dog.getDogLevel(DoggyTalents.HUNTER_DOG);

    //             if (dog.getRandom().nextInt(6) < level + (level >= 5 ? 1 : 0)) {
    //                 event.setLootingLevel(event.getLootingLevel() + level / 2);
    //             }
    //         }
    //     }
    // }
}
