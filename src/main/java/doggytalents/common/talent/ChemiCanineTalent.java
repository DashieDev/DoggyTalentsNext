package doggytalents.common.talent;

import java.util.ArrayList;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;

public class ChemiCanineTalent extends TalentInstance {

    private final ArrayList<MobEffectInstance> storedEffects = new ArrayList<>();
    private final int SEARCH_RADIUS = 12; //const ?? maybe can improve thru talents
    private int absorbEffectCooldown;
    private int tickTillSearch;
    private int tickTillEffectDecay = 0;

    public ChemiCanineTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(AbstractDog abstractDog) {
        if (abstractDog.level().isClientSide) {
            return;
        }

        if (!(abstractDog instanceof Dog)) return;

        var dog = (Dog) abstractDog;

        if (tickTillEffectDecay > 0) {
            --tickTillEffectDecay;
            if (tickTillEffectDecay <= 0 && !this.storedEffects.isEmpty()) {
                this.storedEffects.remove(0);
            }
        }

        if (this.absorbEffectCooldown > 0 ) {
            --this.absorbEffectCooldown; return;
        }
        
        if (
            dog.readyForNonTrivialAction() 
            && !dog.isOrderedToSit()
            && --this.tickTillSearch <= 0
        ) {
            this.tickTillSearch = 10;
            var target = findTargetToAbsorb(abstractDog);
            if (target != null && this.stillValidTarget(dog, target)) {
                this.triggerAbsorbAction(dog, target);
            }
        }

    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        if (!compound.contains("DTN_ChemiCanine", Tag.TAG_COMPOUND))
            return;
        var tg0 = compound.getCompound("DTN_ChemiCanine");
        this.tickTillEffectDecay = tg0.getInt("tickTillEffectDecay");
        if (!tg0.contains("effects", Tag.TAG_LIST))
            return;
        var effectTags = tg0.getList("effects", Tag.TAG_COMPOUND);
        for (int i = 0; i < effectTags.size(); ++i) {
            try {
                var effectTag = effectTags.getCompound(i);
                var effectInst = MobEffectInstance.load(effectTag);
                if (effectInst != null)
                    this.storedEffects.add(effectInst);   
            } catch (Exception e) {
                
            }
        }
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        var tg0 = new CompoundTag();
        tg0.putInt("tickTillEffectDecay", this.tickTillEffectDecay);
        var effectTags = new ListTag();
        for (var effect : this.storedEffects) {
            var effectTag = new CompoundTag();
            effect.save(effectTag);
            effectTags.add(effectTag);
        }
        tg0.put("effects", effectTags);
        compound.put("DTN_ChemiCanine", tg0);
    }

    private void triggerAbsorbAction(Dog dog, @Nonnull LivingEntity target) {
        dog.triggerAction(new AbsorbAction(dog, this, target));
    }

    private LivingEntity findTargetToAbsorb(AbstractDog dog) {
        var absorbTargets = new ArrayList<LivingEntity>();
        Predicate<LivingEntity> harmfulEffectAndWitness =
            e -> this.isTargetHaveNegativeEffect(dog, e) 
                && (dog.getSensing().hasLineOfSight(e));
        
        //Get owner 
        var owner = dog.getOwner();
        if (owner == null) return null;

        //Check owner first
        if (harmfulEffectAndWitness.test(owner)) {
            absorbTargets.add(owner);
        }
        
        //Get Dogs of the same owner
        var dogs = dog.level().getEntitiesOfClass(
            AbstractDog.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            d -> (
                    d.getOwner() == owner
                    && harmfulEffectAndWitness.test(d)
                )
            );
        if (!dogs.isEmpty()) {
            absorbTargets.addAll(dogs);
        }

        //Get Wolves of the same owner
        var wolves = dog.level().getEntitiesOfClass(
            Wolf.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            w -> (
                    w.getOwner() == owner
                    && harmfulEffectAndWitness.test(w)
                )
            );
        if (!wolves.isEmpty()) {
            absorbTargets.addAll(wolves);
        }

        if (dog instanceof Dog ddog && ddog.regardTeamPlayers()) {
            var teamPlayers = dog.level().getEntitiesOfClass(
                Player.class,    
                dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
                p -> (
                        p.isAlliedTo(owner)
                        && harmfulEffectAndWitness.test(p)
                    )
                );
            if (!teamPlayers.isEmpty()) {
                absorbTargets.addAll(teamPlayers);
            }
        }
        return selectAbsorbTarget(dog, absorbTargets);
    }

    private boolean isTargetHaveNegativeEffect(AbstractDog dog, LivingEntity e) {
        for (var effectInst : e.getActiveEffects()) {
            if (isHarmfulEffect(effectInst)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHarmfulEffect(MobEffectInstance effectInst) {
        return effectInst.getEffect().getCategory() == MobEffectCategory.HARMFUL;
    }

    private LivingEntity selectAbsorbTarget(AbstractDog dog, ArrayList<LivingEntity> absorbTargets) {
        if (absorbTargets.isEmpty()) return null;

        var target = absorbTargets.get(0); 
        double mindistanceSqr = target.distanceToSqr(dog);

        var owner = dog.getOwner();
        
        for (var i : absorbTargets) {
            if (owner == i) return i;
            else {
                var d = i.distanceToSqr(dog);
                if (d < mindistanceSqr) {
                    target = i;
                    mindistanceSqr = d;
                }
            }
        }

        //ChopinLogger.l("target : " +target);

        return target;

    }

    public int absorbCost(AbstractDog dog, LivingEntity target) {
        int cost;
        if (this.level() >= 5) {
            cost = 10;
        } else {
            cost = 20;
        }

        return cost;
    }

    public int getMaxAbsorbSize() {
        return this.level()*2;
    }

    private boolean canAffordToAbsorbTarget(AbstractDog dog, LivingEntity e) {
        return dog.getDogHunger() - 10 >= this.absorbCost(dog, e)
            && this.storedEffects.size() < getMaxAbsorbSize();
    }

    private boolean stillValidTarget(Dog dog, LivingEntity target) {
        if (!target.isAlive()) return false;
        if (!this.isTargetHaveNegativeEffect(dog, target)) return false;
        if (!this.canAffordToAbsorbTarget(dog, target)) return false;
        if (dog.distanceToSqr(target) > 400) return false;         
        if (target instanceof Dog d && d.isDefeated()) return false;
        
        return true;
    }

    private boolean canAbsorbTarget(AbstractDog dog, LivingEntity e) {
        return (
            dog.distanceToSqr(e) <= 5
            && dog.getSensing().hasLineOfSight(e)
        );
    }

    private void absorb(AbstractDog dog, LivingEntity e) {
        if (this.absorbEffectCooldown > 0) return;
        MobEffect removeEffect = null;
        for (var effectInst : e.getActiveEffects()) {
            if (isHarmfulEffect(effectInst)) {
                removeEffect = effectInst.getEffect();
                break;
            }
        }
        if (removeEffect == null)
            return;
        var effectInst = e.getEffect(removeEffect);
        if (effectInst == null)
            return;
        if (this.storedEffects.size() >= this.getMaxAbsorbSize())
            return;
        if (!e.removeEffect(removeEffect))
            return;
        this.storedEffects.add(effectInst);
        dog.setDogHunger(
            dog.getDogHunger() - this.absorbCost(dog, e)
        );
        if (dog.level() instanceof ServerLevel) {
            ((ServerLevel) dog.level()).sendParticles(
                ParticleTypes.WITCH, 
                e.getX(), e.getY(), e.getZ(), 
                30, 
                e.getBbWidth(), 0.8f, e.getBbWidth(), 
                0.1
            );
        }
        this.absorbEffectCooldown = dog.getRandom().nextInt(3) * 20; // Between 2 seconds
        this.tickTillEffectDecay = 24000;
    }

    @Override
    public InteractionResult isPotionApplicable(AbstractDog dogIn, MobEffectInstance effectIn) {
        if (
            this.level() >= 5
            && effectIn.getEffect().getCategory() == MobEffectCategory.HARMFUL
        ) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void doAdditionalAttackEffects(AbstractDog dogIn, Entity target) {
        if (!target.isAlive())
            return;
        if (this.storedEffects.isEmpty())
            return;
        var effectAdding = this.storedEffects.get(0);
        
        if (target instanceof LivingEntity e
            && !e.hasEffect(effectAdding.getEffect())) {
            e.addEffect(effectAdding);
            this.storedEffects.remove(effectAdding);
        }
    }

    public static class AbsorbAction extends TriggerableAction {

        private ChemiCanineTalent talentInst;
        private @Nonnull LivingEntity target;

        private int ticksUntilPathRecalc = 0;

        private final int stopDist = 2;    

        public AbsorbAction(Dog dog, ChemiCanineTalent talentInst, @Nonnull LivingEntity target) {
            super(dog, false, true);
            this.talentInst = talentInst;
            this.target = target;
        }

        @Override
        public void onStart() {
            //this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
            ticksUntilPathRecalc = 0;
        }

        @Override
        public void tick() {
            if (!this.talentInst.stillValidTarget(dog, target)) {
                this.setState(ActionState.FINISHED); return;
            }

            if (this.dog.distanceToSqr(this.target) > stopDist*stopDist) {

                this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.ticksUntilPathRecalc <= 0) {
                    this.ticksUntilPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //A Valid target is not that far away and is checked above.
                        //if (dog.distanceToSqr(target) > 400) return;
                        this.dog.getNavigation().moveTo(this.target, dog.getUrgentSpeedModifier());
                    }
                }
            } else {
                //TODO maintain some space ??
                //this.dog.getNavigation().stop();
                if (this.talentInst.canAbsorbTarget(dog, target))
                    this.talentInst.absorb(dog, target);
            }
        }

        @Override
        public void onStop() {
        }
        
    }

}
