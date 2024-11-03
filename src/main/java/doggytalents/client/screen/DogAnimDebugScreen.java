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
        var modeButton = new FlatButton(
            mx - this.getSelectAreaSize()/2 -modeButton_width - 2, 
            my - getSelectAreaSize()/2, 
            modeButton_width, modeButton_height, getModeTitle(selectMode),
            b -> {
                var new_mode = selectMode.cycleMode();
                selectMode = new_mode;
                b.setMessage(getModeTitle(selectMode));
                sendItemChangeRequest();
            }    
        );
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

}
