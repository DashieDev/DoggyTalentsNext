package doggytalents.client.screen.AmnesiaBoneScreen.store.slice;

import doggytalents.client.screen.AmnesiaBoneScreen.store.payload.ChangeTabPayload;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.entity.Dog;

public class ActiveTabSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return Tab.GENERAL;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.type == CommonUIActionTypes.CHANGE_TAB) {
            if (action.payload instanceof ChangeTabPayload tabPayload) {
                return tabPayload.getTab();
            }
        }
        return oldData;
    }

    public enum Tab {
        GENERAL("general"), TALENTS("talents");
        public final String unlocalizedTitle;

        private Tab(String title){
            this.unlocalizedTitle = "amnesia_bone_gui.navbar." + title;
        }
    }

    public static UIAction UIActionCreator(Dog dog, Tab tab) {

        var payload = new ChangeTabPayload(tab);

        return new UIAction(CommonUIActionTypes.CHANGE_TAB, payload);
    }
    
}
