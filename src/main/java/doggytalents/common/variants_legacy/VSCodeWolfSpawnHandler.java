package doggytalents.common.variants_legacy;

import java.util.Objects;

import doggytalents.common.config.ConfigHandler;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.ItemUtil;
import doggytalents.forge_imitate.event.PlayerInteractEvent;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Items;

public class VSCodeWolfSpawnHandler {
    
    public static final String VSCODE_WOLF_STR = "genVSCodeRuns";

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var player = event.getEntity();
        var clicked_face = event.getFace();
        var stack = event.getItemStack();
        if (stack == null || stack.isEmpty())
            return;
        if (!stack.is(Items.WOLF_SPAWN_EGG))
            return;
        
        if (!ItemUtil.hasCustomHoverName(stack))
            return;
        var stack_name = ItemUtil.getCustomHoverName(stack);
        var stack_name_str = stack_name == null ? "" :
            stack_name.getString();
        if (stack_name_str == null)
            stack_name_str = "";
        
        if (!stack_name_str.equals(VSCODE_WOLF_STR))
            return;
        
        if (!ConfigHandler.SERVER.VSCODE_WOLF_SPAWN_EGG.get())
            return;
        var level = player.level();
        var variant_optional = DogUtil.getWolfVariantHolderIfLoaded(level.registryAccess(), 
            DTNWolfVariants.VSCODE);
        if (!variant_optional.isPresent())
            return;
        var variant_vscode = variant_optional.get();

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);

        if (level.isClientSide)
            return;
        
        var clicked_pos = event.getPos();
        var clicked_state = player.level().getBlockState(clicked_pos);
        BlockPos spawn_pos;
        if (clicked_state.getCollisionShape(level, clicked_pos).isEmpty()) {
            spawn_pos = clicked_pos;
        } else {
            spawn_pos = clicked_pos.relative(clicked_face);
        }

        if (!(level instanceof ServerLevel sLevel))
            return;

        var spawn_stack = stack.copy();
        ItemUtil.clearCustomHoverName(spawn_stack);
        boolean upward_collision = 
            !Objects.equals(spawn_pos, clicked_pos) && clicked_face == Direction.UP;
        var wolf = EntityType.WOLF.spawn(sLevel, spawn_stack,
            player, spawn_pos, MobSpawnType.SPAWN_EGG, true,
            upward_collision
        );
        if (wolf == null)
            return;
        
        wolf.setVariant(variant_vscode);

        if (!player.getAbilities().instabuild)
            stack.shrink(1);
    }

}
