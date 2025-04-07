package doggytalents.client.backward_imitate;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.common.item.DoubleDyableAccessoryItem;
import doggytalents.common.util.Util;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class DoubleDyableTint_1_21_5 {

    public static record Fg(int defaultColor) implements ItemTintSource {
    
        @Override
        public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
            var item = stack.getItem();
            if (!(item instanceof DoubleDyableAccessoryItem dyable))
                return -1;
            return dyable.getFgColor(stack);
        }
    
        @Override
        public MapCodec<Fg> type() {
            return FG_CODEC;
        }

    }

    public static record Bg(int defaultColor) implements ItemTintSource {
    
        @Override
        public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
            var item = stack.getItem();
            if (!(item instanceof DoubleDyableAccessoryItem dyable))
                return -1;
            return dyable.getBgColor(stack);
        }
    
        @Override
        public MapCodec<Bg> type() {
            return BG_CODEC;
        }

    }

    public static Fg fg(DoubleDyableAccessoryItem item) {
        return new Fg(item.getDefaultFgColor());
    }

    public static Bg bg(DoubleDyableAccessoryItem item) {
        return new Bg(item.getDefaultBgColor());
    }

    public static final MapCodec<Fg> FG_CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default")
                .forGetter(Fg::defaultColor)
            )
            .apply(builder, Fg::new)
    );

    public static final MapCodec<Bg> BG_CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder.group(
            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default")
                .forGetter(Bg::defaultColor)
            )
            .apply(builder, Bg::new)
    );
    
    public static void registerTints(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Util.getResource("double_dyable_fg"), FG_CODEC);
        event.register(Util.getResource("double_dyable_bg"), BG_CODEC);
    }
}
