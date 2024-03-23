package doggytalents.common.item;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DogPlushieItem extends Item implements IDyeableArmorItem {

    public DogPlushieItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide || !(level instanceof ServerLevel))
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
        var plush = DoggyEntityTypes.DOG_PLUSHIE_TOY.get().create(
            (ServerLevel) level, null, null, spawnAt, 
            MobSpawnType.TRIGGERED, !Objects.equals(pos, spawnAt) && face == Direction.UP
            , false);

        if (plush != null) {
            plush.setYRot(face.getOpposite().toYRot());
            int color = this.getColor(stack);
            plush.setCollarColor(color);
            level.addFreshEntity(plush);
        }
        
        if (player != null && !player.getAbilities().instabuild)
            stack.shrink(1);

        if (player != null)
            player.getCooldowns().addCooldown(this, 20);

        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = "item.doggytalents.dog_plushie_toy_item.description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public int getDefaultColor(ItemStack stack) {
        return 11546150;
    }

}
