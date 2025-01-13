package doggytalents.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class DTNClientDogSleepOnManager {
    
    public static final DTNClientDogSleepOnManager INSTANCE = new DTNClientDogSleepOnManager();

    public static DTNClientDogSleepOnManager get() {
        return INSTANCE;
    }

    private DTNClientDogSleepOnManager() {}


    private final Map<UUID, Dog> sleeperMap = Maps.newHashMap();
    private final List<UUID> toRemove = new ArrayList<>();

    @SubscribeEvent
    public void tickClient(ClientTickEvent.Post event) {
        invalidateSleeperCache();
    }

    public void invalidateSleeperCache() {
        if (this.sleeperMap.isEmpty())
            return;
        for (var entry : this.sleeperMap.entrySet()) {
            var dog = entry.getValue();
            if (!dog.isAlive())
                toRemove.add(entry.getKey());
        }
        if (toRemove.isEmpty())
            return;
        for (var player : toRemove) {
            this.sleeperMap.remove(player);
        }
        toRemove.clear();
    }

    public void onDogSleepOnDataUpdated(Dog dog, DogSleepOnState state) {
        if (!state.is_sleeping()) {
            clearPlayerSleepOnFor(dog);
            return;
        }
        this.sleeperMap.putIfAbsent(state.sleeper(), dog);
        var sleeper_optional = DogSleepOnManager.getSleeperFromDog(dog, state);
        if (!sleeper_optional.isPresent())
            return;
        var sleeper = sleeper_optional.get();
        if (sleeper == Minecraft.getInstance().player)
            DogSleepOnManager.rotatePlayerYRotToDog(dog, sleeper, state.sleep_yrot());
    }

    public void clearPlayerSleepOnFor(Dog dog) {
        if (sleeperMap.isEmpty())
            return;
        var toRemove = new ArrayList<UUID>();
        for (var entry : sleeperMap.entrySet()) {
            if (entry.getValue() == dog) {
                toRemove.add(entry.getKey());
            }
        }
        for (var key : toRemove) {
            this.sleeperMap.remove(key);
        }
    }
    

    public boolean onLivingModelSetupRotation(LivingEntity living, PoseStack stack, 
        float anim_timeline, float yrot, float pticks, float scale) {
        var player_optional = checkIsSleepingOnDog(living);
        if (!player_optional.isPresent())
            return false;
        var player = player_optional.get();
        float facing = player.getYRot() - 180;
        var translate = calclateSleepTranslate(player);
        stack.translate(translate.x, 0, translate.z);
        stack.mulPose(Axis.YP.rotationDegrees(180 - facing));
        stack.mulPose(Axis.XP.rotationDegrees(90));
        return true;
    }

    private Vec3 calclateSleepTranslate(Player player) {
        var uuid = player.getUUID();
        var dog = this.sleeperMap.get(uuid);
        if (dog == null)    
            return Vec3.ZERO;
        
        var view_vec = player.getViewVector(1);
        
        float translate_amount = player.getEyeHeight(Pose.STANDING) - 0.1F;

        return view_vec.normalize().scale(-translate_amount);
    }

    public void afterPlayerModelSetupAnim(LivingEntity living, float limbSwing, 
        float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, PlayerModel<?> model) {
        var player_optional = checkIsSleepingOnDog(living);
        if (!player_optional.isPresent())
            return;
        
        model.head.xRot += 40 * Mth.DEG_TO_RAD; 
    }

    private Optional<Player> checkIsSleepingOnDog(Entity entity) {
        if (!entity.hasPose(Pose.SLEEPING))
            return Optional.empty();
        if (sleeperMap.isEmpty())
            return Optional.empty();
        if (!(entity instanceof Player player))
            return Optional.empty();
        var sleep_on = sleeperMap.get(player.getUUID());
        if (sleep_on == null)
            return Optional.empty();
        
        return Optional.of(player);
    }

    public void afterCameraSetup(Camera camera, Entity entity) {
        if (sleeperMap.isEmpty())
            return;
        var player_optional = checkIsSleepingOnDog(entity);
        if (!player_optional.isPresent())
            return;
        var player = player_optional.get();
        var dog = sleeperMap.get(player.getUUID());
        camera.setRotation(dog.getSleepOnState().sleep_yrot(), 0.0F);
    }

}
