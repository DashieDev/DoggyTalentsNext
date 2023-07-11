package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByGroupData;
import doggytalents.common.network.packet.data.HeelByNameData;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;



public class HeelByGroupScreen extends Screen {

   private Rect2i rect;
   private Player player;
   private List<DogGroup> dogGroupList;
   private List<DogGroup> dogGroupFilterList;
   private int hightlightDogGroup;
   private boolean showUuid = false;
   private String value = "";
   private boolean heelAndSit = false;
   
   private final int HLC_HEEL_NO_SIT = 0xFF10F9; 
   private final int HLC_HEEL_AND_SIT = 0xff6f00; 

   private int hightlightTextColor = HLC_HEEL_NO_SIT;

    private int mouseX0 = -1;
    private int mouseY0 = -1;

   
    private final int MAX_BUFFER_SIZE = 16;

    public HeelByGroupScreen(Player player) {
        super(ComponentUtil.translatable("doggytalents.screen.heel_by_group"));
        this.player = player;   
        this.dogGroupList = new ArrayList<DogGroup>();
        this.dogGroupFilterList = new ArrayList<DogGroup>();
        this.hightlightDogGroup = 0;
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        var screen  = new HeelByGroupScreen(mc.player); 
        mc.setScreen(screen);
        screen.requestGroups();
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.rect = new Rect2i(0, 0,500, 500);

        Button help = new Button(3, 26, 20, 20, ComponentUtil.literal("?"), b -> {} ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(ComponentUtil.translatable("doggytalents.screen.heel_by_group.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.heel_by_group.help");
                list.addAll(ScreenUtil.splitInto(str, 150, HeelByGroupScreen.this.font));

                HeelByGroupScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(help);
    }

 
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
        
        if (this.mouseX0 != mouseX || this.mouseY0 != mouseY) {
            if (this.mouseX0 < 0 || this.mouseY0 < 0) {
                int dx = Math.abs(mouseX - half_width);
                int dy = Math.abs(mouseY - half_height);
                if (dx > 10 || dy > 10) {
                    this.onMouseMoved(mouseX, mouseY);
                    this.mouseX0 = mouseX;
                    this.mouseY0 = mouseY;
                }
            } else {
                this.onMouseMoved(mouseX, mouseY);
                this.mouseX0 = mouseX;
                this.mouseY0 = mouseY;
            }
            
        }
      
        Gui.fill(stack, half_width - 100, half_height - 100, half_width + 100, half_height + 100, Integer.MIN_VALUE);
        Gui.fill(stack, half_width - 100, half_height + 105, half_width + 100, half_height + 117, Integer.MIN_VALUE);

        super.render(stack, mouseX, mouseY, partialTicks);
        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;
        if (this.dogGroupFilterList.size() <= 0) {
            this.font.draw(stack, 
                I18n.get("doggytalents.screen.heel_by_group.no_group_found"), 
                textx, texty + offset, 0xf50a0a);
        }

        for (int i = 0; i < this.dogGroupFilterList.size(); ++i) {
            int color = 0xffffffff;
            var group = this.dogGroupFilterList.get(i);
            if (i == this.hightlightDogGroup) color = this.hightlightTextColor;
            fill(stack, textx, texty -1 + offset, textx + 9, texty -1 + offset + 9, group.color);
            String text = group.name;
            this.font.draw(stack, text, textx + 10, texty + offset, color);
            offset+=10;
            if (offset > 190) break;
        }

        int txtorgx = half_width - 90;
        int txtorgy = half_height + 107;
        
        this.font.draw(stack, this.value + "_", txtorgx, txtorgy,  0xffffffff);
         
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
        int newIndx = getHoveredIndex(x, y, this.dogGroupFilterList.size());
        if (newIndx < 0) return;
        this.hightlightDogGroup = newIndx;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        boolean ret = super.mouseClicked(x, y, p_94697_);
        int mX = this.width/2;
        int mY = this.height/2;
        if (Math.abs(x - mX) > 100) return ret;
        if (Math.abs(y - mY) > 100) return ret;
        int indx = getHoveredIndex(x, y, this.dogGroupFilterList.size());
        if (indx >= 0) {
            requestHeelByGroup(this.dogGroupFilterList.get(indx), this.heelAndSit);
            Minecraft.getInstance().setScreen(null);
        }
        return ret;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (keyCode == 264) {
            this.hightlightDogGroup = Mth.clamp(this.hightlightDogGroup +1, 0, this.dogGroupFilterList.size()-1);
        } else if (keyCode == 265) {
            this.hightlightDogGroup = Mth.clamp(this.hightlightDogGroup -1, 0, this.dogGroupFilterList.size()-1);
        } else if (keyCode == 257) {
            if (this.dogGroupFilterList.isEmpty()) return false; 
            requestHeelByGroup(this.dogGroupFilterList.get(this.hightlightDogGroup), this.heelAndSit);
            this.minecraft.setScreen(null);
        } else if (keyCode == 259) {
            this.popCharInText();
        } else if (keyCode == 340) {
            this.hightlightTextColor = HLC_HEEL_AND_SIT;
            this.heelAndSit = true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 340) {
            this.hightlightTextColor = HLC_HEEL_NO_SIT;
            this.heelAndSit = false;
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
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
        this.dogGroupFilterList.clear();
        this.hightlightDogGroup =0;

        if (this.value == "") {
            for (var i : this.dogGroupList) {
                this.dogGroupFilterList.add(i);
            }
        } else {
            for (int i = 0; i < this.dogGroupList.size(); ++i) {
                if (this.dogGroupList.get(i).name.length() < this.value.length()) continue; 
                if (this.dogGroupList.get(i).name.contains(this.value)) {
                    this.dogGroupFilterList.add(this.dogGroupList.get(i));
                }
            }
        }
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
    
    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void requestGroups() {
        PacketHandler.send(
            PacketDistributor.SERVER.noArg(),
            new HeelByGroupData.REQUEST_GROUP_LIST()    
        );
    }

    private void requestHeelByGroup(DogGroup group, boolean heelAndSit) {
        PacketHandler.send(
            PacketDistributor.SERVER.noArg(),
            new HeelByGroupData.REQUEST_HEEL(group, heelAndSit)    
        );
    }

    public void assignResponse(List<DogGroup> groups) {
        for (var group : groups) {
            this.dogGroupList.add(group);
            this.dogGroupFilterList.add(group);
        }
    }

}