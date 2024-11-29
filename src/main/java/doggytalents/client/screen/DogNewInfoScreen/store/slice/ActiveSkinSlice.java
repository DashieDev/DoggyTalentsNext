package doggytalents.client.screen.DogNewInfoScreen.store.slice;

import java.util.List;

import doggytalents.DoggyEntityTypes;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.payload.interfaces.TabChange;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice.Tab;
import doggytalents.client.screen.framework.AbstractSlice;
import doggytalents.client.screen.framework.CleanableSlice;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.entity.Dog;
import doggytalents.client.screen.framework.CommonUIActionTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ActiveSkinSlice implements CleanableSlice {

    public static Dog DUMMY_DOG_OBJ;

    public boolean showInfo;

    @Override
    public Object getInitalState() {
        var ret = new ActiveSkinSlice();
        ret.showInfo = false;
        return ret;
    }

    @Override
    public Object reducer(Object oldData, UIAction action) {
        if (oldData instanceof ActiveSkinSlice) {
            if (action.type == UIActionTypes.Skins.SHOW_INFO) {
                var ret = new ActiveSkinSlice();
                ret.showInfo = true;
                return ret;
            } else if (action.type == UIActionTypes.Skins.HIDE_INFO) {
                var ret = new ActiveSkinSlice();
                ret.showInfo = false;
                return ret;
            }
        }
        return oldData;
    }

    public static void initLocList() {
        setupDummyDog();
    }

    @Override
    public void cleanUpSlice() {
        DUMMY_DOG_OBJ = null;
    }

    public static void setupDummyDog() {
        if (DUMMY_DOG_OBJ != null) return;
        var level = Minecraft.getInstance().level;
        var dog = DoggyEntityTypes.DOG.get().create(level);
        DUMMY_DOG_OBJ = dog;
    }
    
}
