package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class DogGroupsManager {

    public static final int MAX_GROUP_STRLEN = 16;
    public static final int MAX_GROUP_SIZE = 3;
    
    private List<DogGroup> groups = new ArrayList<DogGroup>(MAX_GROUP_SIZE);
    
    public List<DogGroup> getGroupsReadOnly() {
        return Collections.unmodifiableList(groups);
    }

    public void load(CompoundTag compound) {
        var groupsListTag = compound.getList("doggy_groups", Tag.TAG_COMPOUND);
        for (var tag : groupsListTag) {
            if (!(tag instanceof CompoundTag groupTag)) continue;
            var group_name = groupTag.getString("group_name");
            var group_color = groupTag.getInt("group_color");
            add(new DogGroup(group_name, group_color));
        }
    }
    
    public void save(CompoundTag compound) {
        var groupsListTag = new ListTag();
        for (var group : this.getGroupsReadOnly()) {
            var groupTag = new CompoundTag();
            groupTag.putString("group_name", group.name);
            groupTag.putInt("group_color", group.color);
            groupsListTag.add(groupTag);
        }
        compound.put("doggy_groups", groupsListTag);
    }

    public boolean isGroup(DogGroup group) {
        return this.getGroupsReadOnly().contains(group);
    }

    public boolean add(DogGroup group) {
        if (this.groups.size() >= MAX_GROUP_SIZE) return false;
        if (this.groups.contains(group)) return false;
        if (group.name.length() > MAX_GROUP_STRLEN) return false;
        this.groups.add(group);
        return true;
    }

    public boolean remove(DogGroup group) {
        if (!this.groups.contains(group)) return false;
        this.groups.remove(group);
        return true;
    }

    public void clear() {
        this.groups.clear();
    }

    public static class DogGroup {
        
        public final int color;
        public final String name;

        public DogGroup(String name, int color) {
            this.color = color;
            if (name == null) {
                this.name = "";
            } else {
                this.name = name;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof DogGroup group)) 
                return false;
            return 
                group.color == this.color
                && group.name.equals(this.name);
        }

    }

}
