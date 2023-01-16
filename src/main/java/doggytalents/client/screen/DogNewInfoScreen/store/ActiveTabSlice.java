package doggytalents.client.screen.DogNewInfoScreen.store;

public class ActiveTabSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return Tab.HOME;
    }

    @Override
    public Object reducer(Object oldData, AbstractUIAction action) {
        if (action.payload instanceof Tab) {
            return action.payload;
        }
        return Tab.HOME;
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
