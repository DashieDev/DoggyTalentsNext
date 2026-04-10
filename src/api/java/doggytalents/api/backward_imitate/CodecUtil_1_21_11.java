package doggytalents.api.backward_imitate;

import java.util.List;

import org.joml.Vector3f;

import com.mojang.serialization.Codec;

public class CodecUtil_1_21_11 {
    
    public static final Codec<Vector3f> VECTOR3F = Codec.FLOAT
        .listOf()
        .comapFlatMap(
            p_466430_ -> net.minecraft.util.Util.fixedSize((List<Float>)p_466430_, 3).map(p_253489_ -> new Vector3f(p_253489_.get(0), p_253489_.get(1), p_253489_.get(2))),
            p_454444_ -> List.of(p_454444_.x(), p_454444_.y(), p_454444_.z())
        );

}
