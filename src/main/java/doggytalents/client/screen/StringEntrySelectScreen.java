package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import com.mojang.blaze3d.platform.InputConstants;

import doggytalents.client.screen.framework.widget.TextOnlyButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;

public class StringEntrySelectScreen extends Screen {
    
    private List<String> entries;
    private List<Integer> filteredIndexes;

    protected final MouseUpdate mouseUpdate = new MouseUpdate(this);
    protected final EntryView entryView = new EntryView(this);
    protected final TextField searchField = new TextField(this);

    private TextOnlyButton prevPageButton;
    private TextOnlyButton nextPageButton;

    public StringEntrySelectScreen(Component title) {
        super(title);
        this.entries = new ArrayList<String>();
        this.filteredIndexes = new ArrayList<Integer>();
    }

    @Override
    public void init() {
        super.init();

        addPageButtons();
        addUtilitiesButton();
    }

    protected void addPageButtons() {
        int half_width = this.width / 2;
        int half_height = this.height / 2; 
        var prevPage = new TextOnlyButton(half_width - getSelectAreaSize() / 2 - 20, 
            half_height - 10, 20, 20, Component.literal("<"), b -> {
                this.prevPage();
            }, font);
        var nextPage = new TextOnlyButton(half_width + getSelectAreaSize() / 2, 
            half_height - 10, 20, 20, Component.literal(">"), b -> {
                this.nextPage();
            }, font);
        this.prevPageButton = prevPage;
        this.nextPageButton = nextPage;
        this.addRenderableWidget(prevPage);
        this.addRenderableWidget(nextPage);
    }

    protected void addUtilitiesButton() {

    }

    protected void nextPage() {
        this.entryView.movePage(x -> x + 1);
    }

    protected void prevPage() {
        this.entryView.movePage(x -> x - 1);
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        this.mouseUpdate.update(mouseX, mouseY);
        this.searchField.update();

        super.render(graphics, mouseX, mouseY, partialTicks);
        this.entryView.render(graphics);
        this.searchField.render(graphics);
        
        this.prevPageButton.active = this.entryView.prevPageActive();
        this.nextPageButton.active = this.entryView.nextPageActive();
    }

    protected void onMouseMoved(double mouseX, double mouseY) {
        this.entryView.onMouseMoved(mouseX, mouseY);
    }

    protected void drawSelectAreaBackground(GuiGraphics graphics) {
        int half_width = this.width / 2;
        int half_height = this.height / 2; 
      
        graphics.fill( half_width - getSelectAreaSize() / 2 , half_height - getSelectAreaSize() / 2 , 
            half_width + getSelectAreaSize() / 2 , half_height + getSelectAreaSize() / 2 , Integer.MIN_VALUE);
    }

    protected void drawEntry(GuiGraphics graphics, int entry_x, int entry_y, 
        int start_indx, int render_indx, int selected_entry_in_page) {
        int color = 0xffffffff;

        boolean is_selected_entry =
            render_indx == start_indx + selected_entry_in_page;

        if (is_selected_entry) 
            color = this.getHightlightSelectedColor();
        
        int entry_id = this.filteredIndexes.get(render_indx);
        Component text = Component.literal(this.entries.get(entry_id))
            .setStyle(Style.EMPTY.withColor(color));
        text = modifyEntryText(text, entry_id);
        graphics.drawString(font, text, entry_x, entry_y, 0xffffffff);
    }

    protected Component modifyEntryText(Component entryText, int entryId) {
        return entryText;
    }

    protected void drawNoEntryMsg(GuiGraphics graphics, int x, int y) {
        
    }

    protected void drawPageIndicator(GuiGraphics graphics, int pageCount, int activePage) {
        int half_width = this.width / 2;
        int half_height = this.height / 2;
        
        var page_str = (activePage + 1) + "/" + pageCount;
        var page_str_width = font.width(page_str);
        graphics.drawString(font, page_str, half_width - page_str_width/2, 
            half_height - this.getSelectAreaSize() / 2 - this.getPageIndicatorOffset(), 0xffffffff);
    }

    @Override
    public boolean charTyped(char code, int p_231042_2_) {
        return this.searchField.charTyped(code, p_231042_2_);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
       var mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (this.entryView.keyPressed(keyCode, scanCode, modifiers)) {
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
            var selected_indx = this.entryView.getSelectedFilterId(this.filteredIndexes);
            if (selected_indx.isPresent()) {
                onEntrySelected(selected_indx.get());
            }
            return true;
        }

        if (this.searchField.keyPressed(keyCode, scanCode, modifiers))
            return true;

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        var clicked_id = this.entryView
            .getHoveringEntry(x, y, filteredIndexes);
        if (clicked_id.isPresent()) {
            onEntrySelected(clicked_id.get());
            return true;
        }
        return super.mouseClicked(x, y, p_94697_);
    }

    protected void onEntrySelected(int id) {

    }

    protected void updateEntries(List<String> new_entries) {
        if (new_entries == null)
            return;
        this.entries = new_entries;
        updateFilteredIndexes();
    }

    protected void onTextFieldUpdated() {
        updateFilteredIndexes();
    }

    protected void updateFilteredIndexes() {
        this.filteredIndexes.clear();

        final var search_str = this.searchField.getValue(); 

        if (search_str == null || search_str.isEmpty()) {
            for (int i = 0; i < this.entries.size(); ++i) {
                this.filteredIndexes.add(i);
            }
            onFilteredIndexesUpdated();
            return;
        }

        for (int i = 0; i < this.entries.size(); ++i) {
            var entry = this.entries.get(i);
            if (entry.length() < search_str.length()) continue; 
            boolean matched = this.matchIgnoreCaseSearch() ?
                entry.toLowerCase().contains(search_str.toLowerCase())
                : entry.contains(search_str);
            if (matched) {
                this.filteredIndexes.add(i);
            }
        }
        
        onFilteredIndexesUpdated();
    }

    protected void onFilteredIndexesUpdated() {
        this.entryView.onFilteredIndexesUpdated(filteredIndexes);
    }

    public void setBlockCharInputTime(int millis) {
        this.searchField.setBlockCharInputTime(millis);
    }

    protected int getMaxEntriesPerPage() {
        return 19;
    }

    protected int getSelectAreaSize() {
        return 200;
    }

    protected int getSearchBarOffset() {
        return 7;
    }

    protected int getPageIndicatorOffset() {
        return 10;
    }

    protected int getSpacePerEntry() {
        return 10;
    }

    protected int getHightlightSelectedColor() {
        return 0xFFFF10F9;
    }

    protected int getMaxSearchBufferSize() {
        return 64;
    }

    protected boolean matchIgnoreCaseSearch() {
        return false;
    }

    public static class EntryView {
        private final StringEntrySelectScreen parent;
        private int pageCount = 1;
        private int activePage = 0;
        private int selectedEntryInPage = 0;

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            var mouseKey = InputConstants.getKey(keyCode, scanCode);
            
            if (keyCode == 264) {
                moveSelectedEntryInPage(x -> x + 1);
                return true;
            } else if (keyCode == 265) {
                moveSelectedEntryInPage(x -> x - 1);
                return true;
            }

            return false;
        }
        
        public void onMouseMoved(double mouseX, double mouseY) {
            Optional<Integer> new_indx_optional = this.getHoveredIndexInPage(mouseX, mouseY);
            if (!new_indx_optional.isPresent())
                return;
            final int new_indx = new_indx_optional.get(); 
            this.moveSelectedEntryInPage(x -> new_indx);
        }

        public void onFilteredIndexesUpdated(List<Integer> filtered_indexes) {
            this.resetSelectedEntryInPage();
            this.updatePages(filtered_indexes);
        }

        public Optional<Integer> getHoveringEntry(double x, double y,
            List<Integer> filtered_indexes) {
            if (filtered_indexes.isEmpty())
                return Optional.empty();
            var indx_optional = getHoveredIndexInPage(x, y);
            if (!indx_optional.isPresent())
                return Optional.empty();
            return this.getSelectedFilterId(filtered_indexes);
        }

        public boolean prevPageActive() {
            return this.activePage > 0;
        }

        public boolean nextPageActive() {
            return this.activePage < pageCount - 1; 
        }
        
        private EntryView(StringEntrySelectScreen parent) {
            this.parent = parent;
        }

        private void updatePages(List<Integer> filtered_indexes) {
            int filter_size = filtered_indexes.size();
            this.activePage = 0;
            this.pageCount = filter_size / parent.getMaxEntriesPerPage();
            if (filter_size % parent.getMaxEntriesPerPage() > 0) {
                this.pageCount += 1;
            }
        }

        protected void render(GuiGraphics graphics) {
            parent.drawSelectAreaBackground(graphics);
            drawEntries(graphics);            
            parent.drawPageIndicator(graphics, pageCount, activePage);
        }

        protected void drawEntries(GuiGraphics graphics) {
            int half_width = parent.width / 2;
            int half_height = parent.height / 2;
    
            int entry_offset = 0;
            int entry_start_x = half_width - parent.getSelectAreaSize() / 2 + 2;
            int entry_start_y = half_height - parent.getSelectAreaSize() / 2 + 2;
    
            int startIndx = this.activePage * parent.getMaxEntriesPerPage();
            int drawNo = 0;
            if (parent.filteredIndexes.isEmpty()) {
                parent.drawNoEntryMsg(graphics, entry_start_x, entry_start_y);
                return;
            }
            for (int i = startIndx; i < parent.filteredIndexes.size(); ++i) {
                parent.drawEntry(graphics, entry_start_x, entry_start_y + entry_offset, 
                    startIndx, i, this.selectedEntryInPage);
                entry_offset += parent.getSpacePerEntry();   
                if (++drawNo >= parent.getMaxEntriesPerPage()) break;
            }
        }

        public void movePage(IntFunction<Integer> mover) {
            if (this.pageCount <= 0)
                return;
            int new_page = mover.apply(this.activePage);
            this.activePage =
                Mth.clamp(new_page, 0, this.pageCount - 1);
            onPageMove();
        }

        protected void onPageMove() {
            this.resetSelectedEntryInPage();
        }

        public int getCurrentPageEntries() {
            boolean is_last_page = 
                this.activePage >= this.pageCount - 1;
            if (is_last_page) {
                return parent.filteredIndexes.size() % parent.getMaxEntriesPerPage();
            }
            return parent.getMaxEntriesPerPage();
        }

        public Optional<Integer> getSelectedFilterId(List<Integer> filter_list) {
            if (filter_list.isEmpty())
                return Optional.empty();
            int ret = this.activePage * parent.getMaxEntriesPerPage() + selectedEntryInPage;
            if (0 <= ret && ret < filter_list.size())
                return Optional.of(filter_list.get(ret));
            return Optional.empty();
        }

        protected final void moveSelectedEntryInPage(IntFunction<Integer> mover) {
            var current_page_entries = this.getCurrentPageEntries();
            if (current_page_entries <= 0)
                return;
            int new_value = mover.apply(this.selectedEntryInPage);
            this.selectedEntryInPage = Mth.clamp(new_value, 0, current_page_entries);
        }
    
        protected final void resetSelectedEntryInPage() {
            this.selectedEntryInPage = 0;
        }

        public Optional<Integer> getHoveredIndexInPage(double x, double y) {
            int entry_size = this.getCurrentPageEntries();
            int mX = parent.width/2;
            int mY = parent.height/2;
            int area_size = parent.getSelectAreaSize();
            boolean outside_of_area = 
                Math.abs(x - mX) > area_size / 2
                || Math.abs(y - mY) > area_size / 2;
            if (outside_of_area) return Optional.empty();
            int baseY = mY - area_size / 2;
            int indx = ( Mth.floor(y - baseY) )/parent.getSpacePerEntry();
            if (indx >= entry_size) return Optional.empty();
            return Optional.of(indx);
        }

    }

    public static class MouseUpdate {
        private final StringEntrySelectScreen parent;
        private boolean startedUpdate = false;
        private int mouseX0 = 0;
        private int mouseY0 = 0;

        private MouseUpdate(StringEntrySelectScreen parent) {
            this.parent = parent;
        }
        
        public void update(int mouseX, int mouseY) {
            if (startedUpdate)
                updateMouseMoved(mouseX, mouseY);
            else
                mayStartUpdatingMouseMove(mouseX, mouseY);
        }

        private void updateMouseMoved(int mouseX, int mouseY) {
            boolean mouse_moved = this.mouseX0 != mouseX || this.mouseY0 != mouseY;
        
            if (!mouse_moved)
                return;
            
            parent.onMouseMoved(mouseX, mouseY);
            this.mouseX0 = mouseX;
            this.mouseY0 = mouseY;
        }
    
        private void mayStartUpdatingMouseMove(int mouseX, int mouseY) {
            int half_width = parent.width / 2;
            int half_height = parent.height / 2;
    
            int dx = Math.abs(mouseX - half_width);
            int dy = Math.abs(mouseY - half_height);
            if (dx > 10 || dy > 10) {
                this.startedUpdate = true;
                this.mouseX0 = mouseX;
                this.mouseY0 = mouseY;
            }
        }

    }

    public static class TextField {
        private final StringEntrySelectScreen parent;
        private long blockCharInputMillis = 0;
        private long prevMillis = 0;
        private String searchString = "";

        private TextField(StringEntrySelectScreen parent) {
            this.parent = parent;
        }

        public String getValue() {
            return this.searchString;
        }

        public void update() {
            if (this.blockCharInputMillis <= 0)
                return;
            long passed = System.currentTimeMillis() - prevMillis;
            if (passed > 0) {
                this.blockCharInputMillis -= passed;
                prevMillis = System.currentTimeMillis();
            }
        }

        public void render(GuiGraphics graphics) {
            final int selected_area_size = parent.getSelectAreaSize();

            int half_width = parent.width / 2;
            int half_height = parent.height / 2;
            
            int txtorgx = half_width - selected_area_size / 2 + 10;
            int txtorgy = half_height + selected_area_size / 2 + parent.getSearchBarOffset();
            
            graphics.fill( half_width - selected_area_size / 2 , 
                half_height + selected_area_size / 2 + 5, half_width + selected_area_size / 2, 
                half_height + selected_area_size / 2 + 17, Integer.MIN_VALUE);
            graphics.drawString(parent.font, this.searchString + "_", txtorgx, txtorgy,  0xffffffff);
        }

        public void insertText(String x) {
            if (this.searchString.length() < parent.getMaxSearchBufferSize()) {
                this.searchString = this.searchString + x;
            }
            parent.onTextFieldUpdated();
        }
    
        public void popCharInText() {
            if (this.searchString.length() <= 0) return;
            this.searchString = this.searchString.substring(0, this.searchString.length()-1);
            parent.onTextFieldUpdated();
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == 259) {
                this.popCharInText();
                return true;
            }
            return false;
        }

        public boolean charTyped(char code, int p_231042_2_) {
            if (this.blockCharInputMillis > 0)
                return false;
            if (StringUtil.isAllowedChatCharacter(code)) {
                this.insertText(Character.toString(code));
                return true;
            } else {
                return false;
            }
            
        }

        public void setBlockCharInputTime(int millis) {
            this.blockCharInputMillis = millis;
            this.prevMillis = System.currentTimeMillis();
        }

    }

}
