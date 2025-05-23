package doggytalents.common.util;

import com.google.common.collect.AbstractIterator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;

public class RingSearchIterator extends AbstractIterator<BlockPos> {

    private final int range;
    private final BlockPos center;
    private final int yRange;
    private final boolean inflatingY;

    private int inflate = 0;
    private int index = 0;
    private int maxIndex = 0;
    private final MutableBlockPos cursor = new MutableBlockPos();

    private static final Vec3i[] STAGE_MOVES = {
        new Vec3i(1, 0, 0),
        new Vec3i(0, 0, 1),
        new Vec3i(-1, 0, 0),
        new Vec3i(0, 0, -1)
    };

    public static Iterable<BlockPos> create(BlockPos center, int y, int range, boolean inflatingY) {
        return () -> new RingSearchIterator(center, y, range, inflatingY);
    }

    private RingSearchIterator(BlockPos center, int y, int range, boolean inflatingY) {
        this.range = range;
        this.yRange = y;
        this.center = center;
        this.inflatingY = inflatingY;
        this.cursor.set(0, 0, 0);
    }

    @Override
    protected BlockPos computeNext() {
        if (inflate > range) {
            return this.endOfData();
        }
        var ret = this.center.offset(this.cursor.immutable());
        int y = this.cursor.getY();
        int max_y = this.inflatingY ? Math.min(yRange, inflate) : yRange;
        if (y < max_y) {
            this.cursor.setY(y >= 0 ? -(y + 1) : -y);
            return ret;
        }

        int stage = inflate == 0 ? 0 : (this.index) / (inflate * 2);
        this.cursor.move(STAGE_MOVES[stage]).setY(0);
        ++this.index;
        if (index > maxIndex) {
            inflate += 1;
            index = 0;
            maxIndex = (inflate * 2) * 4 - 1;
            this.cursor.set(-inflate, 0, -inflate);
        }

        return ret;
    }
}
