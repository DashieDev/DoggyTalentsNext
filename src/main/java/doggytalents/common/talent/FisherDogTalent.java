package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.enu.WetSource;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class FisherDogTalent extends TalentInstance {

    public FisherDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void onShakingDry(AbstractDog dogIn, WetSource source) {
        if (dogIn.level().isClientSide) { // On client do nothing
            return;
        }

        if (!source.isWaterBlock())
            return;
        
        int r_fish = dogIn.getRandom().nextInt(15);
        if (r_fish >= this.level() * 2)
            return;
        
        var fishItem = Items.COD;
        fishItem = mayCookFish(dogIn, fishItem);

        dogIn.spawnAtLocation(fishItem);
    }

    private Item mayCookFish(AbstractDog dog, Item fish_raw) {
        var hellhound_optional = dog.getTalent(DoggyTalents.HELL_HOUND);
        if (!hellhound_optional.isPresent())
            return fish_raw;
        if (!(hellhound_optional.get() instanceof HellHoundTalent hellhound))
            return fish_raw;
        if (!hellhound.canGenerateHeat())
            return fish_raw;

        int r_cooked = dog.getRandom().nextInt(15);
        if (r_cooked >= hellhound.level() * 2)
            return fish_raw;
        return Items.COOKED_COD;
    } 
}
