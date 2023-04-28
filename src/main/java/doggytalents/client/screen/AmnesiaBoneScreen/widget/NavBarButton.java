package doggytalents.client.screen.AmnesiaBoneScreen.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTabSlice;
import doggytalents.client.screen.AmnesiaBoneScreen.store.slice.ActiveTabSlice.Tab;
import doggytalents.client.screen.framework.Store;
import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NavBarButton extends AbstractButton {
    protected final Tab tab;
    private Font font;
    private Screen screen;
    private Dog dog;

    public NavBarButton(int x, int y, Component text, Tab tab,
            Font font, Screen screen, Dog dog) {
        super(x, y, font.width(text), font.lineHeight, text);
        this.tab = tab;
        this.font = font;
        this.screen = screen;
        this.dog = dog;
    }

    public void onPress() {
        Store.get(screen)
        //dispatch all to notify all slice of changetab so they can do setup before
        //appearing in the tab.
        .dispatchAll(
            ActiveTabSlice.UIActionCreator(dog, tab)
        );
    }

    public void renderButton(PoseStack stack, int mouseX, int mouseY, float pticks) {
        // No HightLight
        if (this.isHoveredOrFocused()) {
            var s = this.getMessage().copy();
            s.withStyle(s.getStyle().withUnderlined(true).withBold(true));
            this.font.draw(stack, s, this.x-4, this.y, 0xffffffff);

        } else {
            this.font.draw(stack, this.getMessage(), this.x, this.y, 0xffffffff);

        }

    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        return; // TODO
    }
}
