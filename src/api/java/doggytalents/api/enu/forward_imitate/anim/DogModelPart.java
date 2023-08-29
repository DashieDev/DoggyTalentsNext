package doggytalents.api.enu.forward_imitate.anim;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.math.Vector3f;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public class DogModelPart extends ModelPart {

    private PartPose initialPose = PartPose.ZERO;

    public DogModelPart(List<Cube> p_171306_, Map<String, ModelPart> children) {
        super(p_171306_, children);
    }

    public DogModelPart(ModelPart part) {
        super(part.cubes, part.children);
    }

    public static DogModelPart recreateFromModelPart(ModelPart part) {
        var children = part.children;
        Object2ObjectArrayMap newChildrenMap = children.entrySet().stream()
            .collect(Collectors.toMap(entry -> { return entry.getKey(); }, 
            (p_171593_) -> {
                return recreateFromModelPart(p_171593_.getValue());
            }, (p_171595_, p_171596_) -> {
                return p_171595_;
            }, Object2ObjectArrayMap::new));
        var ret = new DogModelPart(part.cubes, newChildrenMap);
        ret.setInitialPose(part.storePose());
        return ret;
    }

    public void offsetRotation(Vector3f vec) {
        this.xRot += vec.x();
        this.yRot += vec.y();
        this.zRot += vec.z();
    }

    public void offsetPos(Vector3f vec) {
        this.x += vec.x();
        this.y += vec.y();
        this.z += vec.z();
    }

    public void offsetScale(Vector3f vec) {
    }

    public void resetPose() {
        this.loadPose(this.initialPose);
    }

    private void setInitialPose(PartPose pose) {
        this.initialPose = pose;
    }

    public boolean hasChild(String child) {
        var c = this.children.get(child);
        return c != null;
    }

    @Override
    public Stream<ModelPart> getAllParts() {
        // TODO Auto-generated method stub
        return super.getAllParts();
    }

    
    
}
