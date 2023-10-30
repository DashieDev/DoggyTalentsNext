package doggytalents.api.registry;

import java.util.function.BiFunction;

import javax.annotation.Nullable;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.inferface.AbstractDog;
import net.minecraft.Util;

/**
 * @author ProPercivalalb
 */
public class Talent {

    @Nullable
    private String translationKey, translationInfoKey;

    @Nullable
    private final BiFunction<Talent, Integer, TalentInstance> create;

    /**
     * @param sup
     */
    public Talent(BiFunction<Talent, Integer, TalentInstance> sup) {
        this.create = sup;
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getLevelCost(int toGoToLevel) {
        return toGoToLevel;
    }

    public int getCummulativeCost(int level) {
        return level * (level + 1) / 2;
    }

    public int getDeTrainXPCost(int level) {
        return level < this.getMaxLevel() ? 1 : 2;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("talent", DoggyTalentsAPI.TALENTS.get().getKey(this));
        }
        return this.translationKey;
    }

    public String getInfoTranslationKey() {
        if (this.translationInfoKey == null) {
            this.translationInfoKey = this.getTranslationKey() + ".description";
        }
        return this.translationInfoKey;
    }

    public TalentInstance getDefault(int level) {
        if (this.create == null) {
            return new TalentInstance(this, level);
        }
        return this.create.apply(this, level);
    }

    public TalentInstance getDefault() {
        return this.getDefault(1);
    }

    public boolean isDogEligible(AbstractDog dog) {
        return true;
    }

    public boolean hasRenderer() {
        return false;
    }
}
