package doggytalents.client.entity.model.dog;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.client.animation.KeyframeAnimations;

public class DogPoseSetups {

    public static boolean setupPose(DogPose pose, DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        switch (pose) {
        case FAINTED:
            setupFaintPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case FAINTED_2:
            setupFaintPose2(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case SIT:
            setUpSitPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case REST:
            setupRestPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case LYING_2:
            setupLyingPose2(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case DROWN:
            setupDrownPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case FLYING:
            setupFlyPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        case REST_BELLY:
            setupRestBellyPose(model, dog, limbSwing, limbSwingAmount, partialTickTime);
            return true;
        default:
            return false;
        }
    }
 
    public static void setUpSitPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.tail.offsetPos(KeyframeAnimations.posVec(0f, -9f, -2f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-18f, 0f, 0f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -2f, 0f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(-45f, 0f, 0f));
        model.body.offsetPos(KeyframeAnimations.posVec(0f, -4f, -2f));
        model.head.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        model.head.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
    }

    public static void setupFaintPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.head.offsetRotation(KeyframeAnimations.degreeVec(6.2f, 4.97f, -2.04f));
        model.head.offsetPos(KeyframeAnimations.posVec(0f, -1f, -0.5f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(2.5f, 0f, 0f));
        model.body.offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -22.5f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -15f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -30f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(-1f, -1f, 0f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -7.5f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(1f, -0.25f, 0f));
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(162.35f, 11.59f, 38.36f));
        model.tail.offsetPos(KeyframeAnimations.posVec(0f, -0.29f, 0.15f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(7.5f, 0f, 0f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -1f, 0.75f));
        model.root.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 90f));
        model.root.offsetPos(KeyframeAnimations.posVec(0f, -5f, 0f));
        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(40.11192f, -29.43433f, -3.24563f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.25f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(38.7522f, -4.33973f, 12.33768f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.25f, 0f));
        }
    }

    public static void setupFaintPose2(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.head.offsetRotation(KeyframeAnimations.degreeVec(4.34f, -4.46f, 16.94f));
        model.head.offsetPos(KeyframeAnimations.posVec(0, -7f, 0.25f));

        model.body.offsetRotation(KeyframeAnimations.degreeVec(-7.5f, 0, 0));
        model.body.offsetPos(KeyframeAnimations.posVec(0, -6.5f, -1.5f));

        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-5, 0, 0));
        model.mane.offsetPos(KeyframeAnimations.posVec(0, -6.75f, 0));

        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(87.38f, -17.48f, 0.79f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(0, -7.5f, -1));

        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(90, 22.5f, 0));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0, -7.5f, -1.25f));

        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-88.01f, 24.9f, -2.33f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0, -6.75f, 0));

        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-91.14f, -29.72f, 4.31f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0, -7, 0));

        model.tail.xRot = ((Dog) dog).getTailRotation();
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(62.36f, -4.65f, 2.29f));
        model.tail.offsetPos(KeyframeAnimations.posVec(0.17f, -7.48f, -1.35f));

        
        
        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(51.29f, 23.61f, -14.74f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(53.71f, -14.26f, 10.25f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public static void setupRestPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.head.offsetRotation(KeyframeAnimations.degreeVec(-7.54f, 0.76f, 2.5f));
        model.head.offsetPos(KeyframeAnimations.posVec(0f, -5f, 2f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(0.5f, 0f, 0f));
        model.body.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 0f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 22.5f, 0f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -7f, 1f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, -22.5f, 0f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.5f, -7f, 1f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, 14.98539f, 0.66963f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, -14.98539f, -0.66963f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        model.tail.xRot = ((Dog) dog).getTailRotation();
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, -40f, 0f));
        model.tail.offsetPos(KeyframeAnimations.posVec(0f, -7f, -0.25f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-2.5f, 0f, 0f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 2f));
        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, 14.48f, -26.57f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, -14.48f, 26.57f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public static void setupLyingPose2(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        
        model.head.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 27.5f));
        model.head.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(0.5f, 0f, 0f));
        model.body.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 0f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 22.5f, 0f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -7f, 1f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, -22.5f, 0f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.5f, -7f, 1f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, 14.98539f, 0.66963f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, -14.98539f, -0.66963f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        model.tail.xRot = ((Dog) dog).getTailRotation();
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        model.tail.offsetPos(KeyframeAnimations.posVec(0f, -7f, -0.25f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-2.5f, 0f, 0f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 2f));
        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, 14.48f, -26.57f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, -14.48f, 26.57f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public static void setupFlyPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(84.84f, -5.13f, -5.65f));
        model.tail.offsetPos(KeyframeAnimations.posVec(0f, -1.18f, -1.63f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-84.33f, 0f, 0f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-100.98f, 0f, 0f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(78.49f, 0f, 0f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, 0f, -1.85f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(59.61f, 0f, 0f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, 0f, -1.85f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-9.67f, 0f, 0f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0.46f));
        model.head.offsetRotation(KeyframeAnimations.degreeVec(-13.12f, 0f, 0f));
        model.head.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        model.root.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        model.root.offsetPos(KeyframeAnimations.posVec(0f, 2.03f, 0f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(1.02f, 0f, 0f));
        model.body.offsetPos(KeyframeAnimations.posVec(0f, -1.03f, -1.32f));

        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(-55.69f, 2.98f, -3.43f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.35f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(-55.69f, -2.98f, 3.43f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.35f, 0f));
        }
        
    }

    public static void setupDrownPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.head.offsetRotation(KeyframeAnimations.degreeVec(35, 0, 0));
        model.head.offsetPos(KeyframeAnimations.posVec(0, -0.5f, 0.25f));

        model.body.offsetRotation(KeyframeAnimations.degreeVec(-22.5f, 0, 0));
        model.body.offsetPos(KeyframeAnimations.posVec(0, 1.5f, -2.5f));

        model.mane.offsetRotation(KeyframeAnimations.degreeVec(10, 0, 0));
        model.mane.offsetPos(KeyframeAnimations.posVec(0, 1, 0));

        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(0, 0, -3.5f));

        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0, 0, -3.5f));

        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(0, 2, 0));

        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0, 1, 0));

        model.tail.xRot = ((Dog) dog).getTailRotation();
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(10, 0, 0));
        model.tail.offsetPos(KeyframeAnimations.posVec(0, -0.5f, -2f));
    }

    public static void setupRestBellyPose(DogModel model, Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        model.head.offsetRotation(KeyframeAnimations.degreeVec(-38.87f, -25.09f, -23.41f));
        model.head.offsetPos(KeyframeAnimations.posVec(-0.01f, -1.9f, 0.36f));
        model.body.offsetRotation(KeyframeAnimations.degreeVec(-18.36f, 14.74f, 22.42f));
        model.body.offsetPos(KeyframeAnimations.posVec(0.21f, -3.81f, -0.83f));
        model.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-26.7f, 14.23f, 47.53f));
        model.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.09f, -3.49f, -0.65f));
        model.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-38.05f, -3.87f, 0f));
        model.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.09f, -6.17f, -2.3f));
        model.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-42.32f, 19.74f, 41.58f));
        model.legFrontRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -2f, 1.18f));
        model.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-29.77f, 2.06f, -2.6f));
        model.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -2f, 0.35f));
        model.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, 29.9f, 0f));
        model.tail.offsetPos(KeyframeAnimations.posVec(1.24f, -6.7f, -1.49f));
        model.mane.offsetRotation(KeyframeAnimations.degreeVec(-12.78f, 7.97f, 26.29f));
        model.mane.offsetPos(KeyframeAnimations.posVec(0f, -2.79f, 0.35f));
        model.root.offsetRotation(KeyframeAnimations.degreeVec(22.48072f, -0.95645f, 132.30991f));
        model.root.offsetPos(KeyframeAnimations.posVec(2f, -8f, 0f));

        if (model.earRight.isPresent()) {
            model.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(37.09314f, 13.50103f, -46.63682f));
            model.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.12f, 0f));
        }
        if (model.earLeft.isPresent()) {
            model.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(32.49638f, 13.81807f, -32.61916f));
            model.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.51f, 0f));
        }
    }
    
}
