package doggytalents.common.backward_imitate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class DogFoodProperties_21_3 {
    
    private FoodProperties.Builder builder = new FoodProperties.Builder();
    private Consumable.Builder effectBuilder = Consumables.defaultFood();
    private List<PossibleEffect_1_21_3> effectList = new ArrayList<>();

    public DogFoodProperties_21_3 nutrition(int val) {
        builder.nutrition(val);
        return this;
    }

    public DogFoodProperties_21_3 saturationModifier(float val) {
        builder.saturationModifier(val);
        return this;
    }

    public DogFoodProperties_21_3 alwaysEdible() {
        builder.alwaysEdible();
        return this;
    }

    public DogFoodProperties_21_3 effect(Supplier<MobEffectInstance> effectCreator, float possiblity) {
        this.effectList.add(new PossibleEffect_1_21_3(effectCreator, possiblity));
        this.effectBuilder.onConsume(
            new ApplyStatusEffectsConsumeEffect(
                List.of(effectCreator.get())
            )
        );
        return this;
    }

    public FoodProperties.Builder getVanillaProps() {
        return this.builder;
    }

    public Consumable getVanillaEffects() {
        return this.effectBuilder.build();
    }

    public List<PossibleEffect_1_21_3> dogEffects() {
        return new ArrayList<>(effectList);
    }

    public static record PossibleEffect_1_21_3(Supplier<MobEffectInstance> effectSup, float probability) {
        public MobEffectInstance effect() {
            return this.effectSup.get();
        }
    }
}
