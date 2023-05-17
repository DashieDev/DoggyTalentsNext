package doggytalents.common.network.packet.data;

import doggytalents.common.entity.Dog.LowHealthStrategy;

public class DogLowHealthStrategyData extends DogData {
    
    public LowHealthStrategy strategy;

    public DogLowHealthStrategyData (int id, LowHealthStrategy strategy) {
        super(id);
        this.strategy = strategy == null ? LowHealthStrategy.NONE : strategy;
    }

}
