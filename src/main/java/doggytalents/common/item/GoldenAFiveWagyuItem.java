package doggytalents.common.item;

import java.util.List;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

public class GoldenAFiveWagyuItem extends DogEddibleItem {

    private static final List<DogMobEffectEntry> EFFECTS = List.of(
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.REGENERATION, 1200, 1), 1f),
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.RESISTANCE, 1200, 1), 1f),
        new DogMobEffectEntry(new MobEffectInstance(MobEffects.SATURATION, 1200, 1), 1f)
    );

    public GoldenAFiveWagyuItem(Properties itemProps) {
        super(itemProps,
            p -> p.rarity(Rarity.UNCOMMON),
            b -> b.nutrition(8).saturationModifier(10F),
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
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}
