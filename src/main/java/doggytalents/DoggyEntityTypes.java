package doggytalents;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogFoodProjectile;
import doggytalents.common.entity.DogGunpowderProjectile;
import doggytalents.common.entity.DoggyBeamEntity;
import doggytalents.common.entity.misc.DogPlushie;
import doggytalents.common.entity.misc.Piano;
import doggytalents.common.entity.misc.Piano.PianoColor;
import doggytalents.common.entity.misc.Piano.PianoType;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_TYPES, Constants.MOD_ID);

    public static final RegistryObject<EntityType<Dog>> DOG = register("dog", Dog::new, MobCategory.CREATURE, (b) -> b
            .sized(0.6F, 0.85F)
            .setUpdateInterval(3)
            .setTrackingRange(16)
            .setShouldReceiveVelocityUpdates(true));

    public static final RegistryObject<EntityType<DoggyBeamEntity>> DOG_BEAM = register("dog_beam", DoggyBeamEntity::new, MobCategory.MISC, (b) -> b
            .sized(0.25F, 0.25F)
            .setUpdateInterval(4)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(true)
            .setCustomClientFactory(DoggyBeamEntity::new)
            .noSummon());

    public static final RegistryObject<EntityType<Piano>> GRAND_PIANO_BLACK = register("grand_piano_black", Piano::new, MobCategory.MISC,
        b -> b
            .sized(3f, 3f)
            .setUpdateInterval(4)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(false));
    
    public static final RegistryObject<EntityType<Piano>> GRAND_PIANO_WHITE = register("grand_piano_white", (type, level) -> new Piano(type, level, PianoType.GRAND, PianoColor.WHITE), MobCategory.MISC,
        b -> b
            .sized(3f, 3f)
            .setUpdateInterval(4)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(false));

    public static final RegistryObject<EntityType<Piano>> UPRIGHT_PIANO_BLACK = register("upright_piano_black", (type, level) -> new Piano(type, level, PianoType.UPRIGHT, PianoColor.BLACK), MobCategory.MISC,
        b -> b
            .sized(3f, 3f)
            .setUpdateInterval(4)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(false));

    public static final RegistryObject<EntityType<Piano>> UPRIGHT_PIANO_BROWN = register("upright_piano_brown", (type, level) -> new Piano(type, level, PianoType.UPRIGHT, PianoColor.BROWN), MobCategory.MISC,
        b -> b
            .sized(3f, 3f)
            .setUpdateInterval(4)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(false));

    public static final RegistryObject<EntityType<DogPlushie>> DOG_PLUSHIE_TOY = register("dog_plushie_toy", (type, level) -> new DogPlushie(type, level), MobCategory.MISC,
        b -> b
            .sized(0.5f, 0.5f)
            .setUpdateInterval(3)
            .setTrackingRange(10)
            .setShouldReceiveVelocityUpdates(true));

    public static final RegistryObject<EntityType<DogFoodProjectile>> DOG_FOOD_PROJ = register("dog_food_projectile", DogFoodProjectile::new, MobCategory.MISC, (b) -> b
        .sized(0.25F, 0.25F)
        .setUpdateInterval(4)
        .setTrackingRange(10)
        .setShouldReceiveVelocityUpdates(true)
        .setCustomClientFactory(DogFoodProjectile::new)
        .noSummon());

    public static final RegistryObject<EntityType<DogGunpowderProjectile>> DOG_GUNPOWDER_PROJ = register("dog_gunpowder_projectile", DogGunpowderProjectile::new, MobCategory.MISC, (b) -> b
        .sized(0.25F, 0.25F)
        .setUpdateInterval(4)
        .setTrackingRange(10)
        .setShouldReceiveVelocityUpdates(true)
        .noSummon());

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<EntityType<E>> register(final String name, final EntityType.EntityFactory<E> sup, final MobCategory classification, final Function<EntityType.Builder<E>, EntityType.Builder<E>> builder) {
         return register(name, () -> builder.apply(EntityType.Builder.of(sup, classification)).build(Util.getResourcePath(name)));
    }

    private static <E extends Entity, T extends EntityType<E>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ENTITIES.register(name, sup);
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(DOG.get(),
                 Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 8.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.3D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                 .add(Attributes.ATTACK_DAMAGE, 2.0D)
                 .add(DoggyAttributes.JUMP_POWER.get(), 0.42D)
                 .add(DoggyAttributes.CRIT_CHANCE.get(), 0.01D)
                 .add(DoggyAttributes.CRIT_BONUS.get(), 1D)
                 .add(Attributes.FLYING_SPEED, 0.3D)
                 .build()
         );
     }
}
