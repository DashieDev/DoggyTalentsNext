package doggytalents.client.screen.framework;

import java.util.Map;

import com.google.common.collect.Maps;

import doggytalents.ChopinLogger;
import net.minecraft.client.gui.screens.Screen;

public class Store {
    
    private static Store INSTANCE;

    //Re-render listener.
    private final Screen screen;

    private final Map<Class<? extends AbstractSlice>, StoreValue> applicationStates
        = Maps.newConcurrentMap();

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

    /**
     * A "dispatch" only happens with user interactions.
     * @param <T>
     * @param slice
     * @param action
     */
    public <T extends AbstractSlice> void dispatch(Class<T> slice, UIAction action, 
        int widthAfter, int heightAfter) {
        var storeValue = this.applicationStates.get(slice);
        if (storeValue == null) return;
        storeValue.state = storeValue.worker.reducer(storeValue.state, action);
        //ChopinLogger.l("Dispatched action: [" + action.type  + "] to ["
        //    + slice.getSimpleName() + "] with payload [" + action.payload +"]."); 
        this.screen.init(
            this.screen.getMinecraft(), 
            widthAfter, 
            heightAfter
        );
    }

    public <T extends AbstractSlice> void dispatch(Class<T> slice, UIAction action) {
        dispatch(slice, action, this.screen.width, this.screen.height);
    }

    public <T extends AbstractSlice> void dispatchAll(UIAction action, 
        int widthAfter, int heightAfter) {
        for (var entry : this.applicationStates.entrySet()) {
            var storeValue = entry.getValue();
            if (storeValue == null) return;
            storeValue.state = storeValue.worker.reducer(storeValue.state, action);
        }
        //ChopinLogger.l("Dispatched action: [" + action.type  + "] to all slices with payload [" + action.payload +"]."); 
        
        this.screen.init(
            this.screen.getMinecraft(), 
            widthAfter, 
            heightAfter
        );
    }

    public <T extends AbstractSlice> void dispatchAll(UIAction action) {
        dispatchAll(action, this.screen.width, this.screen.height);
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
        } else if (screen != INSTANCE.screen) {
            INSTANCE = new Store(screen);
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
