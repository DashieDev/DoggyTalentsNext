package doggytalents;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.phys.Vec3;

public class DoggyEntitySubPredicates {
    
    public static final DeferredRegister<MapCodec<? extends EntitySubPredicate>> ENTITY_SUB_PREDICATES = DeferredRegister.create(() -> BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, Constants.MOD_ID);

    public static final Supplier<MapCodec<? extends EntitySubPredicate>> WOLF_RAW_VARIANT = ENTITY_SUB_PREDICATES
        .register(
            "wolf_raw_variant",
            () -> RawWolfVariantIdSubPredicate.codec
        );

    public static class RawWolfVariantIdSubPredicate implements EntitySubPredicate {

        public static final MapCodec<RawWolfVariantIdSubPredicate> codec
            = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                    ResourceKey.codec(Registries.WOLF_VARIANT).fieldOf("variant")
                        .forGetter(to_encode -> to_encode.variant))
                    .apply(builder, RawWolfVariantIdSubPredicate::new)
            );
        
        public static RawWolfVariantIdSubPredicate of(ResourceKey<WolfVariant> variant) {
            return new RawWolfVariantIdSubPredicate(variant);
        }

        private final ResourceKey<WolfVariant> variant;
        private RawWolfVariantIdSubPredicate(ResourceKey<WolfVariant> variant) {
            this.variant = variant;
        }

        @Override
        public MapCodec<? extends EntitySubPredicate> codec() {
            return codec;
        }

        @Override
        public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 pos) {
            if (!(entity instanceof Wolf wolf))
                return false;
            var variant_holder = wolf.getVariant();
            if (variant_holder == null)
                return false;
            var variant_optional = variant_holder.unwrapKey();
            if (!variant_optional.isPresent())
                return false;
            var variant = variant_optional.get();
            return variant.equals(this.variant);
        }
        
    }

}
