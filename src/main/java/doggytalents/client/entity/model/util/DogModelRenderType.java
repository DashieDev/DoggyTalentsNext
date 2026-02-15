package doggytalents.client.entity.model.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class DogModelRenderType {

    public static final DogModelRenderType CUTOUT = 
        new DogModelRenderType("cutout_no_cull", RenderType::entityCutoutNoCull);
    public static final DogModelRenderType TRANSLUCENT = 
        new DogModelRenderType("translucent", RenderType::entityTranslucent);
    public static final List<DogModelRenderType> ALL = List.of(CUTOUT, TRANSLUCENT);
    public static final BiMap<String, DogModelRenderType> BY_ID = HashBiMap.create(
        ALL.stream().collect(Collectors.toMap(
            DogModelRenderType::id, Function.identity()
    )));
    public static final Codec<DogModelRenderType> CODEC = 
        Codec.STRING.xmap(
            BY_ID::get
            , to_encode -> BY_ID.inverse().get(to_encode)
        );
    
    private final String id;
    private final Function<ResourceLocation, RenderType> renderType;

    private DogModelRenderType(String id, Function<ResourceLocation, RenderType> renderType) {
        this.id = id;
        this.renderType = renderType;
    }

    public String id() {
        return id;
    }

    public Function<ResourceLocation, RenderType> renderType() {
        return renderType;
    }
}
