package doggytalents.client.screen.DogNewInfoScreen.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.framework.Store;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
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
        Component title;
        String help;
        if (this.dog.isDefeated()) {
            renderIncapScreen(graphics, mouseX, mouseY, pTicks);
            return;
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
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
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

    private void renderIncapScreen(GuiGraphics graphics, int mouseX, int mouseY, float pTicks)  {
        var stack = graphics.pose();
        int pX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 44;
        var title = Component.translatable("doggui.invalid_dog.incapacitated.title")
                .withStyle(
                    Style.EMPTY
                    .withBold(true)
                    .withColor(ChatFormatting.RED)
                );
        var help = I18n.get(
            "doggui.invalid_dog.incapacitated.subtitle", 
                dog.getGenderSubject().getString()
            );
        var dog_title = I18n.get(
            "doggui.invalid_dog.info.dog",
            dog.getName().getString()
        );
        var owner_title = I18n.get(
            "doggui.invalid_dog.info.owner",
            this.dog.getOwnersName().orElse(Component.literal("")).getString()
        );
        var escToReturn = I18n.get("doggui.invalid_dog.esc_to_return");
        var lines1 = font.split(title, 120);
        var lines2 = font.split(Component.literal(help), 120);

        int sizeYtotal = lines1.size()*14
            + 10 + lines2.size()*(font.lineHeight+3)
            + 10 + font.lineHeight + 3
            + font.lineHeight;
        pY = this.height/2 - sizeYtotal/2 + 1;
        
        for (var line : lines1) {
            stack.pushPose();
            stack.scale(1.2f, 1.2f, 1.2f);
            graphics.drawString(font, line, Mth.floor(pX/1.2f), Mth.floor(pY/1.2f), 0xffffffff);
            stack.popPose();
            pY += 14;
        }
        pY += 10;
        for (var line : lines2) {
            graphics.drawString(font, line, pX, pY, 0xffffffff);
            pY += font.lineHeight + 3;
        }
        pY += 10;
        graphics.drawString(font, dog_title, pX, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        graphics.drawString(font, owner_title, pX, pY, 0xffffffff );

        int escTX = this.width/2 - font.width(escToReturn)/2;
        int escTY = this.height/2 + 100; 
        graphics.drawString(font, escToReturn, escTX, escTY, 0xffffffff );
        
        drawDefeatedKanji(graphics, pX - 135, mY - 64, 128);

    }

    private void drawDefeatedKanji(GuiGraphics graphics, int x, int y, int size)  {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = size;
        graphics.blit(getDefeatedKanji(this.dog), x, y, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        RenderSystem.disableBlend();
    }

    public static ResourceLocation getDefeatedKanji(Dog dog) {
        var state = dog.getIncapSyncState();
        var cause = state.type;
        switch (cause) {
        case BLOOD:
            return Resources.KANJI_INCAP_BLOOD;
        case BURN:
            return Resources.KANJI_INCAP_BURN;
        case DROWN:
            return Resources.KANJI_INCAP_DROWN;
        case POISON:
            return Resources.KANJI_INCAP_POISON;
        case STARVE:
            return Resources.KANJI_INCAP_STARVE;
        default:
            return Resources.KANJI_INCAP_BLOOD;
        }
    }

    @Override
    public boolean isPauseScreen() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
