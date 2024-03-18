package doggytalents.forge_imitate.event;

public class TagsUpdatedEvent extends Event {
    
    public static enum UpdateCause {
        CLIENT_PACKET_RECEIVED, SERVER
    }    

    private UpdateCause cause;
    
    public TagsUpdatedEvent(UpdateCause cause) {
        this.cause = cause;
    }

    public UpdateCause getUpdateCause() {
        return this.cause;
    }

}
