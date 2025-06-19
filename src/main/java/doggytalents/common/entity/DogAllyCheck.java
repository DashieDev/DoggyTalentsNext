package doggytalents.common.entity;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import doggytalents.DoggyEntityTypes;
import doggytalents.common.config.ConfigHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;

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
        //Avoid triggering the mixin and unecessarily calling isAlliedToDog again here.
        if (entity.getType() != DoggyEntityTypes.DOG.get() 
            && owner.isAlliedTo(entity))
            return true;

        if (entity instanceof TamableAnimal other_dog && other_dog.getOwnerUUID() != null) {
            if (checkSameOwnerUUIDWithDog(owner_uuid, other_dog))
                return true;

            var owner_other = other_dog.getOwner();
            if (owner_other != null)
                return owner.isAlliedTo(owner_other);
            if (other_dog instanceof Dog other_dog_actual)
                return checkSameTeamWithOfflineOwner(other_dog_actual, owner);
            var other_team = findOwnerTeam(other_dog);
            if (other_team.isPresent())
                return owner.isAlliedTo(other_team.get());
            return false;
        }
        return false;
    }

    private static boolean checkOwnerNotAvailable(Dog dog, Entity entity, UUID owner_uuid) {        
        if (entity instanceof TamableAnimal other_dog && other_dog.getOwnerUUID() != null) {
            if (checkSameOwnerUUIDWithDog(owner_uuid, other_dog))
                return true;
            return checkSameTeamWithOfflineOwnerTamable(dog, other_dog);
        }
        
        if (checkSameTeamWithOfflineOwner(dog, entity))
            return true;

        return false;
    }

    private static boolean checkSameTeamWithOfflineOwner(Dog dog, Entity entity) {
        return checkSameTeamWithOfflineOwner(dog, entity.getTeam());
    }

    private static boolean checkSameTeamWithOfflineOwnerTamable(Dog dog, TamableAnimal entity) {
        return checkSameTeamWithOfflineOwner(dog, findTamableTeam(entity));
    }

    private static Team findTamableTeam(TamableAnimal other_dog) {
        return findOwnerTeam(other_dog).orElseGet(other_dog::getTeam);
    }

    private static Optional<Team> findOwnerTeam(TamableAnimal other_dog) {
        var uuid = other_dog.getOwnerUUID();
        if (uuid == null)
            return Optional.empty();
        var server = other_dog.level().getServer();
        if (server == null)
            return Optional.empty();
        var player = server.getPlayerList().getPlayer(uuid);
        if (player == null)
            return Optional.empty();
        var scoreboard = server.getScoreboard();
        var ret = scoreboard.getPlayersTeam(player.getScoreboardName());
        return Optional.ofNullable(ret);
    }

    private static boolean checkSameTeamWithOfflineOwner(Dog dog, Team other_team) {
        if (other_team == null)
            return false;
        
        var owner_name_optional = dog.getOwnersName();
        if (!owner_name_optional.isPresent())
            return false;
        var owner_name = owner_name_optional.get().getString();
        if (owner_name == null || owner_name.isEmpty())
            return false;

        return other_team.getPlayers().contains(owner_name);
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

    //Workaround to apply DogAllyCheck to sweeping attack too.
    private static final ThreadLocal<Boolean> isMixinCalling = ThreadLocal.withInitial(() -> false);
    public static boolean onEntityIsAlliedToServer(Entity self, Entity entity) {
        boolean entity_type_check = 
            self.getType() == EntityType.PLAYER
            && entity.getType() == DoggyEntityTypes.DOG.get();
        if (!entity_type_check)
            return false;
        if (isMixinCalling.get())
            return false;
            
        if (!(entity instanceof Dog dog))
            return false;
        
        isMixinCalling.set(true);
        boolean result = DogAllyCheck.isAlliedToDog(dog, self);
        isMixinCalling.set(false);
        
        return result;
    }

}
