package doggytalents.api.inferface;

import doggytalents.api.enu.WetSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Defines objects which may alter the dog's properties or functions.
 * 
 * @author ProPercivalalb
 * compL by DashieDev
 */
public interface IDogAlteration {

    /**
     * Called when this instance is added to a dog's alterations list, 
     * which includes when the level is first set on the dog, when it is 
     * loaded from NBT or when the talents are synced to the client
     *
     * @param dogIn The dog
     */
    default void init(AbstractDog dogIn) {

    }

    /**
     * Called when this instance is about to be removed from the dog
     * 's alterations list. Or the Dog Object owning this instance is 
     * about to be removed. Notice that the later happens before the call 
     * to save CompoundTag, hence info which need to be saved should always 
     * be ready.
     * 
     * @param dogIn The dog
     */
    default void remove(AbstractDog dogIn) {

    }

    default void onWrite(AbstractDog dogIn, CompoundTag compound) {

    }

    default void onRead(AbstractDog dogIn, CompoundTag compound) {

    }

    /**
     * Called at the end of tick
     */
    default void tick(AbstractDog dogIn) {

    }

    /**
     * Called at the end of livingTick
     */
    default void livingTick(AbstractDog dogIn) {

    }

    default InteractionResultHolder<Integer> hungerTick(AbstractDog dogIn, int hungerTick) {
        return InteractionResultHolder.pass(hungerTick);
    }

    default InteractionResultHolder<Integer> healingTick(AbstractDog dogIn, int healingTick) {
        return InteractionResultHolder.pass(healingTick);
    }

    default InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canBeRiddenInWater(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canTrample(AbstractDog dogIn, BlockState state, BlockPos pos, float fallDistance) {
        return InteractionResult.PASS;
    }

    default InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        return InteractionResultHolder.pass(0F);
    }

    default InteractionResult canBreatheUnderwater(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canAttack(AbstractDog dogIn, LivingEntity target) {
        return InteractionResult.PASS;
    }

    default InteractionResult canAttack(AbstractDog dogIn, EntityType<?> entityType) {
        return InteractionResult.PASS;
    }

    default InteractionResult shouldAttackEntity(AbstractDog dog, LivingEntity target, LivingEntity owner) {
        return InteractionResult.PASS;
    }

    default InteractionResult shouldSkipAttackFrom(AbstractDog dog, Entity entity) {
        return InteractionResult.PASS;
    }

    default void doAdditionalAttackEffects(AbstractDog dogIn, Entity target) {
    }


    default InteractionResultHolder<Float> gettingAttackedFrom(AbstractDog dog, DamageSource source, float damage) {
        return InteractionResultHolder.pass(damage);
    }

    default InteractionResult canBlockDamageSource(AbstractDog dog, DamageSource source) {
        return InteractionResult.PASS;
    }

    default void onDeath(AbstractDog dog, DamageSource source) {

    }

    default void spawnDrops(AbstractDog dog, DamageSource source) {

    }

    default void dropLoot(AbstractDog dog, DamageSource source, boolean recentlyHitIn) {

    }

    default void dropInventory(AbstractDog dogIn) {

    }

    default InteractionResultHolder<Float> attackEntityFrom(AbstractDog dogIn, float distance, float damageMultiplier) {
        return InteractionResultHolder.pass(distance);
    }

    default InteractionResultHolder<Integer> decreaseAirSupply(AbstractDog dogIn, int air) {
        return InteractionResultHolder.pass(air);
    }

    default InteractionResultHolder<Integer> determineNextAir(AbstractDog dogIn, int currentAir) {
        return InteractionResultHolder.pass(currentAir);
    }

    default InteractionResult canStandOnFluid(AbstractDog dogIn, FluidState state) {
        return InteractionResult.PASS;
    }


    default InteractionResultHolder<Integer> setFire(AbstractDog dogIn, int second) {
        return InteractionResultHolder.pass(second);
    }

    default InteractionResult isImmuneToFire(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult isInvulnerableTo(AbstractDog dogIn, DamageSource source) {
        return InteractionResult.PASS;
    }

    default InteractionResult isInvulnerable(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult onLivingFall(AbstractDog dogIn, float distance, float damageMultiplier) {
        return InteractionResult.PASS;
    }

    default InteractionResult isBlockSafe(BlockState blockIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canSwimUnderwater(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canResistPushFromFluidType() {
        return InteractionResult.PASS;
    }

    default <T> LazyOptional<T> getCapability(AbstractDog dogIn, Capability<T> cap, Direction side) {
        return null;
    }

    default void invalidateCapabilities(AbstractDog dogIn) {

    }

    default InteractionResultHolder<Float> getMaxHunger(AbstractDog dogIn, float currentMax) {
        return InteractionResultHolder.pass(currentMax);
    }

    default InteractionResultHolder<Float> setDogHunger(AbstractDog dogIn, float hunger, float diff) {
        return InteractionResultHolder.pass(hunger);
    }

    default InteractionResult isPotionApplicable(AbstractDog dogIn, MobEffectInstance effectIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult blockIdleAnim(AbstractDog dogIn) {
        return InteractionResult.PASS;
    }

    /**
     * Re-infer the type to be a safer type if possible.
     * Noticed that this function here doesnt serve to
     * "dangerify" a path type, only to substitude a safer type
     * to show that the dog can handle that type. 
     * 
     * @param dog
     * @param pos
     * @return
     */
    default InteractionResultHolder<BlockPathTypes> inferType(AbstractDog dog, BlockPathTypes type) {
        return InteractionResultHolder.pass(type);
    }

    /**
     * Only called serverside
     * @param dogIn The dog
     * @param source How the dog initially got wet
     */
    default void onShakingDry(AbstractDog dogIn, WetSource source) {

    }
}
