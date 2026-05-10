package doggytalents.client.screen;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.screen.widget.SmallButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.entity.accessory.DyeableAccessory.DyeableAccessoryInstance;
import doggytalents.common.inventory.container.DogInventoriesContainer;
import doggytalents.common.inventory.container.slot.DogInventorySlot;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogInventoryPageData;
import doggytalents.common.util.Util;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import doggytalents.common.network.PacketDistributor;

import java.util.Optional;

public class DogInventoriesScreen extends AbstractContainerScreen<DogInventoriesContainer> {

    private Button left, right;
    private Player player;

    public DogInventoriesScreen(DogInventoriesContainer packPuppy, Inventory playerInventory, Component displayName) {
        super(packPuppy, playerInventory, displayName);
        this.player = playerInventory.player;
    }

    @Override
    public void init() {
        super.init();
        this.left = new SmallButton(this.leftPos + this.imageWidth - 29, this.topPos + 4, Component.literal("<"), (btn) -> {
            int page = this.getMenu().getViewOffset();

            if (page > 0) {
                PacketHandler.send(PacketDistributor.SERVER.noArg(), new DogInventoryPageData(--page));
            }

            btn.active = page > 0;
            this.right.active = page < this.getMenu().getTotalNumColumns() - 9;
        });
        this.right = new SmallButton(this.leftPos + this.imageWidth - 26 + 9, this.topPos + 4, Component.literal(">"), (btn) -> {
            int page = this.getMenu().getViewOffset();

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
    protected void extractLabels(GuiGraphicsExtractor graphics, int par1, int par2) {
        graphics.text(font, this.title.getString(), 8, 6, 4210752, false);
        graphics.text(font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int xMouse, int yMouse, float partialTicks) {
        int l = (this.width - this.imageWidth) / 2;
        int i1 = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.DOG_INVENTORY, l, i1, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        for (DogInventorySlot slot : this.getMenu().getSlots()) {
            if (!slot.isActive()) {
                continue;
            }
            graphics.blit(RenderPipelines.GUI_TEXTURED, Resources.DOG_INVENTORY, l + slot.x - 1, i1 + slot.y - 1, 197.0F, 2.0F, 18, 18, 256, 256);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
       if (this.minecraft.options.keyInventory.matches(event)) {
           if (this.player.getAbilities().instabuild) {
                this.minecraft.setScreen(new CreativeModeInventoryScreen(this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()));
           } else {
                this.minecraft.setScreen(new InventoryScreen(this.player));
           }
           return true;
       }

       return super.keyPressed(event);
    }
}
