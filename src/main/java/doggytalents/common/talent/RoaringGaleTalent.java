package doggytalents.common.talent;

import java.util.List;
import java.util.stream.Collectors;

import doggytalents.DoggyTalents;
import doggytalents.api.feature.DataKey;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RoaringGaleTalent extends TalentInstance {

    public static DataKey<Integer> COOLDOWN = DataKey.make();

    public RoaringGaleTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    public static int getAffectDuration(int level) {
        if (level >= 5) return 70;
        if (level <= 0) return 0;
        switch (level) {
        case 1 :
            return 20;
        case 2 :
            return 24;
        case 3 :
            return 36;
        case 4 :
            return 48;
        default:
            return 20;
        }
    }

    public static int getKnockback(int level) {
        return level;
    }

    public static int getDamage(int level) {
        return level > 4 ? level * 2 : level;
    }

    public static void roar(List<Dog> dogsList, Level world, Player player) {
        if (world.isClientSide) return;
        var roarDogs = dogsList.stream()
            .filter(dog -> dog.getDogLevel(DoggyTalents.ROARING_GALE) > 0)
            .collect(Collectors.toList());
        if (roarDogs.isEmpty()) {
            player.displayClientMessage(Component.translatable("talent.doggytalents.roaring_gale.level"), true);
            return;
        }
        roarDogs = roarDogs.stream()
            .filter(RoaringGaleTalent::isNotOnRoarCooldown)
            .collect(Collectors.toList());
        if (roarDogs.isEmpty()) {
            player.displayClientMessage(Component.translatable("talent.doggytalents.roaring_gale.cooldown"), true);
            return;
        }
        boolean anyHits = false;

        for (Dog dog : roarDogs) {
            int level = dog.getDogLevel(DoggyTalents.ROARING_GALE);
            int roarCooldown = dog.tickCount;

            int damage = getDamage(level);
            int effectDuration = getAffectDuration(level);
            int knockback = getKnockback(level);

            boolean hit = false;
            var targets = dog.level().<LivingEntity>getEntitiesOfClass(LivingEntity.class, dog.getBoundingBox().inflate(level * 4, 4D, level * 4));
            for (LivingEntity mob : targets) {
                if (!(mob instanceof Enemy)) continue;
                hit = true;
                mob.hurt(mob.damageSources().generic(), damage);
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, effectDuration, 127, false, false));
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, effectDuration, 1, false, false));
                mob.push(
                    Mth.sin(mob.getYRot() * Mth.DEG_TO_RAD) * knockback * 0.5F, 
                    0.1D, 
                    -Mth.cos(mob.getYRot() * Mth.DEG_TO_RAD) * knockback * 0.5F
                );
            }

            if (hit) {
                dog.playSound(SoundEvents.WOLF_GROWL, 0.7F, 1.0F);
                roarCooldown += level >= 5 ? 60 : 100;
                anyHits = true;
            } else {
                dog.playSound(SoundEvents.WOLF_AMBIENT, 1F, 1.2F);
                roarCooldown += level >= 5 ? 30 : 50;
            }

            dog.setData(RoaringGaleTalent.COOLDOWN, roarCooldown);
        }

        if (!anyHits) {
            player.displayClientMessage(Component.translatable("talent.doggytalents.roaring_gale.miss"), true);
        }
    }

    private static boolean isNotOnRoarCooldown(Dog dog) {
        int cooldownDeadline = dog.getDataOrDefault(RoaringGaleTalent.COOLDOWN, dog.tickCount);
        return cooldownDeadline <= dog.tickCount;
    }
}
