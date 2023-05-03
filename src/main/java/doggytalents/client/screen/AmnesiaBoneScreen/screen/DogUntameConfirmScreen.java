package doggytalents.client.screen.AmnesiaBoneScreen.screen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AmnesiaBoneItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogUntameData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.network.PacketDistributor;

public class DogUntameConfirmScreen extends Screen {

    Dog dog;

    protected DogUntameConfirmScreen(Dog dog) {
        super(Component.literal(""));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new DogUntameConfirmScreen(dog);
        mc.setScreen(screen);
    }

    @Override
    protected void init() {
        addUntameButton(); 
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
        title = Component.translatable("doggui.detrain.confirm.title")
        .withStyle(
            Style.EMPTY
            .withBold(true)
            .withColor(ChatFormatting.RED)
        );
        help = I18n.get(
            "doggui.detrain.confirm.subtitle",
            dog.getGenderPronoun()
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
        this.font.draw(stack, title, (mX/1.2f -font.width(title)/2 ), pY/1.2f, 0xffffffff);
        stack.popPose();
        pY += 40;
        this.font.draw(stack, help, mX - font.width(help)/2, pY, 0xffffffff);
        pY += 40;
        this.font.draw(stack, dog_title, mX - font.width(dog_title)/2, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        this.font.draw(stack, owner_title, mX - font.width(owner_title)/2, pY, 0xffffffff );
        pY += 80;
        this.font.draw(stack, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addUntameButton() {
        var untameButton = new CustomButton(this.width/2 - 25, this.height/2 + 58, 
            50, 20, Component.translatable("doggui.untame.confirm.confirmed"), 
            b -> {
                requestUntame();
                Minecraft.getInstance().setScreen(null);
            }
        ) {
            @Override
            public void renderWidget(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                // TODO Auto-generated method stub
                super.renderWidget(stack, mouseX, mouseY, pTicks);
                
                // var costStr = dogLevel < talent.getMaxLevel() ?
                //     "Cost : " + talent.getLevelCost(dogLevel + 1)
                //     : "Max Level Reached.";
                String costStr;
                int costStrColor;
                costStr = I18n.get("doggui.talents.cost") + AmnesiaBoneItem.getUntameXPCost();
                costStrColor = 0xffffffff;
                int tX = this.getX() + this.width/2 - font.width(costStr)/2;
                int tY = this.getY() - 2 - font.lineHeight;
                font.draw(stack, costStr, tX, tY, costStrColor);
                var player = Minecraft.getInstance().player;
                this.active = 
                    (player != null && player.experienceLevel >= AmnesiaBoneItem.getUntameXPCost());
            }

            @Override
            public void render(PoseStack stack, int mouseX, int mouseY, float pTicks) {
                super.render(stack, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                MutableComponent c1;
                if (this.active) {
                    return;
                } else {
                    var player = Minecraft.getInstance().player;
                    if (player != null && player.experienceLevel < AmnesiaBoneItem.getUntameXPCost()) {
                        c1 = Component.translatable("doggui.detrain.talents.insufficent_xp");
                        c1.setStyle(
                            Style.EMPTY
                            .withColor(0xffB20000)
                            .withBold(true)
                        );
                    } else {
                        return;
                    }
                }
                renderComponentTooltip(stack, List.of(c1), mouseX, mouseY);
            }
        };
        var player = Minecraft.getInstance().player;
        untameButton.active = 
            (player != null && player.experienceLevel >= AmnesiaBoneItem.getUntameXPCost());

        this.addRenderableWidget(untameButton);
    }

    private void requestUntame() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new DogUntameData(this.dog.getId()));
    }
    
}
