package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.store.UIAction;

public class ActiveTabSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return Tab.HOME;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.payload instanceof Tab) {
            return action.payload;
        }
        return oldData;
    }

    public enum Tab {
        HOME("home"), TALENTS("talents"), 
        ACCESSORIES("accessories"), STATS("stats");
        public final String title;

        private Tab(String title){
            this.title = title;
        }
    }

    
    
}
