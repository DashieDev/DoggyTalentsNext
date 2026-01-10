package doggytalents.client.entity.model.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.util.ExtraCodecs;

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
                Codec.BOOL.fieldOf("loop")
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
            "rotation", AnimationChannel.Targets.ROTATION
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
            return fixChannelWhenEncode(channel); 
        }
        public static ChannelWithPart of(String part, AnimationChannel.Target type, 
            List<Keyframe> keyframes) {
            return new ChannelWithPart(part, 
                fixChannelWhenDecode(type, keyframes));
        }
    }
    private static AnimationChannel fixChannelWhenDecode(
        AnimationChannel.Target type, List<Keyframe> keyframes) {
        
        if (type != AnimationChannel.Targets.ROTATION)
            return new AnimationChannel(type, keyframes.toArray(Keyframe[]::new));

        var new_keyframes = keyframes.stream()
            .map(kf -> {
                var old_value = kf.target();
                var new_value = new Vector3f(-old_value.x(), -old_value.y(), old_value.z());
                return new Keyframe(kf.timestamp(), new_value, kf.interpolation());
            })
            .toArray(Keyframe[]::new);
        return new AnimationChannel(type, new_keyframes);
    }
    private static List<Keyframe> fixChannelWhenEncode(AnimationChannel channel) {
        var type = channel.target();
        if (type != AnimationChannel.Targets.ROTATION)
            return Arrays.asList(channel.keyframes());
        
        return Arrays.stream(channel.keyframes())
            .map(kf -> {
                var old_value = kf.target();
                var new_value = new Vector3f(-old_value.x(), -old_value.y(), old_value.z());
                return new Keyframe(kf.timestamp(), new_value, kf.interpolation());
            })
            .collect(Collectors.toList());
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
                keyframeCodec().listOf()
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
    private static Codec<Keyframe> keyframeCodec() {
        return RecordCodecBuilder.create(
            builder -> builder.group(
                Codec.FLOAT.fieldOf("at")
                    .forGetter(Keyframe::timestamp),
                ExtraCodecs.VECTOR3F.optionalFieldOf("value", new Vector3f())
                    .forGetter(Keyframe::target),
                Codec.STRING.xmap(
                    DTNAnimationCodec::getInterpFromId, 
                    DTNAnimationCodec::getIdFromInterp
                ).fieldOf("interp").forGetter(Keyframe::interpolation)
            )
            .apply(builder, Keyframe::new)
        );
    }
}
