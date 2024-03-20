package doggytalents.common.talent;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogFlyingMoveControl;
import doggytalents.common.entity.ai.nav.DogFlyingNavigation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class FlyingFurballTalent extends TalentInstance {

    private static UUID FLYING_FURBALL_BOOST_UUID = UUID.fromString("2a802049-97d0-4de5-934e-e24ed1d2ab9f");
    private static UUID FLYING_FURBALL_GRAVITY_UUID = UUID.fromString("76390e7e-e38d-4de5-8fce-83af09b03a3e");

    private DogFlyingMoveControl moveControl;
    private DogFlyingNavigation navigation;
    
    private boolean wasFlying = false;
    private int flyHoldTick = 0;
    
    public FlyingFurballTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        if (dog.level().isClientSide)
            return;
        if (!(dog instanceof Dog d))
            return;
        d.setAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID, this::createSpeedModifier);
        this.moveControl = 
            new DogFlyingMoveControl(d, this);
        this.navigation = 
            new DogFlyingNavigation(d, dog.level());
        startGliding(dog);
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        dogIn.setAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID, this::createSpeedModifier);
    }

    @Override
    public void remove(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        dogIn.removeAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID);
        dogIn.resetMoveControl();
        dogIn.resetNavigation();
        dogIn.setDogFlying(false);
        stopGliding(dogIn);
        dogIn.setNoGravity(false);
        if (dogIn instanceof Dog d && d.getAnim() == DogAnimation.FLY_AIR_BOURNE)
            d.setAnim(DogAnimation.NONE);
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (!(dogIn instanceof Dog dog))
            return;

        if (flyHoldTick > 0)
            --flyHoldTick;

        boolean isDogFlying = dog.isDogFlying();

        if (dog.getNavigation() != navigation && shouldSwitchToFlying(dog)) {
            dog.setMoveControl(moveControl);
            dog.setNavigation(navigation);
        }

        if (isDogFlying && shouldStopFlying(dog))  {
            dog.setDogFlying(false);
            dog.setNoGravity(false);
        }

        if (!isDogFlying && canBeginFlying(dog)) {
            dog.setDogFlying(true);
            this.flyHoldTick = 15;
        }

        isDogFlying = dog.isDogFlying();

        if (isDogFlying)
        if (this.wasFlying) {
            if (dog.getAnim() == DogAnimation.NONE)
                dog.setAnim(DogAnimation.FLY_AIR_BOURNE);
        } else {
            dog.setAnim(DogAnimation.FLY_JUMP_START);
            this.wasFlying = true;
        }

        if (!isDogFlying){
            var anim = dog.getAnim();
            if (anim == DogAnimation.FLY_AIR_BOURNE || anim == DogAnimation.FLY_JUMP_START || (wasFlying && anim == DogAnimation.NONE))
                dog.setAnim(DogAnimation.FLY_LANDING);
            this.wasFlying = false;
        }
    }

    private boolean canBeginFlying(AbstractDog dog) {
        return dog.getAnim() == DogAnimation.NONE && shouldBeFlying(dog);
    }

    private boolean shouldStopFlying(AbstractDog dog) {
        return !shouldBeFlying(dog);
    }

    private boolean shouldBeFlying(AbstractDog dog) {
        if (flyHoldTick > 0)
            return true;
        
        if (!dog.isDoingFine()) {
            if (dog instanceof Dog ddog && !ddog.incapacitatedMananger.canMove())
            return false;
        }

        return !dog.onGround() && !dog.isInSittingPose() && !dog.isPassenger()
            && !dog.isInWater() && dog.getNavigation() == this.navigation;
    }

    private boolean shouldSwitchToFlying(AbstractDog dog) {
        return !dog.isInSittingPose() && !dog.isPassenger()
            //To avoid conflict with SwimmerDog, may changes in future
            && !dog.isInWater();
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = (this.level() - 1)* 0.1;

            return new AttributeModifier(uuidIn, "Flying Furball", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setCanFly().setFallImmune();
    }

    public void startGliding(AbstractDog dog) {
        dog.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), FLYING_FURBALL_GRAVITY_UUID, this::createGravityModifier);
    }

    public void stopGliding(AbstractDog dog) {
        dog.removeAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), FLYING_FURBALL_GRAVITY_UUID);
    }

    public AttributeModifier createGravityModifier(AbstractDog dogIn, UUID uuidIn) {
        return new AttributeModifier(uuidIn, "Flying Furball Gravity", -0.8, AttributeModifier.Operation.MULTIPLY_BASE);
    }

}
