package doggytalents.common.network.packet.data;

import java.util.List;

import doggytalents.common.entity.DogGroupsManager.DogGroup;

public class HeelByGroupData {
    public static class REQUEST_GROUP_LIST {

    }

    public static class RESPONSE_GROUP_LIST {
        public final List<DogGroup> groups;
        public RESPONSE_GROUP_LIST(List<DogGroup> groups) {
            this.groups = groups == null ? List.of() : groups;
        }
    }

    public static class REQUEST_HEEL {
        public final DogGroup group;
        public final boolean heelAndSit;
        public REQUEST_HEEL(DogGroup group, boolean heelAndSit) {
            this.group = group == null ? new DogGroup("", 0) : group;
            this.heelAndSit = heelAndSit;
        }
    }
}
