package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BunnyEars extends Accessory implements IAccessoryHasModel{
    public BunnyEars(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HORN, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.BUNNY_EARS;
    }

    public AccessoryInstance getDefault() {
        return new Inst(this);
    }

    public static class Inst extends AccessoryInstance implements IDogAlteration {

        public Inst(Accessory typeIn) {
            super(typeIn);
        }

        @Override
        public void doAdditionalAttackEffects(AbstractDog dogIn, Entity target) {
            if (target == null || target.isAlive()) return;
            mayDropCandy(dogIn, target);
        }

        public void mayDropCandy(AbstractDog dog, Entity target) {
            float r = dog.getRandom().nextFloat();
            final float chance = 0.15f;
            if (r > chance) return;
            var item = DoggyItems.EASTER_EGG_CANDY.get();
            dog.spawnAtLocation(new ItemStack(item), 0.0F);
        }
        
    }
}
