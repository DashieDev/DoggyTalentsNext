package doggytalents.common.entity;

import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.api.feature.DogSize;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class DogPettingManager {
    
    private final Dog dog;
    private boolean locked = false;
    private long lastPetTimestamp = 0;

    public DogPettingManager(Dog dog) {
        this.dog = dog;
    }

    public boolean isPetting() {
        return dog.getPettingState().is_petting();
    }

    public UUID getPetterId() {
        return dog.getPettingState().petting_id();
    }

    public void setPetting(Player petter, DogPettingType type) {
        if (petter == null || type == null || !canPet(petter))
            return;
        if (isPetting())
            return;
        dog.setPettingState(new DogPettingState(petter.getUUID(), true, type));
        petter.yBodyRot = petter.yHeadRot;
    }

    public void stopPetting() {
        if (!dog.level().isClientSide)
            this.lastPetTimestamp = dog.level().getDayTime();
        dog.setPettingState(DogPettingState.NULL);
    }

    public long getTimeSinceLastPet() {
        long ret = dog.level().getDayTime() - this.lastPetTimestamp;
        if (ret < 0) ret = 0;
        return ret;
    }

    public float getJealousChance() {
        final long chance_time_start = 20 * 20;
        final long chance_time_end = 5 * 60 * 20;
        final float max_chance = 0.2f;
        final float min_chance = 0.05f;

        long last_pet_time = getTimeSinceLastPet();
        if (last_pet_time <= chance_time_start)
            return -1;
        long chance_time = 
            last_pet_time - chance_time_start;
        double progress = chance_time / 
            (double)(chance_time_end - chance_time_start);
        progress = Mth.clamp(progress, 0, 1);
        var ret = min_chance + progress * (max_chance - min_chance);
        return (float) ret;
    }

    public void tick() {
        if (!this.dog.level().isClientSide && this.isPetting() && !canPet(getPetterFromDog()))
            this.stopPetting();
        if (this.dog.level().isClientSide && this.isPetting()) {
            playParticleEffect();    
        }
        if (!this.dog.level().isClientSide && this.isPetting() && dog.dogVariant().burnsPetter())
            mayDoLoveBurns();
        if (this.isPetting()) {
            var petter = this.getPetterFromDog();
            if (petter != null)
                petter.yBodyRot = petter.yHeadRot;
        }
    }

    private int burn_cooldown = 10;
    public void mayDoLoveBurns() {
        if (--burn_cooldown > 0)
            return;
        var random = this.dog.getRandom();
        if (random.nextInt(100) != 0)
            return;
        burn_cooldown = (7 + random.nextInt(9)) * 20;
        var petter = this.getPetterFromDog();
        if (petter == null)
            return;
        var hurt_result = petter.hurt(petter.damageSources().lava(), 0.1f);
        if (hurt_result) {
            petter.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + random.nextFloat() * 0.4F);
            if (dog.level() instanceof ServerLevel) {
                ((ServerLevel) dog.level()).sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                    petter.getX(), petter.getY(), petter.getZ(), 
                    random.nextIntBetweenInclusive(2, 4), 
                    petter.getBbWidth(), 0.8f, petter.getBbWidth(), 
                    0.1
                );
            }
        }
        
    }

    public void playParticleEffect() {
        var random = this.dog.getRandom();
        if (this.dog.getRandom().nextInt(20) == 0) {
            double x = (double)dog.getX() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2 + 0.3);
            double y = (double)dog.getY() + 0.4 + random.nextFloat() * (dog.getBbHeight() - 0.4);
            double z = (double)dog.getZ() + RandomUtil.nextFloatRemapped(random) * (dog.getBbWidth()/2 + 0.3);
            double dx = random.nextGaussian() * 0.02;
            double dy = random.nextGaussian() * 0.02;
            double dz = random.nextGaussian() * 0.02;
            dog.level().addParticle(ParticleTypes.HEART, x, y, z, dx, dy, dz);
        }
    }

    //Common check
    public boolean canPet(Player player) {
        if (this.locked)
            return false;
        
        if (player == null)
            return false;
        if (ObjectUtils.notEqual(dog.getOwnerUUID(), player.getUUID()))
            return false;
        if (!isInPetDistance(dog, player))
            return false;
        if (!isSelectingDog(player, dog))
            return false;

        if (!isPlayerAbleToPet(player))
            return false;
        if (!isDogAbleToBePet(dog))
            return false;
        
        return true;
    }

    public boolean isPlayerAbleToPet(Player player) {
        if (!player.getMainHandItem().isEmpty())
            return false;
        if (!player.getOffhandItem().isEmpty())
            return false;
        if (player.isVehicle() || player.isPassenger())
            return false;
        if (player.isSpectator())
            return false;
        if (!player.isShiftKeyDown())
            return false;
        
        return true;
    }

    public boolean isDogAbleToBePet(Dog dog) {
        if (!dog.isDoingFine())
            return false;
        if (!dog.isInSittingPose() || dog.getDogPose() != DogPose.SIT)
            return false;
        if (dog.isOnFire())
            return false;
        if (dog.isVehicle() || dog.isPassenger())
            return false;
        var size = dog.getDogSize();
        if (!size.largerOrEquals(DogSize.MODERATO))
            return false;
        
        return true;
    }

    public boolean isInPetDistance(Dog dog, Player player) {
        var max_dist = getMaxPetDistance(dog, player);
        if (dog.distanceToSqr(player) >= max_dist * max_dist)
            return false;
        if (!checkEyeDistance(dog, player))
            return false;
        return true;
    }

    private double getMaxPetDistance(Dog dog, Player player) {
        var dog_bbw = dog.getBbWidth();
        return dog_bbw/2 + player.getBbWidth()/2 + 0.5;
    }

    private boolean checkEyeDistance(Dog dog, Player player) {
        var player_eye_y = player.getEyeY();
        var dog_eye_y = dog.getEyeY();
        var d_eye_y = player_eye_y - dog_eye_y;
        return 0.3 <= d_eye_y || d_eye_y <= 0.5;
    }

    private boolean isSelectingDog(Player player, Dog dog) {
        double pick_range = 3; //Fabric hardcode.
        var view_vec = player.getViewVector(1);
        var eye_pos = player.getEyePosition(0);
        var from_vec = eye_pos;
        var to_vec = eye_pos.add(view_vec.scale(pick_range));
        var dog_bb = dog.getBoundingBox();
        boolean hit_dog = dog_bb.clip(from_vec, to_vec).isPresent();

        return hit_dog;
    }

    private Player getPetterFromDog() {
        if (!this.isPetting())
            return null;
        var pet_uuid = this.getPetterId();
        var petter = this.dog.level().getPlayerByUUID(pet_uuid);
        return petter;
    }

    public boolean checkPush(Entity source) {
        if (!this.isPetting())
            return false;
        var petting = getPetterFromDog();
        if (petting != source)
            return false;
        float max_go_inside_bb_dist = getMinClipDistanceWhenPet(); 
        if (max_go_inside_bb_dist <= 0)
            return false;
        if (petting.distanceToSqr(dog) < max_go_inside_bb_dist * max_go_inside_bb_dist)
            return false;
        return true;
    }

    private float getMinClipDistanceWhenPet() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.BACK_HUG || type == DogPettingType.BELLY_RUB)
            return 0.7f * this.dog.getBbWidth()/2;
        return -1;
    }

    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    public void save(CompoundTag tag) {
        var tag0 = new CompoundTag();
        tag0.putLong("dog_last_pet_time", this.lastPetTimestamp);
        tag.put("dogPettingManager", tag0);
    }

    public void load(CompoundTag tag) {
        this.lastPetTimestamp = 0;
        if (tag.contains("dogPettingManager", Tag.TAG_COMPOUND)) {
            var tag0 = tag.getCompound("dogPettingManager");
            this.lastPetTimestamp = tag0.getLong("dog_last_pet_time");
        }
    }

    public static record DogPettingState(UUID petting_id, boolean is_petting, DogPettingType type) {
        public static DogPettingState NULL = new DogPettingState(net.minecraft.Util.NIL_UUID, false, DogPettingType.FACERUB);
    }

    public static enum DogPettingType {
        FACERUB(0),
        HUG(1),
        BELLY_RUB(2),
        BACK_HUG(3);

        private final int id;

        private DogPettingType(int id) {
            this.id = id;
        }

        public int getId() { return this.id; }

        public static DogPettingType fromId(int id) {
            var vals = DogPettingType.values();
            if (id < 0 || id >= vals.length)
                id = 0;
            return vals[id];
        }

    }

}
