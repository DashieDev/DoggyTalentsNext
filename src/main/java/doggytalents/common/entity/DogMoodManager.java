package doggytalents.common.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DogSounds;
import doggytalents.common.util.LangUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.damagesource.DamageSource;

public class DogMoodManager {
    
    private final Dog dog;
    
    //For now: -1 -> unhappy, 0 -> neutral, 1 -> happy
    private int happiness = 0;
    private int toUpdateTimestamp = 0;
    
    public DogMoodManager(Dog dog) {
        this.dog = dog;
    }

    public void tickServer() {
        if (dog.tickCount >= toUpdateTimestamp) {
            toUpdateTimestamp = dog.tickCount + getUpdateInterval();
            happiness = getNextMood();
        }
    }

    private int getNextMood() {
        final float away_from_normal = 0.04f;
        int ret = 0;   
        if (dog.getRandom().nextFloat() <= away_from_normal) {
            ret = dog.getRandom().nextBoolean() ? 1 : -1;
            int non_normal_time = getUpdateInterval() * getNonNormalTime();
            this.toUpdateTimestamp = dog.tickCount + non_normal_time;
        }
        return ret;
    }

    public int getNonNormalTime() {
        final float split = 0.8f;
        final int split_map = 6; 
        final int max_time = 15;
        float r = dog.getRandom().nextFloat();
        if (r < split) {
            int ret = Mth.floor(r/split * split_map);
            return 1 + Mth.clamp(ret, 0, max_time);
        } else {
            int ret = split_map + Mth.floor(((r - split) / (1 - split)) * (max_time - split_map));
            return 1 + Mth.clamp(ret, 0, max_time);
        }
    }

    public int getHappiness() {
        return this.happiness;
    }

    public int getUpdateInterval() {
        return 20 * 20;
    }

    private static final List<Supplier<SoundEvent>> minor_ambient = List.of(
        DogSounds.ANGRY_BARK3,
        DogSounds.ANGRY_BARK2,
        DogSounds.ANGRY_BARK1,
        DogSounds.CUTE_GROWL1,
        DogSounds.CUTE_GROWL3,
        DogSounds.PUGLIN_GROWL3,
        DogSounds.CUTE_PANTING
    );
    private static final List<Supplier<SoundEvent>> perfect_neutral_ambient = List.of(
        DogSounds.CLASSIC_BARK1,
        DogSounds.CLASSIC_BARK2,
        DogSounds.CLASSIC_BARK3,
        DogSounds.CLASSIC_PANTING
    );
    private static final WeightedRandomList
        <WeightedEntry.Wrapper<Supplier<SoundEvent>>> 
        augmented_neutral_ambient = WeightedRandomList.create(
            WeightedEntry.wrap(DogSounds.GRUMPY_BARK2, 6),    
            WeightedEntry.wrap(DogSounds.GRUMPY_BARK3, 6),
            WeightedEntry.wrap(DogSounds.CUTE_BARK1, 4),
            WeightedEntry.wrap(DogSounds.CUTE_BARK3, 4), 
            WeightedEntry.wrap(DogSounds.PUGLIN_BARK2, 1)
        );
    private static final List<Supplier<SoundEvent>> major_ambient = List.of(
        DogSounds.SAD_BARK1,
        DogSounds.PUGLIN_BARK2,
        DogSounds.PUGLIN_BARK3,
        DogSounds.GRUMPY_BARK1,
        DogSounds.CUTE_BARK2,
        DogSounds.CUTE_BARK1,
        DogSounds.CUTE_BARK3,
        DogSounds.GRUMPY_BARK2,
        DogSounds.GRUMPY_BARK3
    );
    public SoundEvent getAmbientSound() {
        if (this.dog.level().isClientSide)
            return DogSounds.CLASSIC_BARK1.get();        
        
        int current_diff = this.happiness;
        var selected_list = perfect_neutral_ambient;
        if (current_diff != 0) {
            float non_neutral_chance = current_diff < 0 ? 0.5f : 0.9f; 
            float r = dog.getRandom().nextFloat();
            if (r < non_neutral_chance) {
                selected_list = current_diff > 0 ? major_ambient : minor_ambient;
            }
        }

        float aug_chance = 0.1f;
        if (this.happiness < 0)
            aug_chance = 0.01f;
        else if (this.happiness > 0)
            aug_chance = 0.5f;

        boolean is_aug = selected_list == perfect_neutral_ambient
            && dog.getRandom().nextFloat() <= aug_chance;
        if (is_aug)
            return augmented_neutral_ambient.getRandom(dog.getRandom()).get().data().get();
        else 
            return LangUtil.getRandomItem(dog.getRandom(), selected_list).get().get();
    }


    private float recordedHealth = 0;
    private int lastHurt = 0;
    private boolean chainHurt = false;

    public void onStartHurting(float current_health) {
        recordedHealth = dog.getHealth();
        chainHurt = dog.tickCount - lastHurt <= 16;
        lastHurt = dog.tickCount;
    }

    private static final List<Supplier<SoundEvent>> forte_hurt = List.of(
        DogSounds.ANGRY_HURT3,
        DogSounds.ANGRY_HURT2,
        DogSounds.GRUMPY_HURT1,
        DogSounds.CLASSIC_HURT1,
        DogSounds.CLASSIC_HURT2, 
        DogSounds.CLASSIC_HURT3,
        DogSounds.BIG_HURT3,
        DogSounds.CUTE_HURT3
    );
    private static final List<Supplier<SoundEvent>> piano_hurt = List.of(
        DogSounds.BIG_HURT1,
        DogSounds.SAD_HURT1,
        DogSounds.PUGLIN_HURT1,
        DogSounds.PUGLIN_HURT2
    );
    private static final List<Supplier<SoundEvent>> tremolo_hurt = List.of(
        DogSounds.CLASSIC_HURT1,
        DogSounds.CLASSIC_HURT2,
        DogSounds.CLASSIC_HURT3,
        DogSounds.CUTE_HURT3
    ); 
    public SoundEvent getHurtSound(DamageSource source) {
        var hurt_list = piano_hurt;
        if (this.recordedHealth - dog.getHealth() >= 4)
            hurt_list = forte_hurt;
        boolean chain_hurt = chainHurt;
        if (chain_hurt) {
            hurt_list = tremolo_hurt;
        }
        
        return LangUtil.getRandomItem(this.dog.getRandom(), hurt_list).get().get();
    }


    public SoundEvent getLowHealthWhine() {
        return DogSounds.CLASSIC_WHINE.get();
    }

    private static final List<Supplier<SoundEvent>> death_sounds = List.of(
        DogSounds.ANGRY_HURT1,
        DogSounds.CLASSIC_DEATH,
        DogSounds.SAD_DEATH
    );
    public SoundEvent getDeathSound() {
        return LangUtil.getRandomItem(dog.getRandom(), death_sounds).get().get();
    }

    private static final List<Supplier<SoundEvent>> injured_ambient = List.of(
        DogSounds.CLASSIC_WHINE,
        DogSounds.SAD_WHINE,
        DogSounds.CUTE_PANTING
    );
    public SoundEvent getInjuredAmbient() {
        return LangUtil.getRandomItem(dog.getRandom(), injured_ambient).get().get();
    }


    private static final List<Supplier<SoundEvent>> serious_growl = List.of(
        DogSounds.ANGRY_GROWL1,
        DogSounds.ANGRY_GROWL2,
        DogSounds.ANGRY_GROWL3,
        DogSounds.PUGLIN_GROWL1,
        DogSounds.GRUMPY_GROWL3
    );
    public SoundEvent getSeriousGrowl() {
        return LangUtil.getRandomItem(this.dog.getRandom(), serious_growl).get().get();
    }

    public SoundEvent getSniffSound() {
        return DogSounds.SAD_PANTING.get();
    }

    private static final List<Supplier<SoundEvent>> sneeze_sounds = List.of(
        DogSounds.SAD_BARK3,
        DogSounds.SAD_HURT3_ALT
    );
    public SoundEvent getSneezeSound() {
        return LangUtil.getRandomItem(dog.getRandom(), sneeze_sounds).get().get();
    }

    private static final WeightedRandomList
        <WeightedEntry.Wrapper<Supplier<SoundEvent>>> 
        greet_whine = WeightedRandomList.create(
            WeightedEntry.wrap(DogSounds.CLASSIC_WHINE, 3),
            WeightedEntry.wrap(DogSounds.SAD_WHINE, 3),
            WeightedEntry.wrap(DogSounds.PUGLIN_WHINE, 1),
            WeightedEntry.wrap(DogSounds.CUTE_WHINE, 1)
        );
    public SoundEvent getGreetWhine() {
        return greet_whine.getRandom(dog.getRandom()).get().data().get();
    }

    private static final List<Supplier<SoundEvent>> jealous_sound = List.of(
        DogSounds.CUTE_WHINE,
        DogSounds.ANGRY_WHINE,
        DogSounds.BIG_WHINE
    );
    public SoundEvent getJealousSound() {
        return LangUtil.getRandomItem(dog.getRandom(), jealous_sound).get().get();
    }

    private static final List<Supplier<SoundEvent>> down_the_hole = List.of(
        DogSounds.CUTE_BARK1,
        DogSounds.GRUMPY_BARK1,
        DogSounds.SAD_BARK1
    ); 
    public SoundEvent getDownTheHoleSound() {
        return LangUtil.getRandomItem(dog.getRandom(), down_the_hole).get().get();
    }

    private static final List<Supplier<SoundEvent>> aww_sound = List.of(
        DogSounds.CUTE_HURT1_ALT,
        DogSounds.CLASSIC_WHINE,
        DogSounds.SAD_HURT2_ALT
    ); 
    public SoundEvent getAwwSound() {
        return LangUtil.getRandomItem(dog.getRandom(), aww_sound).get().get();
    }

    private static final List<Supplier<SoundEvent>> bark_attention = List.of(
        DogSounds.CUTE_BARK1,
        DogSounds.CUTE_BARK2,
        DogSounds.GRUMPY_BARK1,
        DogSounds.SAD_BARK1,
        DogSounds.SAD_GROWL2,
        DogSounds.GRUMPY_HURT2_ALT
    );
    public SoundEvent getBarkAttentionSound() {
        return LangUtil.getRandomItem(dog.getRandom(), bark_attention).get().get();
    }

    private static final List<Supplier<SoundEvent>> whine_attention = List.of(
        DogSounds.PUGLIN_WHINE,
        DogSounds.SAD_WHINE,
        DogSounds.ANGRY_WHINE
    );
    public SoundEvent getWhineAttentionSound() {
        return LangUtil.getRandomItem(dog.getRandom(), whine_attention).get().get();
    }

    private static final List<Supplier<SoundEvent>> protest_sound = List.of(
        DogSounds.GRUMPY_GROWL1,
        DogSounds.CUTE_GROWL2,
        DogSounds.GRUMPY_WHINE,
        DogSounds.CUTE_WHINE
    );
    public SoundEvent getProtestSound() {
        return LangUtil.getRandomItem(dog.getRandom(), protest_sound).get().get();
    }

    private static final List<Supplier<SoundEvent>> petting_ambient = List.of(
        DogSounds.BIG_WHINE,
        DogSounds.BIG_PANTING,
        DogSounds.ANGRY_PANTING,
        DogSounds.CLASSIC_PANTING,
        DogSounds.GRUMPY_PANTING
    );
    public Pair<SoundEvent, Boolean> getPettingAmbient() {
        float r = this.dog.getRandom().nextFloat();
        SoundEvent sound = null;
        if (r <= 0.2f) {
            sound = DogSounds.CLASSIC_WHINE.get();
        } else {
            sound = LangUtil.getRandomItem(dog.getRandom(), petting_ambient).get().get();
        }
        boolean is_classic =    
            sound == DogSounds.CLASSIC_PANTING.get()
            || sound == DogSounds.CLASSIC_WHINE.get();
        return Pair.of(sound, is_classic);
    }

    private static Set<SoundEvent> force_interruptible_sounds = null;
    public boolean isForceInteruptibleSound(SoundEvent sound) {
        if (force_interruptible_sounds == null) {
            force_interruptible_sounds = new HashSet<>(List.of(
                DogSounds.CUTE_PANTING.get(),
                DogSounds.SAD_WHINE.get()
            ));
        }
        return force_interruptible_sounds.contains(sound);
    }
}
