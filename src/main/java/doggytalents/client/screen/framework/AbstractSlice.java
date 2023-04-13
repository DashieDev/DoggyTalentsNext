package doggytalents.client.screen.framework;

public abstract interface AbstractSlice {
    
    public Object getInitalState();

    public Object reducer(Object oldData, UIAction action);

}
