package doggytalents.common.talent.doggy_tools;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DoggyTags;
import doggytalents.DoggyTalents;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.talent.doggy_tools.tool_actions.DogBridgingAction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DogBridging {

    public static Optional<Pair<ItemStack, BlockItem>> getBridgingMaterial(DoggyToolsTalent inst, Dog dog) {
        for (int i = 0; i < DoggyToolsTalent.getSize(inst.level()); ++i) {
            var stack = inst.getTools().getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof BlockItem block_item))
                continue;
            if (block_item.getBlock()
                .builtInRegistryHolder().is(DoggyTags.BRIDGING_DOG_BLACKLIST)) 
                continue;
            return Optional.of(Pair.of(stack, block_item)); 
        }
        return Optional.empty();
    }

    public static boolean isValidBridgingDog(DoggyToolsTalent inst, Dog dog) {
        if (!ConfigHandler.SERVER.DOGGY_TOOLS_BRIDGING.get())
            return false;
        if (inst.level() < inst.getTalent().getMaxLevel())
            return false;
        var pair = getBridgingMaterial(inst, dog);
        if (!pair.isPresent())
            return false;
        return true;
    }

    public static boolean isValidBridgingDog(Dog dog) {
        var inst = dog.getTalent(DoggyTalents.DOGGY_TOOLS.get(), DoggyToolsTalent.class);
        if (!inst.isPresent())
            return false;
        return isValidBridgingDog(inst.get(), dog);
    }

    public static void equipBridgingStack(DoggyToolsTalent inst, Dog dog) {
        var pair = getBridgingMaterial(inst, dog);
        if (!pair.isPresent())
            return;
        dog.setItemInHand(InteractionHand.MAIN_HAND, pair.get().getLeft());
    }

    public static Optional<Pair<Dog, DoggyToolsTalent>> getNearestBridgingDog(Player player, Level level) {
        var valid_dogs = level.getEntitiesOfClass(
            Dog.class, 
            player.getBoundingBox()
                .inflate(7, 2, 7),
            d -> {
                if (!d.isDoingFine())
                    return false;
                if (d.isBusy())
                    return false;
                if (d.isOrderedToSit())
                    return false;
                if (!isValidBridgingDog(d))
                    return false;
                return true;
            }    
        );
        if (valid_dogs.isEmpty())
            return Optional.empty();
        double min_dist = valid_dogs.get(0).distanceToSqr(player);
        Dog choosen_dog = valid_dogs.get(0);
        for (var dog : valid_dogs) {
            double dist = dog.distanceToSqr(player);
            if (dist < min_dist) {
                min_dist = dist;
                choosen_dog = dog;
            }
        }

        var inst = choosen_dog.getTalent(DoggyTalents.DOGGY_TOOLS.get(), DoggyToolsTalent.class);
        if (!inst.isPresent())
            return Optional.empty();

        return Optional.of(Pair.of(choosen_dog, inst.get()));
    }

    public static Optional<Integer> getBridgingLimit() {
        int limit = ConfigHandler.SERVER.DOGGY_TOOLS_BRIDGING_LIMIT.get();
        if (limit <= 0)
            return Optional.empty();
        return Optional.of(limit);
    }
    
    public static boolean checkIfReachedBridgingLimit(Player player) {
        var limit_optional = getBridgingLimit();
        if (!limit_optional.isPresent())
            return false;
        
        final int limit = limit_optional.get();

        var storage = DogLocationStorage.get(player.level().getServer());
        int current_count = storage.bridgingDogLimitMap.getOrDefault(player.getUUID(), 0);
        return current_count >= limit;
    }

    public static void incBridgingCount(LivingEntity player) {
        var storage = DogLocationStorage.get(player.level().getServer());
        storage.bridgingDogLimitMap.compute(player.getUUID(), (uuid, old_val) -> {
            if (old_val == null) {
                return 1;
            }
            return old_val + 1;
        });
    }

    public static void decBridgingCount(LivingEntity player) {
        var storage = DogLocationStorage.get(player.level().getServer());
        storage.bridgingDogLimitMap.computeIfPresent(player.getUUID(), (uuid, old_val)  -> {
            if (old_val == null) {
                return null;
            }
            int new_val = old_val - 1;
            if (new_val <= 0) return null;
            return new_val;
        });
    }

    public static void useBridgingWhistle(WhistleItem item, Player owner, Level level) {
        if (level.isClientSide())
            return;
        if (!ConfigHandler.SERVER.DOGGY_TOOLS_BRIDGING.get())
            return;
        if (checkIfReachedBridgingLimit(owner)) {
            owner.sendSystemMessage(
                Component.translatable("dogcommand.bridging.limit_exceeded",
                getBridgingLimit().orElse(0)).withStyle(ChatFormatting.RED));
            owner.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), 20);
            return;
        }
        var pair_optional = getNearestBridgingDog(owner, level);
        if (!pair_optional.isPresent())
            return;
        var pair = pair_optional.get();
        var dog = pair.getLeft();
        var inst = pair.getRight();
        var action = new DogBridgingAction(dog, owner, owner.getYHeadRot(), 
            owner.blockPosition(), inst);
        if (dog.triggerAction(action)) {
            incBridgingCount(owner);
        }
        
        owner.sendSystemMessage(
            Component.translatable("dogcommand.bridging",
            dog.getName().getString()));
        owner.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(item), 20);
    }

    public static void onBridgingActionStop(LivingEntity owner) {
        decBridgingCount(owner);
    }

}
