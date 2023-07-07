package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.payload.ChangeTabPayload;
import doggytalents.client.screen.DogNewInfoScreen.store.payload.InitSkinIndexPayload;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogGroupsData;
import doggytalents.common.network.packet.data.StatsSyncData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.PacketDistributor;

public class ActiveTabSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return new Object();
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (action.type == CommonUIActionTypes.CHANGE_TAB 
            || action.type == CommonUIActionTypes.SWITCH_TAB) {
            if (action.payload instanceof ChangeTabPayload tabPayload) {
                return tabPayload.getTab();
            }
        } 
        return oldData;
    }

    //UI Action creator for tab setup, network request may be here.
    public static UIAction UIActionCreator(Dog dog, Tab tab, String type) {

        var payload = new ChangeTabPayload(tab);

        if (tab == Tab.STATS) {
            setupStats(dog);
        } else if (tab == Tab.STYLE) {
            payload = setupSkins(dog);
        } else if (tab == Tab.HOME) {
            setupGroups(dog);
        }

        return new UIAction(type, payload);
    }

    public static Tab getPrevTab(Tab tab) {
        switch (tab) {
        case HOME:
            return Tab.STATS;
        case STATS:
            return Tab.STYLE;
        case STYLE:
            return Tab.TALENTS;
        case TALENTS:
            return Tab.HOME;
        default:
            return Tab.HOME;
        }
    }

    public static Tab getNextTab(Tab tab) {
        switch (tab) {
        case HOME:
            return Tab.TALENTS;
        case STATS:
            return Tab.HOME;
        case STYLE:
            return Tab.STATS;
        case TALENTS:
            return Tab.STYLE;
        default:
            return Tab.HOME;
        }
    }

    private static void setupStats(Dog dog) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
        new StatsSyncData.Request(dog.getId()));
    }

    private static InitSkinIndexPayload setupSkins(Dog dog) {
        ActiveSkinSlice.initLocList();
        return new InitSkinIndexPayload(Tab.STYLE, dog);
    }

    private static void setupGroups(Dog dog) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
        new DogGroupsData.FETCH_REQUEST(dog.getId()));
    }

    public static void dispatchGroupUpdates() {
        var screen = Minecraft.getInstance().screen;
        if (screen instanceof DogNewInfoScreen dogInfoScreen) {
            Store.get(dogInfoScreen).dispatch(ActiveTabSlice.class, 
                new UIAction(UIActionTypes.DOG_GROUPS_RESPONSE, new Object()));
        }
    }

    public enum Tab {
        HOME("home"), TALENTS("talents"), 
        STYLE("style"), STATS("stats");
        public final String unlocalizedTitle;

        private Tab(String title){
            this.unlocalizedTitle = "doggui.navbar." + title;
        }
    }

    
    
}
