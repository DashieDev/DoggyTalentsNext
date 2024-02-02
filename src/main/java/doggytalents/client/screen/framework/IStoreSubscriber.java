package doggytalents.client.screen.framework;

import java.util.List;

public interface IStoreSubscriber {
    
    void onStoreUpdated(List<Class<? extends AbstractSlice>> changedSlices);

}
