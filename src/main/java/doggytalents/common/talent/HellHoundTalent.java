package doggytalents.common.talent;

import java.util.UUID;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;

public class HellHoundTalent extends TalentInstance {

    private static final UUID FIRE_SWIM_BOOST_ID = UUID.fromString("69192651-74c8-4366-8375-a488628f4556");

    private float oldLavaCost;
    private float oldFireCost;
    private float oldDangerFireCost;

    private Dog dog;

    private final int SEARCH_RANGE = 3;
    private int tickUntilSearch = 25;

    public HellHoundTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        this.oldLavaCost = dog.getPathfindingMalus(BlockPathTypes.LAVA);
        this.oldFireCost = dog.getPathfindingMalus(BlockPathTypes.DAMAGE_FIRE);
        this.oldDangerFireCost = dog.getPathfindingMalus(BlockPathTypes.DANGER_FIRE);

        dog.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
        dog.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        dog.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
        if (dog instanceof Dog) {
            this.dog = (Dog) dog;
        }
    }

    //TODO Although talents can't be practically removed from dogs,
    //there is nothing stoping users from directly modifying the dogs
    //CompoundTag data... So this is kinda needed...
    @Override
    public void remove(AbstractDog dog) {
        dog.setPathfindingMalus(BlockPathTypes.LAVA, this.oldLavaCost);
        dog.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, this.oldFireCost);
        dog.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, this.oldDangerFireCost);
    }

    @Override
    public InteractionResultHolder<Integer> setFire(AbstractDog dogIn, int second) {
        return InteractionResultHolder.success(this.level() > 0 ? second / this.level() : second);
    }

    @Override
    public InteractionResult isImmuneToFire(AbstractDog dogIn) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult isInvulnerableTo(AbstractDog dogIn, DamageSource source) {
        if (source.isFire()) {
            return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult attackEntityAsMob(AbstractDog dogIn, Entity entity) {
        if (this.level() > 0) {
            entity.setSecondsOnFire(this.level());
            return InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void tick(AbstractDog dog) {
        if (this.level() < 5) return;
        if (dog.isInLava()) {
            this.applySwimAttributes(dog);
        } else {
            this.removeSwimAttributes(dog);
        }
        if (this.dog != null) {
            if (this.dog.isShakingLava()) {
                if (--tickUntilSearch <= 0) {
                    tickUntilSearch = 25;
                    var targets = 
                        this.dog.level.getEntitiesOfClass(
                            LivingEntity.class, 
                            this.dog.getBoundingBox().inflate(SEARCH_RANGE, 2, SEARCH_RANGE)
                        );
                    for (var x : targets) {
                        if (x instanceof Enemy) {
                            x.setSecondsOnFire(2);
                        }
                    }
                }
            }
        }
    }

    private void applySwimAttributes(AbstractDog dog){
        dog.setAttributeModifier(ForgeMod.SWIM_SPEED.get(), FIRE_SWIM_BOOST_ID, (dd, u) -> 
            new AttributeModifier(u, "Lava Swim Boost", 4, Operation.ADDITION)
        );
    }

    private void removeSwimAttributes(AbstractDog dog) {
        dog.removeAttributeModifier(ForgeMod.SWIM_SPEED.get(), FIRE_SWIM_BOOST_ID);
    }
}
