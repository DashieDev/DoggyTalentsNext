package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import java.util.List;

import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.store.payload.ChangeTabPayload;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.UIAction;

public class SkinListSlice implements AbstractSlice {

    @Override
    public Object getInitalState() {
        return null;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (ActiveTabSlice.isChangeTabAction(action.type)) {
            if (
                action.payload instanceof ChangeTabPayload change_tab_payload
                && change_tab_payload.getTab() == ActiveTabSlice.Tab.STYLE
                && change_tab_payload.initData instanceof SkinListData data
            ) {
                return data;
            }
            return null;
        }
        return oldData;
    }

    public static SkinListData initSkinData() {
        return new SkinListData(List.copyOf(DogTextureManager.INSTANCE.getAll()));
    }

    public static class SkinListData {

        public static final SkinListData EMPTY = new SkinListData(List.of());
        
        public final List<DogSkin> skins;

        public SkinListData (List<DogSkin> skins) {
            this.skins = skins;
        }
        
    }
    
}
