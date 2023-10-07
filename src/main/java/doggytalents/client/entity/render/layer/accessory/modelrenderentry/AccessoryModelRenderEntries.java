package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;

public class AccessoryModelRenderEntries {
    
    public static Entry BOWTIE = new BowtieRenderEntry();
    public static Entry SMARTY_GLASSES = new SmartyGlassesRenderEntry();
    public static Entry WIG = new WigRenderEntry();
    public static Entry BACH_WIG = new BachWigRenderEntry();
    public static Entry BEASTARS_UNIFORM_MALE = new BeastarsUniformMaleEntry();
    public static Entry BEASTARS_UNIFORM_FEMALE = new BeastarsUniformFemaleEntry();
    public static Entry LOCATOR_ORB = new LocatorOrbRenderEntry();
    public static Entry HOT_DOG = new HotDogRenderEntry();
    public static Entry GIANT_STICK = new GiantStickRenderEntry();
    public static Entry SNORKEL = new SnorkelRenderEntry();
    public static Entry HEAD_BAND = new HeadBandRenderEntry();
    public static Entry KITSUNE_FRONT = new KitsuneFrontRenderEntry();
    public static Entry KITSUNE_SIDE = new KitsuneSideRenderEntry();
    public static Entry TENGU = new TenguRenderEntry();

    public static void registerEntries() {
        AccessoryModelManager.register(BOWTIE);
        AccessoryModelManager.register(SMARTY_GLASSES);
        AccessoryModelManager.register(WIG);
        AccessoryModelManager.register(BACH_WIG);
        AccessoryModelManager.register(BEASTARS_UNIFORM_MALE);
        AccessoryModelManager.register(BEASTARS_UNIFORM_FEMALE);
        AccessoryModelManager.register(LOCATOR_ORB);
        AccessoryModelManager.register(HOT_DOG);
        AccessoryModelManager.register(GIANT_STICK);
        AccessoryModelManager.register(SNORKEL);
        AccessoryModelManager.register(HEAD_BAND);
        AccessoryModelManager.register(KITSUNE_FRONT);
        AccessoryModelManager.register(KITSUNE_SIDE);
        AccessoryModelManager.register(TENGU);

    }

}
