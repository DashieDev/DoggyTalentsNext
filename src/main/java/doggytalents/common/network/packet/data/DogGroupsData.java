package doggytalents.common.network.packet.data;

import java.util.List;

import doggytalents.common.entity.DogGroupsManager.DogGroup;

public class DogGroupsData {
    
    public static class EDIT extends DogData {

        public final DogGroup group;
        public final boolean add;
    
        public EDIT(int entityId, DogGroup group, boolean add) {
            super(entityId);
            this.group = group == null ? new DogGroup("", 0) : group;
            this.add = add; 
        }

    }

    public static class FETCH_REQUEST extends DogData {

        public FETCH_REQUEST(int entityId) {
            super(entityId);
        }
        
    }

    public static class UPDATE {

        public final int dogId;
        public final List<DogGroup> groups;

        public UPDATE(int id, List<DogGroup> groups) {
            this.dogId = id;
            this.groups = groups == null ? List.of() : groups;
        }
        
    }

}
