package doggytalents.common.item;

import java.util.List;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EasterEggCandyItem extends DogEddibleItem {

    public EasterEggCandyItem(Properties itemProps) {
        super(itemProps,
            b -> b.nutrition(2).saturationModifier(0.6F),
            List.of(new DogMobEffectEntry(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 0), 1f))
        );
    }
}
