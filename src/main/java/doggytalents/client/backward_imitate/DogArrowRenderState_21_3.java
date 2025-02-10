package doggytalents.client.backward_imitate;

import doggytalents.common.entity.misc.DogArrow;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;

public class DogArrowRenderState_21_3 extends ArrowRenderState {
    //TODO using the Dog object iself here for backward compat maximizing
    //May change in the future for several reason, for instance, if 
    //somehow we ended up rendering the dog in other thread and
    //this state system acts as a view of the Dog for another thread  
    public DogArrow dogArrow = null; 
}
