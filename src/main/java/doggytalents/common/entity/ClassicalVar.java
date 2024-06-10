package doggytalents.common.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import doggytalents.common.variants.DTNWolfVariants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;

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
    BAMBOO(12, "bamboo", true),
    CRIMSON(13, "crimson", true),
    WARPED(14, "warped", true),
    BIRCH(15, "birch", true),
    PISTACHIO(16, "pistachio", true),
    GUACAMOLE(17, "guacamole", true),
    VSCODE(18, "vscode", true);

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
    private static final Map<ResourceKey<WolfVariant>, ClassicalVar> vanillToClassical =
        new ImmutableMap.Builder<ResourceKey<WolfVariant>, ClassicalVar>()
        .put(WolfVariants.PALE, ClassicalVar.PALE)
        .put(WolfVariants.CHESTNUT, ClassicalVar.CHESTNUT)
        .put(WolfVariants.STRIPED, ClassicalVar.STRIPED)
        .put(WolfVariants.WOODS, ClassicalVar.WOOD)
        .put(WolfVariants.RUSTY, ClassicalVar.RUSTY)
        .put(WolfVariants.BLACK, ClassicalVar.BLACK)
        .put(WolfVariants.SNOWY, ClassicalVar.SNOWY)
        .put(WolfVariants.ASHEN, ClassicalVar.ASHEN)
        .put(WolfVariants.SPOTTED, ClassicalVar.SPOTTED)
        .put(DTNWolfVariants.CHERRY, ClassicalVar.CHERRY)
        .put(DTNWolfVariants.LEMONY_LIME, ClassicalVar.LEMONY_LIME)
        .put(DTNWolfVariants.HIMALAYAN_SALT, ClassicalVar.HIMALAYAN_SALT)
        .put(DTNWolfVariants.BAMBOO, ClassicalVar.BAMBOO)
        .put(DTNWolfVariants.CRIMSON, ClassicalVar.CRIMSON)
        .put(DTNWolfVariants.WARPED, ClassicalVar.WARPED)
        .put(DTNWolfVariants.BIRCH, ClassicalVar.BIRCH)
        .put(DTNWolfVariants.PISTACHIO, ClassicalVar.PISTACHIO)
        .put(DTNWolfVariants.GUACAMOLE, ClassicalVar.GUACAMOLE)
        .put(DTNWolfVariants.VSCODE, ClassicalVar.VSCODE)
        .build();

    private ClassicalVar(int idInt, String name) {
        this(idInt, name, false);
    }

    private ClassicalVar(int idInt, String name, boolean is_compl) {
        this.idInt = idInt;
        this.id = is_compl ? Util.getResource(name) : new ResourceLocation(name);
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
        if (ret != null)
            return ret;
        var alt_id = Util.getResource(id.getPath());
        ret = idMap.get(alt_id); 
        if (ret != null)
            return ret;
        return ClassicalVar.PALE;
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
        var variant = wolf.getVariant();
        var variant_key = variant.unwrapKey().orElse(WolfVariants.PALE);
        var classical = vanillToClassical.getOrDefault(variant_key, PALE);
        return classical;
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
        .put(ClassicalVar.CRIMSON, 0xff822f33)
        .put(ClassicalVar.WARPED, 0xff235c5d)
        .put(ClassicalVar.BIRCH, 0xffcec192)
        .put(ClassicalVar.PISTACHIO, 0xffa3a25b)
        .put(ClassicalVar.GUACAMOLE, 0xff9ab123)
        .put(ClassicalVar.VSCODE, 0xff0078d4)
        .build();
    
}
