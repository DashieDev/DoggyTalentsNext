package doggytalents.common.fabric_helper.entity;

import doggytalents.common.entity.Dog;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FabricDogKillXPFix {
 
    public static void onMobActuallyBeingHurt(LivingEntity mob, DamageSource source) {
        var hurter = source.getEntity();
        if (hurter == null)
            return;
        if (!(hurter instanceof Dog hurter_dog))
            return;
        var owner = hurter_dog.getOwner();
        if (owner == null)
            return;
        if (!(owner instanceof Player owner_player))
            return;
        mob.lastHurtByPlayer = owner_player;
        mob.lastHurtByPlayerTime = 100;
    }

}
