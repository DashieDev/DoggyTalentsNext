package doggytalents.common.network.packet.data;

import doggytalents.common.entity.Dog.CombatReturnStrategy;

public class CombatReturnStrategyData extends DogData {

    public CombatReturnStrategy val;

    public CombatReturnStrategyData(int entityId, CombatReturnStrategy val) {
        super(entityId);
        this.val = val;
    }
    
}
