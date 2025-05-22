package doggytalents.common.util;

import com.google.common.collect.AbstractIterator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;

public class RingSearchIterator extends AbstractIterator<BlockPos> {

    private final int range;
    private final BlockPos center;
    private final int yRange;

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

    public static Iterable<BlockPos> create(BlockPos center, int y, int range) {
        return () -> new RingSearchIterator(center, y, range);
    }

    private RingSearchIterator(BlockPos center, int y, int range) {
        this.range = range;
        this.yRange = y;
        this.center = center;
        this.cursor.set(0, -this.yRange, 0);
    }

    @Override
    protected BlockPos computeNext() {
        if (inflate > range) {
            return this.endOfData();
        }
        var ret = this.center.offset(this.cursor.immutable());
        if (this.cursor.getY() < yRange) {
            this.cursor.move(0, 1, 0);
            return ret;
        }

        int stage = inflate == 0 ? 0 : (this.index) / (inflate * 2);
        this.cursor.move(STAGE_MOVES[stage]).setY(-yRange);
        ++this.index;
        if (index > maxIndex) {
            inflate += 1;
            index = 0;
            maxIndex = (inflate * 2) * 4 - 1;
            this.cursor.set(-inflate, -this.yRange, -inflate);
        }

        return ret;
    }
}
