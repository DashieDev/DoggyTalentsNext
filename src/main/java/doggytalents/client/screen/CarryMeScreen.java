package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.CarryMeData;
import doggytalents.common.talent.WolfMountTalent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class CarryMeScreen extends StringEntrySelectScreen {

    private final List<String> dogNameList = new ArrayList<>();
    private final List<Integer> dogIdList = new ArrayList<>();

    public CarryMeScreen(Player player) {
        super(ComponentUtil.empty());
        var mc = Minecraft.getInstance();
        var level = mc.level;
        if (level == null)
            return;
    
        var carry_me_list = 
            level.getEntitiesOfClass(Dog.class, player.getBoundingBox().inflate(16, 5D, 16D), 
                d -> d.isOwnedBy(player) && WolfMountTalent.isValidCarryMeDog(d));
        for (Dog d : carry_me_list) {
            this.dogNameList.add(d.getName().getString());
            this.dogIdList.add(d.getId());
        }
        this.updateEntries(dogNameList);
    }

    public static void open(Player player) {
        var mc = Minecraft.getInstance();
        var screen = new CarryMeScreen(player);
        mc.setScreen(screen);
    }

    @Override
    protected void addUtilitiesButton() {
        int mX = this.width/2;
        int mY = this.height/2;

        var help = new FlatButton(mX - 100 - 20 - 2, mY - 100, 20, 20, ComponentUtil.literal("?"), b -> {} ) {
            @Override
            public void renderButton(PoseStack graphics, int mouseX, int mouseY, float pTicks) {
                super.renderButton(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(ComponentUtil.translatable("item.doggytalents.whistle.20")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(str, 150, CarryMeScreen.this.font));

                CarryMeScreen.this.renderComponentTooltip(graphics, list, mouseX, mouseY);
            }
        };
        
        this.addRenderableWidget(help);
    }

    @Override
    protected void onEntrySelected(int id) {
        this.requestCarryMe(this.dogIdList.get(id));
        Minecraft.getInstance().setScreen(null);
    }

    private void requestCarryMe(int id) {
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new CarryMeData(id));
    }
    
}
