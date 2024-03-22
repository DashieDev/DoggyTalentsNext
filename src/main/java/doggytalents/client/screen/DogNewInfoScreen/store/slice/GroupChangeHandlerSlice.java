package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.UIAction;

public class GroupChangeHandlerSlice implements AbstractSlice {
    
    @Override
    public Object getInitalState() {
        return new Object();
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.type == UIActionTypes.DOG_GROUPS_RESPONSE)
            return new Object();
        return oldData;
    }
    

}
