package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.parse.ANTLRParser.parserRule_return;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByNameData;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;



public class HeelByNameScreen extends Screen {

   private Rect2i rect;
   private Player player;
   private List<String> dogNameList;
   private List<Integer> dogIdList;
   private List<String> dogNameFilterList;
   private List<Integer> dogIdFilterList;
   private int hightlightDogName;
   private boolean showUuid = false;
   private String value = "";
   private boolean heelAndSit = false;
   
   private final int HLC_HEEL_NO_SIT = 0xFF10F9; 
   private final int HLC_HEEL_AND_SIT = 0xff6f00; 

   private int hightlightTextColor = HLC_HEEL_NO_SIT;

    private int mouseX0;
    private int mouseY0;

   
    private final int MAX_BUFFER_SIZE = 64;

    public HeelByNameScreen(Player player) {
        super(Component.translatable("doggytalents.screen.whistler.heel_by_name"));
        this.player = player;   
        this.dogNameList = new ArrayList<String>(4);
        this.dogIdList = new ArrayList<Integer>(4);
        this.dogIdFilterList = new ArrayList<Integer>(4);
        this.dogNameFilterList = new ArrayList<String>(4);
        this.hightlightDogName = 0;

        List<Dog> dogsList = Minecraft.getInstance().level.getEntitiesOfClass(Dog.class, this.player.getBoundingBox().inflate(100D, 50D, 100D), d -> d.isOwnedBy(player));
        for (Dog d : dogsList) {
            this.dogNameList.add(d.getName().getString());
            this.dogNameFilterList.add(d.getName().getString());
            this.dogIdList.add(d.getId());
            this.dogIdFilterList.add(d.getId());
        }
    }

    public static void open() { 
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new HeelByNameScreen(mc.player));
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.rect = new Rect2i(0, 0,500, 500);
        
        Button showUuid = new Button(3, 3, 60, 20, Component.translatable("doggytalents.screen.whistler.heel_by_name.show_uuid"), (btn) -> {
            btn.setMessage(Component.translatable("doggytalents.screen.whistler.heel_by_name."
                + (this.showUuid? "show" : "hide")
                +"_uuid"));
            this.showUuid = !this.showUuid;
        });

        Button help = new Button(3, 26, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            public void renderToolTip(PoseStack stack, int mouseX, int mouseY) {
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.whistler.heel_by_name.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.heel_by_name.help");
                list.addAll(ScreenUtil.splitInto(str, 150, HeelByNameScreen.this.font));

                HeelByNameScreen.this.renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
    }

 
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        if (this.mouseX0 != mouseX || this.mouseY0 != mouseY) {
            this.onMouseMoved(mouseX, mouseY);
            this.mouseX0 = mouseX;
            this.mouseY0 = mouseY;
        }

        int half_width = this.width >> 1;
        int half_height = this.height >> 1; 
      
        Gui.fill(stack, half_width - 100, half_height - 100, half_width + 100, half_height + 100, Integer.MIN_VALUE);
        Gui.fill(stack, half_width - 100, half_height + 105, half_width + 100, half_height + 117, Integer.MIN_VALUE);

        super.render(stack, mouseX, mouseY, partialTicks);
        int offset = 0;
        int textx = half_width - 100 + 2;
        int texty = half_height - 100 + 2;
        if (this.dogNameFilterList.size() <= 0) {
            this.font.draw(stack, 
                I18n.get("doggytalents.screen.whistler.heel_by_name.no_dog_found"), 
                textx, texty + offset, 0xf50a0a);
        }

        for (int i = 0; i < this.dogNameFilterList.size(); ++i) {
            int color = 0xffffffff;
            if (i == this.hightlightDogName) color = this.hightlightTextColor;
            String text = this.dogNameFilterList.get(i) + (
                this.showUuid ? 
                " ( " + this.minecraft.level.getEntity(this.dogIdFilterList.get(i)).getStringUUID() + " ) " :
                ""
            );
            this.font.draw(stack, text, textx, texty + offset, color);
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
        int newIndx = getHoveredIndex(x, y, this.dogIdFilterList.size());
        if (newIndx < 0) return;
        this.hightlightDogName = newIndx;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        boolean ret = super.mouseClicked(x, y, p_94697_);
        int mX = this.width/2;
        int mY = this.height/2;
        if (Math.abs(x - mX) > 100) return ret;
        if (Math.abs(y - mY) > 100) return ret;
        int indx = getHoveredIndex(x, y, this.dogIdFilterList.size());
        if (indx >= 0) {
            this.requestHeel(this.dogIdFilterList.get(indx));
            Minecraft.getInstance().setScreen(null);
        }
        return ret;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (keyCode == 264) {
            this.hightlightDogName = Mth.clamp(this.hightlightDogName +1, 0, this.dogNameFilterList.size()-1);
        } else if (keyCode == 265) {
            this.hightlightDogName = Mth.clamp(this.hightlightDogName -1, 0, this.dogNameFilterList.size()-1);
        } else if (keyCode == 257) {
            if (this.dogIdFilterList.isEmpty()) return false; 
            this.requestHeel(this.dogIdFilterList.get(this.hightlightDogName));
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
        this.dogNameFilterList.clear();
        this.dogIdFilterList.clear();
        this.hightlightDogName =0;

        if (this.value == "") {
            for (String i : this.dogNameList) {
                this.dogNameFilterList.add(i);
            }
            for (Integer i : this.dogIdList) {
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


    private void requestHeel(int id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new HeelByNameData(id, this.heelAndSit));
    }

}