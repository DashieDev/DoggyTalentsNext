package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.DoggyItems;
import doggytalents.api.anim.DogAnimation;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.item.DogAnimDebugItem;
import doggytalents.common.item.DogAnimDebugItem.ItemMode;
import doggytalents.common.network.PacketDistributor;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.DogAnimDebugData.UpdateItemSettingsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class DogAnimDebugScreen extends StringEntrySelectScreen {

    private Font font;
    private List<DogAnimation> animList;
    private ItemMode selectMode = ItemMode.ANIM;
    private DogAnimation selectAnim = DogAnimation.NONE;

    public DogAnimDebugScreen(Player player) {
        super(Component.empty());
        this.font = Minecraft.getInstance().font;
        this.animList = Arrays.stream(DogAnimation.values())
            .collect(Collectors.toList());
        getSelectModeFromPlayer(player);
        getSelectAnimFromPlayer(player);
    }

    private void getSelectModeFromPlayer(Player player) {
        var stack = player.getMainHandItem();
        if (stack.getItem() != DoggyItems.DOG_ANIM_DEBUG.get())
            return;
        this.selectMode = DogAnimDebugItem.getItemMode(stack);
    }

    private void getSelectAnimFromPlayer(Player player) {
        var stack = player.getMainHandItem();
        if (stack.getItem() != DoggyItems.DOG_ANIM_DEBUG.get())
            return;
        this.selectAnim = DogAnimDebugItem.getSelectedAnimation(stack);
    }

    public static void open(Player player) {
        var mc = Minecraft.getInstance();
        mc.setScreen(new DogAnimDebugScreen(player));
    }

    @Override
    public void init() {
        super.init();
        initEntries();
        addModeButton();
    }

    @Override
    protected void onEntrySelected(int id) {
        var anim_selected = this.animList.get(id);
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new UpdateItemSettingsData(anim_selected, this.selectMode)
        );
        this.minecraft.setScreen(null);
    }

    private void addModeButton() {
        int mx = this.width/2;
        int my = this.height/2;
        final int modeButton_width = 70;
        final int modeButton_height = 20;
        final boolean help_render_below_view = 
            shouldRenderHelpBelow();
        var modeButton = new FlatButton(
            mx - this.getSelectAreaSize()/2 -modeButton_width - 2, 
            my - getSelectAreaSize()/2, 
            modeButton_width, modeButton_height, getModeTitle(selectMode),
            b -> {
                var new_mode = selectMode.cycleMode();
                selectMode = new_mode;
                b.setMessage(getModeTitle(selectMode));
                sendItemChangeRequest();
                if (!help_render_below_view) {
                    b.setTooltip(Tooltip.create(getModeHelp(selectMode)));
                } 
            }    
        );
        if (!help_render_below_view) {
            modeButton.setTooltip(Tooltip.create(getModeHelp(selectMode)));
        }
        this.addRenderableWidget(modeButton);
    }

    private void sendItemChangeRequest() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new UpdateItemSettingsData(this.selectAnim, this.selectMode)
        );
    }

    private Component getModeTitle(ItemMode mode) {
        return Component.translatable("item.doggytalents.dog_anim_debug_stick.mode." 
            + mode.getId());
    }

    private Component getModeHelp(ItemMode mode) {
        return Component.translatable("item.doggytalents.dog_anim_debug_stick.mode." 
            + mode.getId() + ".help");
    }

    private void initEntries() {
        this.updateEntries(getAnimNameList());
    }

    private List<String> getAnimNameList() {
        return this.animList.stream()
            .map(x -> x.toString())
            .collect(Collectors.toList());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        renderHelp(graphics, selectMode);
    }

    private boolean shouldRenderHelpBelow() {
        return this.height > 353;
    }

    private void renderHelp(GuiGraphics graphics, ItemMode mode) {
        if (!shouldRenderHelpBelow())
            return;
        int mX = this.width / 2;
        var desc = getModeHelp(mode);
        var max_width = Math.min(360, this.width - 10);
        var desc_lines = font.split(desc, max_width);
        int tX = mX - font.width(title)/2;
        int tY = this.height/2 + this.getSelectAreaSize()/2 + 20;
        for (var line : desc_lines) {
            tX = mX - font.width(line)/2;
            graphics.drawString(font, line, tX, tY, 0xffffffff);
            tY += font.lineHeight + 2;
        }
    }

    @Override
    protected boolean matchIgnoreCaseSearch() {
        return true;
    }

}
