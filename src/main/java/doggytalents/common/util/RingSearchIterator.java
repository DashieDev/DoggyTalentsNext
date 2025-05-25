package doggytalents.common.util;

import com.google.common.collect.AbstractIterator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class RingSearchIterator extends AbstractIterator<BlockPos> {

    private final int range;
    private final BlockPos center;
    private final int yRange;
    private final boolean inflatingY;
    private final float startProgress;

    private int inflate = 0;
    private int startIndex = 0;
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
        return () -> new RingSearchIterator(center, y, range, inflatingY, 0);
    }

    public static Iterable<BlockPos> createWithRandom(BlockPos center, int y, int range, boolean inflatingY, RandomSource rand) {
        float r = rand.nextFloat();
        return () -> new RingSearchIterator(center, y, range, inflatingY, r);
    }

    private RingSearchIterator(BlockPos center, int y, int range, boolean inflatingY, float startProgress) {
        this.range = range;
        this.yRange = y;
        this.center = center;
        this.inflatingY = inflatingY;
        this.cursor.set(0, 0, 0);
        this.startProgress = startProgress;
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
        advanceIndex();
        if (index == startIndex) {
            inflate += 1;
            maxIndex = (inflate * 2) * 4 - 1;
            this.startIndex = Mth.clamp(
                Mth.floor(this.startProgress * (maxIndex + 1)),
                0, maxIndex);
            index = this.startIndex;
            this.cursor.set(-inflate, 0, -inflate);
            moveToStartIndex();
        }

        return ret;
    }

    private void moveToStartIndex() {
        if (startIndex <= 0)
            return;
        int stage = startIndex / (inflate * 2);
        int odd_move = startIndex % (inflate * 2);
        for (int i = 0; i < stage; ++i) {
            this.cursor.move(STAGE_MOVES[i].multiply(inflate * 2));
        }
        this.cursor.move(STAGE_MOVES[stage].multiply(odd_move));
    }

    private void advanceIndex() {
        ++index;
        if (index > maxIndex)
            index = 0; 
    }
}
