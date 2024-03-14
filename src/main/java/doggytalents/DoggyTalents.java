package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.lib.Constants;
import doggytalents.common.talent.*;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import doggytalents.common.talent.talentclass.LowCostTalent;
import doggytalents.common.talent.talentclass.SingleLevelTalent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DoggyTalents {

    public static final DeferredRegister<Talent> TALENTS = DeferredRegister.create(DoggyRegistries.Keys.TALENTS_REGISTRY, Constants.MOD_ID);

    public static final RegistryObject<Talent> BED_FINDER = registerInst("bed_finder", BedFinderTalent::new);
    public static final RegistryObject<Talent> BLACK_PELT = registerInst("black_pelt", BlackPeltTalent::new);
    public static final RegistryObject<Talent> CREEPER_SWEEPER = registerInst("creeper_sweeper", CreeperSweeperTalent::new);
    public static final RegistryObject<Talent> DOGGY_DASH = registerInst("doggy_dash", DoggyDashTalent::new);
    public static final RegistryObject<Talent> FISHER_DOG = registerInst("fisher_dog", FisherDogTalent::new);
    public static final RegistryObject<Talent> GUARD_DOG = registerInst("guard_dog", GuardDogTalent::new);
    public static final RegistryObject<Talent> HAPPY_EATER = registerInst("happy_eater", HappyEaterTalent::new);
    public static final RegistryObject<Talent> HELL_HOUND = registerInst("hell_hound", HellHoundTalent::new);
    public static final RegistryObject<Talent> HUNTER_DOG = registerInst("hunter_dog", null);
    public static final RegistryObject<Talent> PACK_PUPPY = registerInst("pack_puppy", PackPuppyTalent::new);
    public static final RegistryObject<Talent> PEST_FIGHTER = registerInst("pest_fighter", PestFighterTalent::new);
    public static final RegistryObject<Talent> PILLOW_PAW = register("pillow_paw", () -> new Talent(PillowPawTalent::new) {
        @Override
        public boolean isDogEligible(AbstractDog dog) {
            return PillowPawTalent.isDogEligible(dog);
        }
    });
    public static final RegistryObject<Talent> POISON_FANG = registerInst("poison_fang", PoisonFangTalent::new);
    public static final RegistryObject<Talent> PUPPY_EYES = registerInst("puppy_eyes", PuppyEyesTalent::new);
    public static final RegistryObject<Talent> QUICK_HEALER = registerInst("quick_healer", QuickHealerTalent::new);
    //public static final RegistryObject<Talent> RANGED_ATTACKER = registerInst("ranged_attacker", RangedAttacker::new);
    public static final RegistryObject<Talent> RESCUE_DOG = registerInst("rescue_dog", RescueDogTalent::new);
    public static final RegistryObject<Talent> ROARING_GALE = registerInst("roaring_gale", RoaringGaleTalent::new);
    public static final RegistryObject<Talent> SHEPHERD_DOG = registerInst("shepherd_dog", ShepherdDogTalent::new);
    public static final RegistryObject<Talent> SWIMMER_DOG = registerInst("swimmer_dog", SwimmerDogTalent::new);
    public static final RegistryObject<Talent> WOLF_MOUNT = registerInst("wolf_mount", WolfMountTalent::new);
    public static final RegistryObject<Talent> DOGGY_TORCH = registerInst("doggy_torch", DoggyTorchTalent::new);
    public static final RegistryObject<LowCostTalent> DOGGY_ARMOR = register("doggy_armor", () -> new LowCostTalent(DoggyArmorTalent::new));
    public static final RegistryObject<Talent> WATER_HOLDER = register("water_holder", () -> new Talent(WaterHolderTalent::new));
    public static final RegistryObject<Talent> DOGGY_TOOLS = register("doggy_tools", () -> new Talent(DoggyToolsTalent::new));
    public static final RegistryObject<Talent> SHOCK_ABSORBER = register("shock_absorber", () -> new Talent(ShockAbsorberTalent::new));
    public static final RegistryObject<Talent> MOB_RETRIEVER = register("mob_retriever", () -> new Talent(MobRetrieverTalent::new));
    public static final RegistryObject<Talent> FLYING_FURBALL = register("flying_furball", () -> 
        new Talent(FlyingFurballTalent::new) {
            @Override
            public int getLevelCost(int toGoToLevel) {
                if (toGoToLevel == 1)
                    return 5;
                return super.getLevelCost(toGoToLevel);
            };

            @Override
            public int getCummulativeCost(int level) {
                if (level <= 0)
                    return 0;
                return level * (level + 1) / 2 + 4;
            }

            @Override
            public boolean isDogEligible(AbstractDog dog) {
                return dog.getDogLevel(DoggyTalents.PILLOW_PAW) <= 0
                    && dog.getDogLevel().getLevel(DogLevel.Type.KAMI) > 0;
            } 

            @Override
            public Optional<String> getNonEligibleTranslationKey(AbstractDog dog) {
                if (dog.getDogLevel(DoggyTalents.PILLOW_PAW) > 0)
                    return Optional.empty();
                return Optional.of(this.getTranslationKey() + ".dog_not_kami");
            }
        });
    public static final RegistryObject<Talent> CHEMI_CANINE = register("chemi_canine", () -> 
        new Talent(ChemiCanineTalent::new) {
            @Override
            public int getLevelCost(int toGoToLevel) {
                if (toGoToLevel == 1)
                    return 5;
                return super.getLevelCost(toGoToLevel);
            };

            @Override
            public int getCummulativeCost(int level) {
                if (level <= 0)
                    return 0;
                return level * (level + 1) / 2 + 4;
            }
        });
    public static final RegistryObject<Talent> FIRE_DRILL = register("fire_drill", () ->
        new SingleLevelTalent(FireDrillTalent::new));
    public static final RegistryObject<Talent> SNIFFER_DOG = register("sniffer_dog", () -> new Talent(SnifferDogTalent::new));
    public static final RegistryObject<Talent> GATE_PASSER = register("gate_passer", () -> new SingleLevelTalent(GatePasserTalent::new));
    public static final RegistryObject<Talent> OOKAMIKAZE = register("ookamikaze", () -> new Talent(OokamiKazeTalent::new) {
        @Override
        public boolean isDogEligible(AbstractDog dog) {
            return dog.getDogLevel().getLevel(DogLevel.Type.KAMI) > 0;
        } 

        @Override
        public Optional<String> getNonEligibleTranslationKey(AbstractDog dog) {
            return Optional.of(this.getTranslationKey() + ".dog_not_kami");
        }
    });

    private static <T extends Talent> RegistryObject<Talent> registerInst(final String name, final BiFunction<Talent, Integer, TalentInstance> sup) {
        return register(name, () -> new Talent(sup));
    }

    private static <T extends Talent> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TALENTS.register(name, sup);
    }
}
