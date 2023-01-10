package doggytalents.common.util.doggyasynctask;

import java.util.ArrayList;

import doggytalents.common.util.doggyasynctask.promise.AbstractPromise;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import static doggytalents.common.util.doggyasynctask.promise.AbstractPromise.State.*;

/**
 * @author DashieDev
 */
public class DogAsyncTaskManager {
    
    private static final ArrayList<AbstractPromise> PROMISES = new ArrayList<AbstractPromise>();

    private static final ArrayList<AbstractPromise> TO_REMOVE = new ArrayList<AbstractPromise>();

    public static void tick() {
        if (PROMISES.isEmpty()) return;
        for (var p : PROMISES) {
            switch (p.getState()) {
                case PENDING : {
                    p.setState(RUNNING); 
                    p.start();
                    break;
                }
                case RUNNING : {
                    p.tick(); break;
                }
                case FULFILLED : {
                    p.onFulfilled(); 
                    TO_REMOVE.add(p);
                    break;
                }
                case REJECTED : {
                    p.onRejected(); 
                    TO_REMOVE.add(p);
                    break;
                }
            }
        }
        cleanUp();
    }

    public static void cleanUp() {
        if (TO_REMOVE.isEmpty()) return;
        for (var p : TO_REMOVE) {
            p.cleanUp();
            PROMISES.remove(p);
        }
        TO_REMOVE.clear();
    }

    public static void forceStop() {
        for (var p : PROMISES) {
            p.forceReject();
        }
        tick();
    }

    public static void addPromise(AbstractPromise promise) {
        PROMISES.add(promise);
    }

    public static boolean addPromiseWithOwner(AbstractPromise promise, ServerPlayer owner) {
        for (var p : PROMISES) {
            if (p.getOwner() == owner) {
                return false;
            }
        }
        promise.setOwner(owner);
        addPromise(promise);
        return true;
    }

}
