package doggytalents.client.entity.model.dog;

import java.util.Optional;
import doggytalents.client.entity.model.SyncedAccessoryModel;
import net.minecraft.client.model.geom.ModelPart;

public class TranslucentOverrideModel extends SyncedAccessoryModel {

    public TranslucentOverrideModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.head = getChildIfExist(box, "head");
        this.realHead = getChildIfExist(this.head, "real_head");
        this.body = getChildIfExist(box, "body");
        this.mane = getChildIfExist(box, "upper_body");
        this.legBackRight = getChildIfExist(box, "right_hind_leg");
        this.legBackLeft = getChildIfExist(box, "left_hind_leg");
        this.legFrontRight = getChildIfExist(box, "right_front_leg");
        this.legFrontLeft = getChildIfExist(box, "left_front_leg");
        this.tail = getChildIfExist(box, "tail");
        this.realTail = getChildIfExist(this.tail, "real_tail");
    }


    private Optional<ModelPart> getChildIfExist(ModelPart part, String id) {
        if (!part.hasChild(id))
            return Optional.empty();
        return Optional.of(part.getChild(id));
    }

    private Optional<ModelPart> getChildIfExist(Optional<ModelPart> part, String id) {
        if (!part.isPresent())
            return Optional.empty();
        return getChildIfExist(part.get(), id);
    }

}