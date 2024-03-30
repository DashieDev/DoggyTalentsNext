package doggytalents.common.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;

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

}
