package doggytalents.common.entity.anim;

import doggytalents.common.util.Util;

public class DogAnimationState {
    private long lastTimeMillis = 0;
    private long accumulatedTimeMillis;
    private boolean started = false;

    public void start(int startTick) {
        started = true;
        this.lastTimeMillis = Util.tickMayWithPartialToMillis(startTick);
        this.accumulatedTimeMillis = 0L;
    }

    public void stop() {
        started = false;
    }

    public void updateTime(float timestampTickWithPartial, float speedModifier) {
        if (this.isStarted()) {
            long i = Util.tickMayWithPartialToMillis(timestampTickWithPartial);
            this.accumulatedTimeMillis += getTimeForAccumulate(i, speedModifier);
            this.lastTimeMillis = i;
        }
    }

    private long getTimeForAccumulate(long timestampMillis, float speedModifier) {
        long dtime = timestampMillis - lastTimeMillis;
        double ret = ((double)dtime)*speedModifier;
        return (long) ret;
    }

    public long getAccumulatedTimeMillis() {
        return this.accumulatedTimeMillis;
    }

    public boolean isStarted() {
        return started;
    }
}
