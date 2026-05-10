package doggytalents.client.entity.model.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

public class DTNAnimationCodec {
    
    /**
     * Format
     * {@snippet Lang=JSON :
     *  {
     *    "dtn_format_version": "1.0",
     *    "length": 0.5,
     *    "channels": [
     *        {
     *        "part": "tail",
     *        "type": "position",
     *        "keyframes": [
     *            {
     *            "at": 0,
     *            "value": [
     *                0,
     *                0.5,
     *                -0.4
     *            ],
     *            "interp": "catmullrom"
     *            }
     *        ]
     *        }
     *    ],
     *    "loop": true
     *  }
     * }
     */
    public static final Codec<AnimationDefinition> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                Codec.FLOAT.fieldOf("length")
                    .forGetter(AnimationDefinition::lengthInSeconds),
                Codec.BOOL.optionalFieldOf("loop", false)
                    .forGetter(AnimationDefinition::looping),
                channelWithPartCodec().listOf().xmap(
                    DTNAnimationCodec::channelWithPartListToMap,
                    DTNAnimationCodec::channelWithPartListFromMap
                ).fieldOf("channels").forGetter(AnimationDefinition::boneAnimations)
            )
            .apply(builder, AnimationDefinition::new)
        );

    private static Map<String, List<AnimationChannel>> 
        channelWithPartListToMap(List<ChannelWithPart> channelsWithParts) {
        
        var channel_by_part = new HashMap<String, List<AnimationChannel>>();
        channelsWithParts.forEach(channelsWithPart -> {
            channel_by_part.computeIfAbsent(channelsWithPart.part(), k -> new ArrayList<>())
                .add(channelsWithPart.channel());
        });
        return channel_by_part;
    }
    private static List<ChannelWithPart> channelWithPartListFromMap(
        Map<String, List<AnimationChannel>> map) {
        
        var ret = new ArrayList<ChannelWithPart>();
        for (var entry : map.entrySet()) {
            var part = entry.getKey();
            var channels = entry.getValue();
            for (var channel : channels) {
                ret.add(new ChannelWithPart(part, channel));
            }
        }
        
        return ret;
    }

    private static final ImmutableBiMap<String, AnimationChannel.Target>
        CHANNEL_TYPE_BY_ID = ImmutableBiMap.of(
            "position", AnimationChannel.Targets.POSITION,
            "rotation", AnimationChannel.Targets.ROTATION,
            "scale", AnimationChannel.Targets.SCALE
        );
    private static final AnimationChannel.Target getChannelTypeFromId(String id) {
        return CHANNEL_TYPE_BY_ID.get(id);
    }
    private static final String getIdFromChannelType(AnimationChannel.Target interp) {
        return CHANNEL_TYPE_BY_ID.inverse().get(interp);
    }
    private record ChannelWithPart(String part, AnimationChannel channel) {
        public AnimationChannel.Target type() { return channel.target(); }
        public List<Keyframe> keyframesForEncode() { 
            return processChannelWhenEncode(channel); 
        }
        public static ChannelWithPart of(String part, AnimationChannel.Target type, 
            List<Keyframe> keyframes) {
            return new ChannelWithPart(part, 
                processChannelWhenDecode(type, keyframes));
        }
    }
    private static record KeyframeProcessor(
        KeyframeValueFunction whenDecode, KeyframeValueFunction whenEncode
    ) {
        public static KeyframeProcessor of(KeyframeValueFunction whenDecode, 
            KeyframeValueFunction whenEncode) {
            return new KeyframeProcessor(whenDecode, whenEncode);
        }
    }
    private static final ImmutableMap<AnimationChannel.Target, KeyframeProcessor>
        KEYFRAME_PROCESSORS = ImmutableMap.of(
            
            AnimationChannel.Targets.POSITION, 
            KeyframeProcessor.of(KeyframeAnimations::posVec, KeyframeAnimations::posVec),
            
            AnimationChannel.Targets.ROTATION,
            KeyframeProcessor.of(KeyframeAnimations::degreeVec, DTNAnimationCodec::invertedRotationVec),

            AnimationChannel.Targets.SCALE,
            KeyframeProcessor.of(KeyframeAnimations::scaleVec, DTNAnimationCodec::invertedScaleVec)
        
        );
    private static AnimationChannel processChannelWhenDecode(
        AnimationChannel.Target type, List<Keyframe> rawKeyframes) {

        final var keyframe_processor = KEYFRAME_PROCESSORS.get(type);
        final boolean fix_rotation = type == AnimationChannel.Targets.ROTATION;

        var keyframes = new ArrayList<Keyframe>(rawKeyframes.size());
        for (var raw_keyframe : rawKeyframes) {
            var value = new org.joml.Vector3f(raw_keyframe.preTarget());
            
            value = keyframe_processor.whenDecode()
                .apply(value.x, value.y, value.z);
            
            if (fix_rotation)
                value = new Vector3f(-value.x, -value.y, value.z);
            
            var keyframe = 
                new Keyframe(raw_keyframe.timestamp(), value, raw_keyframe.interpolation());
            keyframes.add(keyframe);
        }
        return new AnimationChannel(type, keyframes.toArray(Keyframe[]::new));
    }
    private static List<Keyframe> processChannelWhenEncode(AnimationChannel channel) {
        var type = channel.target();
        final var keyframe_processor = KEYFRAME_PROCESSORS.get(type);
        final boolean fix_rotation = type == AnimationChannel.Targets.ROTATION;

        var keyframes = channel.keyframes(); 
        var raw_keyframes = new ArrayList<Keyframe>(keyframes.length);
        for (var keyframe : keyframes) {
            var value = new org.joml.Vector3f(keyframe.preTarget());

            if (fix_rotation)
                value = new Vector3f(-value.x, -value.y, value.z);
            
            value = keyframe_processor.whenEncode()
                .apply(value.x, value.y, value.z);

            value = sanitizeWhenEncode(value);
            
            var raw_keyframe = 
                new Keyframe(keyframe.timestamp(), value, keyframe.interpolation());
            raw_keyframes.add(raw_keyframe);
        }
        return raw_keyframes;
    }
    private static Codec<ChannelWithPart> channelWithPartCodec() {
        return RecordCodecBuilder.create(
            builder -> builder.group(
                Codec.STRING.fieldOf("part")
                    .forGetter(ChannelWithPart::part),
                Codec.STRING
                    .xmap(
                        DTNAnimationCodec::getChannelTypeFromId, 
                        DTNAnimationCodec::getIdFromChannelType
                    )
                    .fieldOf("type")
                    .forGetter(ChannelWithPart::type),
                rawKeyframeCodec().listOf()
                    .fieldOf("keyframes")
                    .forGetter(ChannelWithPart::keyframesForEncode)
            )
            .apply(builder, ChannelWithPart::of)
        );
    }

    private static final ImmutableBiMap<String, AnimationChannel.Interpolation>
        INTERP_BY_ID = ImmutableBiMap.of(
            "linear", AnimationChannel.Interpolations.LINEAR,
            "catmullrom", AnimationChannel.Interpolations.CATMULLROM
        );
    private static final AnimationChannel.Interpolation getInterpFromId(String id) {
        return INTERP_BY_ID.get(id);
    }
    private static final String getIdFromInterp(AnimationChannel.Interpolation interp) {
        return INTERP_BY_ID.inverse().get(interp);
    }
    
    private static Codec<Keyframe> rawKeyframeCodec() {
        return RecordCodecBuilder.create(
            builder -> builder.group(
                Codec.FLOAT.fieldOf("at")
                    .forGetter(Keyframe::timestamp),
                ExtraCodecs.VECTOR3F.optionalFieldOf("value", new Vector3f())
                    .forGetter(Keyframe::preTarget),
                Codec.STRING.xmap(
                    DTNAnimationCodec::getInterpFromId, 
                    DTNAnimationCodec::getIdFromInterp
                ).fieldOf("interp").forGetter(Keyframe::interpolation)
            )
            .apply(builder, Keyframe::new)
        );
    }

    public static Vector3f invertedRotationVec(float x, float y, float z) {
        return new Vector3f(x, y, z).mul(Mth.RAD_TO_DEG);
    }
    public static Vector3f invertedScaleVec(float x, float y, float z) {
        return new Vector3f(x, y, z).add(1, 1, 1);
    }
    private static Vector3f sanitizeWhenEncode(Vector3f vec) {
        vec = zeroSanitize(vec);
        vec = round(vec);
        return vec;
    }
    private static Vector3f zeroSanitize(Vector3f vec) {
        return new Vector3f(
            Mth.equal(vec.x(), 0) ? 0 : vec.x(),
            Mth.equal(vec.y(), 0) ? 0 : vec.y(),
            Mth.equal(vec.z(), 0) ? 0 : vec.z()
        );
    }
    private static Vector3f round(Vector3f vec) {
        final float rounding_mul = 100f;
        return new Vector3f(
            Math.round(vec.x * rounding_mul)/rounding_mul,
            Math.round(vec.y * rounding_mul)/rounding_mul,
            Math.round(vec.z * rounding_mul)/rounding_mul
        );
    }

    @FunctionalInterface
    private static interface KeyframeValueFunction {
        public Vector3f apply(float x, float y, float z);
    }
}
