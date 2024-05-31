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

public class CanineTrackerScreen extends Screen {

    
    private Rect2i rect;
    private Player player;
    private final ArrayList<String> dogNameList;
    private final ArrayList<UUID> dogIdList;
    private final ArrayList<String> dogNameFilterList;
    private final ArrayList<UUID> dogIdFilterList;
    //private int hightlightDogName;
    private int hightLightDogNamePerPage;
    private boolean showUuid = false;
    private String value = "";
    private int maxPages = 1;
    private int pageNumber = 0;
    private final int MAX_PAGES_ENTRIES = 19;
    private TextOnlyButton prevPageButton;
    private TextOnlyButton nextPageButton;
    
    private final int HLC_HEEL_NO_SIT = 0xFF10F9; 
    private Map<UUID, Integer> dogDistanceMap = Maps.newConcurrentMap();
    private Map<UUID, BlockPos> dogPosMap = Maps.newConcurrentMap();
 
    private int hightlightTextColor = HLC_HEEL_NO_SIT;
 
    private final int MAX_BUFFER_SIZE = 64;

    private int mouseX0;
    private int mouseY0;

    public CanineTrackerScreen(Player player) {
        super(ComponentUtil.translatable("doggytalents.screen.conducting_bone"));
        this.player = player;   
        this.dogNameList = new ArrayList<String>(4);
        this.dogIdList = new ArrayList<UUID>(4);
        this.dogIdFilterList = new ArrayList<UUID>(4);
        this.dogNameFilterList = new ArrayList<String>(4);
        this.hightLightDogNamePerPage = 0;
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        var screen = new CanineTrackerScreen(mc.player);
        mc.setScreen(screen);
        screen.requestDogs();
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.rect = new Rect2i(0, 0,500, 500);
        
        Button showUuid = new Button(3, 3, 60, 20, ComponentUtil.translatable("doggytalents.screen.whistler.heel_by_name.show_uuid"), (btn) -> {
            btn.setMessage(ComponentUtil.translatable("doggytalents.screen.whistler.heel_by_name."
                + (this.showUuid? "show" : "hide")
                +"_uuid"));
            this.showUuid = !this.showUuid;
        });

        Button help = new Button(3, 26, 20, 20, ComponentUtil.literal("?"), b -> {} ) {
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

        addPageButtons();

        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
    }

    private void addPageButtons() {
        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
        var prevPage = new TextOnlyButton(half_width - 120, 
            half_height - 10, 20, 20, ComponentUtil.literal("<"), b -> {
                this.pageNumber = Math.max(0, this.pageNumber - 1);
                this.hightLightDogNamePerPage = 0;
            }, font);
        var nextPage = new TextOnlyButton(half_width + 100, 
            half_height - 10, 20, 20, ComponentUtil.literal(">"), b -> {
                this.pageNumber = Math.min(maxPages - 1, this.pageNumber + 1);
                this.hightLightDogNamePerPage = 0;
            }, font);
        prevPage.active = this.pageNumber > 0;
        nextPage.active = this.pageNumber < maxPages - 1;
        this.prevPageButton = prevPage;
        this.nextPageButton = nextPage;
        this.addRenderableWidget(prevPage);
        this.addRenderableWidget(nextPage);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.mouseX0 != mouseX || this.mouseY0 != mouseY) {
            this.onMouseMoved(mouseX, mouseY);
            this.mouseX0 = mouseX;
            this.mouseY0 = mouseY;
        }

        super.render(stack, mouseX, mouseY, partialTicks);

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
    
        Gui.fill(stack, half_width - 100, half_height - 100, half_width + 100, half_height + 100, Integer.MIN_VALUE);
        Gui.fill(stack, half_width - 100, half_height + 105, half_width + 100, half_height + 117, Integer.MIN_VALUE);

        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;
        int textx1 = half_width + 100 - 35;
        int texty1 = half_height - 100 + 2;
        if (this.dogNameFilterList.size() <= 0) {
            this.font.draw(stack, 
                I18n.get("doggytalents.screen.conducting_bone.no_dog_found"), 
                textx, texty + offset, 0xf50a0a);
        }

        int startIndx = this.pageNumber * MAX_PAGES_ENTRIES;
        int drawNo = 0;
        for (int i = startIndx; i < this.dogNameFilterList.size(); ++i) {
            int color = 0xffffffff;
            if (i == this.pageNumber*MAX_PAGES_ENTRIES +  this.hightLightDogNamePerPage) 
                color = this.hightlightTextColor;
            var id = this.dogIdFilterList.get(i);
            String text = this.dogNameFilterList.get(i) + (
            this.showUuid ? 
                " ( " + id + " ) " :
                ""
            );
            this.font.draw(stack, text, textx, texty + offset, color);
            int dist = this.dogDistanceMap.get(id);
            String text1 = 
                this.showUuid ? 
                "" : (dist > 99_999 ? "far" : "" + dist);
            this.font.draw(stack, text1, textx1, texty1 + offset, color);
            offset+=10;
            if (++drawNo >= MAX_PAGES_ENTRIES) break;
        }

        int txtorgx = half_width - 90;
        int txtorgy = half_height + 107;
    
        font.draw(stack, this.value + "_", txtorgx, txtorgy,  0xffffffff);

        var pageStr = (pageNumber + 1) + "/" + maxPages;
        var pageStrWidth = font.width(pageStr);
        font.draw(stack, pageStr, half_width - pageStrWidth/2, 
            half_height - 110, 0xffffffff);
        prevPageButton.active = this.pageNumber > 0;
        nextPageButton.active = this.pageNumber < maxPages - 1;
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
        int newIndx = getHoveredIndex(x, y, getCurrentPageEntries());
        if (newIndx < 0) return;
        this.hightLightDogNamePerPage = newIndx;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        boolean ret = super.mouseClicked(x, y, p_94697_);
        int mX = this.width/2;
        int mY = this.height/2;
        if (Math.abs(x - mX) > 100) return ret;
        if (Math.abs(y - mY) > 100) return ret;
        int indx = getHoveredIndex(x, y, getCurrentPageEntries());
        indx += pageNumber * MAX_PAGES_ENTRIES;
        if (indx >= 0 && indx < this.dogIdFilterList.size()) {
            var uuid = this.dogIdFilterList.get(indx);
            var name = this.dogNameFilterList.get(indx);
            this.startLocateDog(uuid);
            Minecraft.getInstance().setScreen(null);
        }
        return ret;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (keyCode == 264) {
            int currentPageEntries = getCurrentPageEntries() - 1;
            this.hightLightDogNamePerPage = 
                Mth.clamp(this.hightLightDogNamePerPage + 1, 0, currentPageEntries);
            return true;
        } else if (keyCode == 265) {
            int currentPageEntries = getCurrentPageEntries() - 1;
            this.hightLightDogNamePerPage = 
                Mth.clamp(this.hightLightDogNamePerPage - 1, 0, currentPageEntries);
            return true;
        } else if (keyCode == 263) {
            if (this.prevPageButton.active)
            this.prevPageButton.onClick(0, 0);
            return true;
        } else if (keyCode == 262) {
            if (this.nextPageButton.active)
            this.nextPageButton.onClick(0, 0);
            return true;
        } else if (keyCode == 257) {
            if (this.dogIdFilterList.isEmpty()) return false; 
            var selectedId = getSelectedId();
            if (selectedId >= 0 && selectedId < this.dogIdFilterList.size()) {
                var uuid = this.dogIdFilterList.get(selectedId);
                var name = this.dogNameFilterList.get(selectedId);
                this.startLocateDog(uuid);
            }
            this.minecraft.setScreen(null);
            return true;
        } else if (keyCode == 259) {
            this.popCharInText();
            return true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private int getCurrentPageEntries() {
        if (this.pageNumber >= this.maxPages - 1) {
            return this.dogIdFilterList.size() % MAX_PAGES_ENTRIES;
        }
        return MAX_PAGES_ENTRIES;
    }

    private int getSelectedId() {
        return this.pageNumber * MAX_PAGES_ENTRIES + this.hightLightDogNamePerPage;
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
        this.dogNameFilterList.clear();
        this.dogIdFilterList.clear();
        this.hightLightDogNamePerPage =0;

        if (this.value == "") {
            for (var i : this.dogNameList) {
                this.dogNameFilterList.add(i);
            }
            for (var i : this.dogIdList) {
                this.dogIdFilterList.add(i);
            }
        } else {
            for (int i = 0; i < this.dogIdList.size(); ++i) {
                if (this.dogNameList.get(i).length() < this.value.length()) continue; 
                if (this.dogNameList.get(i).contains(this.value)) {
                    this.dogIdFilterList.add(this.dogIdList.get(i));
                    this.dogNameFilterList.add(this.dogNameList.get(i));
                }
            }
        }
        updatePages();
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

    private void updatePages() {
        int dogNumbers = this.dogIdFilterList.size();
        this.maxPages = 1 + dogNumbers / MAX_PAGES_ENTRIES;
        this.pageNumber = 0;
        this.hightLightDogNamePerPage = 0;
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

    private void requestDogs() {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new CanineTrackerData.RequestDogsData());
    }

    public void assignResponse(List<Triple<UUID, String, BlockPos>> dogLs) {
        for (var entry : dogLs) {
            this.dogNameList.add(entry.getMiddle());
            this.dogNameFilterList.add(entry.getMiddle());
            this.dogIdList.add(entry.getLeft());
            this.dogIdFilterList.add(entry.getLeft());
            var pos = entry.getRight();
            int distance = Mth.ceil(Mth.sqrt((float)this.player.blockPosition().distSqr(pos)));
            this.dogDistanceMap.put(entry.getLeft(), distance);
            this.dogPosMap.put(entry.getLeft(), pos);
        }
        updatePages();
    }

    private void startLocateDog(UUID uuid) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new CanineTrackerData.StartLocatingData(uuid));
    }

}
