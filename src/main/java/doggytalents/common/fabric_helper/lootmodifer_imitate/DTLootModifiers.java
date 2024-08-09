package doggytalents.common.fabric_helper.lootmodifer_imitate;

import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import doggytalents.common.entity.Dog;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DTLootModifiers {

    

    public static void init() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
            riceFromGrains_onBlockBreak(level, player, pos, state);
            
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((e, s) -> {
            soyFromZombie_onEntityKilled(e, s);
        });
    }

    private static void riceFromGrains_onBlockBreak(Level level, Player player, BlockPos pos, BlockState state) {
        if (!state.is(Blocks.SHORT_GRASS))
            return;
        var item = player.getMainHandItem();
        if (item.is(Items.SHEARS))
            return;
        float r = player.getRandom().nextFloat();
        if (r > 0.01F)
            return;
        var resultEntity = new ItemEntity(
            level, 
            pos.getX() + 0.5, 
            pos.getY() + 0.5, 
            pos.getZ() + 0.5, new ItemStack(DoggyItems.RICE_GRAINS.get()));
        level.addFreshEntity(resultEntity);
    }

    private static void soyFromZombie_onEntityKilled(LivingEntity entity, DamageSource source) {
        var killer = source.getEntity();
        if (!(killer instanceof Dog dog))
            return;
        if (!entity.getType().is(DoggyTags.DROP_SOY_WHEN_DOG_KILL))
            return;
        float r = dog.getRandom().nextFloat();
        if (r > 0.125F)
            return;
        int r2 = 1 + dog.getRandom().nextInt(3);
        var pos = entity.blockPosition();
        var resultEntity = new ItemEntity(
            dog.level(), 
            pos.getX() + 0.5, 
            pos.getY() + 0.5, 
            pos.getZ() + 0.5, new ItemStack(DoggyItems.SOY_BEANS.get(), r2));
        dog.level().addFreshEntity(resultEntity);
    }

}
