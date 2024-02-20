package doggytalents.client.screen.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.OpenDogScreenData;
import doggytalents.common.network.packet.data.OpenDogScreenData.ScreenType;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

public class DogInventoryButton extends AbstractButton {

    private Screen parent;
    private int baseX;
    private boolean openSingle = false;
    private Optional<Dog> openSingleDog = Optional.empty();
    private Font font;

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
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.parent instanceof CreativeModeInventoryScreen) {
            int tabIndex = ((CreativeModeInventoryScreen) this.parent).getSelectedTab();
            this.visible = tabIndex == CreativeModeTab.TAB_INVENTORY.getId();
            this.active = this.visible;
        }

        if (this.parent instanceof InventoryScreen) {
            RecipeBookComponent recipeBook = ((InventoryScreen) this.parent).getRecipeBookComponent();
            if (recipeBook.isVisible()) {
                this.x = this.baseX + 77;
            } else {
                this.x = this.baseX;
            }
        }

        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            List<Dog> dogs = mc.level.getEntitiesOfClass(Dog.class, mc.player.getBoundingBox().inflate(12D, 12D, 12D),
                (dog) -> dog.canInteract(mc.player) && PackPuppyTalent.hasInventory(dog)
            );
            this.active = !dogs.isEmpty();
        }

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderButton(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.setShaderTexture(0, Resources.SMALL_WIDGETS);
       Minecraft mc = Minecraft.getInstance();
       int i = this.getYImage(this.isHoveredOrFocused());
       RenderSystem.enableBlend();
       RenderSystem.defaultBlendFunc();
       RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
       this.blit(stack, this.x, this.y, 0, 36 + i * 10, this.width, this.height);
       if (this.openSingle) {
            int tX = this.getX() + 11;
            int tY = this.getY() + 5;
            font.draw(stack, "x1", tX, tY, 0xffffffff);
       }
    }

    @Override
    public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
        if (this.active) {
            Component msg = Component.translatable("container.doggytalents.dog_inventories.link");
            this.parent.renderTooltip(stack, msg, mouseX, mouseY);
        } else {
            Component msg = Component.translatable("container.doggytalents.dog_inventories.link").withStyle(ChatFormatting.RED);
            this.parent.renderTooltip(stack, msg, mouseX, mouseY);
        }
    }

    @Override
    public void onPress() {
        var mc = Minecraft.getInstance();
        if (mc.screen instanceof AbstractContainerScreen container) {
            var menu = container.getMenu();
            if (menu != null)
                menu.setCarried(ItemStack.EMPTY);
        }
        if (openSingle && openSingleDog.isPresent()) {
            var dog = openSingleDog.get();
            PacketHandler.send(PacketDistributor.SERVER.noArg(), 
                new OpenDogScreenData(ScreenType.INVENTORY_SINGLE, dog.getId()));
        } else {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new OpenDogScreenData());
        }
        
        this.active = false;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
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

    private int getX() {return this.x;}
    private int getY() {return this.y;}

}
