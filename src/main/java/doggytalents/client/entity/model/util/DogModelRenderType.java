package doggytalents.client.entity.model.util;

import java.util.List;
import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

public class DogModelRenderType implements StringRepresentable {

    public static final DogModelRenderType CUTOUT = 
        new DogModelRenderType("cutout_no_cull", RenderTypes::entityCutout);
    public static final DogModelRenderType TRANSLUCENT = 
        new DogModelRenderType("translucent", RenderTypes::entityTranslucent);
    public static final List<DogModelRenderType> ALL = List.of(CUTOUT, TRANSLUCENT);
    public static final Codec<DogModelRenderType> CODEC = StringRepresentable
        .fromValues(() -> ALL.toArray(DogModelRenderType[]::new));
    
    private final String id;
    private final Function<Identifier, RenderType> renderType;

    private DogModelRenderType(String id, Function<Identifier, RenderType> renderType) {
        this.id = id;
        this.renderType = renderType;
    }

    public String id() {
        return id;
    }

    public Function<Identifier, RenderType> renderType() {
        return renderType;
    }

    @Override
    public String getSerializedName() {
        return this.id();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
