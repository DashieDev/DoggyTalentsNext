package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.OneLineLimitedTextArea;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DebugView extends AbstractElement {

    public DebugView(AbstractElement parent, Screen screen) {
        super(parent, screen);
    }

    @Override
    public AbstractElement init() {

        final var char_counter = useState(0);
        final var edit_box = useRef((EditBox) null);
        if (edit_box.value == null) {
            var edit_box_val = new EditBox(Minecraft.getInstance().font, 
            getRealX(), getRealY() + 30, 120, 30, Component.literal(""));
            edit_box_val.setResponder(new_str -> {
                char_counter.update(new_str.length());
            });
            edit_box.value = edit_box_val;
        }

        var counter_view = new OneLineLimitedTextArea(getRealX(), getRealY(), 120, 
            Component.literal("" + char_counter.value()));

        addChildren(counter_view);
        addPersistentChildren(edit_box.value);
        
        // final var counter = useState(0);
        // final var counter2 = useState(19);
        
        // var counter_view = new OneLineLimitedTextArea(getRealX(), getRealY(), 120, 
        //     Component.literal("" + counter.value()));
        // var counter2_view = new OneLineLimitedTextArea(getRealX() + 40, getRealY(), 120, 
        //     Component.literal("" + counter2.value()));
        // var plus_button = new FlatButton(getRealX(), getRealY() + 40, 20, 20, 
        //     Component.literal("+"), b -> {
        //         counter.update(counter.value() + 1);
        //     });
        // var minus_button = new FlatButton(getRealX() + 30, getRealY() + 40, 20, 20, 
        //     Component.literal("-"), b -> {
        //         counter2.update(counter2.value() - 1);
        //     });
        
        // addChildren(counter_view);
        // addChildren(counter2_view);
        // addChildren(plus_button);
        // addChildren(minus_button);
        

        return super.init();
    }
    
}
