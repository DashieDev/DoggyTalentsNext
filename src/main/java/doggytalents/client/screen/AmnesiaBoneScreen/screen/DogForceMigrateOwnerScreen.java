package doggytalents.client.screen.AmnesiaBoneScreen.screen;

import java.util.List;

import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AmnesiaBoneItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ForceChangeOwnerData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import doggytalents.common.network.PacketDistributor;

public class DogForceMigrateOwnerScreen extends Screen {

    Dog dog;

    protected DogForceMigrateOwnerScreen(Dog dog) {
        super(Component.literal(""));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new DogForceMigrateOwnerScreen(dog);
        mc.setScreen(screen);
    }

    @Override
    protected void init() {
        addForceChangeButton(); 
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        this.renderBackground(graphics, mouseX, mouseY, pTicks);
        super.render(graphics, mouseX, mouseY, pTicks);

        var stack = graphics.pose();
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
        Component title;
        String help;
        title = Component.translatable("doggui.force_migrate_owner.confirm.title")
        .withStyle(
            Style.EMPTY
            .withBold(true)
            .withColor(ChatFormatting.RED)
        );
        help = I18n.get(
            "doggui.force_migrate_owner.confirm.subtitle",
            dog.getGenderPronoun().getString()
        );
        var dog_title = I18n.get(
            "doggui.invalid_dog.info.dog",
            dog.getName().getString()
        );
        var owner_title = I18n.get(
            "doggui.invalid_dog.info.owner",
            this.dog.getOwnersName().orElse(Component.literal("")).getString()
        );
        var escToReturn= I18n.get("doggui.invalid_dog.esc_to_return");
        stack.pushPose();
        stack.scale(1.2f, 1.2f, 1.2f);
        graphics.drawString(font, title, Mth.floor(mX/1.2f -font.width(title)/2 ), Mth.floor(pY/1.2f), 0xffffffff);
        stack.popPose();
        pY += 40;
        graphics.drawString(font, help, mX - font.width(help)/2, pY, 0xffffffff);
        pY += 40;
        graphics.drawString(font, dog_title, mX - font.width(dog_title)/2, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        graphics.drawString(font, owner_title, mX - font.width(owner_title)/2, pY, 0xffffffff );
        pY += 80;
        graphics.drawString(font, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addForceChangeButton() {
        var untameButton = new CustomButton(this.width/2 - 25, this.height/2 + 58, 
            50, 20, Component.translatable("doggui.untame.confirm.confirmed"), 
            b -> {
                requestForceChange();
                Minecraft.getInstance().setScreen(null);
            }
        );

        this.addRenderableWidget(untameButton);
    }

    private void requestForceChange() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new ForceChangeOwnerData(this.dog.getId()));
    }
}