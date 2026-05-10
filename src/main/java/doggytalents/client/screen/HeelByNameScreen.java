package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.input.KeyEvent;

import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.client.screen.framework.widget.TextOnlyButton;
import doggytalents.client.screen.widget.CustomButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByNameData;
import doggytalents.common.util.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import doggytalents.common.network.PacketDistributor;



public class HeelByNameScreen extends StringEntrySelectScreen {

    private final Player player;
        
    private final List<String> dogNameList = new ArrayList<>();
    private final List<Integer> dogIdList = new ArrayList<>();

    private boolean showUuid = false;
    private boolean heelAndSit = false;
    private boolean softHeel = false;

    private final int HLC_HEEL_AND_SIT = 0xff6f00; 

    public HeelByNameScreen(Player player, boolean softHeel) {
        super(Component.translatable("doggytalents.screen.whistler.heel_by_name"));
        this.player = player;
        this.softHeel = softHeel;
        List<Dog> dogsList = Minecraft.getInstance().level.getEntitiesOfClass(Dog.class, this.player.getBoundingBox().inflate(100D, 50D, 100D), d -> d.isOwnedBy(player));
        dogsList = FrequentHeelStore.get(this).sortDogList(dogsList);
        for (Dog d : dogsList) {
            this.dogNameList.add(d.getName().getString());
            this.dogIdList.add(d.getId());
        }
        this.updateEntries(dogNameList);
    }

    public static void open() {
        open(0);
    }

    public static void open(int blockCharInputMillis) { 
        Minecraft mc = Minecraft.getInstance();
        var stack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == null) return;
        if (!(stack.getItem() instanceof WhistleItem)) return;
        boolean softHeel = false;
        if (ItemUtil.hasTag(stack)) {
            softHeel = ItemUtil.getTag(stack).getBooleanOr("soft_heel", false);
        }
        var screen = new HeelByNameScreen(mc.player, softHeel);
        screen.setBlockCharInputTime(blockCharInputMillis);
        mc.setScreen(screen);
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
        final String soft_heel_title_id = "doggytalents.screen.whistler.heel_by_name.soft_heel";
        var inital_softHeel_c1 = this.softHeel ? 
            Component.translatable(soft_heel_title_id)
                .withStyle(Style.EMPTY.withColor(this.getHightlightSelectedColor()))
            : Component.translatable(soft_heel_title_id);
        var softHeel = new FlatButton(0, pY, 60, 20, 
            inital_softHeel_c1, b -> {
                this.softHeel = !this.softHeel;
                if (this.softHeel) {
                    b.setMessage(Component.translatable(soft_heel_title_id)
                        .withStyle(Style.EMPTY.withColor(this.getHightlightSelectedColor())));
                } else {
                    b.setMessage(Component.translatable(soft_heel_title_id));
                }
        }) {
            @Override
            protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                super.extractContents(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.whistler.heel_by_name.soft_heel")
                    .withStyle(Style.EMPTY.withBold(true)));
                String str = I18n.get("doggytalents.screen.whistler.heel_by_name.soft_heel.help");
                list.addAll(ScreenUtil.splitInto(str, 150, HeelByNameScreen.this.font));

                graphics.setComponentTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        };
        softHeel.setX(mX - 100 - softHeel.getWidth() - 2);
        pY += softHeel.getHeight() + 2;
        var help = new FlatButton(0, pY, 20, 20, Component.literal("?"), b -> {} ) {
            @Override
            protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float pTicks) {
                super.extractContents(graphics, mouseX, mouseY, pTicks);
                if (!this.isHovered) return;
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable("doggytalents.screen.whistler.heel_by_name.help_title")
                    .withStyle(Style.EMPTY.withBold(true)));
                String base_help = I18n.get("doggytalents.screen.general.entry_select.help");
                list.addAll(ScreenUtil.splitInto(base_help, 150, HeelByNameScreen.this.font));
                String additional_help = I18n.get("doggytalents.screen.whistler.heel_by_name.help");
                list.addAll(ScreenUtil.splitInto(additional_help, 150, HeelByNameScreen.this.font));

                graphics.setComponentTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        };
        help.setX(mX - 100 - help.getWidth() - 2);
        
        this.addRenderableWidget(showUuid);
        this.addRenderableWidget(help);
        this.addRenderableWidget(softHeel);
    }

    @Override
    protected void drawNoEntryMsg(GuiGraphicsExtractor graphics, int x, int y) {
        graphics.text(font, 
            I18n.get("doggytalents.screen.whistler.heel_by_name.no_dog_found"), 
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
        var dog_id = this.dogIdList.get(entry_id);
        var dog = this.minecraft.level.getEntity(dog_id);
        if (dog == null)
            return;
        var uuid = dog.getStringUUID();
        if (uuid == null)
            return;
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
    protected void onEntrySelected(int id) {
        this.requestHeel(this.dogIdList.get(id));
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == InputConstants.KEY_LSHIFT) {
            this.heelAndSit = true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        if (event.key() == InputConstants.KEY_LSHIFT) {
            this.heelAndSit = false;
        }
        return super.keyReleased(event);
    }

    private void requestHeel(int id) {
        FrequentHeelStore.get(this).pushDogToFrequentStack(id);
        PacketHandler.send(PacketDistributor.SERVER.noArg(), new HeelByNameData(id, this.heelAndSit, this.softHeel));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class FrequentHeelStore {

        private static FrequentHeelStore INSTANCE;

        private ArrayList<UUID> dogFrequentStack = new ArrayList<UUID>();
        private Screen screen;
        private ToIntFunction<Dog> GET_FREQ_COUNT = d -> this.getFrequentWeightFor(d);
        private static final int STORE_CAP = 32; 

        private FrequentHeelStore(Screen screen) {
            this.screen = screen;
        }

        public void pushDogToFrequentStack(int id) {
            var dog = screen.getMinecraft().level.getEntity(id);
            if (dog == null) return;
            var uuid_str = dog.getStringUUID();
            if (uuid_str == null) return;
            UUID uuid = null;
            try {
                uuid = UUID.fromString(uuid_str);
            } catch (Exception e) {}
            if (uuid == null) return;
            int indx = dogFrequentStack.indexOf(uuid);
            if (indx > 0) {
                if (indx >= dogFrequentStack.size() - 1) return;
                dogFrequentStack.remove(uuid);
            }
            dogFrequentStack.add(uuid);
            if (dogFrequentStack.size() > STORE_CAP) {
                dogFrequentStack.remove(0);
            }
        }

        public int getFrequentWeightFor(Dog dog) {
            var uuid_str = dog.getStringUUID();
            if (uuid_str == null) return 0;
            UUID uuid = null;
            try {
                uuid = UUID.fromString(uuid_str);
            } catch (Exception e) {}
            if (uuid == null) return 0;
            int indx = dogFrequentStack.indexOf(uuid);
            return Math.max(0, indx + 1);
        }

        public List<Dog> sortDogList(List<Dog> dogList) {
            return
                dogList.stream()
                .sorted(Comparator.comparingInt(GET_FREQ_COUNT).reversed())
                .collect(Collectors.toList());
        }
    
        public static FrequentHeelStore get(Screen screen) {
            if (INSTANCE == null) {
                INSTANCE = new FrequentHeelStore(screen);
            }
            if (INSTANCE.screen != screen) {
                INSTANCE.screen = screen;
            }
            return INSTANCE;
        }

    }


}