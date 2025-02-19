package doggytalents.common.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public class DogAllyCheck {
    
    public static boolean isAlliedToDog(Dog dog, Entity entity) {
        return isAlliedToDog(dog, entity, dog.getOwner());
    }

    public static boolean isAlliedToDog(Dog dog, Entity entity, @Nullable LivingEntity owner) {
        if (dog == null || entity == null)
            return false;
        if (entity == dog)
            return true;

        var owner_uuid = dog.getOwnerUUID();
        if (owner_uuid == null)
            return false;

        boolean ally_check = owner != null ?
            checkOwnerAvailable(dog, entity, owner_uuid, owner)
            : checkOwnerNotAvailable(dog, entity, owner_uuid);
        if (ally_check)
            return true;

        boolean all_player_ally_to_dog = checkAllPlayerCannotHitDog(dog, entity);
        if (all_player_ally_to_dog)
            return true;
            
        return false;
    }

    private static boolean checkOwnerAvailable(Dog dog, Entity entity, UUID owner_uuid, LivingEntity owner) {
        if (entity == owner)
            return true;
        if (owner.isAlliedTo(entity))
            return true;

        if (entity instanceof TamableAnimal other_dog) {
            if (checkSameOwnerUUIDWithDog(owner_uuid, other_dog))
                return true;
            var owner_other = other_dog.getOwner();
            if (owner_other != null)
                return owner.isAlliedTo(owner_other);
            else if (entity instanceof Dog other_dog_actual)
                return checkSameTeamWithOfflineOwner(other_dog_actual, owner);
            else
                return false;
        }
        return false;
    }

    private static boolean checkOwnerNotAvailable(Dog dog, Entity entity, UUID owner_uuid) {        
        if (entity instanceof TamableAnimal other_dog) {
            if (checkSameOwnerUUIDWithDog(owner_uuid, other_dog))
                return true;
            var owner_other = other_dog.getOwner();
            return owner_other != null && checkSameTeamWithOfflineOwner(dog, owner_other);
        }
        
        if (checkSameTeamWithOfflineOwner(dog, entity))
            return true;

        return false;
    }

    private static boolean checkSameTeamWithOfflineOwner(Dog dog, Entity entity) {
        var owner_name_optional = dog.getOwnersName();
        if (!owner_name_optional.isPresent())
            return false;
        var owner_name = owner_name_optional.get().getString();
        if (owner_name == null || owner_name.isEmpty())
            return false;

        var team = entity.getTeam();
        if (team == null)
            return false;
        return team.getPlayers().contains(owner_name);
    }

    private static boolean checkSameOwnerUUIDWithDog(UUID dog_owner_uuid, TamableAnimal entity) {
        var other_owner_uuid = entity.getOwnerUUID();
        if (other_owner_uuid == null)
            return false;
        return dog_owner_uuid.equals(other_owner_uuid);
    }

    private static boolean checkAllPlayerCannotHitDog(Dog dog, Entity entity) {
        if (!ConfigHandler.SERVER.ALL_PLAYER_CANNOT_ATTACK_DOG.get())
            return false;

        if (entity instanceof Player) {
            return true;
        } else if (entity instanceof TamableAnimal other_dog) {
            return other_dog.getOwnerUUID() != null;
        } else {
            return false;
        }
    }
}
