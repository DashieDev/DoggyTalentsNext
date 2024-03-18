package doggytalents.common.fabric_helper.block.dogbed;

import doggytalents.api.impl.BeddingMaterial;
import doggytalents.api.impl.CasingMaterial;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager;
import net.minecraft.core.Direction;

public class DogBedModelData {

    public static DogBedModelData EMPTY = builder().build();
    
    private ICasingMaterial casing;
    private IBeddingMaterial bedding;
    private Direction dir;

    private DogBedModelData() {}

    public static Builder builder() {
        return new Builder();
    }

    public ICasingMaterial casing() {
        return this.casing;
    }

    public IBeddingMaterial bedding() {
        return this.bedding;
    }

    public Direction direction() {
        return this.dir;
    }

    public static class Builder {
        
        private ICasingMaterial casing = DogBedMaterialManager.NaniCasing.NULL;
        private IBeddingMaterial bedding = DogBedMaterialManager.NaniBedding.NULL;
        private Direction dir = Direction.NORTH;

        private Builder() {}

        public Builder casing(ICasingMaterial casing) {
            if (casing == null)
                return this;
            this.casing = casing;
            return this;
        }

        public Builder bedding(IBeddingMaterial bedding) {
            if (bedding == null)
                return this;
            this.bedding = bedding;
            return this;
        }

        public Builder facing(Direction dir) {
            if (dir == null)
                return this;
            this.dir = dir;
            return this;
        }

        public DogBedModelData build() {
            var ret = new DogBedModelData();
            ret.bedding = this.bedding;
            ret.casing = this.casing;
            ret.dir = this.dir;
            return ret;
        }

    }

}
