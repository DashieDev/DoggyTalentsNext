package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.store.UIAction;

public class TalentListPageCounterSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return 1;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        switch (action.type) {
            case "resize" : return 1;
            case "talent_list_page.increment" : {
                if (oldData instanceof Integer i) {
                    return i + 1;
                } else return 1;
            }
            case "talent_list_page.decrement" : {
                if (oldData instanceof Integer i) {
                    return i - 1;
                } else return 1;
            }
        } 
        if (action.payload instanceof Integer ) {
            return action.payload;
        }
        return 1;
    }
    
}
