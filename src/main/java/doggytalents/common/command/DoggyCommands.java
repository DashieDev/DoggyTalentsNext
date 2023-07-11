package doggytalents.common.command;

import com.google.common.base.Strings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.command.arguments.UUIDArgument;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.RadarItem;
import doggytalents.common.lib.Constants;
import doggytalents.common.storage.*;
import doggytalents.common.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.literal;

public class DoggyCommands {

    public static final DynamicCommandExceptionType NOTFOUND_EXCEPTION = new DynamicCommandExceptionType((arg) -> {
        return ComponentUtil.translatable("command.dogrespawn.notfound", arg);
    });

    public static final DynamicCommandExceptionType RESPAWN_EXCEPTION = new DynamicCommandExceptionType((arg) -> {
        return ComponentUtil.translatable("command.dogrespawn.exception", arg);
    });

    public static final DynamicCommandExceptionType AMBIGUOUS_NAME_EXCEPTION = new DynamicCommandExceptionType((name) -> {
        return ComponentUtil.translatable("command.dogrespawn.imprecise", name);
    });

    public static final DynamicCommandExceptionType BAD_UUID_STRING = new DynamicCommandExceptionType((name) -> {
        return ComponentUtil.translatable("command.dogrespawn.bad_uuid_str", name);
    });

    public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("dog")
                    .requires(s -> s.hasPermission(2))
                    .then(
                        Commands.literal("locate")
                        .then(
                            Commands.literal("byuuid")
                            .then(
                                Commands.argument("ownerName", StringArgumentType.string())
                                .suggests(DoggyCommands.getOwnerNameSuggestionsLocate())
                                .then(
                                    Commands.argument("dogUUID", StringArgumentType.string())
                                    .suggests(DoggyCommands.getDogIdSuggestionsLocate())
                                    .executes(c -> locate(c)))))
                        .then(
                            Commands.literal("byname")
                           .then(
                                Commands.argument("ownerName", StringArgumentType.string())
                                .suggests(DoggyCommands.getOwnerNameSuggestionsLocate())
                                .then(
                                    Commands.argument("dogName", StringArgumentType.string())
                                    .suggests(DoggyCommands.getDogNameSuggestionsLocate())
                                    .executes(c -> locate2(c))
                                    .then(
                                        Commands.argument("dogUUID", StringArgumentType.string())
                                        .suggests(DoggyCommands.getDogIdSuggestionsLocate())
                                        .executes(c -> locate(c)))))))
                    .then(
                        Commands.literal("revive")
                        .then(
                            Commands.literal("byuuid")
                            .then(
                                Commands.argument("ownerName", StringArgumentType.string())
                                .suggests(DoggyCommands.getOwnerNameSuggestionsRevive())
                                .then(
                                    Commands.argument("dogUUID", StringArgumentType.string())
                                        .suggests(DoggyCommands.getDogIdSuggestionsRevive())
                                        .executes(c -> respawn(c)))))
                        .then(
                            Commands.literal("byname")
                            .then(
                                Commands.argument("ownerName", StringArgumentType.string())
                                .suggests(DoggyCommands.getOwnerNameSuggestionsRevive())
                                .then(
                                    Commands.argument("dogName", StringArgumentType.string())
                                    .suggests(DoggyCommands.getDogNameSuggestionsRevive())
                                    .executes(c -> respawn2(c))
                                    .then(
                                        Commands.argument("dogUUID", StringArgumentType.string())
                                        .suggests(DoggyCommands.getDogIdSuggestionsRevive())
                                        .executes(c -> respawn(c))))))));                

    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerIdSuggestionsLocate() {
        return (context, builder) -> getOwnerIdSuggestions(DogLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerIdSuggestionsRevive() {
        return (context, builder) -> getOwnerIdSuggestions(DogRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getOwnerIdSuggestions(Collection<? extends IDogData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {

            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .map(IDogData::getOwnerId)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet()),
                   builder);

        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion(context);
        } else {
            return Suggestions.empty();
        }
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getDogIdSuggestionsLocate() {
        return (context, builder) -> getDogIdSuggestions(DogLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getDogIdSuggestionsRevive() {
        return (context, builder) -> getDogIdSuggestions(DogRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getDogIdSuggestions(Collection<? extends IDogData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            //dogName is an optional argument
            //if dogName is set then this will dump all of the uuid having that ambiguous name
            //from the ownerName
            //if dogName is null then this will dump all of the dog of ownerName instead.
            String dogName = null;
            String ownerName = context.getArgument("ownerName", String.class);

            try {
                dogName = context.getArgument("dogName", String.class);
            } catch (IllegalArgumentException exception) {

            }

            if (ownerName == null) {
                return Suggestions.empty();
            }

            Predicate<IDogData> filter;

            final String dogName2 = dogName;

            if (dogName2 == null) {
                filter = data -> ownerName.equals(data.getOwnerName());
            } else {
                filter = data -> (
                    dogName2.equals(data.getDogName())
                    && ownerName.equals(data.getOwnerName())
                );
                    
            }

            return SharedSuggestionProvider.suggest(possibilities.stream()
                     .filter(filter)
                     .map(IDogData::getDogId)
                     .map(Object::toString)
                     .collect(Collectors.toSet()),
                    builder);
        } else if (context.getSource() instanceof SharedSuggestionProvider) {
             return context.getSource().customSuggestion(context);
        } else {
             return Suggestions.empty();
        }
    }


    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerNameSuggestionsLocate() {
        return (context, builder) -> getOwnerNameSuggestions(DogLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getOwnerNameSuggestionsRevive() {
        return (context, builder) -> getOwnerNameSuggestions(DogRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    public static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getOwnerNameSuggestions(Collection<? extends IDogData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .map(IDogData::getOwnerName)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet()),
                   builder);

        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return context.getSource().customSuggestion(context);
        } else {
            return Suggestions.empty();
        }
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getDogNameSuggestionsLocate() {
        return (context, builder) -> getDogNameSuggestions(DogLocationStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    private static <S extends SharedSuggestionProvider> SuggestionProvider<S> getDogNameSuggestionsRevive() {
        return (context, builder) -> getDogNameSuggestions(DogRespawnStorage.get(((CommandSourceStack)context.getSource()).getLevel()).getAll(), context, builder);
    }

    public static <S extends SharedSuggestionProvider> CompletableFuture<Suggestions> getDogNameSuggestions(Collection<? extends IDogData> possibilities, final CommandContext<S> context, final SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            String ownerName = context.getArgument("ownerName", String.class);

            if (ownerName == null) {
                return Suggestions.empty();
            }

            return SharedSuggestionProvider.suggest(possibilities.stream()
                    .filter(data -> ownerName.equals(data.getOwnerName()))
                    .map(IDogData::getDogName)
                    .filter((str) -> !Strings.isNullOrEmpty(str))
                     .collect(Collectors.toList()),
                     builder);

        } else if (context.getSource() instanceof SharedSuggestionProvider) {
             return context.getSource().customSuggestion(context);
        } else {
             return Suggestions.empty();
        }
    }

    private static int respawn(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        String uuidStr = ctx.getArgument("dogUUID", String.class);

        UUID uuid = null;

        try {
            uuid = UUID.fromString(uuidStr);
        } catch (Exception e) {
            
        }

        if (uuid == null) {
            throw BAD_UUID_STRING.create("null");
        }

        DogRespawnStorage respawnStorage = DogRespawnStorage.get(world);
        DogRespawnData respawnData = respawnStorage.getData(uuid);

        if (respawnData == null) {
            throw NOTFOUND_EXCEPTION.create(uuid.toString());
        }

        return respawn(respawnStorage, respawnData, source);
    }

    private static int respawn2(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        String ownerName = ctx.getArgument("ownerName", String.class);

        String dogName = ctx.getArgument("dogName", String.class);
        DogRespawnStorage respawnStorage = DogRespawnStorage.get(world);
        List<DogRespawnData> respawnData = respawnStorage.getDogs(ownerName).filter((data) -> data.getDogName().equalsIgnoreCase(dogName)).collect(Collectors.toList());

        if (respawnData.isEmpty()) {
            throw NOTFOUND_EXCEPTION.create(dogName);
        }

        if (respawnData.size() > 1) {
            StringJoiner joiner = new StringJoiner(", ");
            for (DogRespawnData data : respawnData) {
                joiner.add(Objects.toString(data.getDogId()));
            }
            throw AMBIGUOUS_NAME_EXCEPTION.create(dogName); //TODO!
        }

        return respawn(respawnStorage, respawnData.get(0), source);
    }

    private static int respawn(DogRespawnStorage respawnStorage, DogRespawnData respawnData, final CommandSourceStack source) throws CommandSyntaxException {
        Dog dog = respawnData.respawn(source.getLevel(), source.getPlayerOrException(), source.getPlayerOrException().blockPosition().above());

        if (dog != null) {
            respawnStorage.remove(respawnData.getDogId());
            source.sendSuccess(ComponentUtil.translatable("commands.dogrespawn.uuid.success", respawnData.getDogName()), false);
            return 1;
        }

        source.sendSuccess(ComponentUtil.translatable("commands.dogrespawn.uuid.failure", respawnData.getDogName()), false);
        return 0;
    }

    private static int locate(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        String uuidStr = ctx.getArgument("dogUUID", String.class);

        UUID uuid = null;

        try {
            uuid = UUID.fromString(uuidStr);
        } catch (Exception e) {

        }

        if (uuid == null) {
            throw BAD_UUID_STRING.create("null");
        }

        DogLocationStorage locationStorage = DogLocationStorage.get(world);
        DogLocationData locationData = locationStorage.getData(uuid);

        if (locationData == null) {
            throw NOTFOUND_EXCEPTION.create(uuid.toString());
        }

        return locate(locationStorage, locationData, source);
    }

    private static int locate2(final CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();
        source.getPlayerOrException(); // Check source is a player
        ServerLevel world = source.getLevel();

        String ownerName = ctx.getArgument("ownerName", String.class);

        String dogName = ctx.getArgument("dogName", String.class);
        DogLocationStorage locationStorage = DogLocationStorage.get(world);
        List<DogLocationData> locationData = locationStorage.getAll().stream()
                .filter(data -> ownerName.equals(data.getOwnerName())).filter((data) -> data.getDogName().equals(dogName)).collect(Collectors.toList());

        if (locationData.isEmpty()) {
            throw NOTFOUND_EXCEPTION.create(dogName);
        }

        if (locationData.size() > 1) {
            StringJoiner joiner = new StringJoiner(", ");
            for (DogLocationData data : locationData) {
                joiner.add(Objects.toString(data.getDogId()));
            }
            throw AMBIGUOUS_NAME_EXCEPTION.create(dogName);
        }

        return locate(locationStorage, locationData.get(0), source);
    }

    private static int locate(DogLocationStorage respawnStorage, DogLocationData locationData, final CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayerOrException();

        if (locationData.getDimension().equals(player.level.dimension())) {
            String translateStr = RadarItem.getDirectionTranslationKey(locationData, player);
            int distance = Mth.ceil(locationData.getPos() != null ? locationData.getPos().distanceTo(player.position()) : -1);

            source.sendSuccess(ComponentUtil.translatable(translateStr, locationData.getName(player.level), distance), false);
        } else {
            source.sendSuccess(ComponentUtil.translatable("dogradar.notindim", locationData.getDimension()), false); // TODO change message
        }
        return 1;

    }
}
