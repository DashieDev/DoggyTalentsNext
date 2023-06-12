package doggytalents.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.DoggyAccessories;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.screen.widget.SmallButton;
import doggytalents.common.entity.accessory.DyeableAccessory.DyeableAccessoryInstance;
import doggytalents.common.inventory.container.DogInventoriesContainer;
import doggytalents.common.inventory.container.slot.DogInventorySlot;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogInventoryPageData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;

public class DogInventoriesScreen extends AbstractContainerScreen<DogInventoriesContainer> {

    private Button left, right;
    private Player player;

    public DogInventoriesScreen(DogInventoriesContainer packPuppy, Inventory playerInventory, Component displayName) {
        super(packPuppy, playerInventory, displayName);
        this.player = playerInventory.player;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void init() {
        super.init();
        this.left = new SmallButton(this.leftPos + this.imageWidth - 29, this.topPos + 4, Component.literal("<"), (btn) -> {
            int page = this.getMenu().getPage();

            if (page > 0) {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogInventoryPageData(--page));
            }

            btn.active = page > 0;
            this.right.active = page < this.getMenu().getTotalNumColumns() - 9;
        });
        this.right = new SmallButton(this.leftPos + this.imageWidth - 26 + 9, this.topPos + 4, Component.literal(">"), (btn) -> {
            int page = this.getMenu().getPage();

            if (page < this.getMenu().getTotalNumColumns() - 9) {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogInventoryPageData(++page));
            }

            btn.active = page < this.getMenu().getTotalNumColumns() - 9;
            this.left.active = page > 0;

        });
        if (this.getMenu().getTotalNumColumns() > 9) {
            this.left.active = false;
            this.right.active = true;
        } else {
            this.left.visible = false;
            this.right.visible = false;
        }

        this.addRenderableWidget(this.left);
        this.addRenderableWidget(this.right);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int par1, int par2) {
        graphics.drawString(font, this.title.getString(), 8, 6, 4210752);
        graphics.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int xMouse, int yMouse) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int l = (this.width - this.imageWidth) / 2;
        int i1 = (this.height - this.imageHeight) / 2;
        graphics.blit(Resources.DOG_INVENTORY, l, i1, 0, 0, this.imageWidth, this.imageHeight);

        for (DogInventorySlot slot : this.getMenu().getSlots()) {
            if (!slot.isActive()) {
                continue;
            }

            Optional<AccessoryInstance> inst = slot.getDog().getAccessory(DoggyAccessories.DYEABLE_COLLAR.get());
            if (inst.isPresent()) {
                float[] color = inst.get().cast(DyeableAccessoryInstance.class).getColor();
                RenderSystem.setShaderColor(color[0], color[1], color[2], 1.0F);
            } else {
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }

            graphics.blit(Resources.DOG_INVENTORY, l + slot.x - 1, i1 + slot.y - 1, 197, 2, 18, 18);
        }
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
       InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
       if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
           if (this.player.getAbilities().instabuild) {
                this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
           } else {
                this.minecraft.setScreen(new InventoryScreen(this.player));
           }
           return true;
       }

       return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
//            if (this.hoveredSlot instanceof DogInventorySlot) {
//                this.renderTooltip(Arrays.asList(new TranslationTextComponent("test").applyTextStyle(TextFormatting.RED).getFormattedText()), mouseX, mouseY);
//            } else {
                graphics.renderItem(this.hoveredSlot.getItem(), mouseX, mouseY);
//            }
        }

    }
}
