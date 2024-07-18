package doggytalents.client.screen;

import java.util.List;

import doggytalents.client.DTNClientPettingManager;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class PetSelectScreen extends StringEntrySelectScreen {

    public PetSelectScreen() {
        super(Component.empty());
    }

    public static void open() {
        var mc = Minecraft.getInstance();
        mc.setScreen(new PetSelectScreen());
    }

    @Override
    public void init() {
        super.init();
        initEntries();
    }

    private void initEntries() {
        this.updateEntries(List.of(
            I18n.get("dog.petmode.facerub"), 
            I18n.get("dog.petmode.hug"),
            I18n.get("doggytalents.screen.pet_select.exit")    
        ));
    }

    @Override
    protected void onEntrySelected(int id) {
        switch (id) {
        case 0:
            DTNClientPettingManager.get().setPetMode(DogPettingType.FACERUB);
            break;
        case 1:
            DTNClientPettingManager.get().setPetMode(DogPettingType.HUG);
            break;
        default:
            DTNClientPettingManager.get().setPetMode(null);
            break;
        }
        
        this.getMinecraft().setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
}
