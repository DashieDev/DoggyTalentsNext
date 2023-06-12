package doggytalents.client.screen.DogNewInfoScreen.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.framework.Store;
import doggytalents.common.entity.Dog;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class DogCannotInteractWithScreen extends Screen {

    Dog dog;

    protected DogCannotInteractWithScreen(Dog dog) {
        super(Component.literal(""));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new DogCannotInteractWithScreen(dog);
        mc.setScreen(screen);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
        var stack = graphics.pose();
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, pTicks);
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
        Component title;
        String help;
        if (this.dog.isDefeated()) {
            title = Component.translatable("doggui.invalid_dog.incapacitated.title")
                .withStyle(
                    Style.EMPTY
                    .withBold(true)
                    .withColor(ChatFormatting.RED)
                );
            help = I18n.get(
            "doggui.invalid_dog.incapacitated.subtitle", 
                dog.getGenderSubject().getString()
            );
        } else {
            if (this.dog.canInteract(Minecraft.getInstance().player)) {
                DogNewInfoScreen.open(dog);
                return;
            };
            title = Component.translatable("doggui.invalid_dog.no_permission.title")
            .withStyle(
                Style.EMPTY
                .withBold(true)
                .withColor(ChatFormatting.RED)
            );
            help = I18n.get(
                "doggui.invalid_dog.no_permission.subtitle",
                dog.getGenderPronoun().getString()
            );
        }
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
        pY += 40;
        graphics.drawString(font, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
