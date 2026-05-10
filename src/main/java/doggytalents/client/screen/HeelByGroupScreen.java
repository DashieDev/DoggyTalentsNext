package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.client.screen.framework.widget.FlatButton;
import net.minecraft.client.input.KeyEvent;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByGroupData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import doggytalents.common.network.PacketDistributor;



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
            protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                super.extractContents(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.heel_by_group.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(str, 150, HeelByGroupScreen.this.font));

                graphics.setComponentTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(help);
    }

    @Override
    protected void drawNoEntryMsg(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.text(font, 
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
    protected void drawEntry(GuiGraphicsExtractor graphics, int entry_x, int entry_y,
        int entry_id, boolean is_selected) {

        super.drawEntry(graphics, entry_x + 12, entry_y, entry_id, is_selected);
        var group = this.dogGroupList.get(entry_id);
        graphics.fill(entry_x, entry_y -1, entry_x + 9, entry_y -1 + 9, group.color);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == com.mojang.blaze3d.platform.InputConstants.KEY_LSHIFT) {
            this.heelAndSit = true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        if (event.key() == com.mojang.blaze3d.platform.InputConstants.KEY_LSHIFT) {
            this.heelAndSit = false;
        }
        return super.keyReleased(event);
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