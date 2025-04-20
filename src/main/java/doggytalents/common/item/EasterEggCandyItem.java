package doggytalents.common.item;

import java.util.List;

import doggytalents.api.inferface.AbstractDog;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class EasterEggCandyItem extends DogEddibleItem{

    public EasterEggCandyItem(Properties itemProps) {
        super(itemProps, 
            b -> b
                .nutrition(2)
                .saturationModifier(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1)
        );
    }
}
