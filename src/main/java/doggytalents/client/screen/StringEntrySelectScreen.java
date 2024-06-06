package doggytalents.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;

import doggytalents.client.screen.framework.widget.TextOnlyButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;

public class StringEntrySelectScreen extends Screen {
    
    private List<String> entries;
    private List<Integer> filteredIndexes;
    private int pageCount = 1;
    private int activePage = 0;
    private int selectedEntryInPage = 0;

    private String searchString = "";

    private boolean startedUpdatingMouseMove = false;
    private int mouseX0 = 0;
    private int mouseY0 = 0;

    private long blockCharInputMillis = 0;
    private long prevMillis = 0;

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
        prevPage.active = this.activePage > 0;
        nextPage.active = this.activePage < pageCount - 1;
        this.prevPageButton = prevPage;
        this.nextPageButton = nextPage;
        this.addRenderableWidget(prevPage);
        this.addRenderableWidget(nextPage);
    }

    protected void nextPage() {
        this.activePage =
            Mth.clamp(this.activePage + 1, 0, this.pageCount - 1);
        onPageUpdated();
    }

    protected void prevPage() {
        this.activePage =
            Mth.clamp(this.activePage - 1, 0, this.pageCount - 1);
        onPageUpdated();
    }

    protected void onPageUpdated() {
        this.selectedEntryInPage = 0;
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        updateBlockCharInputMillis();

        if (startedUpdatingMouseMove)
            updateMouseMoved(mouseX, mouseY);
        else
            mayStartUpdatingMouseMove(mouseX, mouseY);

        super.render(graphics, mouseX, mouseY, partialTicks);
        drawSelectArea(graphics);
        drawSearchBar(graphics);
        drawPageIndicator(graphics);
        
        this.prevPageButton.active = this.activePage > 0;
        this.nextPageButton.active = this.activePage < pageCount - 1;
    }

    private void updateBlockCharInputMillis() {
        if (this.blockCharInputMillis <= 0)
            return;
        long passed = System.currentTimeMillis() - prevMillis;
        if (passed > 0) {
            this.blockCharInputMillis -= passed;
            prevMillis = System.currentTimeMillis();
        }
    }

    private void updateMouseMoved(int mouseX, int mouseY) {
        boolean mouse_moved = this.mouseX0 != mouseX || this.mouseY0 != mouseY;
    
        if (!mouse_moved)
            return;
        
        this.onMouseMoved(mouseX, mouseY);
        this.mouseX0 = mouseX;
        this.mouseY0 = mouseY;
    }

    private void mayStartUpdatingMouseMove(int mouseX, int mouseY) {
        int half_width = this.width / 2;
        int half_height = this.height / 2;

        int dx = Math.abs(mouseX - half_width);
        int dy = Math.abs(mouseY - half_height);
        if (dx > 10 || dy > 10) {
            this.startedUpdatingMouseMove = true;
            this.mouseX0 = mouseX;
            this.mouseY0 = mouseY;
        }
    }

    protected void onMouseMoved(double mouseX, double mouseY) {
        int newIndx = getHoveredIndex(mouseX, mouseY, getCurrentPageEntries());
        if (newIndx < 0) return;
        this.selectedEntryInPage = newIndx;
    }

    private int getHoveredIndex(double x, double y, int entry_size) {
        int mX = this.width/2;
        int mY = this.height/2;
        int area_size = this.getSelectAreaSize();
        boolean outside_of_area = 
            Math.abs(x - mX) > area_size / 2
            || Math.abs(y - mY) > area_size / 2;
        if (outside_of_area) return -1;
        int baseY = mY - area_size / 2;
        int indx = ( Mth.floor(y - baseY) )/getSpacePerEntry();
        if (indx >= entry_size) return -1;
        return indx;
    }

    private int getCurrentPageEntries() {
        boolean is_last_page = 
            this.activePage >= this.pageCount - 1;
        if (is_last_page) {
            return this.filteredIndexes.size() % getMaxEntriesPerPage();
        }
        return getMaxEntriesPerPage();
    }

    protected void drawSelectArea(GuiGraphics graphics) {
        drawSelectAreaBackground(graphics);
        drawEntries(graphics);
    }

    protected void drawSelectAreaBackground(GuiGraphics graphics) {
        int half_width = this.width / 2;
        int half_height = this.height / 2; 
      
        graphics.fill( half_width - getSelectAreaSize() / 2 , half_height - getSelectAreaSize() / 2 , 
            half_width + getSelectAreaSize() / 2 , half_height + getSelectAreaSize() / 2 , Integer.MIN_VALUE);
    }

    protected void drawEntries(GuiGraphics graphics) {
        int half_width = this.width / 2;
        int half_height = this.height / 2;

        int entry_offset = 0;
        int entry_start_x = half_width - getSelectAreaSize() / 2 + 2;
        int entry_start_y = half_height - getSelectAreaSize() / 2 + 2;

        int startIndx = this.activePage * getMaxEntriesPerPage();
        int drawNo = 0;
        for (int i = startIndx; i < this.filteredIndexes.size(); ++i) {
            drawEntry(graphics, entry_start_x, entry_start_y + entry_offset, startIndx, i);
            entry_offset += getSpacePerEntry();   
            if (++drawNo >= getMaxEntriesPerPage()) break;
        }
    }

    protected void drawEntry(GuiGraphics graphics, int entry_x, int entry_y, int start_indx, int render_indx) {
        int color = 0xffffffff;

        boolean is_selected_entry =
            render_indx == start_indx + this.selectedEntryInPage;

        if (is_selected_entry) 
            color = this.getHightlightSelectedColor();
        
        int entry_id = this.filteredIndexes.get(render_indx);
        var text = this.entries.get(entry_id);
        graphics.drawString(font, text, entry_x, entry_y, color);
    }

    protected void drawSearchBar(GuiGraphics graphics) {
        int half_width = this.width / 2;
        int half_height = this.height / 2;
        
        int txtorgx = half_width - this.getSelectAreaSize() / 2 + 10;
        int txtorgy = half_height + this.getSelectAreaSize() / 2 + this.getSearchBarOffset();
        
        graphics.fill( half_width - this.getSelectAreaSize() / 2 , 
            half_height + this.getSelectAreaSize() / 2 + 5, half_width + this.getSelectAreaSize() / 2, 
            half_height + this.getSelectAreaSize() / 2 + 17, Integer.MIN_VALUE);
        graphics.drawString(font, this.searchString + "_", txtorgx, txtorgy,  0xffffffff);
    }

    protected void drawPageIndicator(GuiGraphics graphics) {
        int half_width = this.width / 2;
        int half_height = this.height / 2;
        
        var pageStr = (activePage + 1) + "/" + pageCount;
        var pageStrWidth = font.width(pageStr);
        graphics.drawString(font, pageStr, half_width - pageStrWidth/2, 
            half_height - this.getSelectAreaSize() / 2 - this.getPageIndicatorOffset(), 0xffffffff);
    }

    @Override
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

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
       var mouseKey = InputConstants.getKey(keyCode, scanCode);

        if (keyCode == 264) {
            int currentPageEntries = getCurrentPageEntries() - 1;
            this.selectedEntryInPage = 
                Mth.clamp(this.selectedEntryInPage + 1, 0, currentPageEntries);
            return true;
        } else if (keyCode == 265) {
            int currentPageEntries = getCurrentPageEntries() - 1;
            this.selectedEntryInPage = 
                Mth.clamp(this.selectedEntryInPage - 1, 0, currentPageEntries);
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
            if (this.filteredIndexes.isEmpty()) return false; 
            var selectedId = getSelectedFilterId(this.selectedEntryInPage);
            if (selectedId >= 0 && selectedId < this.filteredIndexes.size()) {
                onEntrySelected(this.filteredIndexes.get(selectedId));
            }
            return true;
        } else if (keyCode == 259) {
            this.popCharInText();
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        boolean ret = super.mouseClicked(x, y, p_94697_);
        if (this.filteredIndexes.isEmpty()) return ret; 
        int indx = getHoveredIndex(x, y, getCurrentPageEntries());
        if (indx < 0)
            return ret;
        int selected_id = this.getSelectedFilterId(indx);
        if (selected_id >= 0 && selected_id < this.filteredIndexes.size()) {
            onEntrySelected(this.filteredIndexes.get(selected_id));
        }
        return ret;
    }

    protected void onEntrySelected(int id) {

    }

    private void insertText(String x) {
        if (this.searchString.length() < getMaxSearchBufferSize()) {
            this.searchString = this.searchString + x;
        }
        this.updateFilteredIndexes();
    }

    private void popCharInText() {
        if (this.searchString.length() <= 0) return;
        this.searchString = this.searchString.substring(0, this.searchString.length()-1);
        this.updateFilteredIndexes();
    }

    protected void updateEntries(List<String> new_entries) {
        if (new_entries == null)
            return;
        this.entries = new_entries;
        updateFilteredIndexes();
    }

    protected void updateFilteredIndexes() {
        this.filteredIndexes.clear();
        this.selectedEntryInPage = 0;

        if (this.searchString == null || this.searchString.isEmpty()) {
            for (int i = 0; i < this.entries.size(); ++i) {
                this.filteredIndexes.add(i);
            }
            updatePages();
            return;
        }

        for (int i = 0; i < this.entries.size(); ++i) {
            var entry = this.entries.get(i);
            if (entry.length() < searchString.length()) continue; 
            if (entry.contains(searchString)) {
                this.filteredIndexes.add(i);
            }
        }
        
        updatePages();
    }

    private void updatePages() {
        int filter_size = this.filteredIndexes.size();
        this.activePage = 0;
        this.pageCount = filter_size / this.getMaxEntriesPerPage();
        if (filter_size % this.getMaxEntriesPerPage() > 0) {
            this.pageCount += 1;
        }
        onPageUpdated();
    }

    private int getSelectedFilterId(int selected_index_per_page) {
        return this.activePage * getMaxEntriesPerPage() + selected_index_per_page;
    }

    public void setBlockCharInputMillis(int x) {
        this.blockCharInputMillis = x;
        this.prevMillis = System.currentTimeMillis();
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

}
