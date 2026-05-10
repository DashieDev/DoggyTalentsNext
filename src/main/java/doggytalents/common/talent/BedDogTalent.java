package doggytalents.common.talent;

import doggytalents.DoggyItems;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnFailMessage;
import doggytalents.common.entity.DogSleepOnManager.StartSleepOnDogResult;
import doggytalents.common.util.DogUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BedDogTalent extends TalentInstance {

    //Calc based on level.getDayTime instead of dog.tickCount
    private long cooldownDealine = 0;
    
    public BedDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    public void onSuccessfulSleep(AbstractDog dog) {
        var level = dog.level();
        this.cooldownDealine = level.getDefaultClockTime() + getCooldownTicks();
        float set_hunger = dog.getDogHunger();
        final float base_threshold = getBaseMinHungerForSleep();
        if (set_hunger > base_threshold) {
            set_hunger -= getHungerCostPerSleep();
            if (set_hunger < base_threshold)
                set_hunger = base_threshold; 
        }

        dog.setDogHunger(set_hunger);
    }

    public boolean isOnCooldown(AbstractDog dog) {
        var level = dog.level();
        return level.getDefaultClockTime() < this.cooldownDealine;
    }

    public int getCooldownDaysLeft(AbstractDog dog) {
        var level = dog.level();
        long time_till = this.cooldownDealine - level.getDefaultClockTime();
        if (time_till <= 0)
            return 0;
        return Mth.ceil(time_till / 24000.0f);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.cooldownDealine = compound.getLongOr("cooldown_end", 0L);
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.putLong("cooldown_end", this.cooldownDealine);
    }

    @Override
    public boolean allowDetrain(AbstractDog dog) {
        return !this.isOnCooldown(dog);
    }

    public static void useBedDog(Level level, Player player) {
        if (level.isClientSide())
            return;
        final int reach_range = 30;
        var dog_optional = DogUtil.getLookingAtDog(player, reach_range, 
            filter_dog -> filter_dog.isDoingFine());
        if (!dog_optional.isPresent())
            return;
        var dog = dog_optional.get();

        if (dog.getDogLevel(DoggyTalents.BED_DOG) <= 0)
            return;
        
        var result = DogSleepOnManager.getServer(level.getServer()).setOrRequestSleepOn(dog, player);
        proccessResult(result, dog, player);
        if (result.isFailMsg(DogSleepOnFailMessage.NO_POS)) {
            player.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(DoggyItems.WHISTLE.get()), 10);
        }
    }

    private static void proccessResult(StartSleepOnDogResult result, Dog dog, Player player) {
        if (result.ok() || result.other())
            return;
        if (result.isFailMsg(DogSleepOnFailMessage.COOLDOWN)) {
            sendCooldownMsg(dog, player);
            return;
        }
        player.sendSystemMessage(result.failMsg().getMsg(dog));
    }

    private static void sendCooldownMsg(Dog dog, Player player) {
        var inst_optional = dog.getTalent(DoggyTalents.BED_DOG.get(), BedDogTalent.class);
        if (!inst_optional.isPresent())
            return;
        var inst = inst_optional.get();
        player.sendSystemMessage(Component.translatable("talent.doggytalents.bed_dog.fail.cooldown",
            dog.getName().getString(), Integer.toString(inst.getCooldownDaysLeft(dog))));
    }
    
    public static StartSleepOnDogResult isSleepCondition(Dog dog, BedDogTalent inst) {
        if (dog.getDogHunger() < inst.getMinHungerForSleep())
            return DogSleepOnFailMessage.DOG_LOW_HUNGER.asResult();
        if (inst.isOnCooldown(dog))
            return DogSleepOnFailMessage.COOLDOWN.asResult();
        return StartSleepOnDogResult.OK;
    }

    public int getHungerCostPerSleep() {
        var level = this.level();
        if (level <= 3)
            return 40;
        if (level == 4)
            return 30;
        return 0;
    }
    
    public int getMinHungerForSleep() {
        return getHungerCostPerSleep() + getBaseMinHungerForSleep();
    }

    public int getBaseMinHungerForSleep() {
        return 20;
    }

    public int getCooldownTicks() {
        var level = this.level();
        //24000
        if (level <= 3)
            return 3 * 24000;
        if (level == 4)
            return 2 * 24000;
        return 0;
    }
}
