package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.parserRule_return;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.DoggyItems;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByNameData;
import doggytalents.common.network.packet.data.WhisltleEditHotKeyData;
import doggytalents.common.network.packet.data.WhistleRequestModeData;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

    private int mouseX0;
    private int mouseY0;

    private boolean settingKeysMode = false;
    private int pKey = 0;
    private int[] hotkeysModeArr = {-1, -1, -1, -1};

    public WhistleScreen() {
        super(ComponentUtil.translatable("doggytalents.screen.whistler.title"));
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
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

        Button help = new Button(3, 3, 20, 20, ComponentUtil.literal("?"), b -> {} ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(ComponentUtil.translatable("doggytalents.screen.whistler.screen.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.screen.help");
                list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

                WhistleScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };

        Button setKey = new Button(3, 23, 60, 20, ComponentUtil.translatable("doggytalents.screen.whistler.screen.set_hotkey"),
            b -> {
                if (settingKeysMode) {
                    settingKeysMode = false;
                    b.setMessage(ComponentUtil.translatable("doggytalents.screen.whistler.screen.set_hotkey"));
                } else {
                    settingKeysMode = true;
                    b.setMessage(ComponentUtil.translatable("doggytalents.screen.whistler.screen.use_whistle"));
                }
            }
        ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(ComponentUtil.translatable("doggytalents.screen.whistler.screen.set_hotkey")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.screen.set_hotkey.help");
                list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

                WhistleScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(help);
        this.addRenderableWidget(setKey);
    }

 
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.mouseX0 != mouseX || this.mouseY0 != mouseY) {
            this.onMouseMoved(mouseX, mouseY);
            this.mouseX0 = mouseX;
            this.mouseY0 = mouseY;
        }

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
      
        Gui.fill(stack, half_width - 100, half_height - 100, half_width + 100, half_height + 100, Integer.MIN_VALUE);
        Gui.fill(stack, half_width - 100, half_height + 105, half_width + 100, half_height + 117, Integer.MIN_VALUE);

        super.render(stack, mouseX, mouseY, partialTicks);

        if (this.settingKeysMode) {
            renderModeListSetHotkey(stack, mouseX, mouseY, partialTicks);
        } else {
            renderModeListWhistleUse(stack, mouseX, mouseY, partialTicks);
        }

        
        int txtorgx = half_width - 90;
        int txtorgy = half_height + 107;
        
        this.font.draw(stack, this.value + "_", txtorgx, txtorgy,  0xffffffff);
         
    }

    public void renderModeListWhistleUse(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
        
        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;

        for (int i = 0; i < this.modeFilterList.size(); ++i) {
            int color = 0xffffffff;
            if (i == this.selectedId) color = this.hightlightTextColor;
            var text = ComponentUtil.translatable(this.modeFilterList.get(i).getUnlocalisedTitle());
            text.withStyle(
                Style.EMPTY
                .withBold(false)
                .withColor(color)
            );
            this.font.draw(stack, text, textx, texty + offset, color);
            offset+=10;
            if (offset > 190) break;
        }
    }

    public void renderModeListSetHotkey(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        updateCurrentHotKeys();
        this.pKey = findEmptyHotkey();

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
        
        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;

        for (int i = 0; i < this.modeFilterList.size(); ++i) {
            int color = 0xffffffff;
            if (i == this.selectedId) color = this.hightlightTextColor;
            int mode_id = this.modeFilterList.get(i).getIndex();
            int hotkey_indx = findHotkeyForMode(mode_id);
            int prefix_color = 0xff6f00;
            MutableComponent text; 
            if (i == this.selectedId) {
                boolean remove = false;
                prefix_color = 0x0aff43;
                if (hotkey_indx >= 0) { 
                    prefix_color = 0xff3636;
                    remove = true;
                }
                text = ComponentUtil.literal(
                    remove ? "- " : pKey + " "
                );
            } else if (hotkey_indx >= 0) {
                prefix_color = 0xff6f00;
                text = ComponentUtil.literal(hotkey_indx + " ");
            } else {
                text = ComponentUtil.literal("  ");
            }
            text.withStyle(
                Style.EMPTY
                    .withBold(true)
                    .withColor(prefix_color)
            );
            var title = ComponentUtil.translatable(this.modeFilterList.get(i).getUnlocalisedTitle());
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
    }

    private void updateCurrentHotKeys() {
        for (int i = 0; i < this.hotkeysModeArr.length; ++i) {
            this.hotkeysModeArr[i] = -1;
        }
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;
        var stack = player.getMainHandItem();
        if (stack == null) return;
        if (stack.getItem() != DoggyItems.WHISTLE.get()) return;
        var tag = stack.getTag();
        if (tag == null) return;
        var list = tag.getIntArray("hotkey_modes");
        if (list == null) return;
        for (int i = 0; i < this.hotkeysModeArr.length; ++i) {
            if (i >= list.length) break;
            this.hotkeysModeArr[i] = list[i];
        }
    }

    private int findEmptyHotkey() {
        for (int i = 0; i < this.hotkeysModeArr.length; ++i) {
            if (this.hotkeysModeArr[i] < 0) 
                return i;
        }
        return 3;
    }

    private int findHotkeyForMode(int mode_id) {
        for (int i = 0; i < this.hotkeysModeArr.length; ++i) {
            if (this.hotkeysModeArr[i] == mode_id) 
                return i;
        }
        return -1;
    }

    private void sendHotKeyEdits(int hotkey_id, int mode_id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new WhisltleEditHotKeyData(hotkey_id, mode_id));
    }

    private int getHoveredIndex(double x, double y, int entry_size) {
        int mX = this.width/2;
        int mY = this.height/2;
        if (Math.abs(x - mX) > 100) return -1;
        if (Math.abs(y - mY) > 100) return -1;
        int baseY = mY - 100;
        int indx = ( Mth.floor(y - baseY) )/10;
        if (indx >= entry_size) return -1;
        return indx;
    }

    private void onMouseMoved(double x, double y) {
        int newIndx = getHoveredIndex(x, y, this.modeFilterList.size());
        if (newIndx < 0) return;
        this.selectedId = newIndx;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        boolean ret = super.mouseClicked(x, y, p_94697_);
        int mX = this.width/2;
        int mY = this.height/2;
        if (Math.abs(x - mX) > 100) return ret;
        if (Math.abs(y - mY) > 100) return ret;
        int indx = getHoveredIndex(x, y, this.modeFilterList.size());
        if (indx >= 0) {
            proccessSelectIndx(indx);
        }
        return ret;
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
            proccessSelectIndx(this.selectedId);
        } else if (keyCode == 259) {
            this.popCharInText();
        } 

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public void proccessSelectIndx(int indx) {
        if (this.settingKeysMode) {
            int new_mode_id = this.modeFilterList.get(indx).getIndex();
            int send_key = this.pKey;
            int occupied_key = this.findHotkeyForMode(new_mode_id);
            if (occupied_key >= 0) {
                new_mode_id = -1;
                send_key = occupied_key;
            }
                
            this.sendHotKeyEdits(send_key, new_mode_id);
        } else {
            this.requestMode(this.modeFilterList.get(indx).getIndex());
            Minecraft.getInstance().setScreen(null);
        }
    }


    @Override
    public boolean charTyped(char code, int p_231042_2_) {

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
                String text = I18n.get(i.getUnlocalisedTitle());
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
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    private void requestMode(int id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new WhistleRequestModeData(id));
    }
}
