package doggytalents.client.backward_imitate;

import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class DogRenderState_21_3 extends LivingEntityRenderState {

    //TODO using the Dog object iself here for backward compat maximizing
    //May change in the future for several reason, for instance, if 
    //somehow we ended up rendering the dog in other thread and
    //this state system acts as a view of the Dog for another thread  
    public Dog dog = null;

    
    //1.21.9+
    public Runnable defferedSetup_1_21_9 = null;
    public void runDefferedSetupAndInvalidate_1_21_9() {
        if (defferedSetup_1_21_9 == null)
            return;
        defferedSetup_1_21_9.run();
        defferedSetup_1_21_9 = null;
    }

}
