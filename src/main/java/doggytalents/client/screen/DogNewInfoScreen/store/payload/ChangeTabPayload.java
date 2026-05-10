package doggytalents.client.screen.DogNewInfoScreen.store.payload;

import doggytalents.client.screen.DogNewInfoScreen.store.payload.interfaces.TabChange;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice.Tab;

public class ChangeTabPayload implements TabChange {

    Tab tab;
    public Object initData = null;

    public ChangeTabPayload(Tab tab) {
        this.tab = tab;
    }

    @Override
    public Tab getTab() {
        return tab;
    }
    
}
