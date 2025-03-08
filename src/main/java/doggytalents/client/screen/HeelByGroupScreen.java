package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByGroupData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.network.PacketDistributor;



public class HeelByGroupScreen extends StringEntrySelectScreen {

    private final List<DogGroup> dogGroupList = new ArrayList<>();

    private boolean heelAndSit = false;

    private final int HLC_HEEL_AND_SIT = 0xff6f00;

    public HeelByGroupScreen() {
        super(Component.translatable("doggytalents.screen.heel_by_group"));
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        var screen  = new HeelByGroupScreen(); 
        mc.setScreen(screen);
        screen.requestGroups();
    }

    @Override
    protected void addUtilitiesButton() {
        int mX = this.width/2;
        int mY = this.height/2;

        var help = new FlatButton(mX - 100 - 20 - 2, mY - 100, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.heel_by_group.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(str, 150, HeelByGroupScreen.this.font));

                HeelByGroupScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(help);
    }

    @Override
    protected void drawNoEntryMsg(PoseStack graphics, int x, int y) {
        font.draw(graphics, 
            I18n.get("doggytalents.screen.heel_by_group.no_group_found"), 
            x, y, 0xf50a0a);
    }

    @Override
    protected Component modifyEntryText(Component entryText, int entryId, boolean is_selected) {
        if (this.heelAndSit && is_selected) {
            entryText = entryText.copy()
                .withStyle(Style.EMPTY.withColor(HLC_HEEL_AND_SIT));   
        }
        return entryText;
    }

    @Override
    protected void drawEntry(PoseStack graphics, int entry_x, int entry_y,
        int entry_id, boolean is_selected) {

        super.drawEntry(graphics, entry_x + 12, entry_y, entry_id, is_selected);
        var group = this.dogGroupList.get(entry_id);
        fill(graphics, entry_x, entry_y -1, entry_x + 9, entry_y -1 + 9, group.color);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_LSHIFT) {
            this.heelAndSit = true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_LSHIFT) {
            this.heelAndSit = false;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    protected void onEntrySelected(int id) {
        requestHeelByGroup(this.dogGroupList.get(id), this.heelAndSit);
        Minecraft.getInstance().setScreen(null);
    }

    private void requestGroups() {
        PacketHandler.send(
            PacketDistributor.SERVER.noArg(),
            new HeelByGroupData.REQUEST_GROUP_LIST()    
        );
    }

    public void assignResponse(List<DogGroup> groups) {
        this.dogGroupList.clear();
        for (var group : groups) {
            this.dogGroupList.add(group);
        }
        this.updateEntries(this.dogGroupList.stream()
            .map(x -> x.name).collect(Collectors.toList()));
    }

    private void requestHeelByGroup(DogGroup group, boolean heelAndSit) {
        PacketHandler.send(
            PacketDistributor.SERVER.noArg(),
            new HeelByGroupData.REQUEST_HEEL(group, heelAndSit)    
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}