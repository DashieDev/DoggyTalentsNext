package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.DoggyItems;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.item.WhistleItem.WhistleMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.WhisltleEditHotKeyData;
import doggytalents.common.network.packet.data.WhistleRequestModeData;
import doggytalents.common.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import doggytalents.common.network.PacketDistributor;


public class WhistleScreen extends StringEntrySelectScreen {

    private final List<WhistleMode> modeList;

    private boolean settingKeysMode = false;
    private int pKey = 0;
    private int[] hotkeysModeArr = {-1, -1, -1, -1};

    private boolean dogOnDutyOnly;

    public WhistleScreen() {
        super(Component.translatable("doggytalents.screen.whistler.title"));
        this.modeList = Arrays.stream(WhistleMode.VALUES)
            .collect(Collectors.toList());
        this.updateEntries(this.modeList.stream()
            .map(x -> I18n.get(x.getUnlocalisedTitle()))
            .collect(Collectors.toList()));
    }

    public static void open(ItemStack whistle_stack) { 
        Minecraft mc = Minecraft.getInstance();
        var screen = new WhistleScreen();
        screen.dogOnDutyOnly = WhistleItem.isDogOnDutyOnly(whistle_stack);
        mc.setScreen(screen);
    }

    @Override
    protected void addUtilitiesButton() {
        int mX = this.width/2;
        int mY = this.height/2;
        int pY = mY - 100;

        var setKey = new FlatButton(mX - 100 - 60 - 2, pY, 60, 20, Component.translatable("doggytalents.screen.whistler.screen.set_hotkey"),
            b -> {
                if (settingKeysMode) {
                    settingKeysMode = false;
                    b.setMessage(Component.translatable("doggytalents.screen.whistler.screen.set_hotkey"));
                } else {
                    settingKeysMode = true;
                    b.setMessage(Component.translatable("doggytalents.screen.whistler.screen.use_whistle"));
                }
            }
        ) {
            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super.renderWidget(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.whistler.screen.set_hotkey")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.screen.set_hotkey.help");
                list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

                graphics.renderComponentTooltip(font, list, mouseX, mouseY);
            }
        };
        pY += setKey.getHeight() + 2;

        var onDuty = new FlatButton(mX - 100 - 60 - 2, pY, 60, 20, dogOnDutyOnly ? 
            Component.translatable("doggytalents.screen.whistler.target.on_duty") 
            : Component.translatable("doggytalents.screen.whistler.target.all"), 
            b -> {
                if (dogOnDutyOnly) {
                    dogOnDutyOnly = false;
                    b.setMessage(Component.translatable("doggytalents.screen.whistler.target.all"));
                } else {
                    dogOnDutyOnly = true;
                    b.setMessage(Component.translatable("doggytalents.screen.whistler.target.on_duty"));
                }
            } 
        ) {
            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super.renderWidget(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                var title = Component.translatable("doggytalents.screen.whistler.target.title")
                    .withStyle(Style.EMPTY.withBold(true));
                list.add(title);
                String str = I18n.get("doggytalents.screen.whistler.target.help");
                list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

                graphics.renderComponentTooltip(font, list, mouseX, mouseY);
            }
        };

        pY += onDuty.getHeight() + 2;

        var help = new FlatButton(mX - 100 - 20 - 2, pY, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super.renderWidget(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.whistler.screen.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.screen.help");
                list.addAll(ScreenUtil.splitInto(str, 150, WhistleScreen.this.font));

                graphics.renderComponentTooltip(font, list, mouseX, mouseY);
            }
        };

        this.addRenderableWidget(help);
        this.addRenderableWidget(setKey);
        this.addRenderableWidget(onDuty);
    }

    @Override
    protected void drawEntry(GuiGraphics graphics, int entry_x, int entry_y, 
        int entry_id, boolean is_selected) {
        
        if (this.settingKeysMode) {
            drawSetMode(graphics, entry_x, entry_y, entry_id, is_selected);
        } else {
            drawNonSetMode(graphics, entry_x, entry_y, entry_id, is_selected);
        }
    }

    private void drawNonSetMode(GuiGraphics graphics, int entry_x, int entry_y, 
        int entry_id, boolean is_selected) {
        
        int color = 0xffffffff;
        if (is_selected) 
            color = getHightlightSelectedColor();
        var text = Component.translatable(this.modeList.get(entry_id).getUnlocalisedTitle());
        text.withStyle(
            Style.EMPTY
            .withBold(false)
            .withColor(color)
        );
        graphics.drawString(font, text, entry_x, entry_y, color);
    }

    private void drawSetMode(GuiGraphics graphics, int entry_x, int entry_y, 
        int entry_id, boolean is_selected) {
            
        int color = 0xffffffff;
        if (is_selected) 
            color = getHightlightSelectedColor();
        var mode = this.modeList.get(entry_id);
        int hotkey_indx = findHotkeyForMode(mode.getIndex());
        int prefix_color = 0xff6f00;
        MutableComponent text; 
        if (is_selected) {
            boolean remove = false;
            prefix_color = 0x0aff43;
            if (hotkey_indx >= 0) { 
                prefix_color = 0xff3636;
                remove = true;
            }
            text = Component.literal(
                remove ? "- " : pKey + " "
            );
        } else if (hotkey_indx >= 0) {
            prefix_color = 0xff6f00;
            text = Component.literal(hotkey_indx + " ");
        } else {
            text = Component.literal("  ");
        }
        text.withStyle(
            Style.EMPTY
                .withBold(true)
                .withColor(prefix_color)
        );
        var title = Component.translatable(mode.getUnlocalisedTitle());
        title.withStyle(
            Style.EMPTY
            .withBold(false)
            .withColor(color)
        );
        text.append(title);
        graphics.drawString(font, text, entry_x, entry_y, color);
    }

    public void tick() {
        updateCurrentHotKeys();
        this.pKey = findEmptyHotkey();
    };

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
        var tag = ItemUtil.getTag(stack);
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

    @Override
    protected void onEntrySelected(int id) {
        proccessSelectIndx(id);
    }

    private void proccessSelectIndx(int indx) {
        if (this.settingKeysMode) {
            int new_mode_id = this.modeList.get(indx).getIndex();
            int send_key = this.pKey;
            int occupied_key = this.findHotkeyForMode(new_mode_id);
            if (occupied_key >= 0) {
                new_mode_id = -1;
                send_key = occupied_key;
            }
                
            this.sendHotKeyEdits(send_key, new_mode_id);
        } else {
            this.requestMode(this.modeList.get(indx).getIndex());
            Minecraft.getInstance().setScreen(null);
        }
    }

    private void requestMode(int id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new WhistleRequestModeData(id, this.dogOnDutyOnly));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}