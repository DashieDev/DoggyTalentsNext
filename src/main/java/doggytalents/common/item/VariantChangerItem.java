package doggytalents.common.item;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import doggytalents.DoggyItems;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.entity.ClassicalVar;
import doggytalents.common.entity.Dog;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class VariantChangerItem extends Item implements IDogItem {

    public final ClassicalVar variant;
    public static final String desc_id = "item.doggytalents.variant_changer";

    public static final Map<ClassicalVar, Item> REPR_ITEM =
        new ImmutableMap.Builder<ClassicalVar, Item>()
        .put(ClassicalVar.PALE, Items.BONE)
        .put(ClassicalVar.CHESTNUT, Items.SPRUCE_SAPLING)
        .put(ClassicalVar.STRIPED, Items.DEAD_BUSH)
        .put(ClassicalVar.WOOD, Items.OAK_LOG)
        .put(ClassicalVar.RUSTY, Items.JUNGLE_LOG)
        .put(ClassicalVar.BLACK, Items.DARK_OAK_LOG)
        .put(ClassicalVar.SNOWY, Items.SNOW_BLOCK)
        .put(ClassicalVar.ASHEN, Items.SPRUCE_LOG)
        .put(ClassicalVar.SPOTTED, Items.ACACIA_LOG)
        .build();

    public VariantChangerItem(Properties p_41383_, ClassicalVar variant) {
        super(p_41383_);
        this.variant = variant;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        if (!(dogIn instanceof Dog dog))
            return InteractionResult.CONSUME;
        if (!dog.canInteract(playerIn))
            return InteractionResult.CONSUME;
        if (dog.getClassicalVar() == this.variant)
            return InteractionResult.CONSUME;
            
        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        dog.setClassicalVar(this.variant);
        dog.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
        if (dog.level() instanceof ServerLevel sL) {
            var item = REPR_ITEM.get(this.variant);
            sL.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), 
                dog.getX(), dog.getY(), dog.getZ(), 
                24, 
                dog.getBbWidth(), 0.8f, dog.getBbWidth(), 
                0.1
            );
        }
        var stack = playerIn.getItemInHand(handIn);
        stack.hurtAndBreak(1, playerIn, (player_consume) -> {
            player_consume.broadcastBreakEvent(handIn);
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    protected String getOrCreateDescriptionId() {
        return desc_id;
    }
    
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level p_339594_, List<Component> list,
            TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_339594_, list, p_41424_);
        var item = stack.getItem();
        if (!(item instanceof VariantChangerItem changer))
            return;
        var variant = changer.variant;
        var variant_c1 = (ComponentUtil.translatable(variant.getTranslationKey()));
        list.add(ComponentUtil.translatable("item.doggytalents.variant_changer.description", variant_c1));
    }
    
    //TODO ADD RECI PE
}
