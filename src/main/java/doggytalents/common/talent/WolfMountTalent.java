package doggytalents.common.talent;

import doggytalents.DoggyAttributes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class WolfMountTalent extends TalentInstance {

    private static final UUID WOLF_MOUNT_JUMP = UUID.fromString("7f338124-f223-4630-8515-70ee0bfbc653");

    public WolfMountTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        dogIn.setAttributeModifier(DoggyAttributes.JUMP_POWER.get(), WOLF_MOUNT_JUMP, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        dogIn.setAttributeModifier(DoggyAttributes.JUMP_POWER.get(), WOLF_MOUNT_JUMP, this::createSpeedModifier);
    }

    @Override
    public void remove(AbstractDog dog) {
        dog.removeAttributeModifier(DoggyAttributes.JUMP_POWER.get(), WOLF_MOUNT_JUMP);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = 0.06D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, "Wolf Mount", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!stack.isEmpty())
            return InteractionResult.PASS;
        if (this.level() <= 0)
            return InteractionResult.PASS;
        if (dog.isVehicle() || dog.isPassenger())
            return InteractionResult.PASS;
        if (!dog.canInteract(player))
            return InteractionResult.PASS;
        
        if (player.isPassenger())
            return InteractionResult.PASS;
        if (player.onGround())
            return InteractionResult.PASS;

        if (!dog.level().isClientSide) {
            dog.setOrderedToSit(false);
            player.setYRot(dog.getYRot());
            player.setXRot(dog.getXRot());
            player.startRiding(dog);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void livingTick(AbstractDog dog) {
        if (dog.isVehicle() && dog.getDogHunger() < 1) {
            dog.getControllingPassenger().sendSystemMessage(Component.translatable("talent.doggytalents.wolf_mount.exhausted", dog.getName()));

            dog.ejectPassengers();
        }
    }

    @Override
    public InteractionResultHolder<Integer> hungerTick(AbstractDog dogIn, int hungerTick) {
        if (dogIn.isVehicle()) {
            hungerTick += this.level() < 5 ? 3 : 1;
            return InteractionResultHolder.success(hungerTick);
        }

        return InteractionResultHolder.pass(hungerTick);
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() >= 5) {
            return InteractionResultHolder.success(distance - 1F);
        }

        return InteractionResultHolder.pass(0F);
    }

    @Override
    public InteractionResult shouldSkipAttackFrom(AbstractDog dogIn, Entity entity) {
        // If the attacking entity is riding block
        return dogIn.isPassengerOfSameVehicle(entity) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}
