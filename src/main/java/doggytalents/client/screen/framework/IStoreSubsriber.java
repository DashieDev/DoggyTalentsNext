package doggytalents.client.screen.framework;

import java.util.List;

import net.minecraft.client.gui.components.events.GuiEventListener;

public interface IStoreSubsriber {
    
    public void onStoreUpdate(List<Class<? extends AbstractSlice>> changedSlices);

    public boolean isChildrenOf(GuiEventListener listener);

}
