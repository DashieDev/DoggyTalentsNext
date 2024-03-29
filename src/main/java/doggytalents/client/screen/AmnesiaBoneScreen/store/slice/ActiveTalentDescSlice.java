package doggytalents.client.screen.AmnesiaBoneScreen.store.slice;

import doggytalents.api.registry.Talent;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.UIAction;

public class ActiveTalentDescSlice implements AbstractSlice {

    public Talent activeTalent;
    
    public ActiveTalentDescSlice() {}

    public ActiveTalentDescSlice(Talent talent) {
        this.activeTalent = talent;
    }

    @Override
    public Object getInitalState() {
        return new ActiveTalentDescSlice(null);
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.payload instanceof ActiveTalentDescSlice s1) {
            return new ActiveTalentDescSlice(s1.activeTalent);
        }
        return oldData;
    }
    
}
