package doggytalents.client.screen.DogNewInfoScreen.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NavBarButton extends AbstractButton {
    protected final NavBarButton.OnPress onPress;
    private Font font;

    public NavBarButton(int x, int y, Component text, NavBarButton.OnPress onpress,
            Font font) {
        super(x, y, font.width(text), font.lineHeight, text);
        this.onPress = onpress;
        this.font = font;
    }

    public void onPress() {
        this.onPress.onPress(this);
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

    public interface OnPress {
        void onPress(NavBarButton p_93751_);
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        return; // TODO
    }
}
