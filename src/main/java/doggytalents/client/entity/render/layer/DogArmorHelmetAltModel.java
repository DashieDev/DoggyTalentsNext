package doggytalents.client.entity.render.layer;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DogArmorHelmetAltModel {
 
    private HumanoidModel<Dog> helmetModel;
    private HumanoidModel<Dog> dummyModel;

    public DogArmorHelmetAltModel(EntityRendererProvider.Context ctx) {
        this.helmetModel = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
        this.dummyModel = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
        this.helmetModel.setAllVisible(false);
        this.helmetModel.head.visible = true;
        this.helmetModel.young = false;
        
        this.dummyModel.setAllVisible(false);
        this.dummyModel.head.visible = true;
        this.dummyModel.young = false;
    }

    public Model getModel() {
        return helmetModel;
    }

    public HumanoidModel<?> getDummy() {
        return dummyModel;
    }

}
