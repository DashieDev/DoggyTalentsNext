package doggytalents.forge_imitate.event;

public class Event {
    
    private boolean cancelled;

    public Event() {

    }

    public void setCancelled() {
        this.cancelled = true;
    }

    public void setCanceled(boolean val) {
        this.cancelled = val;
    }

    public boolean isCanceled() {
        return this.cancelled;
    }

}
