package doggytalents.common.talent;

import doggytalents.DoggyAttributes;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;
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
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (stack.isEmpty()) { // Held item
            if (dogIn.canInteract(playerIn) && this.level() > 0) { // Dog
                if (playerIn.getVehicle() == null && !playerIn.isOnGround()) { // Player
                    if (!dogIn.level.isClientSide) {
                        dogIn.setOrderedToSit(false);
                        playerIn.setYRot(dogIn.getYRot());
                        playerIn.setXRot(dogIn.getXRot());
                        playerIn.startRiding(dogIn);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void livingTick(AbstractDog dog) {
        if (dog.isVehicle() && dog.getDogHunger() < 1) {
            dog.getControllingPassenger().sendMessage(ComponentUtil.translatable("talent.doggytalents.wolf_mount.exhausted", dog.getName()), net.minecraft.Util.NIL_UUID);

            dog.ejectPassengers();
        }
    }

    @Override
    public InteractionResultHolder<Integer> hungerTick(AbstractDog dogIn, int hungerTick) {
        if (dogIn.isControlledByLocalInstance()) {
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
