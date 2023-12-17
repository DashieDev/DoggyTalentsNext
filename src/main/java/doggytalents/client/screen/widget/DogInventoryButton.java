package doggytalents.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

public class DogInventoryButton extends AbstractButton {

    private Screen parent;
    private int baseX;

    private final Tooltip TOOLTIP_ACTIVE = 
        Tooltip.create(Component.translatable("container.doggytalents.dog_inventories.link"));
    
    private final Tooltip TOOLTIP_NO_ACTIVE = 
        Tooltip.create(Component.translatable("container.doggytalents.dog_inventories.link")
            .withStyle(ChatFormatting.RED));

    public DogInventoryButton(int x, int y, Screen parentIn) {
        super(x, y, 13, 10, Component.literal(""));
        this.baseX = x;
        this.parent = parentIn;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        if (this.parent instanceof CreativeModeInventoryScreen) {
            var cscreen = ((CreativeModeInventoryScreen) this.parent);
            this.visible = cscreen.isInventoryOpen();
            this.active = this.visible;
        }

        if (this.parent instanceof InventoryScreen) {
            RecipeBookComponent recipeBook = ((InventoryScreen) this.parent).getRecipeBookComponent();
            if (recipeBook.isVisible()) {
                this.setX(this.baseX + 77);
            } else {
                this.setX(this.baseX);
            }
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

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
       Minecraft mc = Minecraft.getInstance();
       int i = this.getTextureY();
       RenderSystem.enableBlend();
       RenderSystem.defaultBlendFunc();
       RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
       graphics.blit(Resources.SMALL_WIDGETS, this.getX(), this.getY(), 0, 36 + i * 10, this.width, this.height);
       //TODO : 1.19.4 ???
       //this.renderBg(stack, mc, mouseX, mouseY);

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
    public void onPress() {
        var mc = Minecraft.getInstance();
        if (mc.screen instanceof AbstractContainerScreen container) {
            var menu = container.getMenu();
            if (menu != null)
                menu.setCarried(ItemStack.EMPTY);
        }
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
        this.active = false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

}
