package doggytalents.common.network.packet.data;

public class DogIncapMsgData {
    
    public static class Request extends DogData {

        public Request(int entityId) {
            super(entityId);
        }
        
    }

    public static class Response  extends DogData{
        public String msg = "";
        public Response(String msg, int id) {
            super(id);
            this.msg = msg;
        }
    }

}
