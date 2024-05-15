package doggytalents.client.entity.render.layer.accessory.modelrenderentry;

import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.common.entity.accessory.BirthdayHatAccessory;

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
    public static Entry TENGU_MASK = new TenguMaskRenderEmtry();
    public static Entry DEMON_HORNS = new DemonHornsRenderEntry();
    public static Entry KITSUNE_MASK = new KitsuneMaskRenderEntry();
    public static Entry BIRTHDAY_HAT = new BirthdayHatRenderEntry();
    public static Entry WITCH_HAT = new WitchHatRenderEntry();
    public static Entry PLAGUE_DOC_MASK = new PlagueDoctorMaskRenderEntry();
    public static Entry HEAD_BAND = new HeadBandRenderEntry();
    public static Entry BAKER_HAT = new BakerHatRenderEntry();
    public static Entry CHEF_HAT = new ChefHatRenderEntry();
    public static Entry FLYING_CAPE = new FlyingCapeRenderEntry();
    public static Entry BAT_WINGS = new BatWingsRenderEntry();
    public static Entry CROW_WINGS = new CrowWingsRenderEntry();
    public static Entry FIERY_REFL = new FieryReflectorRenderEntry();
    public static Entry LAB_COAT = new LabCoatRenderEntry();
    public static Entry CERE_GARB = new CeremonialGarbRenderEntry();
    public static Entry CONTACTS = new ContactsRenderEntry();
    public static Entry PROPELLAR = new PropellarRenderEntry();
    public static Entry FEDORA = new FedoraRenderEntry();
    public static Entry FLATCAP = new FlatCapRenderEntry();
    public static Entry DYABLE_LOCATOR_ORB = new DyableLocatorOrbEntry();

    public static void registerEntries() {
        AccessoryModelManager.register(BOWTIE);
        AccessoryModelManager.register(CONTACTS);
        AccessoryModelManager.register(SMARTY_GLASSES);
        AccessoryModelManager.register(WIG);
        AccessoryModelManager.register(BACH_WIG);
        AccessoryModelManager.register(BEASTARS_UNIFORM_MALE);
        AccessoryModelManager.register(BEASTARS_UNIFORM_FEMALE);
        AccessoryModelManager.register(LOCATOR_ORB);
        AccessoryModelManager.register(HOT_DOG);
        AccessoryModelManager.register(GIANT_STICK);
        AccessoryModelManager.register(SNORKEL);
        AccessoryModelManager.register(TENGU_MASK);
        AccessoryModelManager.register(KITSUNE_MASK);
        AccessoryModelManager.register(DEMON_HORNS);
        AccessoryModelManager.register(KITSUNE_MASK);
        AccessoryModelManager.register(BIRTHDAY_HAT);
        AccessoryModelManager.register(WITCH_HAT);
        AccessoryModelManager.register(PLAGUE_DOC_MASK);
        AccessoryModelManager.register(HEAD_BAND);
        AccessoryModelManager.register(BAKER_HAT);
        AccessoryModelManager.register(CHEF_HAT);
        AccessoryModelManager.register(FLYING_CAPE);
        AccessoryModelManager.register(BAT_WINGS);
        AccessoryModelManager.register(CROW_WINGS);
        AccessoryModelManager.register(FIERY_REFL);
        AccessoryModelManager.register(LAB_COAT);
        AccessoryModelManager.register(CERE_GARB);
        AccessoryModelManager.register(PROPELLAR);
        AccessoryModelManager.register(FEDORA);
        AccessoryModelManager.register(FLATCAP);
        AccessoryModelManager.register(DYABLE_LOCATOR_ORB);
    }

}
