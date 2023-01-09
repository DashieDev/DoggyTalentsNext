package doggytalents.common.util.doggyasynctask.promise;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

/**
 * @author DashieDev
 */
public abstract class AbstractPromise {

    private @Nullable ServerPlayer owner;

    private State state;

    protected String rejectedMsg = "";

    public AbstractPromise() {
        this.state = State.PENDING;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ServerPlayer getOwner() {
        return this.owner;
    }

    public void setOwner(ServerPlayer owner) {
        this.owner = owner;
    }

    public abstract void start();
    
    public abstract void tick();

    public abstract void onFulfilled();

    public abstract void onRejected();

    public abstract void cleanUp();

    public void forceReject() {
        this.setState(State.REJECTED);
        this.rejectedMsg = "FORCED";
    }

    public static enum State {
        PENDING,
        RUNNING, 
        FULFILLED,
        REJECTED
    }

}
