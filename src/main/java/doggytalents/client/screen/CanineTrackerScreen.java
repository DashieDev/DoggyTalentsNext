package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ConductingBoneData;
import doggytalents.common.network.packet.data.CanineTrackerData;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import doggytalents.common.network.PacketDistributor;

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
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                super.renderWidget(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.radar.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(str, 150, CanineTrackerScreen.this.font));

                graphics.renderComponentTooltip(font, list, mouseX, mouseY);
            }
        };

        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
    }

    @Override
    protected void drawEntry(GuiGraphics graphics, int entry_x, int entry_y, 
        int entry_id, boolean is_selected) {

        super.drawEntry(graphics, entry_x, entry_y, entry_id, is_selected);
        int textx1 = this.width/2 + 100 - 35;
        var dogId = this.dogIdList.get(entry_id);
        int dist = this.dogDistanceMap.get(dogId);
        String text1 = (dist > 99_999 ? "far" : "" + dist);
        int color = 0xffffffff;
        if (is_selected) 
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
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        mayRenderShowUUID(graphics, mouseX, mouseY, partialTicks);
    }

    private void mayRenderShowUUID(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.showUuid)
            return;
        var hover_entry_optional = this.getHoveredEntry(mouseX, mouseY);
        if (!hover_entry_optional.isPresent())
            return;
        int entry_id = hover_entry_optional.get();
        var uuid = this.dogIdList.get(entry_id);
        var uuid_c1 = Component.literal(uuid.toString())
            .withStyle(ChatFormatting.GRAY);
        if (this.height >= 273) {
            int mX = this.width/2;
            int mY = this.height/2;

            int uuid_width = font.width(uuid_c1);
            int tX = mX - uuid_width/2;
            int tY = mY + getSelectAreaSize()/2 + 23;
            graphics.drawString(font, uuid_c1, tX, tY, 0xffffffff);
        } else {
            graphics.renderComponentTooltip(font, 
                List.of(uuid_c1), mouseX, mouseY);
        }
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
        this.dogNameList.clear();
        this.dogIdList.clear();
        this.dogDistanceMap.clear();
        this.dogPosMap.clear();
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