package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView.view.ArtifactsView.widget.ArtifactHolder;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AccessoryItem;
import doggytalents.common.item.DoggyArtifactItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArtifactEditElement extends AbstractElement {

    private static final int BUTTON_SPACING = 4;

    Player player;
    Dog dog;
    Inventory inventory;
    Minecraft mc;
    final ArrayList<ArtifactHolder> artifactHolders = new ArrayList<ArtifactHolder>(5);

    TextOnlyButton lastPage;
    TextOnlyButton nextPage;

    //Local State that gets updated every frame.
    int startIndex = 0;

    public ArtifactEditElement(AbstractElement parent, Screen screen, Player player, Dog dog) {
        super(parent, screen);
        //TODO Auto-generated constructor stub
        mc = Minecraft.getInstance();
        this.player = player;
        this.dog = dog;
        inventory = player.getInventory();
    }

    @Override
    public AbstractElement init() {
        int mX = this.getSizeX()/2;
        int mY = this.getSizeY()/2;

        for (int i = 0; i < 5; ++i) {
            var artifactHolder = new ArtifactHolder(
                0, 0,
                this.mc.getItemRenderer(), this.dog);
            this.artifactHolders.add(artifactHolder);
        }

        int artifactHolderTotalsSize = 
            5 * ArtifactHolder.WIDGET_SIZE + 4 * BUTTON_SPACING;
        int startY = this.getRealY() + mY - ArtifactHolder.WIDGET_SIZE/2;
        int pX = this.getRealX() + mX - artifactHolderTotalsSize/2;
        for (var holder : this.artifactHolders) {
            holder.x = pX;
            holder.y = startY;
            this.addChildren(holder);
            pX += ArtifactHolder.WIDGET_SIZE + BUTTON_SPACING;
        }

        
        this.lastPage = new TextOnlyButton(
            this.getRealX() + mX - 80 - 9, 
            this.getRealY() + mY - 9, 18, 18,  
            ComponentUtil.literal("<"), b -> {
                startIndex -= artifactHolders.size();
            }, mc.font);
        this.addChildren(lastPage);

        this.nextPage = new TextOnlyButton(
            this.getRealX() + mX + 80 - 9, 
            this.getRealY() + mY - 9, 18, 18, 
            ComponentUtil.literal(">"), b -> {
                startIndex += artifactHolders.size();
            }, mc.font);
        this.addChildren(nextPage);

        return this;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int holderIndx = 0;
        var items = this.inventory.items;
        this.lastPage.active = startIndex > 0;
        this.nextPage.active = false;
        this.startIndex = Math.max(0, startIndex);
        int tillStart = startIndex;
        for (int i = 0; i < items.size(); ++i) {
            var item = items.get(i);
            if (item == null || !this.eligibleArtifact(item)) continue;
            if (holderIndx >= this.artifactHolders.size()) {
                this.nextPage.active = true;
                break;
            }
            if (--tillStart < 0) {
                var holder = this.artifactHolders.get(holderIndx);
                holder.setStack(item);
                holder.setInventorySlotId(i);
                ++holderIndx;
            }
        }
        if (holderIndx <= 0) {
            if (startIndex > 0) {
                this.startIndex = 0;
            } else {
                int mX = this.getSizeX()/2;
                int mY = this.getSizeY()/2;
                var txt = ComponentUtil.translatable("doggui.home.artifacts.no_artifacts_in_inv");
                int tX = this.getRealX() + mX - mc.font.width(txt)/2;
                int tY = this.getRealY() + mY - mc.font.lineHeight/2;
                mc.font.draw(stack, txt, tX, tY, 0xffffffff);
            }
        } else {
            int tX = this.getRealX() + 6;
            int tY = this.getRealY() + 6;
            mc.font.draw(stack, I18n.get("doggui.home.artifacts.your_artifacts"), tX, tY, 0xffffffff);
        }
        while (holderIndx < this.artifactHolders.size()) {
            this.artifactHolders.get(holderIndx).setStack(ItemStack.EMPTY);
            ++holderIndx;
        }
    }

    private boolean eligibleArtifact(@Nonnull ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof DoggyArtifactItem)) return false;
        return true;
    }
    
}
