package doggytalents.common.backward_imitate;

import doggytalents.common.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityHelper_21_3 {
    
    public static ResourceKey<EntityType<?>> entityKey(ResourceLocation name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, name);
    }

    public static ResourceKey<EntityType<?>> entityKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Util.getResource(name));
    }

    public static float fixDegreeClamping(float val_degree) {
        return Mth.wrapDegrees(val_degree);
    }

}
