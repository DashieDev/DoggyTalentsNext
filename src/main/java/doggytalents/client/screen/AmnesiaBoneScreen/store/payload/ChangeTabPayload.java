package doggytalents.client.screen.AmnesiaBoneScreen.store.payload;

import doggytalents.client.screen.AmnesiaBoneScreen.store.payload.interfaces.TabChange;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTabSlice.Tab;

public class ChangeTabPayload implements TabChange {

    Tab tab;

    public ChangeTabPayload(Tab tab) {
        this.tab = tab;
    }

    @Override
    public Tab getTab() {
        // TODO Auto-generated method stub
        return tab;
    }
    
}
