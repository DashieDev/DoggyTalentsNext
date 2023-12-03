package doggytalents.client.screen.framework;

public class UIAction {
    public String type;
    public Object payload;
    public Class<? extends AbstractSlice> targetSlice;

    public UIAction (String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
