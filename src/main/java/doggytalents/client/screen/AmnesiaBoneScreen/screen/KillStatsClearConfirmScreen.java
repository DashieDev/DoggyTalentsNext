package doggytalents.client.screen.AmnesiaBoneScreen.screen;

import java.util.function.Consumer;

import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketDistributor;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ForceClearKillStatsData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class KillStatsClearConfirmScreen extends Screen {

    Dog dog;

    protected KillStatsClearConfirmScreen(Dog dog) {
        super(Component.literal(""));
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
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
        //this.renderBackground(graphics, mouseX, mouseY, pTicks);
        super.extractRenderState(graphics, mouseX, mouseY, pTicks);

        var stack = graphics.pose();
        int mX = this.width/2;
        int mY = this.height/2; 

        int pY = mY - 72;
        Component title;
        String help;
        title = Component.translatable("doggui.clear_dog_kill_stats.confirm.title")
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
            this.dog.getOwnersName().orElse(Component.literal("")).getString()
        );
        var escToReturn= I18n.get("doggui.invalid_dog.esc_to_return");
        stack.pushMatrix();
        stack.scale(1.2f);
        graphics.text(font, title, Mth.floor(mX/1.2f -font.width(title)/2 ), Mth.floor(pY/1.2f), 0xffffffff);
        stack.popMatrix();
        pY += 40;
        graphics.text(font, help, mX - font.width(help)/2, pY, 0xffffffff);
        pY += 40;
        graphics.text(font, dog_title, mX - font.width(dog_title)/2, pY, 0xffffffff );
        pY += font.lineHeight + 3;
        graphics.text(font, owner_title, mX - font.width(owner_title)/2, pY, 0xffffffff );
        pY += 80;
        graphics.text(font, escToReturn, mX - font.width(escToReturn)/2, pY, 0xffffffff );

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addConfirmButton() {
        var clearButton = new CustomButton(this.width/2 - 25, this.height/2 + 58, 
            50, 20, Component.translatable("doggui.untame.confirm.confirmed"), 
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
        if (!player.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.OWNERS)))
            return;

        var str = Component.literal("Clear Kill Stats");
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
