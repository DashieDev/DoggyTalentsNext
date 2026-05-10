package doggytalents;

import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.common.util.Util;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class DogSounds {
    
    //Minecraft's
    public static final Supplier<SoundEvent> ANGRY_BARK1 = register("dog_angry_bark1");
    public static final Supplier<SoundEvent> ANGRY_BARK2 = register("dog_angry_bark2");
    public static final Supplier<SoundEvent> ANGRY_BARK3 = register("dog_angry_bark3");
    public static final Supplier<SoundEvent> ANGRY_DEATH = register("dog_angry_death");
    public static final Supplier<SoundEvent> ANGRY_GROWL1 = register("dog_angry_growl1");
    public static final Supplier<SoundEvent> ANGRY_GROWL2 = register("dog_angry_growl2");
    public static final Supplier<SoundEvent> ANGRY_GROWL3 = register("dog_angry_growl3");
    public static final Supplier<SoundEvent> ANGRY_HURT1 = register("dog_angry_hurt1");
    public static final Supplier<SoundEvent> ANGRY_HURT2 = register("dog_angry_hurt2");
    public static final Supplier<SoundEvent> ANGRY_HURT3 = register("dog_angry_hurt3");
    public static final Supplier<SoundEvent> ANGRY_PANTING = register("dog_angry_panting");
    public static final Supplier<SoundEvent> ANGRY_WHINE = register("dog_angry_whine");
    public static final Supplier<SoundEvent> BIG_BARK1 = register("dog_big_bark1");
    public static final Supplier<SoundEvent> BIG_BARK2 = register("dog_big_bark2");
    public static final Supplier<SoundEvent> BIG_BARK3 = register("dog_big_bark3");
    public static final Supplier<SoundEvent> BIG_DEATH = register("dog_big_death");
    public static final Supplier<SoundEvent> BIG_GROWL1 = register("dog_big_growl1");
    public static final Supplier<SoundEvent> BIG_GROWL2 = register("dog_big_growl2");
    public static final Supplier<SoundEvent> BIG_GROWL3 = register("dog_big_growl3");
    public static final Supplier<SoundEvent> BIG_HURT1 = register("dog_big_hurt1");
    public static final Supplier<SoundEvent> BIG_HURT2 = register("dog_big_hurt2");
    public static final Supplier<SoundEvent> BIG_HURT3 = register("dog_big_hurt3");
    public static final Supplier<SoundEvent> BIG_PANTING = register("dog_big_panting");
    public static final Supplier<SoundEvent> BIG_WHINE = register("dog_big_whine");
    public static final Supplier<SoundEvent> CLASSIC_BARK1 = register("dog_classic_bark1");
    public static final Supplier<SoundEvent> CLASSIC_BARK2 = register("dog_classic_bark2");
    public static final Supplier<SoundEvent> CLASSIC_BARK3 = register("dog_classic_bark3");
    public static final Supplier<SoundEvent> CLASSIC_DEATH = register("dog_classic_death");
    public static final Supplier<SoundEvent> CLASSIC_GROWL1 = register("dog_classic_growl1");
    public static final Supplier<SoundEvent> CLASSIC_GROWL2 = register("dog_classic_growl2");
    public static final Supplier<SoundEvent> CLASSIC_GROWL3 = register("dog_classic_growl3");
    public static final Supplier<SoundEvent> CLASSIC_HURT1 = register("dog_classic_hurt1");
    public static final Supplier<SoundEvent> CLASSIC_HURT2 = register("dog_classic_hurt2");
    public static final Supplier<SoundEvent> CLASSIC_HURT3 = register("dog_classic_hurt3");
    public static final Supplier<SoundEvent> CLASSIC_PANTING = register("dog_classic_panting");
    public static final Supplier<SoundEvent> CLASSIC_WHINE = register("dog_classic_whine");
    public static final Supplier<SoundEvent> CUTE_BARK1 = register("dog_cute_bark1");
    public static final Supplier<SoundEvent> CUTE_BARK2 = register("dog_cute_bark2");
    public static final Supplier<SoundEvent> CUTE_BARK3 = register("dog_cute_bark3");
    public static final Supplier<SoundEvent> CUTE_DEATH = register("dog_cute_death");
    public static final Supplier<SoundEvent> CUTE_GROWL1 = register("dog_cute_growl1");
    public static final Supplier<SoundEvent> CUTE_GROWL2 = register("dog_cute_growl2");
    public static final Supplier<SoundEvent> CUTE_GROWL3 = register("dog_cute_growl3");
    public static final Supplier<SoundEvent> CUTE_HURT1 = register("dog_cute_hurt1");
    public static final Supplier<SoundEvent> CUTE_HURT2 = register("dog_cute_hurt2");
    public static final Supplier<SoundEvent> CUTE_HURT3 = register("dog_cute_hurt3");
    public static final Supplier<SoundEvent> CUTE_PANTING = register("dog_cute_panting");
    public static final Supplier<SoundEvent> CUTE_WHINE = register("dog_cute_whine");
    public static final Supplier<SoundEvent> GRUMPY_BARK1 = register("dog_grumpy_bark1");
    public static final Supplier<SoundEvent> GRUMPY_BARK2 = register("dog_grumpy_bark2");
    public static final Supplier<SoundEvent> GRUMPY_BARK3 = register("dog_grumpy_bark3");
    public static final Supplier<SoundEvent> GRUMPY_DEATH = register("dog_grumpy_death");
    public static final Supplier<SoundEvent> GRUMPY_GROWL1 = register("dog_grumpy_growl1");
    public static final Supplier<SoundEvent> GRUMPY_GROWL2 = register("dog_grumpy_growl2");
    public static final Supplier<SoundEvent> GRUMPY_GROWL3 = register("dog_grumpy_growl3");
    public static final Supplier<SoundEvent> GRUMPY_HURT1 = register("dog_grumpy_hurt1");
    public static final Supplier<SoundEvent> GRUMPY_HURT2 = register("dog_grumpy_hurt2");
    public static final Supplier<SoundEvent> GRUMPY_HURT3 = register("dog_grumpy_hurt3");
    public static final Supplier<SoundEvent> GRUMPY_PANTING = register("dog_grumpy_panting");
    public static final Supplier<SoundEvent> GRUMPY_WHINE = register("dog_grumpy_whine");
    public static final Supplier<SoundEvent> PUGLIN_BARK1 = register("dog_puglin_bark1");
    public static final Supplier<SoundEvent> PUGLIN_BARK2 = register("dog_puglin_bark2");
    public static final Supplier<SoundEvent> PUGLIN_BARK3 = register("dog_puglin_bark3");
    public static final Supplier<SoundEvent> PUGLIN_DEATH = register("dog_puglin_death");
    public static final Supplier<SoundEvent> PUGLIN_GROWL1 = register("dog_puglin_growl1");
    public static final Supplier<SoundEvent> PUGLIN_GROWL2 = register("dog_puglin_growl2");
    public static final Supplier<SoundEvent> PUGLIN_GROWL3 = register("dog_puglin_growl3");
    public static final Supplier<SoundEvent> PUGLIN_HURT1 = register("dog_puglin_hurt1");
    public static final Supplier<SoundEvent> PUGLIN_HURT2 = register("dog_puglin_hurt2");
    public static final Supplier<SoundEvent> PUGLIN_HURT3 = register("dog_puglin_hurt3");
    public static final Supplier<SoundEvent> PUGLIN_PANTING = register("dog_puglin_panting");
    public static final Supplier<SoundEvent> PUGLIN_WHINE = register("dog_puglin_whine");
    public static final Supplier<SoundEvent> SAD_BARK1 = register("dog_sad_bark1");
    public static final Supplier<SoundEvent> SAD_BARK2 = register("dog_sad_bark2");
    public static final Supplier<SoundEvent> SAD_BARK3 = register("dog_sad_bark3");
    public static final Supplier<SoundEvent> SAD_DEATH = register("dog_sad_death");
    public static final Supplier<SoundEvent> SAD_GROWL1 = register("dog_sad_growl1");
    public static final Supplier<SoundEvent> SAD_GROWL2 = register("dog_sad_growl2");
    public static final Supplier<SoundEvent> SAD_GROWL3 = register("dog_sad_growl3");
    public static final Supplier<SoundEvent> SAD_HURT1 = register("dog_sad_hurt1");
    public static final Supplier<SoundEvent> SAD_HURT2 = register("dog_sad_hurt2");
    public static final Supplier<SoundEvent> SAD_HURT3 = register("dog_sad_hurt3");
    public static final Supplier<SoundEvent> SAD_PANTING = register("dog_sad_panting");
    public static final Supplier<SoundEvent> SAD_WHINE = register("dog_sad_whine");
    public static final Supplier<SoundEvent> CUTE_HURT1_ALT = register("dog_cute_hurt1_alt");
    public static final Supplier<SoundEvent> GRUMPY_DEATH_ALT = register("dog_grumpy_death_alt");
    public static final Supplier<SoundEvent> GRUMPY_HURT2_ALT = register("dog_grumpy_hurt2_alt");
    public static final Supplier<SoundEvent> PUGLIN_HURT2_ALT = register("dog_puglin_hurt2_alt");
    public static final Supplier<SoundEvent> SAD_HURT2_ALT = register("dog_sad_hurt2_alt");
    public static final Supplier<SoundEvent> SAD_HURT3_ALT = register("dog_sad_hurt3_alt");

    public static void bootstrap() {}

    private static Supplier<SoundEvent> register(final String name) {
        return register(name, () -> SoundEvent.createVariableRangeEvent(Util.getResource(name)));
    }

    private static <T extends SoundEvent> Supplier<T> register(final String name, final Supplier<T> sup) {
        return DoggySounds.SOUNDS.register(name, sup);
    }

}
