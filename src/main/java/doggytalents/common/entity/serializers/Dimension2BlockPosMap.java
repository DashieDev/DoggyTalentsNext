package doggytalents.common.entity.serializers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.*;

import com.google.common.collect.Maps;

public class Dimension2BlockPosMap {

    private final Map<ResourceKey<Level>, BlockPos> map = Maps.newHashMap();

    public Dimension2BlockPosMap() {}

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return this.map.size();
    }

    public Dimension2BlockPosMap copy() {
        var ret = new Dimension2BlockPosMap();
        ret.map.putAll(this.map);
        return ret;
    }

    public Iterable<Map.Entry<ResourceKey<Level>, BlockPos>> entrySet() {
        return this.map.entrySet();
    }

    public Optional<BlockPos> get(ResourceKey<Level> dimension) {
        return Optional.ofNullable(map.get(dimension));
    }

    public void put(ResourceKey<Level> dimension, BlockPos pos) {
        if (dimension == null)
            return;
        if (pos == null)
            this.map.remove(dimension);
        else
            this.map.put(dimension, pos);
    }

    public Dimension2BlockPosMap copyAndSet(ResourceKey<Level> dimension, Optional<BlockPos> pos) {
        if (dimension == null || pos == null)
            return this;
        var ret = copy();
        ret.put(dimension, pos.orElse(null));
        return ret;
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        if (!(obj instanceof Dimension2BlockPosMap other)) 
            return false;
        return this.map.equals(other.map);
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

}
