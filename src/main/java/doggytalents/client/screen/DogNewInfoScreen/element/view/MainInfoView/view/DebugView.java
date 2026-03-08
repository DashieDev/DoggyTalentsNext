package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.OneLineLimitedTextArea;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.util.DebugUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.StatFormatter;

public class DebugView extends AbstractElement {

    private static AbstractElement currentRendering = null;

    public DebugView(AbstractElement parent, Screen screen) {
        super(parent, screen);
    }

    @Override
    public AbstractElement init() {
        final var dog = useContextOrThrow(DogNewInfoScreen.DOG);


        final var upper_case_char_counter = useState(0);
        final var edit_box = useRef((EditBox) null);

        final UnaryOperator<Integer> expensive_method = x -> {
            DebugUtil.LOGGER.info("Upper case characters : " + x);
            return x * 2;
        };
        final var memo = useMemo(
            () -> expensive_method.apply(upper_case_char_counter.value()), 
            upper_case_char_counter.value()); 


        final UnaryOperator<Integer> test_lamda = (x) -> {
            DebugUtil.LOGGER.info("hey from debug!");
            return x * 2;
        };

        if (edit_box.value == null) {
            var edit_box_val = new EditBox(Minecraft.getInstance().font, 
            getRealX(), getRealY() + 30, 120, 30, Component.literal(""));
            edit_box_val.setResponder(new_str -> {
                upper_case_char_counter.update((int) 
                    new_str.chars().filter(Character::isUpperCase).count());
            });
            edit_box.value = edit_box_val;
        }

        var counter_view = new OneLineLimitedTextArea(getRealX(), getRealY(), 120, 
            Component.literal(" " + memo));
        
        var dog_health_view = new OneLineLimitedTextArea(getRealX(), getRealY() + 80, 120, 
            Component.literal(dog.getName().getString() + " has " 
            + StatFormatter.DECIMAL_FORMAT.format(dog.getHealth()) + "hp."));

        addChildren(counter_view);
        addChildren(dog_health_view);
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
