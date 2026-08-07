package doggytalents.api.feature;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.world.entity.LivingEntity;

// TODO: Add javadoc
public interface IDog {

    public AbstractDog getDog();

    public boolean canInteract(LivingEntity playerIn);

    public DogMode getMode();

    public DogLevel getDogLevel();
    public void increaseLevel(DogLevel.Type typeIn);

    /**
     * Convenience method to get the level of a talent
     * @param talentGetter A getter function, typically a {@link RegistryObject<Talent>} would be provided
     * @return The level of the talent
     */
    default int getDogLevel(Supplier<? extends Talent> talentGetter) {
        return this.getDogLevel(talentGetter.get());
    }

    /**
     * Returns the level of the given talent
     * @param talentIn The {@link Talent}
     * @return The level of the talent
     */
    public int getDogLevel(Talent talentIn);

    default Optional<TalentInstance> getTalent(Supplier<? extends Talent> talentGetter) {
        return this.getTalent(talentGetter.get());
    }

    public Optional<TalentInstance> getTalent(Talent talentIn);

    public DogSize getDogSize();
    public void setDogSize(DogSize size);

    public int getMaxDogIncapVal();
    public int getDefaultInitIncapVal();
    public int getDogIncapValue();
    public void setDogIncapValue(int val);

    public float getMaxHunger();
    public float getDogHunger();
    public void addHunger(float add);
    public void setDogHunger(float hunger);

    //Incapacitated
    public boolean isDefeated();

    public boolean addAccessory(AccessoryInstance inst);
    public List<AccessoryInstance> getAccessories();
    public List<AccessoryInstance> removeAccessories();

    public boolean isLying();

    public List<IDogFoodHandler> getFoodHandlers();
}
