package doggytalents.common.talent;

import doggytalents.DoggyAttributes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class BlackPeltTalent extends TalentInstance {

    private static final ResourceLocation BLACK_PELT_DAMAGE_ID = Util.getResource("black_pelt_damage");
    private static final ResourceLocation BLACK_PELT_CRIT_CHANCE_ID = Util.getResource("black_pelt_crit_chance");
    private static final ResourceLocation BLACK_PELT_CRIT_BONUS_ID = Util.getResource("black_pelt_crit_bonus");

    public BlackPeltTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        dogIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, BLACK_PELT_DAMAGE_ID, this::createPeltModifier);
        dogIn.setAttributeModifier(DoggyAttributes.CRIT_CHANCE.getHolder().orElseThrow(), BLACK_PELT_CRIT_CHANCE_ID, this::createPeltCritChance);
        dogIn.setAttributeModifier(DoggyAttributes.CRIT_BONUS.getHolder().orElseThrow(), BLACK_PELT_CRIT_BONUS_ID, this::createPeltCritBonus);
    }

    @Override
    public void set(AbstractDog dogIn, int levelBefore) {
        dogIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, BLACK_PELT_DAMAGE_ID, this::createPeltModifier);
        dogIn.setAttributeModifier(DoggyAttributes.CRIT_CHANCE.getHolder().orElseThrow(), BLACK_PELT_CRIT_CHANCE_ID, this::createPeltCritChance);
        dogIn.setAttributeModifier(DoggyAttributes.CRIT_BONUS.getHolder().orElseThrow(), BLACK_PELT_CRIT_BONUS_ID, this::createPeltCritBonus);
    }

    @Override
    public void remove(AbstractDog dogIn) {
        dogIn.removeAttributeModifier(Attributes.ATTACK_DAMAGE, BLACK_PELT_DAMAGE_ID);
        dogIn.removeAttributeModifier(DoggyAttributes.CRIT_CHANCE.getHolder().orElseThrow(), BLACK_PELT_CRIT_CHANCE_ID);
        dogIn.removeAttributeModifier(DoggyAttributes.CRIT_BONUS.getHolder().orElseThrow(), BLACK_PELT_CRIT_BONUS_ID);
    }

    public AttributeModifier createPeltModifier(AbstractDog dogIn, ResourceLocation uuidIn) {
        if (this.level() > 0) {
            double damageBonus = this.level();

            if (this.level() >= 5) {
                damageBonus += 2;
            }

            return new AttributeModifier(uuidIn, damageBonus, AttributeModifier.Operation.ADD_VALUE);
        }

        return null;
    }

    public AttributeModifier createPeltCritChance(AbstractDog dogIn, ResourceLocation uuidIn) {
        if (this.level() <= 0) {
            return null;
        }

        double damageBonus = 0.15D * this.level();

        if (this.level() >= 5) {
            damageBonus = 1D;
        }

        return new AttributeModifier(uuidIn, damageBonus, AttributeModifier.Operation.ADD_VALUE);
    }

    public AttributeModifier createPeltCritBonus(AbstractDog dogIn, ResourceLocation uuidIn) {
        if (this.level() <= 0) {
            return null;
        }

        return new AttributeModifier(uuidIn, 1.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
