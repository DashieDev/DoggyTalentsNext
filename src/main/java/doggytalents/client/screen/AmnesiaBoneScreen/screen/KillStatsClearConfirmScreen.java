package doggytalents.client.screen.AmnesiaBoneScreen.screen;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ForceClearKillStatsData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraftforge.network.PacketDistributor;

public class KillStatsClearConfirmScreen extends Screen {

    Dog dog;

    protected KillStatsClearConfirmScreen(Dog dog) {
        super(ComponentUtil.literal(""));
        this.dog = dog;
    }

    public static void open(Dog dog) {
        var mc = Minecraft.getInstance();
        var screen = new KillStatsClearConfirmScreen(dog);
        mc.setScreen(screen);
    }

    @Override
    protected void init() {
        addConfirmButton(); 
    }

    @Override
    public void render(PoseStack graphics, int mouseX, int mouseY, float pTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, pTicks);

        var stack = graphics;
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
        Component title;
        String help;
        title = ComponentUtil.translatable("doggui.clear_dog_kill_stats.confirm.title")
        .withStyle(
            Style.EMPTY
            .withBold(true)
            .withColor(ChatFormatting.RED)
        );
        help = I18n.get(
            "doggui.clear_dog_kill_stats.confirm.subtitle",
            dog.getName().getString()
        );
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
        font.draw(stack, title, Mth.floor(mX/1.2f -font.width(title)/2 ), Mth.floor(pY/1.2f), 0xffffffff);
        stack.popPose();
        pY += 40;
        font.draw(stack, help, mX - font.width(help)/2, pY, 0xffffffff);
        pY += 40;
        font.draw(stack, dog_title, mX - font.width(dog_title)/2, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        font.draw(stack, owner_title, mX - font.width(owner_title)/2, pY, 0xffffffff );
        pY += 80;
        font.draw(stack, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addConfirmButton() {
        var clearButton = new Button(this.width/2 - 25, this.height/2 + 58, 
            50, 20, ComponentUtil.translatable("doggui.untame.confirm.confirmed"), 
            b -> {
                requestClearKillStats();
                Minecraft.getInstance().setScreen(null);
            }
        );
        var player = Minecraft.getInstance().player;
        this.addRenderableWidget(clearButton);
    }

    private void requestClearKillStats() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new ForceClearKillStatsData(this.dog.getId()));
    }

    public static void addClearKillStatsButton(final Dog dog, Font font,
        int mid_x, int y, Consumer<TextOnlyButton> button_consumer) {
        var player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if (!player.hasPermissions(4))
            return;

        var str = ComponentUtil.literal("Clear Kill Stats");
        var str_width = font.width(str);
        var button_width = str_width + 4;
        button_consumer.accept(
            new TextOnlyButton(mid_x - button_width/2, y, button_width, font.lineHeight + 2, 
                str.withStyle(ChatFormatting.RED)
            , b -> {
                KillStatsClearConfirmScreen.open(dog);
            }, font)
        );
    }
    
}
