package doggytalents.client.screen.DogNewInfoScreen.store.payload;

import doggytalents.client.DogTextureManager;
import doggytalents.client.screen.DogNewInfoScreen.store.payload.interfaces.InitSkinIndex;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveSkinSlice;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice.Tab;
import doggytalents.client.screen.framework.payload.ChangeTabPayload;
import doggytalents.common.entity.Dog;

public class InitSkinIndexPayload extends ChangeTabPayload implements InitSkinIndex {

    Dog dog;
    int index;

    public InitSkinIndexPayload(Tab tab, Dog dog) {
        super(tab);
        this.dog = dog;
        index = Math.max(0, ActiveSkinSlice.locList.indexOf(dog.getClientSkin())); 
    }

    @Override
    public ActiveSkinSlice getInitSkinIndex() {
        var ret = new ActiveSkinSlice();
        ret.activeSkinId = index;
        return ret;
    }
    
}
