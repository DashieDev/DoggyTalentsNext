package doggytalents.client.screen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DTNClientPettingManager;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class PetSelectScreen extends StringEntrySelectScreen {
    
    private Font font;

    public PetSelectScreen() {
        super(Component.empty());
        this.font = Minecraft.getInstance().font;
    }

    public static void open() {
        var mc = Minecraft.getInstance();
        mc.setScreen(new PetSelectScreen());
    }

    @Override
    public void init() {
        super.init();
        initEntries();
    }

    private void initEntries() {
        this.updateEntries(List.of(
            I18n.get("dog.petmode.facerub"), 
            I18n.get("dog.petmode.hug"),
            I18n.get("dog.petmode.belly_rub"),
            I18n.get("dog.petmode.back_hug"),
            I18n.get("doggytalents.screen.pet_select.exit")    
        ));
    }

    @Override
    protected void onEntrySelected(int id) {
        switch (id) {
        case 0:
            DTNClientPettingManager.get().setPetMode(DogPettingType.FACERUB);
            break;
        case 1:
            DTNClientPettingManager.get().setPetMode(DogPettingType.HUG);
            break;
        case 2:
            DTNClientPettingManager.get().setPetMode(DogPettingType.BELLY_RUB);
            break;
        case 3:
            DTNClientPettingManager.get().setPetMode(DogPettingType.BACK_HUG);
            break;
        default:
            DTNClientPettingManager.get().setPetMode(null);
            break;
        }
        
        this.getMinecraft().setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        drawHelpStringsLeft(graphics, mouseX, mouseY, partialTicks);
        drawHelpStringsRight(graphics, mouseX, mouseY, partialTicks);
    }

    private void drawHelpStringsLeft(PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.width/2;
        int mY = this.height/2;
        
        int t_width_limit = (this.width - this.getSelectAreaSize())/2 - 10;
        t_width_limit = Mth.clamp(t_width_limit, 0, 150);
        if (t_width_limit < 89)
            return;

        final int line_spacing = 2;
        Component text = Component.translatable("doggytalents.screen.pet_select.help.0"); 
        var text_lines = font.split(text, t_width_limit);
        int lines_count = text_lines.size();

        int total_height = lines_count * font.lineHeight
            + (lines_count - 1) * line_spacing;
        int tX = mX - this.getSelectAreaSize()/2
            - 5 - t_width_limit;
        int tY = mY - total_height/2;
        
        for (var line : text_lines) {
            font.draw(graphics, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }

    private void drawHelpStringsRight(PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
        int mX = this.width/2;
        int mY = this.height/2;

        int t_width_limit = (this.width - this.getSelectAreaSize())/2 - 10;
        t_width_limit = Mth.clamp(t_width_limit, 0, 150);
        if (t_width_limit < 89)
            return;

        var current_mode = DTNClientPettingManager.get().getPetMode();
        var current_mode_str = current_mode == DogPettingType.HUG ?
            I18n.get("dog.petmode.hug")
            : I18n.get("dog.petmode.facerub");
        var title = Component.literal(current_mode_str)
            .withStyle(Style.EMPTY.withBold(true));

        final int line_spacing = 2;
        Component text = Component.translatable("doggytalents.screen.pet_select.help.1"); 
        var text_lines = font.split(text, t_width_limit);
        int lines_count = text_lines.size() + 1;

        int total_height = lines_count * font.lineHeight
            + (lines_count - 1) * line_spacing;
        
        if (total_height >= this.height - 3)
            return; 

        int tX = mX + this.getSelectAreaSize()/2
            + 8;
        int tY = mY - total_height/2;
        
        font.draw(graphics, title, tX, tY, 0xffffffff);
        tY += font.lineHeight + 2;
        for (var line : text_lines) {
            font.draw(graphics, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }
    
}
