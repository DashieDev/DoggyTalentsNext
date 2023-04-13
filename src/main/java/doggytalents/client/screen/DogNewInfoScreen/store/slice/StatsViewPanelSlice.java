package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.UIAction;

public class StatsViewPanelSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        // TODO Auto-generated method stub
        return StatsViewPanelTab.GENERAL;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.payload instanceof StatsViewPanelTab) {
            return action.payload;
        }
        return oldData;
    }

    public static enum StatsViewPanelTab {
        GENERAL("general"), MOB_KILLS("mob_kills");

        public final String unLocalizedTitle;

        StatsViewPanelTab(String title) {
            this.unLocalizedTitle = "doggui.stats." + title;
        }
    }
    
}
