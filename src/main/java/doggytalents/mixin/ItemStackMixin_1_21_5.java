package doggytalents.mixin;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.api.backward_imitate.HoverTextAppender_1_21_5;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

@Mixin(ItemStack.class)
public class ItemStackMixin_1_21_5 {
    
    @Inject(at = @At("HEAD"),  method = "addDetailsToTooltip")
    public void dtn__addDetailsToTooltip(Item.TooltipContext context, TooltipDisplay display, @Nullable Player player, TooltipFlag tooltipFlag, Consumer<Component> consumer, CallbackInfo info) {
        var self = (ItemStack)(Object)this;
        var appender_optional = HoverTextAppender_1_21_5.findFromStack(self);
        if (!appender_optional.isPresent())
            return;
        var appender = appender_optional.get();
        var component_list = new ArrayList<Component>();
        appender.appendHoverText(self, context, component_list, tooltipFlag);
        component_list.forEach(consumer);
    }

}
