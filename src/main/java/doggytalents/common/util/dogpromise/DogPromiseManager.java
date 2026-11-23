package doggytalents.common.util.dogpromise;

import static doggytalents.common.util.dogpromise.promise.AbstractPromise.State.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import doggytalents.common.util.dogpromise.promise.AbstractPromise;
import net.minecraft.server.level.ServerPlayer;

/**
 * @author DashieDev
 */
public class DogPromiseManager {
    
    private static final ArrayList<AbstractPromise> PENDING_ADD = new ArrayList<AbstractPromise>();
    private static final ArrayList<AbstractPromise> TICKING = new ArrayList<AbstractPromise>();
    private static final ArrayList<AbstractPromise> TO_REMOVE = new ArrayList<AbstractPromise>();

    private static Stream<AbstractPromise> getAllActivePromise() {
        return Streams.concat(PENDING_ADD.stream(), TICKING.stream());
    }

    public static void tick() {
        
        if (!PENDING_ADD.isEmpty()) {
            TICKING.addAll(PENDING_ADD);
            PENDING_ADD.clear();
        }
        if (TICKING.isEmpty()) return;
        for (var p : TICKING) {
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

    private static void cleanUp() {
        if (TO_REMOVE.isEmpty()) return;
        
        TO_REMOVE.forEach(AbstractPromise::doCleanUp);
        TICKING.removeAll(TO_REMOVE);
        TO_REMOVE.clear();
    }

    public static void onServerStop() {
        getAllActivePromise().forEach(p -> {
            p.forceReject();
            p.doCleanUp();
        });

        PENDING_ADD.clear();
        TICKING.clear();
        TO_REMOVE.clear();
    }

    public static void addPromise(AbstractPromise promise) {
        PENDING_ADD.add(promise);
    }

    public static boolean addPromiseWithOwner(AbstractPromise promise, ServerPlayer owner) {
        final boolean owner_already_has_promise = getAllActivePromise()
            .anyMatch(p -> p.getOwner() == owner);
        if (owner_already_has_promise)
            return false; 
        promise.setOwner(owner);
        addPromise(promise);
        return true;
    }

    public static boolean addPromiseWithOwnerAndStartImmediately(AbstractPromise promise, ServerPlayer owner) {
        final boolean owner_already_has_promise = getAllActivePromise()
            .anyMatch(p -> p.getOwner() == owner);
        if (owner_already_has_promise)
            return false; 

        promise.setOwner(owner);
        addPromise(promise);
        
        promise.start();
        final var start_state = promise.getState();
        if (start_state == PENDING) {
            //State Machine Constraint: PENDING -> RUNNING
            promise.setState(RUNNING);
        }
        return true;
    }

}
