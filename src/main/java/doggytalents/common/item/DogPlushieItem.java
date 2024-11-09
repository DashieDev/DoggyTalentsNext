package doggytalents.common.item;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
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
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DogPlushieItem extends Item implements IDyeableArmorItem, IDogItem {

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
            (ServerLevel) level, null, spawnAt, 
            MobSpawnType.TRIGGERED, !Objects.equals(pos, spawnAt) && face == Direction.UP
            , false);

        if (plush != null) {
            plush.setYRot(face.getOpposite().toYRot());
            int color = ItemUtil.getDyeColorForStack(stack);
            plush.setCollarColor(color);
            var variant = getDogVariant(stack);
            plush.setDogVariant(variant);
            var collar_thicc = getCollarThicc(stack);
            plush.setCollarThicc(collar_thicc);
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
        var desc_id = "item.doggytalents.dog_plushie_toy_item.description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }

    @Override
    public int getDefaultColor(ItemStack stack) {
        return 11546150;
    }

    public static void setDogVariant(ItemStack stack, DogVariant variant) {
        if (stack.getItem() != DoggyItems.DOG_PLUSHIE_TOY.get())
            return;
        var tag = ItemUtil.getTag(stack);
        tag.putString("dogVariant", DogVariantUtil.toSaveString(variant));
        ItemUtil.putTag(stack, tag);
    }

    public static DogVariant getDogVariant(ItemStack stack) {
        if (stack.getItem() != DoggyItems.DOG_PLUSHIE_TOY.get())
            return DogVariantUtil.getDefault();
        var tag = ItemUtil.getTag(stack);
        if (!tag.contains("dogVariant", Tag.TAG_STRING))
            return DogVariantUtil.getDefault();
        var variant_str = tag.getString("dogVariant");
        return DogVariantUtil.fromSaveString(variant_str);
    }

    public static void setCollarThicc(ItemStack stack, boolean val) {
        if (stack.getItem() != DoggyItems.DOG_PLUSHIE_TOY.get())
            return;
        var tag = ItemUtil.getTag(stack);
        tag.putBoolean("collarThicc", val);
        ItemUtil.putTag(stack, tag);
    }

    public static boolean getCollarThicc(ItemStack stack) {
        if (stack.getItem() != DoggyItems.DOG_PLUSHIE_TOY.get())
            return false;
        var tag = ItemUtil.getTag(stack);
        return tag.getBoolean("collarThicc");
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, 
        Player playerIn, InteractionHand handIn) {
        
        if (!(dogIn instanceof Dog dog))
            return InteractionResult.PASS;

        var stack = playerIn.getItemInHand(handIn);
        
        if (copyDogToStack(dog, stack))
            return InteractionResult.SUCCESS;

        return InteractionResult.FAIL;
    }

    private boolean copyDogToStack(Dog dog, ItemStack stack) {
        boolean changed = false;
        var variant_dog = dog.dogVariant();
        var variant_stack = getDogVariant(stack);
        if (variant_dog != variant_stack) {
            if (!dog.level().isClientSide)
                setDogVariant(stack, variant_dog);
            changed = true;
        }

        boolean collar_thicc_dog = 
            dog.getAccessory(DoggyAccessories.DYEABLE_COLLAR_THICC.get())
            .isPresent();
        boolean collar_thicc_stack = getCollarThicc(stack);
        if (collar_thicc_dog != collar_thicc_stack) {
            if (!dog.level().isClientSide)
                setCollarThicc(stack, collar_thicc_dog);
            changed = true;
        }

        return changed;
    }

    

}
