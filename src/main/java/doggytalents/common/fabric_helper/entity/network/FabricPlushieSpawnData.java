package doggytalents.common.fabric_helper.entity.network;

import doggytalents.common.variant.DogVariant;

public class FabricPlushieSpawnData {
    
    public final int entityId;
    public final DogVariant variant;
    public final int collarColor;
    public final boolean collarThicc;

    public FabricPlushieSpawnData(int entityId, DogVariant variant, 
        int colarColor, boolean collarThicc) {

        this.entityId = entityId;
        this.variant = variant;
        this.collarColor = colarColor;
        this.collarThicc = collarThicc;
    }

}
