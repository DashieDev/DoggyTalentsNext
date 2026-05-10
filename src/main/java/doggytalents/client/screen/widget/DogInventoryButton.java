package doggytalents.client.screen.widget;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.network.packet.data.OpenDogScreenData.ScreenType;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ScreenEvent;
import doggytalents.common.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

public class DogInventoryButton extends AbstractButton {

    private Screen parent;
    private int baseX;
    private boolean openSingle = false;
    private Optional<Dog> openSingleDog = Optional.empty();
    private Font font;

    private final Tooltip TOOLTIP_ACTIVE = 
        Tooltip.create(Component.translatable("container.doggytalents.dog_inventories.link"));
    
    private final Tooltip TOOLTIP_NO_ACTIVE = 
        Tooltip.create(Component.translatable("container.doggytalents.dog_inventories.link")
            .withStyle(ChatFormatting.RED));

    public DogInventoryButton(int x, int y, Screen parentIn) {
        super(x, y, 13, 10, Component.literal(""));
        this.baseX = x;
        this.parent = parentIn;
        this.font = Minecraft.getInstance().font;
    }

    public DogInventoryButton(int x, int y, Screen parentIn, Dog dog) {
        super(x, y, 13, 10, Component.literal(""));
        this.baseX = x;
        this.parent = parentIn;
        this.openSingleDog = Optional.of(dog);
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {

        if (this.parent instanceof CreativeModeInventoryScreen) {
            var cscreen = ((CreativeModeInventoryScreen) this.parent);
            this.visible = cscreen.isInventoryOpen();
            this.active = this.visible;
        }

        if (this.parent instanceof InventoryScreen) {
            this.setX(this.baseX);
        }

        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            List<Dog> dogs = mc.level.getEntitiesOfClass(Dog.class, mc.player.getBoundingBox().inflate(12D, 12D, 12D),
                (dog) -> dog.canInteract(mc.player) && PackPuppyTalent.hasInventory(dog)
            );
            this.active = !dogs.isEmpty();
            if (this.active) {
                this.setTooltip(TOOLTIP_ACTIVE);
            } else {
                this.setTooltip(TOOLTIP_NO_ACTIVE);
            }
        }

        renderWidget2(graphics, mouseX, mouseY, partialTicks);
    }

    //@Override
    public void renderWidget2(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
       int i = this.getTextureY();
       graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.SMALL_WIDGETS, this.getX(), this.getY(), 0.0F, (float)(36 + i * 10), this.width, this.height, 256, 256);
       //this.renderBg(stack, mc, mouseX, mouseY);
       if (this.openSingle) {
            int tX = this.getX() + 11;
            int tY = this.getY() + 5;
            graphics.text(font, "x1", tX, tY, 0xffffffff);
       }

    }
    
    private int getTextureY() {
        int i = 1;
        if (!this.active) {
           i = 0;
        } else if (this.isHoveredOrFocused()) {
           i = 2;
        }
  
        return i;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        var mc = Minecraft.getInstance();
        if (openSingle && openSingleDog.isPresent()) {
            var dog = openSingleDog.get();
            PacketHandler.send(PacketDistributor.SERVER.noArg(), 
                new OpenDogScreenData(ScreenType.INVENTORY_SINGLE, dog.getId()));
        } else {
            boolean do_close_container = 
                mc.screen instanceof AbstractContainerScreen container
                && container.getMenu() == mc.player.inventoryMenu;
            PacketHandler.send(PacketDistributor.SERVER.noArg(), 
                OpenDogScreenData.dogInventory(do_close_container));
        }
        
        this.active = false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

    public void keyGlobalPressed(int keyCode, int scanCode, int modifier) {
        var mc = Minecraft.getInstance();
        var shiftKey = mc.options.keyShift;
        if (shiftKey.getKey().getValue() == keyCode && this.openSingleDog.isPresent()) {
            this.openSingle = true;
        }
    }

    public void keyGlobalReleased(int keyCode, int scanCode, int modifier) {
        this.openSingle = false;
    }

    private static DogInventoryButton inventoryButton;

    public static void onScreenInit(final ScreenEvent.Init.Post event) {
        var screen = event.getScreen();
        boolean is_survival = screen != null 
            && screen.getClass() == InventoryScreen.class;
        if (!is_survival)
            return;

        if (!ConfigHandler.CLIENT.DOG_INV_BUTTON_IN_INV.get()) 
            return;
        
        var mc = Minecraft.getInstance();
    
        int screen_w = mc.getWindow().getGuiScaledWidth();
        int screen_h = mc.getWindow().getGuiScaledHeight(); 
        int inv_w = 176;
        int inv_h = 166;
        int inv_x = (screen_w - inv_w) / 2;
        int inv_y = (screen_h - inv_h) / 2;

        final int button_x_offset = ConfigHandler.CLIENT.DOG_INV_IN_INV_BUTTON_X.get(); 
        final int button_y_offset = ConfigHandler.CLIENT.DOG_INV_IN_INV_BUTTON_Y.get();
        int button_x = inv_x + button_x_offset;
        int button_y = inv_y + button_y_offset;
        
        inventoryButton = new DogInventoryButton(button_x, button_y, screen);
        event.addListener(inventoryButton);
    }

    public static void onScreenRenderForeground(final ScreenEvent.Render.Post event) {
        
    }

}
