package doggytalents.common.item;

import java.util.List;

import doggytalents.DoggyEffects;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.accessory.CeremonialGarb.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class NattoRiceItem extends DogEddibleBowlFoodItem {

    public NattoRiceItem(Properties itemProps) {
        super(itemProps,
            b -> b.nutrition(8).saturationModifier(0.8f)
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
        return List.of(new DogMobEffectEntry(new MobEffectInstance(DoggyEffects.NATTO_BITE, 300 * 20, 1), 1f));
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }
}
