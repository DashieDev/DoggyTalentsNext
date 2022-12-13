package doggytalents.common.network.packet.data;

import net.minecraft.world.item.ItemStack;

public class DogEatingParticleData {
    public int dogId;
    public ItemStack food;

    public DogEatingParticleData(int dogId, ItemStack food) {
        this.dogId = dogId;
        this.food = food;
    }
}
