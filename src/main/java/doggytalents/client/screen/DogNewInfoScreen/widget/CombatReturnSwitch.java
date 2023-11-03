package doggytalents.client.screen.DogNewInfoScreen.widget;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.ScreenUtil;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.CombatReturnStrategy;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.CombatReturnStrategyData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.network.PacketDistributor;
import doggytalents.api.enu.forward_imitate.ComponentUtil;

public class CombatReturnSwitch extends AbstractWidget {
 
    static final int DEFAULT_COLOR = 0x485e5d5d;
    static final int DEFAULT_HLCOLOR = 0x835e5d5d;
    static final int PADDING_HORIZONTAL = 3;
    static final int TICK_HOVERED_NO_CLK_TILL_SHOW_INFO = 30;

    Dog dog;
    Font font;
    Screen screen;

    boolean hoveredLeft = false;
    boolean hoveredRight = false;

    int timeHoveredWithoutClick = 0;
    boolean stillHovered;
    long tickCount0;

    public CombatReturnSwitch(int x, int y, int width, int height, Dog dog, Font font, Screen screen) {
        super(x, y, width, height, ComponentUtil.translatable(
            dog.getCombatReturnStrategy().getUnlocalisedTitle()
        ));
        this.dog = dog;
        this.font = font;
        this.screen = screen;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {

        this.timeHoveredWithoutClick = 0;

        CombatReturnStrategy strategy;
        if (hoveredLeft) {
            strategy = this.dog.getCombatReturnStrategy().prev();
        } else {
            strategy = this.dog.getCombatReturnStrategy().next();
        }
        this.setMessage(ComponentUtil.translatable(strategy.getUnlocalisedTitle()));

        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new CombatReturnStrategyData(this.dog.getId(), strategy));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        if (!this.visible) return;

        int cl = this.isHovered ? DEFAULT_HLCOLOR : DEFAULT_COLOR;
        fill(stack, this.getX(), this.getY(), this.getX()+this.width, this.getY() +this.height, cl);

        this.updateHover(mouseX, mouseY);

        hoveredLeft = false;
        hoveredRight = false;

        if (this.isHovered) {
            if (mouseX - this.getX() < this.width/2) {
                hoveredLeft = true;
                hoveredRight = false;
            } else {
                hoveredLeft = false;
                hoveredRight = true;
            }
        }

        int mX = this.getX() + this.width/2;
        int mY = this.getY() + this.height/2;

        var back_c1 = ComponentUtil.literal("<");
        back_c1.withStyle(
            Style.EMPTY.withBold(hoveredLeft)
        );
        int back_tX = this.getX() + PADDING_HORIZONTAL;
        int back_tY = mY - font.lineHeight/2;
        font.draw(stack, back_c1, back_tX, back_tY, hoveredLeft ? 0xffffffff : 0xa5ffffff);

        var next_c1 = ComponentUtil.literal(">");
        next_c1.withStyle(
            Style.EMPTY.withBold(hoveredRight)
        );
        int next_tX = this.getX() + this.width - PADDING_HORIZONTAL - font.width(next_c1);
        int next_tY = mY - font.lineHeight/2;
        font.draw(stack, next_c1, next_tX, next_tY, hoveredRight ? 0xffffffff : 0xa5ffffff);

        var mode_c1 = this.getMessage();
        int mode_tX = mX - this.font.width(mode_c1)/2;
        int mode_tY = mY - this.font.lineHeight/2;
        font.draw(stack, mode_c1, mode_tX, mode_tY, 0xffffffff);

        if (this.stillHovered) {
            if (this.dog.tickCount - this.tickCount0 >= 1) {
                ++this.timeHoveredWithoutClick;
                this.tickCount0 = this.dog.tickCount;
            }
        }

        if (this.timeHoveredWithoutClick >= 25) {
            this.setOverlayToolTip(stack, mouseX, mouseY);
        }

    }

    private void updateHover(int mouseX, int mouseY) {
        boolean isHovered0 = this.isHovered;
        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
        if (isHovered0 != this.isHovered) {
            this.stillHovered = this.isHovered;
            if (this.isHovered) {
                this.onStartHovering();
            } else {
                this.onStopHovering();
            }
        }
    }

    private void onStartHovering() {
        this.timeHoveredWithoutClick = 0;
    }

    private void onStopHovering() {
        this.timeHoveredWithoutClick = 0;
    }

    public void setOverlayToolTip(PoseStack stack, int mouseX, int mouseY) {
        List<Component> list = new ArrayList<>();
        String str = I18n.get(dog.getCombatReturnStrategy().getUnlocalisedInfo());
        list.addAll(ScreenUtil.splitInto(str, 150, this.font));

        ToolTipOverlayManager.get().setComponents(list);
    }
    
    //Imitate
    private int getX() {
        return this.x;
    }

    private int getY() {
        return this.y;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

}
