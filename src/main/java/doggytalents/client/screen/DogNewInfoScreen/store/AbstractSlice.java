package doggytalents.client.screen.DogNewInfoScreen.store;

public abstract interface AbstractSlice {
    
    public Object getInitalState();

    public Object reducer(Object oldData, UIAction action);

}
