package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ConductingBoneData;
import doggytalents.common.network.packet.data.CanineTrackerData;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class CanineTrackerScreen extends StringEntrySelectScreen {

    private Player player;

    private final ArrayList<String> dogNameList = new ArrayList<>();
    private final ArrayList<UUID> dogIdList = new ArrayList<>();
    private final Map<UUID, Integer> dogDistanceMap = Maps.newHashMap();
    private final Map<UUID, BlockPos> dogPosMap = Maps.newHashMap();

    private boolean showUuid = false;

    public CanineTrackerScreen(Player player) {
        super(Component.translatable("doggytalents.screen.conducting_bone"));
        this.player = player;
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        var screen = new CanineTrackerScreen(mc.player);
        mc.setScreen(screen);
        screen.requestDogs();
    }

    @Override
    protected void addUtilitiesButton() {
        int mX = this.width/2;
        int mY = this.height/2;
        int pY = mY - 100;
        var showUuid = new FlatButton(mX - 100 - 60 - 2, pY, 60, 20, Component.translatable("doggytalents.screen.whistler.heel_by_name.show_uuid"), (btn) -> {
            btn.setMessage(Component.translatable("doggytalents.screen.whistler.heel_by_name."
                + (this.showUuid? "show" : "hide")
                +"_uuid"));
            this.showUuid = !this.showUuid;
        });
        pY += showUuid.getHeight() + 2;
        
        var help = new FlatButton(mX - 100 - 20 - 2, pY, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(ComponentUtil.translatable("doggytalents.screen.radar.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.radar.help");
                list.addAll(ScreenUtil.splitInto(str, 150, CanineTrackerScreen.this.font));

                CanineTrackerScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };

        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
    }

    @Override
    protected void drawEntry(GuiGraphics graphics, int entry_x, int entry_y, int start_indx, int render_indx,
            int selected_entry_in_page) {
        super.drawEntry(graphics, entry_x, entry_y, start_indx, render_indx, selected_entry_in_page);
        int textx1 = this.width/2 + 100 - 35;
        var dogId = this.dogIdList.get(render_indx);
        int dist = this.dogDistanceMap.get(dogId);
        String text1 = 
            this.showUuid ? 
            "" : (dist > 99_999 ? "far" : "" + dist);
        int color = 0xffffffff;
        boolean is_selected_entry =
            render_indx == start_indx + selected_entry_in_page;
        if (is_selected_entry) 
            color = this.getHightlightSelectedColor();
        graphics.drawString(font, text1, textx1, entry_y, color);
    }

    @Override
    protected void drawNoEntryMsg(GuiGraphics graphics, int x, int y) {
        graphics.drawString(font, 
            I18n.get("doggytalents.screen.conducting_bone.no_dog_found"), 
            x, y, 0xf50a0a);
    }

    @Override
    protected String modifyEntryText(String entryText, int entryId) {
        if (this.showUuid) {
            entryText = entryText +
                " ( " + this.dogIdList.get(entryId) + " ) ";
        }

        return entryText;
    }

    @Override
    protected void onEntrySelected(int id) {
        var uuid = this.dogIdList.get(id);
        this.startLocateDog(uuid);
        Minecraft.getInstance().setScreen(null);
    }

    private void requestDogs() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new CanineTrackerData.RequestDogsData());
    }

    public void assignResponse(List<Triple<UUID, String, BlockPos>> dogLs) {
        for (var entry : dogLs) {
            this.dogNameList.add(entry.getMiddle());
            this.dogIdList.add(entry.getLeft());
            var pos = entry.getRight();
            int distance = Mth.ceil(Mth.sqrt((float)this.player.blockPosition().distSqr(pos)));
            this.dogDistanceMap.put(entry.getLeft(), distance);
            this.dogPosMap.put(entry.getLeft(), pos);
        }
        this.updateEntries(dogNameList);
    }

    private void startLocateDog(UUID uuid) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new CanineTrackerData.StartLocatingData(uuid));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}