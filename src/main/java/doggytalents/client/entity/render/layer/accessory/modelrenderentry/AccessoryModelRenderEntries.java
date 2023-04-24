package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;

public class AccessoryModelRenderEntries {
    
    public static Entry BOWTIE = new BowtieRenderEntry();
    public static Entry SMARTY_GLASSES = new SmartyGlassesRenderEntry();
    public static Entry WIG = new WigRenderEntry();

    public static void registerEntries() {
        AccessoryModelManager.register(BOWTIE);
        AccessoryModelManager.register(SMARTY_GLASSES);
        AccessoryModelManager.register(WIG);
    }

}
