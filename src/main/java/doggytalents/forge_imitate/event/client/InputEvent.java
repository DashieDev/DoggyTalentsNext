package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;

public class InputEvent {
    
    public static class Key extends Event {
        
        public final int keyCode;
        public final int scanCode;
        private int modifers;

        public Key(int keyCode, int scanCode, int modifiers) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.modifers = modifiers;
        }



        
    }

}
