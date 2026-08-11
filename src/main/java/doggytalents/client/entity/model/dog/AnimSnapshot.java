package doggytalents.client.entity.model.dog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class AnimSnapshot {

    
    public Part root = new Part();
    
    public Part head = new Part();
    public Part realHead = new Part();
    public Part body = new Part();
    public Part mane = new Part();
    public Part legBackRight = new Part();
    public Part legBackLeft = new Part();
    public Part legFrontRight = new Part();
    public Part legFrontLeft = new Part();
    public Part tail = new Part();
    public Part realTail = new Part();

    //Optional parts
    public Part earLeft = new Part();
    public Part earRight = new Part();


    public void store(DogModel model) {
        storePart(root, model.root);
        storePart(head, model.head);
        storePart(realHead, model.realHead);
        storePart(body, model.body);
        storePart(mane, model.mane);
        storePart(legBackRight, model.legBackRight);
        storePart(legBackLeft, model.legBackLeft);
        storePart(legFrontRight, model.legFrontRight);
        storePart(legFrontLeft, model.legFrontLeft);
        storePart(tail, model.tail);
        storePart(realTail, model.realTail);

        model.earLeft.ifPresent(x -> storePart(earLeft, x));
        model.earRight.ifPresent(x -> storePart(earRight, x));
    }

    public void load(DogModel model) {
        loadPart(model.root, root);
        loadPart(model.head, head);
        loadPart(model.realHead, realHead);
        loadPart(model.body, body);
        loadPart(model.mane, mane);
        loadPart(model.legBackRight, legBackRight);
        loadPart(model.legBackLeft, legBackLeft);
        loadPart(model.legFrontRight, legFrontRight);
        loadPart(model.legFrontLeft, legFrontLeft);
        loadPart(model.tail, tail);
        loadPart(model.realTail, realTail);

        model.earLeft.ifPresent(x -> loadPart(x, earLeft));
        model.earRight.ifPresent(x -> loadPart(x, earRight));
    }

    private static void storePart(Part part, ModelPart model_part) {
        part.x = model_part.x;
        part.y = model_part.y;
        part.z = model_part.z;
        part.xrot = model_part.xRot;
        part.yrot = model_part.yRot;
        part.zrot = model_part.zRot;
    }

    private static void loadPart(ModelPart model_part, Part part) {
        model_part.x = part.x;
        model_part.y = part.y;
        model_part.z = part.z;
        model_part.xRot = part.xrot;
        model_part.yRot = part.yrot;
        model_part.zRot = part.zrot;
    }

    public static void blendAndApplyHeadRotAndChildrenOnly(float progress, 
        AnimSnapshot result1, AnimSnapshot result2,
        DogModel model) {

        blendPartAndApplyRotOnly(progress, result1.head, result2.head, model.head);
        blendPartAndApplyRotOnly(progress, result1.realHead, result2.realHead, model.realHead);

        model.earLeft.ifPresent(x -> blendPartAndApply(progress, result1.earLeft, result2.earLeft, x));
        model.earRight.ifPresent(x -> blendPartAndApply(progress, result1.earRight, result2.earRight, x));
    }

    public static void blendAndApply(float progress, 
        AnimSnapshot result1, AnimSnapshot result2,
        DogModel model) {

        blendPartAndApply(progress, result1.root, result2.root, model.root);
        blendPartAndApply(progress, result1.head, result2.head, model.head);
        blendPartAndApply(progress, result1.realHead, result2.realHead, model.realHead);
        blendPartAndApply(progress, result1.body, result2.body, model.body);
        blendPartAndApply(progress, result1.mane, result2.mane, model.mane);
        blendPartAndApply(progress, result1.legBackRight, result2.legBackRight, model.legBackRight);
        blendPartAndApply(progress, result1.legBackLeft, result2.legBackLeft, model.legBackLeft);
        blendPartAndApply(progress, result1.legFrontRight, result2.legFrontRight, model.legFrontRight);
        blendPartAndApply(progress, result1.legFrontLeft, result2.legFrontLeft, model.legFrontLeft);
        blendPartAndApply(progress, result1.tail, result2.tail, model.tail);
        blendPartAndApply(progress, result1.realTail, result2.realTail, model.realTail);

        model.earLeft.ifPresent(x -> blendPartAndApply(progress, result1.earLeft, result2.earLeft, x));
        model.earRight.ifPresent(x -> blendPartAndApply(progress, result1.earRight, result2.earRight, x));
    }

    private static void blendPartAndApply(float progress, 
        Part part1, Part part2, ModelPart model_part) {
        
        model_part.x = interp(progress, part1.x, part2.x);
        model_part.y = interp(progress, part1.y, part2.y);
        model_part.z = interp(progress, part1.z, part2.z);
        model_part.xRot = interpRot(progress, part1.xrot, part2.xrot);
        model_part.yRot = interpRot(progress, part1.yrot, part2.yrot);
        model_part.zRot = interpRot(progress, part1.zrot, part2.zrot);
    }

    private static void blendPartAndApplyRotOnly(float progress, 
        Part part1, Part part2, ModelPart model_part) {
        
        model_part.xRot = interpRot(progress, part1.xrot, part2.xrot);
        model_part.yRot = interpRot(progress, part1.yrot, part2.yrot);
        model_part.zRot = interpRot(progress, part1.zrot, part2.zrot);
    }

    private static float interp(float progress, float a, float b) {
        return Mth.lerp(progress, a, b);
    }

    private static float interpRot(float progress, float a, float b) {
        return Mth.rotLerp(progress ,a * Mth.RAD_TO_DEG, b * Mth.RAD_TO_DEG) * Mth.DEG_TO_RAD;
    }
    
    public static class Part {
        public float x, y, z, xrot, yrot, zrot;
    }

}
