package doggytalents.client.entity.render;

import doggytalents.client.entity.skin.DogSkin;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class DogRenderState extends LivingEntityRenderState {
    // Store entity reference for layer access (pragmatic approach)
    // This is safe as long as rendering stays single-threaded
    public Dog dog;
    // Skin captured at extraction time so preview renders use the correct skin
    // even when the entity's skin is temporarily changed and then restored.
    public DogSkin activeSkin;
    public Identifier skinTexture;
    // Animation data needed for model setup
    public float walkAnimSpeed;
    public float walkAnimPos;
    public float ageInTicksForAnim;
    public float headYawForAnim;
    public float headPitchForAnim;
}
