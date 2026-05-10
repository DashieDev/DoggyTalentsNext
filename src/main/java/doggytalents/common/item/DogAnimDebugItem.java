package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.client.screen.DogAnimDebugScreen;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimationManager.DogAnimDebugState;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.ItemUtil;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

public class DogAnimDebugItem extends Item implements IDogItem {
    
    public static final String SELECT_ANIM_ID = "dtn_selected_id";
    public static final String ITEM_MODE_ID = "dtn_item_mode";

    public DogAnimDebugItem(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }
    
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (level.isClientSide())
            DogAnimDebugScreen.open(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        if (!dogIn.level().isClientSide())
            useActionOnDog(dogIn, playerIn);

        return InteractionResult.SUCCESS;
    }

    private void useActionOnDog(AbstractDog dogIn, Player player) {
        if (!(dogIn instanceof Dog dog))
            return;
        if (!player.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.OWNERS)))
            return;
        if (!player.isCreative())
            return;
        
        var stack = player.getMainHandItem();
        if (stack.getItem() != this)
            return;
        var item_mode = getItemMode(stack);
        var anim_manager = dog.animationManager;
        switch (item_mode) {
        case ANIM: {
            if (player.isShiftKeyDown()) {
                anim_manager.setDogAnimDebugState(DogAnimDebugState.NONE);
                return;
            }
            var anim = getSelectedAnimation(stack);
            if (anim.isNone()) {
                anim_manager.setDogAnimDebugState(DogAnimDebugState.NONE);
                return;
            }
            var debug_state = dog.getDogAnimDebugState();
            if (debug_state.anim() != anim) {
                anim_manager.setDogAnimDebugState(DogAnimDebugState.of(anim, 0, 0f));
                dog.setAnim(anim);
                return;
            }
            if (dog.getAnim().isNone()) {
                dog.setAnim(anim);
            } else {
                anim_manager.setDogAnimDebugState(dog.animationManager.getFreezeDebugState(anim));
                dog.setAnim(DogAnimation.NONE);
            }
            
            return;
        }
        case TIME_SET: {
            if (!dog.isDogInAnimDebug())
                return;
            var debug_state = dog.getDogAnimDebugState();
            var anim = debug_state.anim();
            if (anim.isNone())
                return;
            int new_timestamp = debug_state.timestamp()
                + ( player.isShiftKeyDown() ? -1 : 1 );
            new_timestamp = Mth.clamp(new_timestamp, 0, anim.getLengthTicks());
            anim_manager.setDogAnimDebugState(DogAnimDebugState.of(anim, 
                new_timestamp, debug_state.yRot()));
            return;
        }
        case YROT: {
            anim_manager.setDebugFreezeYRot(player.yHeadRot);
            return;
        }
        default:
            return;
        }
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true; 
    }

    public static DogAnimation getSelectedAnimation(ItemStack stack) {
        var tag = ItemUtil.getTag(stack);
        if (!tag.contains(SELECT_ANIM_ID))
            return DogAnimation.NONE;
        int anim_id = tag.getIntOr(SELECT_ANIM_ID, 0);
        var anim = DogAnimation.byId(anim_id);
        return anim;
    }

    public static ItemMode getItemMode(ItemStack stack) {
        var tag = ItemUtil.getTag(stack);
        if (!tag.contains(ITEM_MODE_ID))
            return ItemMode.ANIM;
        int mode_id = tag.getIntOr(ITEM_MODE_ID, 0);
        var mode = ItemMode.fromId(mode_id);
        return mode;
    }

    public static void editDebugAnimStack(ItemStack stack, 
        @Nullable DogAnimation selected, @Nullable ItemMode mode) {
            
        if (stack.getItem() != DoggyItems.DOG_ANIM_DEBUG.get())
            return;
        var tag = ItemUtil.getTag(stack);
        if (selected != null) {
            tag.putInt(SELECT_ANIM_ID, selected.getId());
        }
        if (mode != null) {
            tag.putInt(ITEM_MODE_ID, mode.getId());
        }
        ItemUtil.putTag(stack, tag);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> componentConsumer, TooltipFlag flags) {
        var desc_id = "item.doggytalents.dog_anim_debug_stick.help";
        componentConsumer.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true))
        );
    }

    public static enum ItemMode {
        ANIM(0), TIME_SET(1), YROT(2);
        
        private final int id;

        private ItemMode(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static ItemMode fromId(int id) {
            var values = ItemMode.values();
            if (id < 0 || id >= values.length)
                return ItemMode.ANIM;
            return values[id];
        }

        public ItemMode cycleMode() {
            var modes = ItemMode.values();
            int current_id = this.getId();
            int new_id = current_id + 1;
            if (new_id >= modes.length)
                new_id = 0;
            var new_mode = ItemMode.fromId(new_id);
            return new_mode;
        }
    }
    
}
