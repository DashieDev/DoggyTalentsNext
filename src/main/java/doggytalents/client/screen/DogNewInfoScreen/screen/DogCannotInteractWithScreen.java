package doggytalents.client.screen.DogNewInfoScreen.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.framework.Store;
import doggytalents.common.entity.Dog;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class DogCannotInteractWithScreen extends Screen {

    Dog dog;

    protected DogCannotInteractWithScreen(Dog dog) {
        super(ComponentUtil.literal(""));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new DogCannotInteractWithScreen(dog);
        mc.setScreen(screen);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, pTicks);
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
        Component title;
        String help;
        if (this.dog.isDefeated()) {
            title = ComponentUtil.translatable("doggui.invalid_dog.incapacitated.title")
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
            title = ComponentUtil.translatable("doggui.invalid_dog.no_permission.title")
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
            this.dog.getOwnersName().orElse(ComponentUtil.literal("")).getString()
        );
        var escToReturn= I18n.get("doggui.invalid_dog.esc_to_return");
        stack.pushPose();
        stack.scale(1.2f, 1.2f, 1.2f);
        this.font.draw(stack, title, (mX/1.2f -font.width(title)/2 ), pY/1.2f, 0xffffffff);
        stack.popPose();
        pY += 40;
        this.font.draw(stack, help, mX - font.width(help)/2, pY, 0xffffffff);
        pY += 40;
        this.font.draw(stack, dog_title, mX - font.width(dog_title)/2, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        this.font.draw(stack, owner_title, mX - font.width(owner_title)/2, pY, 0xffffffff );
        pY += 40;
        this.font.draw(stack, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
