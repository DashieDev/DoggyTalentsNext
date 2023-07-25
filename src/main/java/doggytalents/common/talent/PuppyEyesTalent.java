package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.util.EntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class PuppyEyesTalent extends TalentInstance {

    private int cooldown;

    public PuppyEyesTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public TalentInstance copy() {
        PuppyEyesTalent inst = new PuppyEyesTalent(this.getTalent(), this.level);
        inst.cooldown = this.cooldown;
        return inst;
    }

    @Override
    public void init(AbstractDog dogIn) {
        this.cooldown = dogIn.tickCount;
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        int timeLeft = this.cooldown - dogIn.tickCount;
        compound.putInt("cooldown", timeLeft);
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.cooldown = dogIn.tickCount + compound.getInt("cooldown");
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(AbstractDog dogIn, CompoundTag compound) {
        if (compound.contains("charmercharge")) {
            this.cooldown = dogIn.tickCount + compound.getInt("charmercharge");
        }
    }

    @Override
    public void livingTick(AbstractDog dogIn) {
        if (dogIn.tickCount % 40 != 0) {
            return;
        }

        if (dogIn.level.isClientSide || !dogIn.isTame()) {
            return;
        }

        if (this.level() <= 0) {
            return;
        }
        int timeLeft = this.cooldown - dogIn.tickCount;

        if (timeLeft <= 0) {
            LivingEntity owner = dogIn.getOwner();

            // Dog doesn't have owner or is offline
            if (owner == null) {
                return;
            }

            Villager villager = this.getClosestVisibleVillager(dogIn, 5D);

            if (villager != null) {

                //Gone is the unecessary (and a bit Cringy?) villager dialog and gift mechanic,
                //players already abuse villages too much 🥴

                //Instead of dropping items, add good gossips based on the level.
                int add_val = this.level() * 20;
                villager.getGossips()
                    .add(owner.getUUID(), GossipType.MINOR_POSITIVE, add_val);

                this.cooldown = dogIn.tickCount + (this.level() >= 5 ? 24000 : 48000);
            }
        }
    }
    

    public Villager getClosestVisibleVillager(AbstractDog dogIn, double radiusIn) {
        List<Villager> list = dogIn.level.getEntitiesOfClass(
            Villager.class,
            dogIn.getBoundingBox().inflate(radiusIn, radiusIn, radiusIn),
            (village) -> village.hasLineOfSight(dogIn)
        );

        return EntityUtil.getClosestTo(dogIn, list);
    }
}
