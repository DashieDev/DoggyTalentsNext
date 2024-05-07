package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.api.inferface.IDogFoodPredicate;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.util.ItemUtil;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HappyEaterTalent extends TalentInstance implements IDogFoodHandler {

    public static final IDogFoodPredicate INNER_DYN_PRED = (stackIn) -> {
        Item item = stackIn.getItem();
        return (ItemUtil.isEddible(stackIn) && stackIn.is(ItemTags.FISHES));
    };

    public HappyEaterTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Float> setDogHunger(AbstractDog dogIn, float hunger, float diff) {
        hunger += diff / 10 * this.level();
        return InteractionResultHolder.success(hunger);
    }

    @Override
    public boolean isFood(ItemStack stackIn) {
        return HappyEaterTalent.INNER_DYN_PRED.isFood(stackIn);
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, Entity entityIn) {
        if (dogIn.isDefeated()) return false;
        
        Item item = stackIn.getItem();

        if (this.level() >= 2 && ItemUtil.isEddible(stackIn) && stackIn.is(ItemTags.FISHES)) {
            return true;
        }

        return false;
    }

    @Override
    public InteractionResult consume(AbstractDog dogIn, ItemStack stackIn, Entity entityIn) {
        if (dogIn.level().isClientSide)
            return InteractionResult.SUCCESS;

        Item item = stackIn.getItem();

        if (this.level() >= 2 && item.isEdible() && stackIn.is(ItemTags.FISHES)) {
            dogIn.addHunger(item.getFoodProperties().getNutrition() * 5);
            dogIn.consumeItemFromStack(entityIn, stackIn);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
