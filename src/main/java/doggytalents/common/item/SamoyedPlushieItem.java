package doggytalents.common.item;

import java.util.List;
import java.util.Objects;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.util.DogVariantUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SamoyedPlushieItem extends Item implements IDyeableArmorItem, IDogItem {

    public SamoyedPlushieItem(Properties itemProps) {
        super(itemProps.stacksTo(1));
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
        var plush = DoggyEntityTypes.SAMOYED_PLUSHIE.get().create(
            (ServerLevel) level, null, spawnAt, 
            MobSpawnType.TRIGGERED, !Objects.equals(pos, spawnAt) && face == Direction.UP
            , false);

        if (plush != null) {
            plush.setYRot(face.getOpposite().toYRot());
            level.addFreshEntity(plush);
        }
        
        if (player != null && !player.getAbilities().instabuild)
            stack.shrink(1);

        if (player != null)
            player.getCooldowns().addCooldown(this, 20);

        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
            TooltipFlag flags) {
        if (context.level() == null)    
            return;
        var desc_id = "item.doggytalents.samoyed_plushie_toy_item.description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, 
        Player playerIn, InteractionHand handIn) {
            return InteractionResult.SUCCESS;
        }

}