package doggytalents.common.talent;

import doggytalents.DoggyAttributes;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import doggytalents.api.inferface.DTNInteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class WolfMountTalent extends TalentInstance {

    private static final Identifier WOLF_MOUNT_JUMP = Util.getResource("wolf_mount_jump");
    private int lastClickTick;

    public WolfMountTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        dogIn.setAttributeModifier(DoggyAttributes.JUMP_POWER, WOLF_MOUNT_JUMP, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        dogIn.setAttributeModifier(DoggyAttributes.JUMP_POWER, WOLF_MOUNT_JUMP, this::createSpeedModifier);
    }

    @Override
    public void remove(AbstractDog dog) {
        dog.removeAttributeModifier(DoggyAttributes.JUMP_POWER, WOLF_MOUNT_JUMP);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, Identifier uuidIn) {
        if (this.level() > 0) {
            double speed = 0.06D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, speed, AttributeModifier.Operation.ADD_VALUE);
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

        int lastClickTick0 = this.lastClickTick;
        lastClickTick = player.tickCount;
        if (lastClickTick - lastClickTick0 > 5) {
            return InteractionResult.PASS;
        }

        if (!dog.level().isClientSide()) {
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
            var control = dog.getControllingPassenger();
            if (control != null)
                if (control instanceof net.minecraft.world.entity.player.Player _p_sys) _p_sys.sendSystemMessage(Component.translatable("talent.doggytalents.wolf_mount.exhausted", dog.getName()));

            dog.ejectPassengers();
        }
    }

    @Override
    public DTNInteractionResultHolder<Float> hungerTick(AbstractDog dogIn, float hungerTick_add) {
        if (dogIn.getControllingPassenger() != null) {
            hungerTick_add += this.level() < 5 ? 3 : 1;
            return DTNInteractionResultHolder.success(hungerTick_add);
        }

        return DTNInteractionResultHolder.pass(hungerTick_add);
    }

    @Override
    public DTNInteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() >= 5) {
            return DTNInteractionResultHolder.success(distance - 1F);
        }

        return DTNInteractionResultHolder.pass(0F);
    }

    @Override
    public InteractionResult shouldSkipAttackFrom(AbstractDog dogIn, Entity entity) {
        // If the attacking entity is riding block
        return dogIn.isPassengerOfSameVehicle(entity) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    public static boolean isValidCarryMeDog(Dog dog) {
        if (!dog.isAlive())
            return false;
        if (dog.isVehicle() || dog.isPassenger())
            return false;
        return dog.getDogLevel(DoggyTalents.WOLF_MOUNT) > 0;
    }

    public static boolean isValidCarryMeTarget(LivingEntity target) {
        if (!(target instanceof Player))
            return false;
        if (!target.isAlive() || target.isSpectator())
            return false;
        if (target.isVehicle() || target.isPassenger())
            return false;
        
        return true;
    }
}
