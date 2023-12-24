package doggytalents.common.item;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.DoggyItemGroups;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.misc.Piano;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobSpawnType;
import net.minecraft.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;

public class PianoItem extends Item {

    private Supplier<EntityType<Piano>> pianoSup;

    public PianoItem(Supplier<EntityType<Piano>> pianoSup) {
        super(new Properties().stacksTo(1).tab(DoggyItemGroups.GENERAL));
        this.pianoSup = pianoSup;
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
        var piano = pianoSup.get().create(
            (ServerLevel) level, null, null, null, spawnAt, 
            MobSpawnType.TRIGGERED, !Objects.equals(pos, spawnAt) && face == Direction.UP
            , false);

        if (piano != null) {
            piano.setYRot(face.getOpposite().toYRot());
            level.addFreshEntity(piano);
        }
        
        if (player != null && !player.getAbilities().instabuild)
            stack.shrink(1);

        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = "items.doggytalents.piano_item_common.description";
        components.add(ComponentUtil.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

}
