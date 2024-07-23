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

    public static class MouseButton {
        public static class Pre extends Event {

            private final int button;
            private final int action;
            
            public Pre(int button, int action) {
                this.button = button;
                this.action = action;
            }

            public int getButton() {
                return this.button;
            }

            public int getAction() {
                return this.action;
            }
        }
    }

}
