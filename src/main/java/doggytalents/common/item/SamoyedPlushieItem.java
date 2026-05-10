package doggytalents.common.item;

import java.util.List;
import java.util.Objects;

import doggytalents.DoggyEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

public class SamoyedPlushieItem extends Item {

    public SamoyedPlushieItem(Properties itemProps) {
        super(itemProps.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide() || !(level instanceof ServerLevel))
            return InteractionResult.SUCCESS;
        var player = context.getPlayer();
        var stack = context.getItemInHand();
        var pos = context.getClickedPos();
        var face = context.getClickedFace();
        var state = level.getBlockState(pos);

        BlockPos spawnAt;
        if (state.getCollisionShape(level, pos).isEmpty()) {
            spawnAt = pos;
        } else {
            spawnAt = pos.relative(face);
        }
        var plush = DoggyEntityTypes.SAMOYED_PLUSHIE_TOY.get().create(
            (ServerLevel) level, null, spawnAt, 
            EntitySpawnReason.TRIGGERED, !Objects.equals(pos, spawnAt) && face == Direction.UP
            , false);

        if (plush != null) {
            plush.setYRot(face.getOpposite().toYRot());
            level.addFreshEntity(plush);
        }
        
        if (player != null && !player.getAbilities().instabuild)
            stack.shrink(1);

        if (player != null)
            player.getCooldowns().addCooldown(stack, 20);

        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> componentConsumer, TooltipFlag flags) {
        if (context.level() == null)
            return;
        var desc_id = "items.doggytalents.piano_item_common.description";
        componentConsumer.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
}