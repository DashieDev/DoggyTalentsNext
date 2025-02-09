package doggytalents.client.entity.render;

import doggytalents.client.backward_imitate.DogRenderState_21_3;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.entity.Dog;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollarRenderManager {

    private static final List<LayerFactory<DogRenderState_21_3, DogModel>> backer = new ArrayList<>();
    private static final List<LayerFactory<DogRenderState_21_3, DogModel>> accessoryRendererMap = Collections.synchronizedList(backer);

    /**
     * Register a renderer for a collar type
     * Call this during {@link net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent}.
     * This method is safe to call during parallel mod loading.
     */
    public static void registerLayer(LayerFactory<DogRenderState_21_3, DogModel> shader) {
        accessoryRendererMap.add(shader);
    }

    @Nullable
    public static List<LayerFactory<DogRenderState_21_3, DogModel>> getLayers() {
        return Collections.unmodifiableList(backer);
    }
}
