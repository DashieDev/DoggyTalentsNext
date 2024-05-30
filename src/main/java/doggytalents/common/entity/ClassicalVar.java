package doggytalents.common.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import doggytalents.common.config.ConfigHandler;
import doggytalents.common.util.Util;
import doggytalents.common.variants.DTNWolfVariants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public enum ClassicalVar {
    //Minecraft
    PALE(0, "pale"),
    CHESTNUT(1, "chestnut"),
    STRIPED(2, "striped"),
    WOOD(3, "woods"),
    RUSTY(4, "rusty"),
    BLACK(5, "black"),
    SNOWY(6, "snowy"),
    ASHEN(7, "ashen"),
    SPOTTED(8, "spotted"),
    
    //Compl. DTN
    CHERRY(9, "cherry", true),
    LEMONY_LIME(10, "lemony_lime", true),
    HIMALAYAN_SALT(11, "himalayan_salt", true),
    BAMBOO(12, "bamboo", true);

    private final int idInt;
    private final boolean isCompl;
    private final ResourceLocation id;
    private final ResourceLocation textureLoc;
    private final ResourceLocation vanillaTextureLoc;
    private final ResourceLocation iconLoc;
    private final String translationKey;
    private static final ClassicalVar[] VALUES = ClassicalVar.values();
    private static final ClassicalVar[] COMPLS = 
        Arrays.stream(VALUES)
            .filter(x -> x.isCompl)
            .toArray(ClassicalVar[]::new);
    private static final Map<ResourceLocation, ClassicalVar> idMap = 
        Arrays.stream(VALUES)
            .collect(Collectors.toMap(toKey -> toKey.id, toVal -> toVal));
    // private static final Map<ResourceKey<WolfVariant>, ClassicalVar> vanillToClassical =
    //     new ImmutableMap.Builder<ResourceKey<WolfVariant>, ClassicalVar>()
    //     .put(WolfVariants.PALE, ClassicalVar.PALE)
    //     .put(WolfVariants.CHESTNUT, ClassicalVar.CHESTNUT)
    //     .put(WolfVariants.STRIPED, ClassicalVar.STRIPED)
    //     .put(WolfVariants.WOODS, ClassicalVar.WOOD)
    //     .put(WolfVariants.RUSTY, ClassicalVar.RUSTY)
    //     .put(WolfVariants.BLACK, ClassicalVar.BLACK)
    //     .put(WolfVariants.SNOWY, ClassicalVar.SNOWY)
    //     .put(WolfVariants.ASHEN, ClassicalVar.ASHEN)
    //     .put(WolfVariants.SPOTTED, ClassicalVar.SPOTTED)
    //     .put(DTNWolfVariants.CHERRY, ClassicalVar.CHERRY)
    //     .put(DTNWolfVariants.LEMONY_LIME, ClassicalVar.LEMONY_LIME)
    //     .put(DTNWolfVariants.HIMALAYAN_SALT, ClassicalVar.HIMALAYAN_SALT)
    //     .put(DTNWolfVariants.BAMBOO, ClassicalVar.BAMBOO)
    //     .build();

    private ClassicalVar(int idInt, String name) {
        this(idInt, name, false);
    }

    private ClassicalVar(int idInt, String name, boolean is_compl) {
        this.idInt = idInt;
        this.id = new ResourceLocation(name);
        this.textureLoc = createTextureLoc(name, is_compl);
        this.translationKey = createTranslationKey(name, is_compl);
        this.iconLoc = createIconLoc(name, is_compl);
        this.isCompl = is_compl;
        
        //TODO Insert vanilla provided texture here.
        this.vanillaTextureLoc = textureLoc;
    }

    private static ResourceLocation createTextureLoc(String name, boolean is_compl) {
        if (is_compl)
            return Util.getResource("textures/entity/dog/classical/compl/wolf_" + name + ".png");
        return Util.getResource("textures/entity/dog/classical/wolf_" + name + ".png");
    }

    private static ResourceLocation createIconLoc(String name, boolean is_compl) {
        if (is_compl)
            return Util.getResource("textures/item/doggy_charm.png");
        return Util.getResource("textures/entity/dog/classical_icon/" + name + ".png");
    }

    private static String createTranslationKey(String name, boolean is_compl) {
        if (is_compl)
            return "dog.classical.variant.compl." + name;
        return "dog.classical.variant." + name;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int getIdInt() {
        return this.idInt;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }
    
    public ResourceLocation getTexture(boolean use_vanilla_for_classical) {
        if (use_vanilla_for_classical) {
            return this.vanillaTextureLoc;
        }
        return this.textureLoc;
    }

    public ResourceLocation getIcon() {
        return this.iconLoc;
    }

    public static ClassicalVar fromId(ResourceLocation id) {
        var ret = idMap.get(id);
        if (ret == null)
            ret = ClassicalVar.PALE;
        return ret;
    }

    public static ClassicalVar bySaveName(String id) {
        if (id == null) id = "";
        var res = new ResourceLocation(id);
        return fromId(res);
    }

    public static ClassicalVar fromIdInt(int id) {
        if (id < 0 || id >= VALUES.length) {
            return ClassicalVar.PALE;
        }
        return VALUES[id];
    }

    public static ClassicalVar getWolf(Wolf wolf) {
        //(Non 1.20.5+)
        if (ConfigHandler.SERVER.RANDOM_VAR_ON_TRAIN.get()) {
            var vals = ClassicalVar.values();
            int r = wolf.getRandom().nextInt(vals.length);
            return vals[r];
        }
        return ClassicalVar.PALE;
    }

    public static ClassicalVar random(Dog dog) {
        var vals = ClassicalVar.values();
        int r = dog.getRandom().nextInt(vals.length);
        return vals[r];
    }

    public static ClassicalVar[] getCompls() {
        return COMPLS;
    }
     
    public static Map<ClassicalVar, Integer> COLOR_MAP = 
        new ImmutableMap.Builder<ClassicalVar, Integer>()
        .put(ClassicalVar.PALE, 0xffdad7d8)
        .put(ClassicalVar.CHESTNUT, 0xff9a8483)
        .put(ClassicalVar.STRIPED, 0xffc9af80)
        .put(ClassicalVar.WOOD, 0xff76583c)
        .put(ClassicalVar.RUSTY, 0xffde8338)
        .put(ClassicalVar.BLACK, 0xff292629)
        .put(ClassicalVar.SNOWY, 0xffb8877e)
        .put(ClassicalVar.ASHEN, 0xff928991)
        .put(ClassicalVar.SPOTTED, 0xffc8bc30)
        .put(ClassicalVar.CHERRY, 0xfffa9de5)
        .put(ClassicalVar.LEMONY_LIME, 0xffa8c882)
        .put(ClassicalVar.HIMALAYAN_SALT, 0xffb55c63)
        .put(ClassicalVar.BAMBOO, 0xff629122)
        .build();
    
}
