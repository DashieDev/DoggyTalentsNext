package doggytalents.client.screen.framework;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.element.ElementPosition.PosType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;

public class DropdownMenuManager extends AbstractElement {

    private DropdownMenuManager(Screen screen) { super(null, screen); }

    private static DropdownMenuManager INSTANCE;
    public static DropdownMenuManager get(Screen screen) {
        if (INSTANCE == null) {
            INSTANCE = new DropdownMenuManager(screen);
        } else if (INSTANCE.getScreen() != screen) {
            INSTANCE = new DropdownMenuManager(screen);
        }
        return INSTANCE;
    }
    public static void finish() {
        INSTANCE = null;
    }

    private AbstractElement activeDropdownMenu;

    public void setActiveDropdownMenu(Screen screen, int x, int y, 
        int sizeX, int sizeY, AbstractElement dropdown) {
        dropdown.children().clear();
        
        if (x + sizeX > screen.width) {
            x = screen.width - sizeX;
        }
        if (y + sizeY > screen.height) {
            y = screen.height - sizeY;
        }

        if (x < 0) x = 0;
        if (y < 0) y = 0;

        dropdown
            .setPosition(PosType.FIXED, x, y)
            .setSize(sizeX, sizeY)
            .init();
        
        activeDropdownMenu = dropdown;
    }

    public void clearActiveDropdownMenu() {
        this.activeDropdownMenu = null;
    }

    public boolean hasDropdownMenu() {
        return activeDropdownMenu != null;
    }

    public AbstractElement getDropdownMenu() {
        return activeDropdownMenu;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean flag) {
        if (!this.hasDropdownMenu()) return false;
        if (!this.activeDropdownMenu.isMouseOver(event.x(), event.y())) {
            this.activeDropdownMenu = null;
            return false;
        }
        activeDropdownMenu.mouseClicked(event, flag);
        return true;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (!(this.getFocused() instanceof EditBox)) {
            this.activeDropdownMenu = null;
            return false;
        }
        return super.keyPressed(event);
    }

    public void attach(Screen screen, Consumer<DropdownMenuManager> screenChildrenAdder) {
        screenChildrenAdder.accept(this);
        if (this.hasDropdownMenu()) {
            var dropdown = this.getDropdownMenu();
            this.setActiveDropdownMenu(screen, 
                dropdown.getRealX(), 
                dropdown.getRealY(), 
                dropdown.getSizeX(), 
                dropdown.getSizeY(), dropdown);
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
       
    }
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        if (!this.hasDropdownMenu()) return null;
        return this.activeDropdownMenu.getFocused();
    }
    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (!this.hasDropdownMenu()) return;
        this.activeDropdownMenu.setFocused(guiEventListener);
    }
    @Override
    public void renderElement(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {

    }
}
