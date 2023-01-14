package doggytalents.client.screen.DogNewInfoScreen.store;

public class ActiveTabSlice {
    public static Tab activeTab = Tab.HOME;
    public enum Tab {
        HOME("home"), TALENTS("talents"), 
        ACCESSORIES("accessories"), STATS("stats");
        public final String title;

        private Tab(String title){
            this.title = title;
        }
    }
}
