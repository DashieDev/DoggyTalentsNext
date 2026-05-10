package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ConductingBoneData;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import doggytalents.common.network.PacketDistributor;

public class ConductingBoneScreen extends StringEntrySelectScreen {

    private Font font;

    private final ArrayList<String> dogNameList = new ArrayList<>();
    private final ArrayList<UUID> dogIdList = new ArrayList<>();

    private boolean showUuid = false;
    private boolean toBed = false;

    public ConductingBoneScreen() {
        super(Component.translatable("doggytalents.screen.conducting_bone"));
        this.font = Minecraft.getInstance().font;
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        var screen = new ConductingBoneScreen();
        if (mc.player != null && mc.player.isShiftKeyDown()) {
            screen.toBed = true;
        }
        mc.setScreen(screen);
        screen.requestDogs();
    }

    @Override
    protected void addUtilitiesButton() {
        int mX = this.width/2;
        int mY = this.height/2;

        int pY = mY - 100;

        var showUuid = new FlatButton(0, pY, 60, 20, Component.translatable("doggytalents.screen.whistler.heel_by_name.show_uuid"), (btn) -> {
            btn.setMessage(Component.translatable("doggytalents.screen.whistler.heel_by_name."
                + (this.showUuid? "show" : "hide")
                +"_uuid"));
            this.showUuid = !this.showUuid;
        });
        showUuid.setX(mX - 100 - showUuid.getWidth() - 2);
        pY += showUuid.getHeight() + 2;
        
        var toBedButton = new FlatButton(0, pY, 60, 20, Component.literal(this.toBed? "To Bed" : "To Self"), b -> {
            if (ConductingBoneScreen.this.toBed) {
                ConductingBoneScreen.this.toBed = false;
                b.setMessage(Component.literal("To Self"));
            } else {
                ConductingBoneScreen.this.toBed = true;
                b.setMessage(Component.literal("To Bed"));
            }
        } );
        toBedButton.setX(mX - 100 - toBedButton.getWidth() - 2);
        pY += toBedButton.getHeight() + 2;
        var help = new FlatButton(0, pY, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                super.extractContents(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.conducting_bone.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(str, 150, ConductingBoneScreen.this.font));

                graphics.setComponentTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        };
        help.setX(mX - 100 - help.getWidth() - 2);
        pY += help.getHeight() + 2;
        
        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
        this.addRenderableWidget(toBedButton);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        mayRenderShowUUID(graphics, mouseX, mouseY, partialTicks);
    }

    private void mayRenderShowUUID(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
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
            graphics.text(font, uuid_c1, tX, tY, 0xffffffff);
        } else {
            graphics.setComponentTooltipForNextFrame(font,
                List.of(uuid_c1), mouseX, mouseY);
        }
    }

    @Override
    protected void drawNoEntryMsg(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.text(font,
            I18n.get("doggytalents.screen.conducting_bone.no_dog_found"), 
            x, y, 0xf50a0a);
    }

    @Override
    protected void onEntrySelected(int id) {
        this.requestDistantTeleport(this.dogIdList.get(id));
        Minecraft.getInstance().setScreen(null);
    }

    private void requestDogs() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new ConductingBoneData.RequestDogsData());
    }

    public void assignResponse(List<Pair<UUID, String>> dogLs) {
        this.dogNameList.clear();
        this.dogIdList.clear();
        for (var entry : dogLs) {
            this.dogNameList.add(entry.getRight());
            this.dogIdList.add(entry.getLeft());
        }
        this.updateEntries(dogNameList);
    }

    private void requestDistantTeleport(UUID dogUuid) {
        PacketHandler.send(
            PacketDistributor.SERVER.noArg(), 
            new ConductingBoneData.RequestDistantTeleportDogData(dogUuid, this.toBed)
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
