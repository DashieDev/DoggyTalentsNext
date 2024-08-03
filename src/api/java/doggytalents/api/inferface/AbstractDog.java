package doggytalents.api.inferface;

import java.util.UUID;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.feature.IDog;
import doggytalents.api.impl.DogArmorItemHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractDog extends TamableAnimal implements IDog {

    protected AbstractDog(EntityType<? extends TamableAnimal> type, Level worldIn) {
        super(type, worldIn);
    }

    public void setAttributeModifier(Holder<Attribute> attribute, ResourceLocation modifierLoc, BiFunction<AbstractDog, ResourceLocation, AttributeModifier> modifierGenerator) {
        AttributeInstance attributeInst = this.getAttribute(attribute);

        AttributeModifier currentModifier = attributeInst.getModifier(modifierLoc);

        // Remove modifier if it exists
        if (currentModifier != null) {

            // Use UUID version as it is more efficient since
            // getModifier would need to be called again
            attributeInst.removeModifier(modifierLoc);
        }

        AttributeModifier newModifier = modifierGenerator.apply(this, modifierLoc);

        if (newModifier != null) {
            attributeInst.addTransientModifier(newModifier);
        }
    }

    public void removeAttributeModifier(Holder<Attribute> attribute, ResourceLocation modifierLoc) {
        var attrib = this.getAttribute(attribute);
        if (attrib == null) return;
        attrib.removeModifier(modifierLoc);
    }

    @Override
    public AbstractDog getDog() {
        return this;
    }

    // Makes the method public
    @Override
    public float getSoundVolume() {
        return super.getSoundVolume();
    }

    @Override
    public void spawnTamingParticles(boolean play) {
        super.spawnTamingParticles(play);
    }

    public void consumeItemFromStack(@Nullable Entity entity, ItemStack stack) {
        if (entity instanceof Player) {
            super.usePlayerItem((Player) entity, InteractionHand.MAIN_HAND, stack);
        } else {
            stack.shrink(1);
        }
    }

    public abstract Component getTranslationKey(Function<EnumGender, String> function);

    public Component getGenderPronoun() {
        return this.getTranslationKey(EnumGender::getUnlocalisedPronoun);
    }

    public Component getGenderSubject() {
        return this.getTranslationKey(EnumGender::getUnlocalisedSubject);
    }

    public Component getGenderPossessiveAdj() {
        return this.getTranslationKey(EnumGender::getUnlocalisedPossessiveAdj);
    }

    public Component getGenderTitle() {
        return this.getTranslationKey(EnumGender::getUnlocalisedTitle);
    }

    public Component getGenderTip() {
        return this.getTranslationKey(EnumGender::getUnlocalisedTip);
    }

    public Component getGenderName() {
        return this.getTranslationKey(EnumGender::getUnlocalisedName);
    }

    public void setNavigation(PathNavigation p) {
        if (p == null) p = this.getDefaultNavigation();
        if (this.navigation == p) return;
        this.navigation.stop();
        this.navigation = p;
    }

    //TODO try to replicate the bug and check if moveControl.haveWantedPosition using debug magic
    public void setMoveControl(MoveControl m) {
        if (m == null) m = this.getDefaultMoveControl();
        breakMoveControl();

        this.moveControl = m;
    }

    public void breakMoveControl() {
        /*
         * Force the MoveControl To Reset :
         * this will set the dog's wanted Position to his current Position
         * which will cause the moveControl to halt movement and reset in the 
         * next tick(). 
         * And then immediately update the moveControl by calling tick() so 
         * that everything is resolved before anything else.
         */
        this.moveControl.setWantedPosition(
            this.getX(), 
            this.getY(), 
            this.getZ(), 1.0
        );
        this.moveControl.tick();

        //Also reset jump just to be sure.
        this.setJumping(false);

        //Also reset accelerations just to be sure.
        this.setSpeed(0.0F);
        this.setXxa(0.0F);
        this.setYya(0.0F);
        this.setZza(0.0F);
    }

    public abstract void resetNavigation();

    public abstract void resetMoveControl();

    public abstract MoveControl getDefaultMoveControl();
    public abstract PathNavigation getDefaultNavigation();

    public abstract boolean canSwimUnderwater();
    public abstract void setDogSwimming(boolean val);
    public abstract void setDogFlying(boolean val);
    public abstract boolean isDogFlying();
    public abstract float getClientAnimatedYBodyRotInRadians();
    public abstract float getDogVisualBbHeight();
    public abstract float getDogVisualBbWidth();
    public abstract DogArmorItemHandler dogArmors();
    public abstract ItemStack wolfArmor();
    public abstract boolean canDogWearArmor();
    public abstract boolean canDogUseTools();
    public abstract DogAnimation getAnim();
    public abstract boolean forcedWhenNoneAnim();
    public abstract boolean isDogVariantRenderEffective();

    //Start : Re-adjust armor behaviour
    //All dog start hurting Amrmor in armorItems regradless of anything.
    @Override
    protected void hurtArmor(DamageSource p_36251_, float p_36252_) {
        this.doHurtEquipment(p_36251_, p_36252_, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.BODY});
    }

    @Override
    protected void hurtHelmet(DamageSource p_150103_, float p_150104_) {
        this.doHurtEquipment(p_150103_, p_150104_, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }
    //End : Re-adjust armor behaviour

    public boolean isDefeated() {
        return this.getMode() == EnumMode.INJURED;
    }

    public boolean isDoingFine() {
        return this.isAlive() && !this.isDefeated();
    }

    public boolean isDogLowHealth() {
        return false;
    }

    public boolean canStillEat() {
        return false;
    }
}
