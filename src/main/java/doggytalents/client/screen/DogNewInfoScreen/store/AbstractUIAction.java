package doggytalents.client.screen.DogNewInfoScreen.store;

public class AbstractUIAction {
    public String type;
    public Object payload;

    public AbstractUIAction (String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
