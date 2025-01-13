package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.util.DogUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BedDogTalent extends TalentInstance {
    
    public BedDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    public static void useBedDog(Level level, Player player) {
        if (level.isClientSide)
            return;
        final int reach_range = 30;
        var dog_optional = DogUtil.getLookingAtDog(player, reach_range, 
            filter_dog -> filter_dog.isDoingFine());
        if (!dog_optional.isPresent())
            return;
        var dog = dog_optional.get();

        if (dog.getDogLevel(DoggyTalents.BED_DOG) <= 0)
            return;
        
        DogSleepOnManager.getServer(level.getServer()).setOrRequestSleepOn(dog, player);
    }

}
