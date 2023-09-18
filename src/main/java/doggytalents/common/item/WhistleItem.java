package doggytalents.common.item;

import doggytalents.ChopinLogger;
import doggytalents.DoggyItems;
import doggytalents.DoggySounds;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.client.screen.HeelByGroupScreen;
import doggytalents.client.screen.HeelByNameScreen;
import doggytalents.client.screen.WhistleScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DoggyBeamEntity;
import doggytalents.common.entity.ai.triggerable.DogGoBehindOwnerAction;
import doggytalents.common.entity.ai.triggerable.DogMoveToBedAction;
import doggytalents.common.talent.MobRetrieverTalent;
import doggytalents.common.talent.RoaringGaleTalent;
import doggytalents.common.util.DogUtil;
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
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.I18NParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WhistleItem extends Item implements IDogItem {

    public static enum WhistleMode {
        STAND(0, WhistleSound.LONG),
        HEEL(1, WhistleSound.LONG),
        STAY(2, WhistleSound.SHORT),
        OKAY(3, WhistleSound.LONG),
        SHELPERD(4, WhistleSound.SHORT),
        TACTICAL(5, WhistleSound.NONE),
        ROAR(6, WhistleSound.NONE),
        HEEL_BY_NAME(7, WhistleSound.NONE),
        TO_BED(8, WhistleSound.LONG),
        GO_BEHIND(9, WhistleSound.SHORT),
        HEEL_BY_GROUP(10, WhistleSound.NONE),
        MOB_RETRIEVER(11, WhistleSound.SHORT),
        HEEL_BY_LOOK(12, WhistleSound.SHORT);
        
        
        public static final WhistleMode[] VALUES = 
            Arrays.stream(WhistleMode.values())
            .sorted(
                Comparator.comparingInt(WhistleMode::getIndex)
            ).toArray(size -> {
                return new WhistleMode[size];
            });

        private int id;
        private WhistleSound sound;

        private WhistleMode (int id, WhistleSound sound) {
            this.id = id;
            this.sound = sound;
        }

        public int getIndex() {
            return this.id;
        }

        public WhistleSound getSound() {
            return this.sound;
        }

        public String getUnlocalisedTitle() {
            return "item.doggytalents.whistle." + this.getIndex();
        }
    }

    public static enum WhistleSound { NONE, SHORT, LONG }

    public WhistleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player player,
            InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);  
        byte id_mode = 0;

        if (stack.hasTag() && stack.getTag().contains("mode", Tag.TAG_ANY_NUMERIC)) {
            id_mode = stack.getTag().getByte("mode");
        }
        if (id_mode >= WhistleMode.VALUES.length) id_mode = 0;
        var mode = WhistleMode.VALUES[id_mode];

        return mode == WhistleMode.MOB_RETRIEVER? 
            InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (world.isClientSide) {
                WhistleScreen.open();
            }

            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
        else {
            byte id_mode = 0;

            if (stack.hasTag() && stack.getTag().contains("mode", Tag.TAG_ANY_NUMERIC)) {
                id_mode = stack.getTag().getByte("mode");
            }

            List<Dog> dogsList = world.getEntitiesOfClass(
                Dog.class, 
                player.getBoundingBox().inflate(100D, 50D, 100D), 
                dog -> dog.isDoingFine() && dog.isOwnedBy(player)
            );

            if (id_mode >= WhistleMode.VALUES.length) id_mode = 0;
            var mode = WhistleMode.VALUES[id_mode];

            useMode(mode, dogsList, world, player, hand);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
        }
    }

    public void useMode(WhistleMode mode, List<Dog> dogsList, Level world, Player player, InteractionHand hand) {
        if (mode == null) return;

        if (ConfigHandler.WHISTLE_SOUNDS)
        switch (mode.getSound()) {
        case NONE:
            break;
        case LONG:
            world.playSound(null, player.blockPosition(), 
                DoggySounds.WHISTLE_LONG.get(), 
                SoundSource.PLAYERS, 
                0.6F + world.random.nextFloat() * 0.1F, 
                0.8F + world.random.nextFloat() * 0.2F
            );
            break;
        case SHORT:
            world.playSound(null, player.blockPosition(),
                DoggySounds.WHISTLE_SHORT.get(), 
                SoundSource.PLAYERS, 
                0.6F + world.random.nextFloat() * 0.1F, 
                0.8F + world.random.nextFloat() * 0.2F
            );
            break;            
        }
        
        boolean successful = false;
        switch (mode) {
        case STAND:
            if (world.isClientSide) return;
            for (var dog : dogsList) {
                dog.setOrderedToSit(false);
                dog.getNavigation().stop();
                dog.setTarget(null);
                if (dog.getMode() == EnumMode.WANDERING) {
                    dog.setMode(EnumMode.DOCILE);
                }
                successful = true;
            }

            if (successful) {
                player.sendSystemMessage(Component.translatable("dogcommand.come"));
            }
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        case HEEL:
            if (world.isClientSide) return;
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            int max_heel_count = ConfigHandler.ServerConfig.getConfig(
                ConfigHandler.SERVER.MAX_HEEL_LIMIT
            );

            var heel_list = dogsList.stream()
                .filter(filter_dog -> {
                    if (filter_dog.isOrderedToSit()) 
                        return false;
                    if (!filter_dog.getMode().shouldFollowOwner())
                        return false;
                    return filter_dog.distanceToSqr(filter_dog.getOwner()) > 9;
                })
                .collect(Collectors.toList());
            if (max_heel_count > 0) {
                if (heel_list.size() > max_heel_count) {
                    Collections.sort(heel_list, new EntityUtil.Sorter(player));
                    heel_list = heel_list.subList(0, max_heel_count);
                }
            }
            if (heel_list.isEmpty()) return;

            DogUtil.dynamicSearchAndTeleportToOwnwerInBatch(
                world, heel_list, player, 3);

            player.sendSystemMessage(Component.translatable("dogcommand.heel"));
            return;
        case STAY:
            if (world.isClientSide) return;
            for (Dog dog : dogsList) {
                dog.setOrderedToSit(true);
                dog.getNavigation().stop();
                dog.setTarget(null);
                if (dog.getMode() == EnumMode.WANDERING) {
                    dog.setMode(EnumMode.DOCILE);
                }
                successful = true;
            }

            if (successful) {
                player.sendSystemMessage(Component.translatable("dogcommand.stay"));
            }
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        case OKAY:
            if (world.isClientSide) return;
            for (var dog : dogsList) {
                successful = true;
                dog.getNavigation().stop();
                dog.setTarget(null);
                dog.setOrderedToSit(
                    dog.getMaxHealth() / 2 >= dog.getHealth()
                );
            }
            if (successful) {
                player.sendSystemMessage(Component.translatable("dogcommand.ok"));
            }
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        case SHELPERD:
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        case TACTICAL:
            if (world.isClientSide) return;
            var doggyBeam = new DoggyBeamEntity(world, player);
            doggyBeam.shootFromRotation(player, 
                player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
            world.addFreshEntity(doggyBeam);
            return;
        case ROAR:
            RoaringGaleTalent.roar(dogsList, world, player);
            return;
        case HEEL_BY_NAME:
            if (world.isClientSide) 
                HeelByNameScreen.open();
            return;
        case TO_BED: 
        {
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            if (dogsList.isEmpty()) return;
            if (player.level().isClientSide) return;
            boolean noDogs = true;
            for (var dog : dogsList) {
                noDogs = false;
                if (!dog.readyForNonTrivialAction()) continue;
                var bedPos = dog.getBedPos(player.level().dimension()).orElse(null);
                if (bedPos == null) continue;
                if (dog.blockPosition().equals(bedPos) && dog.isInSittingPose()) continue;
                if (dog.distanceToSqr(Vec3.atBottomCenterOf(bedPos)) < 400) {
                    dog.triggerActionDelayed(2, new DogMoveToBedAction(dog, bedPos, false));
                }
            }
            return;
        }
        case GO_BEHIND:
        {
            if (player.level().isClientSide) return;
            boolean noDogs = true;
            for (var dog : dogsList) {
                if (!dog.getMode().shouldFollowOwner()) continue;
                var owner = dog.getOwner();
                if (owner == null) continue;
                if (dog.distanceToSqr(owner) > 400) continue;
                dog.setTarget(null);
                dog.clearTriggerableAction();
                dog.triggerAction(new DogGoBehindOwnerAction(dog, owner));
                noDogs = false;
            }
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        }
        case HEEL_BY_GROUP:
            if (world.isClientSide) 
                HeelByGroupScreen.open();
            return;
        case MOB_RETRIEVER:
            if (world.isClientSide)
                return;
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            var retrieverOptional = MobRetrieverTalent.chooseNearestDog(player, world);
            if (retrieverOptional.isEmpty())
                return;
            var retriever = retrieverOptional.get();
            var talentOptional = retriever.getTalent(DoggyTalents.MOB_RETRIEVER.get());
            if (talentOptional.isEmpty())
                return;
            var talentInst = (MobRetrieverTalent) talentOptional.get();
            
            final int reach_range = MobRetrieverTalent.getSelectTargetRange();
            var eye_pos = player.getEyePosition();
            var view_vec = player.getViewVector(1);
            var max_reach_vec = view_vec.scale(reach_range);
            var max_pos = eye_pos.add(max_reach_vec);
            var search_area = player.getBoundingBox().expandTowards(max_reach_vec).inflate(1.0D, 1.0D, 1.0D);
            var hitResult = ProjectileUtil.getEntityHitResult(
                player, eye_pos, max_pos, search_area, e -> {
                    if (!(e instanceof LivingEntity living))
                        return false; 
                    return talentInst.isValidTarget(retriever, living);
                }, reach_range*reach_range);
            if (hitResult == null)
                return;
            var entity = hitResult.getEntity();
            if (entity == null)
                return;
            talentInst.setTarget(retriever, (LivingEntity) entity);
            return;
        case HEEL_BY_LOOK:
            heelByLook(world, player);
            player.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 20);
            return;
        }
    }

    private void heelByLook(Level level, Player player) {
        if (level.isClientSide)
            return;
        final int reach_range = 30;
        var eye_pos = player.getEyePosition();
        var view_vec = player.getViewVector(1);
        var max_reach_vec = view_vec.scale(reach_range);
        var max_pos = eye_pos.add(max_reach_vec);
        var search_area = player.getBoundingBox().expandTowards(max_reach_vec).inflate(1.0D, 1.0D, 1.0D);
        var hitResult = ProjectileUtil.getEntityHitResult(
            player, eye_pos, max_pos, search_area, e -> {
                return (e instanceof Dog);
            }, reach_range*reach_range);
        if (hitResult == null)
            return;
        var entity = hitResult.getEntity();
        if (entity == null)
            return;
        if (!(entity instanceof Dog dog))
            return;
        DogUtil.dynamicSearchAndTeleportToOwnwer(dog, player, 2);
        dog.setOrderedToSit(false);
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
