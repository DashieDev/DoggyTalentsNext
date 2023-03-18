package doggytalents.api.inferface;

import java.util.UUID;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import doggytalents.api.feature.EnumGender;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.feature.IDog;
import net.minecraft.network.chat.Component;
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

    public void setAttributeModifier(Attribute attribute, UUID modifierUUID, BiFunction<AbstractDog, UUID, AttributeModifier> modifierGenerator) {
        AttributeInstance attributeInst = this.getAttribute(attribute);

        AttributeModifier currentModifier = attributeInst.getModifier(modifierUUID);

        // Remove modifier if it exists
        if (currentModifier != null) {

            // Use UUID version as it is more efficient since
            // getModifier would need to be called again
            attributeInst.removeModifier(modifierUUID);
        }

        AttributeModifier newModifier = modifierGenerator.apply(this, modifierUUID);

        if (newModifier != null) {
            attributeInst.addTransientModifier(newModifier);
        }
    }

    public void removeAttributeModifier(Attribute attribute, UUID modifierUUID) {
        this.getAttribute(attribute).removeModifier(modifierUUID);
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
        if (this.navigation == p) return;
        this.navigation.stop();
        this.navigation = p;
    }

    //TODO try to replicate the bug and check if moveControl.haveWantedPosition using debug magic
    public void setMoveControl(MoveControl m) {
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

        this.moveControl = m;
    }

    public abstract void resetNavigation();

    public abstract void resetMoveControl();

    public abstract boolean canSwimUnderwater();

    //Start : Re-adjust armor behaviour
    //All dog start hurting Amrmor in armorItems regradless of anything.
    @Override
    protected void hurtArmor(DamageSource p_36251_, float p_36252_) {
        if (!(p_36252_ <= 0.0F)) {
            p_36252_ /= 4.0F;
            if (p_36252_ < 1.0F) {
                p_36252_ = 1.0F;
            }

            int j = 0;
            for(var i : this.getArmorSlots()) {
                ItemStack itemstack = i;
                if ((!p_36251_.is(DamageTypeTags.IS_FIRE) || !itemstack.getItem().isFireResistant()) && itemstack.getItem() instanceof ArmorItem) {
                    final var slot = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, j);
                    itemstack.hurtAndBreak((int)p_36252_, this, (p_35997_) -> {
                        p_35997_.broadcastBreakEvent(slot);
                    });
                }
                ++j;
            }

        }
    }

    @Override
    protected void hurtHelmet(DamageSource p_150103_, float p_150104_) {
        if (!(p_150104_ <= 0.0F)) {
            p_150104_ /= 4.0F;
            if (p_150104_ < 1.0F) {
                p_150104_ = 1.0F;
            }


            var i = this.getItemBySlot(EquipmentSlot.HEAD);

            ItemStack itemstack = i;
            if ((!p_150103_.is(DamageTypeTags.IS_FIRE) || !itemstack.getItem().isFireResistant()) && itemstack.getItem() instanceof ArmorItem) {
                itemstack.hurtAndBreak((int)p_150104_, this, (p_35997_) -> {
                    p_35997_.broadcastBreakEvent(EquipmentSlot.HEAD);
                });
            }

        }
    }
    //End : Re-adjust armor behaviour

    public boolean isDefeated() {
        return this.getMode() == EnumMode.INCAPACITATED;
    }
}
