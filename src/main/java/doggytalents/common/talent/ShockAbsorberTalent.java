package doggytalents.common.talent;


import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShockAbsorberTalent extends TalentInstance {

    public ShockAbsorberTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        var attrib =
            dogIn.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attrib == null) return;
        attrib.setBaseValue(this.getKnockbackResist());
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        var attrib = 
            dogIn.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attrib == null) return;
        attrib.setBaseValue(this.getKnockbackResist());
    }

    @Override
    public void remove(AbstractDog dogIn) {
        var attrib = 
            dogIn.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attrib == null) return;
        attrib.setBaseValue(0.25);
    }

    public double getKnockbackResist() {
        int level = this.level();
        if (level >= 5) return 1;
        switch (level) {
        case 0 :
            return 0.25;
        case 1 :
            return 0.40;
        case 2 :
            return 0.60;
        case 3 :
            return 0.75;
        case 4 :
            return 0.9;
        default :
            return 0.25;
        }
    }
    
}
