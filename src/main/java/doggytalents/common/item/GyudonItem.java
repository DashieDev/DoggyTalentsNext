package doggytalents.common.item;

import java.util.ArrayList;
import java.util.List;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class GyudonItem extends DogEddibleBowlFoodItem {

    private static final List<DogMobEffectEntry> EFFECTS = List.of(
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.RESISTANCE, 2400, 1), 1f),
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1f),
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1f),
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1), 1f)
    );

    public GyudonItem(Properties itemProps) {
        super(itemProps,
            b -> b.nutrition(20).saturationModifier(1f),
            EFFECTS
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        components.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public List<DogMobEffectEntry> getAdditionalEffectsWhenDogConsume(ItemStack useStack, AbstractDog dog) {
        var ret = super.getAdditionalEffectsWhenDogConsume(useStack, dog);
        var newRet = new ArrayList<DogMobEffectEntry>(ret.size());
        for (var entry : ret) {
            var effectInst = entry.effect();
            var newDuration = effectInst.getEffect().value().isInstantenous() ?
                effectInst.getDuration()
                : effectInst.mapDuration(x -> x + 2 * 60 * 20);
            newRet.add(new DogMobEffectEntry(
                new MobEffectInstance(effectInst.getEffect(), newDuration, effectInst.getAmplifier()),
                1f
            ));
        }
        return newRet;
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}
