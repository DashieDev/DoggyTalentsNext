package doggytalents.common.entity;

import doggytalents.api.impl.DogAlterationProps;
import doggytalents.common.entity.ai.nav.DogSwimMoveControl;
import doggytalents.common.entity.ai.nav.DogWaterBoundNavigation;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.neoforge.common.NeoForgeMod;

public class DogSwimmingManager {
    
    private final Dog dog;

    private static final ResourceLocation SWIM_BOOST_ID = Util.getResource("swim_boost");
    private DogSwimMoveControl moveControl_water;
    private DogWaterBoundNavigation navigator_water;
    private boolean swimming = false;
    private static final float baseSwimSpeedModifierAdd = 2;
    private float swimSpeedModifierAdd = baseSwimSpeedModifierAdd;

    public DogSwimmingManager(Dog dog) {
        this.dog = dog;
    }

    public void tickServer() {
        if (!dog.canSwimUnderwater())
            return;
        if (moveControl_water == null || navigator_water == null)
            return;
        if (this.swimming) updateSwimming(dog);
        else updateNotSwimming(dog);
    }

    public void onPropsUpdated(DogAlterationProps props) {
        if (this.swimming) {
            this.swimming = false;
            stopSwimming(dog);
        }
        if (props.canSwimUnderwater()) {
            this.moveControl_water = new DogSwimMoveControl(dog);
            this.navigator_water = new DogWaterBoundNavigation(dog, dog.level());
            this.swimSpeedModifierAdd = baseSwimSpeedModifierAdd + props.bonusSwimSpeed();
        } else {
            this.moveControl_water = null;
            this.navigator_water = null;   
        }
    }

    private void updateSwimming(Dog dog) {
        if (
            (!dog.isInWater() && dog.onGround())
            || dog.isLowAirSupply()
            || dog.isDefeated()
        ) {
            this.swimming = false;
            stopSwimming(dog);
        }
    }

    private void updateNotSwimming(Dog dog) {
        if (
            dog.isInWater()
            && !dog.isDefeated()
            && readyToBeginSwimming(dog)
            && !dog.isDogSwimming()
        ) {
            this.swimming = true;
            this.startSwimming(dog);
        }
    }

    private boolean readyToBeginSwimming(Dog dog) {
        return dog.getAirSupply() == dog.getMaxAirSupply();
    }

    private void applySwimAttributes(Dog dog){
        dog.setAttributeModifier(NeoForgeMod.SWIM_SPEED, SWIM_BOOST_ID, (dd, u) -> 
            new AttributeModifier(u, this.swimSpeedModifierAdd, Operation.ADD_VALUE)
        );
    }

    private void removeSwimAttributes(Dog dog) {
        dog.removeAttributeModifier(NeoForgeMod.SWIM_SPEED, SWIM_BOOST_ID);
    }
    
    private void startSwimming(Dog dog) {
        dog.setJumping(false);
        dog.setNavigation(this.navigator_water);
        dog.setMoveControl(this.moveControl_water);
        if (dog.isInSittingPose()) { 
            dog.setInSittingPose(false);
        }
        applySwimAttributes(dog);
        dog.setDogSwimming(true);
    }

    private void stopSwimming(Dog dog) {
        dog.resetMoveControl();
        dog.resetNavigation();
        removeSwimAttributes(dog);
        dog.setDogSwimming(false);
    }

}
