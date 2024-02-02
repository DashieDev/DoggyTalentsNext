package doggytalents.client.screen.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.client.gui.screens.Screen;

public class Store {
    
    private static Store INSTANCE;

    //Re-render listener.
    private final Screen screen;
    private IStoreSubscriber subscriber;

    private final Map<Class<? extends AbstractSlice>, StoreValue> applicationStates
        = Maps.newConcurrentMap();

    private final ArrayList<UIAction> dispatchedAction = new ArrayList<UIAction>();

    private Store(Screen screen) {
        this.screen = screen;
        this.registerSlices();
        this.init();
    }

    private <T extends AbstractSlice> void registerSlice(Class<T> slice) {
        try {   
            var worker = slice.getConstructor().newInstance();
            var storeValue = new StoreValue(worker, worker.getInitalState());
            this.applicationStates.computeIfAbsent(slice, $ -> storeValue);
        } catch (Exception e) {
        }
    }

    private void registerSlices() {
        if (!(this.screen instanceof StoreConnectedScreen)) return;
        var storeScr = (StoreConnectedScreen) this.screen;
        for (var slice : storeScr.getSlices()) {
            registerSlice(slice);
        }
    }

    private void init() {
        for (var x : this.applicationStates.entrySet()) {
            var initState = x.getValue().worker.getInitalState();
            x.getValue().state = initState;
        }
    }

    public <T extends AbstractSlice> void dispatch(Class<T> slice, UIAction action) {
        action.targetSlice = slice;
        this.dispatchedAction.add(action);
    }

    public <T extends AbstractSlice> void dispatchAll(UIAction action) {
        action.targetSlice = null;
        this.dispatchedAction.add(action);
    } 

    public void update() {
        if (this.dispatchedAction.isEmpty())
            return;
        var changedSlices = new ArrayList<Class<? extends AbstractSlice>>();
        for (var action : this.dispatchedAction) {
            processUIAction(action, changedSlices);
        }
        this.dispatchedAction.clear();
        if (!changedSlices.isEmpty()) {
            this.subscriber.onStoreUpdated(changedSlices);
        }
    }

    private void processUIAction(UIAction action, ArrayList<Class<? extends AbstractSlice>> changedSlices) {
        var targetSlice = action.targetSlice;
        if (targetSlice == null) {
            for (var entry : this.applicationStates.entrySet()) {
                var storeValue = entry.getValue();
                if (storeValue == null) return;
                var oldState = storeValue.state;
                storeValue.state = storeValue.worker.reducer(storeValue.state, action);
                if (oldState != storeValue.state) {
                    changedSlices.add(entry.getKey());
                }
            }
            return;
        }
        var storeValue = this.applicationStates.get(targetSlice);
        if (storeValue == null) return;
        var oldState = storeValue.state;
        storeValue.state = storeValue.worker.reducer(storeValue.state, action);
        if (oldState != storeValue.state) {
            changedSlices.add(targetSlice);
        }
    }

    public <T extends Object, S extends AbstractSlice> T getStateOrDefault(
        Class<S> slice, Class<T> cast, T defaultState) {
        var storeValue = this.applicationStates.get(slice);
        if (storeValue == null) return defaultState;
        if (cast.isInstance(storeValue.state)) {
            return cast.cast(storeValue.state);
        }
        return defaultState;
    }

    public static Store get(Screen screen) {
        if (INSTANCE == null) {
            INSTANCE = new Store(screen);
            if (screen instanceof IStoreSubscriber sub)
                INSTANCE.subscriber = sub;
        } else if (screen != INSTANCE.screen) {
            INSTANCE = new Store(screen);
            if (screen instanceof IStoreSubscriber sub)
                INSTANCE.subscriber = sub;
        }
        return INSTANCE;
    }

    private static void cleanUpStaticCache() {
        if (INSTANCE == null) return;
        for (var entry : INSTANCE.applicationStates.entrySet()) {
            var slice = entry.getValue().worker;
            if (slice instanceof CleanableSlice cSlice) {
                cSlice.cleanUpSlice();
            }
        }
    }

    public static void finish() {
        cleanUpStaticCache();
        INSTANCE = null;
        ToolTipOverlayManager.finish();
    }

    private static class StoreValue {
        public AbstractSlice worker;
        public Object state;

        public StoreValue(AbstractSlice worker, Object state) {
            this.worker = worker;
            this.state = state;
        }
    }

}
