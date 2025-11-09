package doggytalents.client.backward_imitate;

import doggytalents.client.backward_imitate.WrapperVanillaModel_1_21_9.DefferedSetupContainer_1_21_9;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;

public class WrappedEntityRenderState_21_3<T extends Entity> extends EntityRenderState implements DefferedSetupContainer_1_21_9 {
        
    public T entity;
    public WrappedEntityRenderState_21_3(T entity) {
        this.entity = entity;
    }



    //1.21.9+
    public Runnable defferedSetup_1_21_9 = null;
    @Override
    public void runDefferedSetupAndInvalidate_1_21_9() {
        if (defferedSetup_1_21_9 == null)
            return;
        defferedSetup_1_21_9.run();
        defferedSetup_1_21_9 = null;
    }



    //Fabric
    public float partialTick;

}