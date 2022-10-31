package doggytalents.common.talent;

import doggytalents.ChopinLogger;
import doggytalents.api.feature.DataKey;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class PillowPawTalent extends TalentInstance {

    private static final UUID PILLOW_PAW_BOOST_ID = UUID.fromString("1f002df0-9d35-49c6-a863-b8945caa4af4");

    private static DataKey<PillowPawGlideGoal> GLIDE_AI = DataKey.make();

    public PillowPawTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        if (!(dogIn instanceof Dog)) return;
        if (!dogIn.hasData(GLIDE_AI)) {
            var glideGoal = new PillowPawGlideGoal((Dog)dogIn, this);
            dogIn.goalSelector.addGoal(7, glideGoal);
            dogIn.setData(GLIDE_AI, glideGoal);
        }
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        //dogIn.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), PILLOW_PAW_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
        if (this.level() >= 5) {
            return new AttributeModifier(uuidIn, "Pillow Paw", -0.065D, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    @Override
    public InteractionResult canTrample(AbstractDog dogIn, BlockState state, BlockPos pos, float fallDistance) {
        return this.level() >= 5 ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public InteractionResult onLivingFall(AbstractDog dogIn, float distance, float damageMultiplier) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() > 0) {
            return InteractionResultHolder.success(distance - this.level() * 3);
        }

        return InteractionResultHolder.pass(0F);
    }

    public static class PillowPawGlideGoal extends Goal{
        
        private Dog dog;
        private PillowPawTalent talentInst;
        
        public PillowPawGlideGoal(Dog dog, PillowPawTalent talentInst) {
            this.dog = dog;
            this.talentInst = talentInst;
        }

        @Override
        public boolean canUse() {
            return this.dog.fallDistance > 3;
        }

        @Override
        public boolean canContinueToUse() {
            return this.dog.fallDistance > 3;
        }

        @Override
        public void start() {
            this.startGliding();
        }

        @Override
        public void stop() {
            this.stopGliding();
        }

        private void startGliding() {
            this.dog.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), PILLOW_PAW_BOOST_ID, this::createSpeedModifier);
            ChopinLogger.lwn(this.dog, "start gliding");
        }

        private void stopGliding() {
            this.dog.removeAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), PILLOW_PAW_BOOST_ID);
        }

        public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
            if (this.talentInst.level() >= 5) {
                return new AttributeModifier(uuidIn, "Pillow Paw", -0.065D, AttributeModifier.Operation.ADDITION);
            }
    
            return null;
        }



    }
}
