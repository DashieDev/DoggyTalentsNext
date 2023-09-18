package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.registry.Accessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class HotDogAccessoryItem extends AccessoryItem {

    public HotDogAccessoryItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties.food(
            (new FoodProperties.Builder())
                        .nutrition(8)
                        .saturationMod(1F)
                        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 1)
                        .build()
        ));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}
