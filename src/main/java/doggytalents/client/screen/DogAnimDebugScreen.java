package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

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
    private GuiItemMode selectMode = GuiItemMode.ANIM;
    private RotationLockMode selectRotMode = RotationLockMode.YROT;
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
        var mode_pair = getGuiItemMode(DogAnimDebugItem.getItemMode(stack));
        this.selectMode = mode_pair.getLeft();
        this.selectRotMode = mode_pair.getRight();
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
        reInitEntries();
        addModeButton();
    }

    @Override
    protected void onEntrySelected(int id) {

        if (isAnimEntries()) {
            var anim_selected = this.animList.get(id);
            PacketHandler.send(PacketDistributor.SERVER.noArg(),
                new UpdateItemSettingsData(anim_selected, getItemMode(this.selectMode, this.selectRotMode))
            );
        } else {
            var rot_mode_selected = RotationLockMode.values()[id];
            PacketHandler.send(PacketDistributor.SERVER.noArg(),
                new UpdateItemSettingsData(this.selectAnim, getItemMode(this.selectMode, rot_mode_selected))
            );
        }
        
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
                this.reInitEntries();
            }    
        );
        if (!help_render_below_view) {
            modeButton.setTooltip(Tooltip.create(getModeHelp(selectMode)));
        }
        this.addRenderableWidget(modeButton);
    }

    private void sendItemChangeRequest() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(),
            new UpdateItemSettingsData(this.selectAnim, getItemMode(this.selectMode, this.selectRotMode))
        );
    }

    private Component getModeTitle(GuiItemMode mode) {
        final int id = switch(mode) {
            case ANIM -> 0;
            case TIME_SET -> 1;
            case ROTATION_LOCK -> 2;
        };
        return Component.translatable("item.doggytalents.dog_anim_debug_stick.mode." 
            + id);
    }

    private Component getModeHelp(GuiItemMode mode) {
        final int id = switch(mode) {
            case ANIM -> 0;
            case TIME_SET -> 1;
            case ROTATION_LOCK -> 2;
        };
        return Component.translatable("item.doggytalents.dog_anim_debug_stick.mode." 
            + id + ".help");
    }

    private void reInitEntries() {
        var entries = isAnimEntries() ? getAnimNameList()
            : getRotationLockModeList();
        this.updateEntries(entries);
    }

    private boolean isAnimEntries() {
        return this.selectMode != GuiItemMode.ROTATION_LOCK;
    }

    private List<String> getAnimNameList() {
        return this.animList.stream()
            .map(x -> x.toString())
            .collect(Collectors.toList());
    }

    private List<String> getRotationLockModeList() {
        return Arrays.stream(RotationLockMode.values())
            .map(RotationLockMode::toString)
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

    private void renderHelp(GuiGraphics graphics, GuiItemMode mode) {
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

    //is this code
    private ItemMode getItemMode(GuiItemMode mode, RotationLockMode rotMode) {
        return switch(mode) {
            case ANIM -> ItemMode.ANIM;
            case TIME_SET -> ItemMode.TIME_SET;
            case ROTATION_LOCK -> switch(rotMode) {
                case YROT -> ItemMode.YROT;
                case HEAD_YROT -> ItemMode.HEAD_YROT;
                case HEAD_XROT -> ItemMode.HEAD_XROT;
                case BANKING -> ItemMode.BANKING;
                case TAIL_XROT -> ItemMode.TAIL_XROT;
            };
        };
    }

    private Pair<GuiItemMode, RotationLockMode> getGuiItemMode(ItemMode mode) {
        return switch(mode) {
            case ANIM -> Pair.of(GuiItemMode.ANIM, RotationLockMode.YROT);
            case TIME_SET -> Pair.of(GuiItemMode.TIME_SET, RotationLockMode.YROT);
            case YROT -> Pair.of(GuiItemMode.ROTATION_LOCK, RotationLockMode.YROT);
            case HEAD_YROT -> Pair.of(GuiItemMode.ROTATION_LOCK, RotationLockMode.HEAD_YROT);
            case HEAD_XROT -> Pair.of(GuiItemMode.ROTATION_LOCK, RotationLockMode.HEAD_XROT);
            case BANKING -> Pair.of(GuiItemMode.ROTATION_LOCK, RotationLockMode.BANKING);
            case TAIL_XROT -> Pair.of(GuiItemMode.ROTATION_LOCK, RotationLockMode.TAIL_XROT);
        };
    }

    private static enum GuiItemMode {
        ANIM, TIME_SET, ROTATION_LOCK;

        public GuiItemMode cycleMode() {
            return switch (this) {
                case ANIM -> TIME_SET;
                case TIME_SET -> ROTATION_LOCK;
                case ROTATION_LOCK -> ANIM;   
            };
        }
    }

    private static enum RotationLockMode {
        YROT, HEAD_YROT, HEAD_XROT, BANKING, TAIL_XROT;
    }

}
