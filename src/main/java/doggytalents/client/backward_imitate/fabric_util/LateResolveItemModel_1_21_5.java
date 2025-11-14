package doggytalents.client.backward_imitate.fabric_util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LateResolveItemModel_1_21_5 implements ItemModel {
    
    private ItemModel itemModel = null;


    public void resolve(ItemModel resolved) {
        this.itemModel = resolved;
    }

    @Override
    public void update(ItemStackRenderState arg0, ItemStack arg1, ItemModelResolver arg2, ItemDisplayContext arg3,
            ClientLevel arg4, ItemOwner arg5, int arg6) {
        this.itemModel.update(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

}
