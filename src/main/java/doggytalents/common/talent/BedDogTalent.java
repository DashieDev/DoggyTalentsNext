package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BedDogTalent extends TalentInstance {
    
    public BedDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void set(AbstractDog dog, int levelBefore) {
    }

    @Override
    public void livingTick(AbstractDog dog) {
    }

    public static void useBedDog(Level level, Player player) {
        
    }

}
