package doggytalents.common.item;

import doggytalents.DoggySounds;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.EnumMode;
import doggytalents.client.screen.HeelByNameScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DoggyBeamEntity;
import doggytalents.common.talent.RoaringGaleTalent;
import doggytalents.common.util.EntityUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class WhistleItem extends Item {

    public WhistleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                if (!stack.hasTag()) {
                    stack.setTag(new CompoundTag());
                    stack.getTag().putByte("mode", (byte)0);
                }

                int mode = stack.getTag().getInt("mode");
                stack.getTag().putInt("mode", (mode + 1) % 8);
            }

            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
        else {
            byte mode = 0;

            if (stack.hasTag() && stack.getTag().contains("mode", Tag.TAG_ANY_NUMERIC)) {
                mode = stack.getTag().getByte("mode");
            }

            List<Dog> dogsList = world.getEntitiesOfClass(Dog.class, player.getBoundingBox().inflate(100D, 50D, 100D), dog -> dog.isOwnedBy(player));
            boolean successful = false;

            if (mode == 0) { // Stand
                if (!world.isClientSide) {
                    for (Dog dog : dogsList) {
                        dog.setOrderedToSit(false);
                        dog.getNavigation().stop();
                        dog.setTarget(null);
                        if (dog.getMode() == EnumMode.WANDERING) {
                            dog.setMode(EnumMode.DOCILE);
                        }
                        successful = true;
                    }

                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound(null, player.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendSystemMessage(Component.translatable("dogcommand.come"));
                    }
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 1) { // Heel
                if (!world.isClientSide) {
                    for (Dog dog : dogsList) {
                        if (!dog.isInSittingPose() && dog.getMode() != EnumMode.WANDERING) {
                            if (dog.distanceToSqr(dog.getOwner()) > 9) { // Only heel if less than 3 blocks away
                                EntityUtil.tryToTeleportNearEntity(dog, dog.getNavigation(), dog.getOwner(), 2);
                            }
                            successful = true;
                        }
                    }

                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound(null, player.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendSystemMessage(Component.translatable("dogcommand.heel"));
                    }
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 2) { // Stay
                if (!world.isClientSide) {
                    for (Dog dog : dogsList) {
                        dog.setOrderedToSit(true);
                        dog.getNavigation().stop();
                        dog.setTarget(null);
                        if (dog.getMode() == EnumMode.WANDERING) {
                            dog.setMode(EnumMode.DOCILE);
                        }
                        successful = true;
                    }

                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound(null, player.blockPosition(), DoggySounds.WHISTLE_SHORT.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendSystemMessage(Component.translatable("dogcommand.stay"));
                    }
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            }
            else if (mode == 3) { // Ok
                if (!world.isClientSide) {
                    for (Dog dog : dogsList) {
                        if (dog.getMaxHealth() / 2 >= dog.getHealth()) {
                            dog.setOrderedToSit(true);
                            dog.getNavigation().stop();
                            dog.setTarget(null);
                        }
                        else {
                            dog.setOrderedToSit(false);
                            dog.getNavigation().stop();
                            dog.setTarget(null);
                        }
                        successful = true;
                    }

                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound(null, player.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.4F + world.random.nextFloat() * 0.2F);

                    if (successful) {
                        player.sendSystemMessage(Component.translatable("dogcommand.ok"));
                    }

                    return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
                }
            }
            else if (mode == 4) {
                if (!world.isClientSide) {
                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound(null, player.blockPosition(), DoggySounds.WHISTLE_SHORT.get(), SoundSource.PLAYERS, 0.6F + world.random.nextFloat() * 0.1F, 0.8F + world.random.nextFloat() * 0.2F);

                    //player.sendSystemMessage(new TranslationTextComponent("dogcommand.shepherd"));
                }

                return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
            } else if (mode == 5) {
                if (!world.isClientSide) {
                    if (ConfigHandler.WHISTLE_SOUNDS)
                    world.playSound((Player)null, player.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));

                    DoggyBeamEntity doggyBeam = new DoggyBeamEntity(world, player);
                    doggyBeam.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
                    world.addFreshEntity(doggyBeam);
                }

                return new InteractionResultHolder<>(InteractionResult.CONSUME, player.getItemInHand(hand));
            } else if (mode == 6) {
                if (!world.isClientSide) {
                    List<Dog> roarDogs = dogsList.stream().filter(dog -> dog.getDogLevel(DoggyTalents.ROARING_GALE) > 0).collect(Collectors.toList());
                    if (roarDogs.isEmpty()) {
                        player.displayClientMessage(Component.translatable("talent.doggytalents.roaring_gale.level"), true);
                    } else {
                        List<Dog> cdDogs = roarDogs.stream().filter(dog -> dog.getDataOrDefault(RoaringGaleTalent.COOLDOWN, dog.tickCount) <= dog.tickCount).collect(Collectors.toList());
                        if (cdDogs.isEmpty()) {
                            player.displayClientMessage(Component.translatable("talent.doggytalents.roaring_gale.cooldown"), true);
                        } else {
                            boolean anyHits = false;

                            for (Dog dog : dogsList) {
                                int level = dog.getDogLevel(DoggyTalents.ROARING_GALE);
                                int roarCooldown = dog.tickCount;

                                byte damage = (byte)(level > 4 ? level * 2 : level);

                                /*
                                 * If level = 1, set duration to  20 ticks (1 second); level = 2, set duration to 24 ticks (1.2 seconds)
                                 * If level = 3, set duration to 36 ticks (1.8 seconds); If level = 4, set duration to 48 ticks (2.4 seconds)
                                 * If level = max (5), set duration to 70 ticks (3.5 seconds);
                                 */
                                byte effectDuration = (byte)(level > 4 ? level * 14 : level * (level == 1 ? 20 : 12));
                                byte knockback = (byte)level;

                                boolean hit = false;
                                List<LivingEntity> list = dog.level.<LivingEntity>getEntitiesOfClass(LivingEntity.class, dog.getBoundingBox().inflate(level * 4, 4D, level * 4));
                                for (LivingEntity mob : list) {
                                    if (mob instanceof Enemy) {
                                        hit = true;
                                        mob.hurt(DamageSource.GENERIC, damage);
                                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, effectDuration, 127, false, false));
                                        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, effectDuration, 1, false, false));
                                        mob.push(Mth.sin(mob.getYRot() * (float) Math.PI / 180.0F) * knockback * 0.5F, 0.1D, -Mth.cos(mob.getYRot() * (float) Math.PI / 180.0F) * knockback * 0.5F);
                                    }
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
                    }
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
            } else if (mode == 7 && !dogsList.isEmpty() && player.level.isClientSide)  { //Debug chopin
                HeelByNameScreen.open();
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
            }

            //world.playSound(null, player.getPosition(), DoggySounds.WHISTLE_LONG, SoundCategory.PLAYERS, 0.8F, 0.8F + world.rand.nextFloat() * 0.2F);
            //world.playSound(null, player.getPosition(), DoggySounds.WHISTLE_SHORT, SoundCategory.PLAYERS, 0.8F, 0.6F + world.rand.nextFloat() * 0.2F);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        byte mode = 0;

        if (stack.hasTag() && stack.getTag().contains("mode", Tag.TAG_ANY_NUMERIC)) {
            mode = stack.getTag().getByte("mode");
        }
        return this.getDescriptionId() + "." + mode;

    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
}
