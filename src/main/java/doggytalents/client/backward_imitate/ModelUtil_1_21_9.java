package doggytalents.client.backward_imitate;

import net.minecraft.client.model.geom.ModelPart;

public class ModelUtil_1_21_9 {
    
    public static void copyModelPartFrom(ModelPart part, ModelPart from) {
        part.xScale = from.xScale;
        part.yScale = from.yScale;
        part.zScale = from.zScale;
        part.xRot = from.xRot;
        part.yRot = from.yRot;
        part.zRot = from.zRot;
        part.x = from.x;
        part.y = from.y;
        part.z = from.z;
    } 

}
