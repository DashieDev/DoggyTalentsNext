package doggytalents.common.item;

import java.util.List;

import doggytalents.DoggyEffects;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class NattoItem extends DogEddibleItem {

    public NattoItem(Properties itemProps) {
        super(itemProps, 
            b -> b
                .nutrition(6)
                .saturationModifier(0.6F)
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
        return List.of(new DogMobEffectEntry(new MobEffectInstance(DoggyEffects.NATTO_BITE, 180 * 20, 1), 1f));
    }

    @Override
    public boolean alwaysEatWhenDogConsume(AbstractDog dog) {
        return true;
    }

}
