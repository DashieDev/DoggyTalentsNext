package doggytalents.common.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;

public enum ClassicalVar {
    PALE(0, "pale"),
    CHESTNUT(1, "chestnut"),
    STRIPED(2, "striped"),
    WOOD(3, "woods"),
    RUSTY(4, "rusty"),
    BLACK(5, "black"),
    SNOWY(6, "snowy"),
    ASHEN(7, "ashen"),
    SPOTTED(8, "spotted");

    private final int idInt;
    private final ResourceLocation id;
    private final ResourceLocation textureLoc;
    private final ResourceLocation vanillaTextureLoc;
    private static final ClassicalVar[] VALUES = ClassicalVar.values();
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
        .build();

    private ClassicalVar(int idInt, String name) {
        this.idInt = idInt;
        this.id = new ResourceLocation(name);
        this.textureLoc = createTextureLoc(name);
        
        //TODO Insert vanilla provided texture here.
        this.vanillaTextureLoc = textureLoc;
    }

    private static ResourceLocation createTextureLoc(String name) {
        return Util.getResource("textures/entity/dog/classical/wolf_" + name + ".png");
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int getIdInt() {
        return this.idInt;
    }
    
    public ResourceLocation getTexture(boolean use_vanilla_for_classical) {
        if (use_vanilla_for_classical) {
            return this.vanillaTextureLoc;
        }
        return this.textureLoc;
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
        var variant = wolf.getVariant();
        var variant_key = variant.unwrapKey().orElse(WolfVariants.PALE);
        var classical = vanillToClassical.getOrDefault(variant_key, PALE);
        return classical;
    }

}
