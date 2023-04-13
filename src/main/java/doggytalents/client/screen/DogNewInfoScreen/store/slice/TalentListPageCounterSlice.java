package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import doggytalents.client.screen.framework.UIAction;

public class TalentListPageCounterSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return 1;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.type == CommonUIActionTypes.RESIZE) {
            return 1;
        } else if (action.type == UIActionTypes.Talents.LIST_INC) {
            if (oldData instanceof Integer i) {
                return i + 1;
            } else return 1;
        } else if (action.type == UIActionTypes.Talents.LIST_DEC) {
            if (oldData instanceof Integer i) {
                return i - 1;
            } else return 1;
        }
        return 1;
    }
    
}
