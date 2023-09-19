package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BedFinderTalent extends TalentInstance {

    public BedFinderTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void set(AbstractDog dog, int levelBefore) {
        if (dog.level().isClientSide) return;
        if (levelBefore > 0 && this.level() <= 0) {
            dog.unRide();
        }
    }

    @Override
    public void livingTick(AbstractDog dog) {

    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level level, Player player, InteractionHand hand) {
        if (this.level() <= 0)
            return InteractionResult.PASS;
        if (player.hasPassenger(dog)) {
            if (!dog.level().isClientSide)
                dog.unRide();
            return InteractionResult.SUCCESS;
        }
        var item = player.getItemInHand(hand).getItem();
        if (item != Items.BONE)
            return InteractionResult.PASS;
        if (!dog.canInteract(player))
            return InteractionResult.PASS;
        if (!dog.level().isClientSide) {
            dog.startRiding(player);
            player.displayClientMessage(
                Component.translatable(
                    "talent.doggytalents.bed_finder.dog_mount", 
                    dog.getGenderPronoun()), true);
        }
        return InteractionResult.SUCCESS;
    }
}
