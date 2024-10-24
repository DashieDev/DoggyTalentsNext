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
import doggytalents.common.command.arguments.UUIDArgument;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.CanineTrackerItem;
import doggytalents.common.lib.Constants;
import doggytalents.common.storage.*;
import doggytalents.common.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.literal;

public class DoggyCommands {

    
    //public static final DeferredRegister<ArgumentTypeInfo<?,?>> ARG_TYPE = DeferredRegister.create(ForgeRegistries.Keys.COMMAND_ARGUMENT_TYPES, Constants.MOD_ID);
    //public static final RegistryObject<SingletonArgumentInfo<UUIDArgument>> SUUID = ARG_TYPE.register("doggy_uuid", () -> SingletonArgumentInfo.contextFree(UUIDArgument::uuid));


    public static final DynamicCommandExceptionType NOTFOUND_EXCEPTION = new DynamicCommandExceptionType((arg) -> {
        return Component.translatable("command.dogrespawn.notfound", arg);
    });

    public static final DynamicCommandExceptionType RESPAWN_EXCEPTION = new DynamicCommandExceptionType((arg) -> {
        return Component.translatable("command.dogrespawn.exception", arg);
    });

    public static final DynamicCommandExceptionType AMBIGUOUS_NAME_EXCEPTION = new DynamicCommandExceptionType((name) -> {
        return Component.translatable("command.dogrespawn.imprecise", name);
    });

    public static final DynamicCommandExceptionType BAD_UUID_STRING = new DynamicCommandExceptionType((name) -> {
        return Component.translatable("command.dogrespawn.bad_uuid_str", name);
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

            var level = ((CommandSourceStack) context.getSource()).getLevel();
            var owner_id_optional = getOwnerIdFromName(level, ownerName);
            if (!owner_id_optional.isPresent())
                return Suggestions.empty();
            var owner_id = owner_id_optional.get();

            Predicate<IDogData> filter;

            final String dogName2 = dogName;

            if (dogName2 == null) {
                filter = data -> owner_id.equals(data.getOwnerId());
            } else {
                filter = data -> (
                    dogName2.equals(data.getDogName())
                    && owner_id.equals(data.getOwnerId())
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
            var level = ((CommandSourceStack) context.getSource()).getLevel();
            var owner_names = getOwnerNames(level, possibilities);
            return SharedSuggestionProvider.suggest(owner_names, builder);

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

            var level = ((CommandSourceStack) context.getSource()).getLevel();
            var owner_id_optional = getOwnerIdFromName(level, ownerName);
            if (!owner_id_optional.isPresent())
                return Suggestions.empty();
            var owner_id = owner_id_optional.get();

            var suggest_list = possibilities.stream()
                .filter(data -> owner_id.equals(data.getOwnerId()))
                .map(IDogData::getDogName)
                .filter((str) -> !Strings.isNullOrEmpty(str))
                .map(str -> wrapStringInQuoteIfContainsSpace(str))
                .collect(Collectors.toList());

            return SharedSuggestionProvider.suggest(suggest_list, builder);

        } else if (context.getSource() instanceof SharedSuggestionProvider) {
             return context.getSource().customSuggestion(context);
        } else {
             return Suggestions.empty();
        }
    }

    private static String wrapStringInQuoteIfContainsSpace(String str) {
        if (str == null || str.isEmpty())
            return str;
        var spaceIndx = str.indexOf(" ");
        if (spaceIndx < 0)
            return str;
        
        return "\"" + str + "\"";
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

        var owner_id_optional = getOwnerIdFromName(world, ownerName);
        if (!owner_id_optional.isPresent()) {
            throw NOTFOUND_EXCEPTION.create(dogName);
        }
        var owner_id = owner_id_optional.get();

        DogRespawnStorage respawnStorage = DogRespawnStorage.get(world);
        List<DogRespawnData> respawnData = respawnStorage.getDogs(owner_id).filter((data) -> data.getDogName().equals(dogName)).collect(Collectors.toList());

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
            source.sendSuccess(() -> Component.translatable("commands.dogrespawn.uuid.success", respawnData.getDogName()), false);
            return 1;
        }

        source.sendSuccess(() -> Component.translatable("commands.dogrespawn.uuid.failure", respawnData.getDogName()), false);
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

        var owner_id_optional = getOwnerIdFromName(world, ownerName);
        if (!owner_id_optional.isPresent()) {
            throw NOTFOUND_EXCEPTION.create(dogName);
        }
        var owner_id = owner_id_optional.get();

        DogLocationStorage locationStorage = DogLocationStorage.get(world);
        List<DogLocationData> locationData = locationStorage.getAll().stream()
                .filter(data -> owner_id.equals(data.getOwnerId())).filter((data) -> data.getDogName().equals(dogName)).collect(Collectors.toList());

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

        source.sendSuccess(() -> getLocationInfo(locationData), false);

        return 1;

    }

    public static Component getLocationInfo(DogLocationData loc) {
        var pos = loc.getPos();
        BlockPos bpos = null;
        if (pos != null) {
            bpos = BlockPos.containing(pos);
        }
        var posStr = bpos == null ? "[???]" : 
            "[ " + bpos.getX() + ", " + bpos.getY() + ", " + bpos.getZ() + " ]";
        var dim = loc.getDimension();
        var dim_loc = dim == null ? null : dim.location(); 
        var dimStr = dim == null ? "[???]" :
            "[ " + (dim_loc == null ? "" : dim_loc) + " ]";
        var dogName = loc.getDogName();
        if (dogName == null) dogName = "noname";
        return Component.translatable("command.doglocate.info", dogName, posStr, dimStr);
    }

    public static Optional<UUID> getOwnerIdFromName(ServerLevel level, String name) {
        if (name == null || name.isEmpty())
            return Optional.empty();

        var player_list = level.players();
        ServerPlayer found_player = null;
        for (var player : player_list) {
            var player_name = player.getName().getString();
            if (player_name.equals(name)) {
                found_player = player;
                break;
            }
        }
        
        if (found_player == null)
            return Optional.empty();
        
        return Optional.ofNullable(found_player.getUUID());
    }

    public static Set<String> getOwnerNames(ServerLevel level, Collection<? extends IDogData> dog_datas) {
        var player_id_list = dog_datas.stream()
            .filter(data -> data.getOwnerId() != null)
            .map(data -> data.getOwnerId())
            .collect(Collectors.toList());
        var player_id_set = new HashSet<>(player_id_list);
        var ret = new HashSet<String>();
        for (var owner_id : player_id_set) {
            var player = level.getPlayerByUUID(owner_id);
            if (player == null)
                continue;
            var name = player.getName().getString();
            if (name == null || name.isEmpty())
                continue;
            ret.add(name);
        }
        return ret;
    }
}
