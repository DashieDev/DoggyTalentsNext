package doggytalents.client.screen;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.AllStandSwitchModeData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class AllStandSwitchModeScreen extends StringEntrySelectScreen {

    private List<EnumMode> modeList = List.of();
    private Player player;

    protected AllStandSwitchModeScreen(Player player) {
        super(Component.empty());
        this.player = player;
    }

    public static void open(Player user) {
        var modeList = Arrays.stream(EnumMode.VALUES)
            .filter(x -> !x.canWander())
            .collect(Collectors.toList());
        var mc = Minecraft.getInstance();
        var screen = new AllStandSwitchModeScreen(user);
        mc.setScreen(screen);
        screen.setModeList(modeList);
    }

    public void setModeList(List<EnumMode> modeList) {
        if (modeList == null)
            return;
        this.modeList = modeList;
        Function<EnumMode, String> str_getter = mode_to_get -> {
            var unloc = mode_to_get.getUnlocalisedName();
            return I18n.get(unloc);
        };
        var modeList_str = 
            this.modeList.stream().map(str_getter)
            .collect(Collectors.toList());
        this.updateEntries(modeList_str);
    }

    @Override
    protected void onEntrySelected(int id) {
        var selected_mode = this.modeList.get(id);
        PacketHandler.send(PacketDistributor.SERVER.noArg(), 
            new AllStandSwitchModeData(selected_mode)
        );
        var mc = this.minecraft;
        if (mc != null)
            mc.setScreen(null);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
