package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.parserRule_return;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByNameData;
import doggytalents.common.network.packet.data.WhistleRequestModeData;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;


public class WhistleScreen extends Screen{

   private List<WhistleMode> modeFilterList;
   private int selectedId;
   private String value = "";
   
   private final int HLC_SELECTED = 0xFF10F9; 

   private int hightlightTextColor = HLC_SELECTED;

   
    private final int MAX_BUFFER_SIZE = 64;

    public WhistleScreen() {
        super(Component.translatable("doggytalents.screen.whistler.title"));
        this.modeFilterList = new ArrayList<WhistleMode>();
        for (var i : WhistleMode.VALUES) {
            this.modeFilterList.add(i);
        }
        this.selectedId = 0;
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new WhistleScreen());
    }

    @Override
    public void init() {
        super.init();
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(true);


        //TODO 1.19.3 ??
        // Button s = new Button(3, 3, 20, 20, Component.literal("?"), b -> {} ) {
        //     @Override
        //     public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
        //         List<Component> list = new ArrayList<>();
        //         list.add(Component.translatable("doggytalents.screen.whistler.screen.help_title")
        //             .withStyle(Style.EMPTY.withBold(true)));
        //         String str = I18n.get("doggytalents.screen.whistler.screen.help");
        //         list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

        //         WhistleScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
        //     }
        // };

        Button help = new Button.Builder(Component.literal("?"), b -> {})
            .pos(3, 3)
            .size(20, 20).build();

        help.setTooltip(Tooltip.create(Component.translatable("doggytalents.screen.whistler.screen.help")));
        
        
        this.addRenderableWidget(help);
    }

 
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
      
        Gui.fill(stack, half_width - 100, half_height - 100, half_width + 100, half_height + 100, Integer.MIN_VALUE);
        Gui.fill(stack, half_width - 100, half_height + 105, half_width + 100, half_height + 117, Integer.MIN_VALUE);

        super.render(stack, mouseX, mouseY, partialTicks);
        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;

        for (int i = 0; i < this.modeFilterList.size(); ++i) {
            int color = 0xffffffff;
            if (i == this.selectedId) color = this.hightlightTextColor;
            var text = Component.literal(i + " ");
            text.withStyle(
                Style.EMPTY
                    .withBold(true)
                    .withColor(0xff6f00)
            );
            var title = Component.literal(this.modeFilterList.get(i).getTitle());
            title.withStyle(
                Style.EMPTY
                .withBold(false)
                .withColor(color)
            );
            text.append(title);
            this.font.draw(stack, text, textx, texty + offset, color);
            offset+=10;
            if (offset > 190) break;
        }

        int txtorgx = half_width - 90;
        int txtorgy = half_height + 107;
        
        this.font.draw(stack, this.value + "_", txtorgx, txtorgy,  0xffffffff);
         
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (keyCode == 264) {
            this.selectedId = Mth.clamp(this.selectedId +1, 0, this.modeFilterList.size()-1);
        } else if (keyCode == 265) {
            this.selectedId = Mth.clamp(this.selectedId -1, 0,  this.modeFilterList.size()-1);
        } else if (keyCode == 257) {
            if (this.modeFilterList.isEmpty()) return false; 
            this.requestMode(this.modeFilterList.get(this.selectedId).getIndex());
            this.minecraft.setScreen(null);
        } else if (keyCode == 259) {
            this.popCharInText();
        } 

        return super.keyReleased(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean charTyped(char code, int p_231042_2_) {
        int number = code - 48;
        if (0 <= number && number <= 9) {
            if (number < this.modeFilterList.size()) {
                this.requestMode(this.modeFilterList.get(number).getIndex());
                this.minecraft.setScreen(null);
            }
        }

        if (SharedConstants.isAllowedChatCharacter(code)) {
            this.insertText(Character.toString(code));
            return true;
        } else {
            return false;
        }
        
    }

    private void updateFilter() {
        this.modeFilterList.clear();
        this.selectedId = 0;

        if (this.value == "") {
            for (var i : WhistleMode.VALUES) {
                this.modeFilterList.add(i);
            }
        } else {
            for (var i : WhistleMode.VALUES) {
                String text = i.getTitle();
                if (text.length() < this.value.length()) continue; 
                if (text.contains(this.value)) {
                    this.modeFilterList.add(i);
                }
            }
        }
    } 

    private void insertText(String x) {
        if (this.value.length() < MAX_BUFFER_SIZE) {
            this.value = this.value + x;
        }
        this.updateFilter();
    }

    private void popCharInText() {
        if (this.value.length() <= 0) return;
        this.value = this.value.substring(0, this.value.length()-1);
        this.updateFilter();
    }
    
    @Override
    public void removed() {
        super.removed();
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    private void requestMode(int id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new WhistleRequestModeData(id));
    }
}
