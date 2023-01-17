package doggytalents.client.screen.DogNewInfoScreen.store;

public class TalentListPageCounterSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return 0;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if ("resize".equals(action.type)) {
            return 0;
        }
        if (action.payload instanceof Integer) {
            return action.payload;
        }
        return 0;
    }
    
}
