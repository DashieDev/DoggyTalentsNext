package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.UIAction;

public class TalentChangeHandlerSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return new Object();
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.type == UIActionTypes.Talents.TALENT_UPDATE)
            return new Object();
        return oldData;
    }
    
}
