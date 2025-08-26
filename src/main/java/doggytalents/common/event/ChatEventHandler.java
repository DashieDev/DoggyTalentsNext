package doggytalents.common.event;

import doggytalents.DogSounds;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.llm.LLMService;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber
public class ChatEventHandler {

    private static final int CONVERSATION_RADIUS = 15; // blocks

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getRawText().trim();

        AABB searchBox = player.getBoundingBox().inflate(CONVERSATION_RADIUS);
        List<Dog> nearbyDogs = player.level().getEntitiesOfClass(Dog.class, searchBox, dog -> dog.isOwnedBy(player));

        if (nearbyDogs.isEmpty()) {
            return;
        }

        for (Dog dog : nearbyDogs) {
            String dogName = dog.getName().getString();
            if (message.toLowerCase().startsWith(dogName.toLowerCase())) {
                
                String prompt = message.substring(dogName.length()).trim();
                // Remove leading comma or colon if present
                if (prompt.startsWith(",") || prompt.startsWith(":")) {
                    prompt = prompt.substring(1).trim();
                }

                if (prompt.isEmpty()) {
                    continue;
                }

                event.setCanceled(false);

                LLMService.getResponse(dog, prompt, (response) -> {
                    // Ensure the reply is sent on the main server thread
                    player.getServer().execute(() -> {
                        // Play a random sound
                        List<Supplier<SoundEvent>> sounds = List.of(
                            DogSounds.CLASSIC_BARK1,
                            DogSounds.CLASSIC_BARK2,
                            DogSounds.CLASSIC_BARK3,
                            DogSounds.CUTE_BARK1,
                            DogSounds.CUTE_BARK2,
                            DogSounds.CUTE_BARK3,
                            DogSounds.GRUMPY_BARK1,
                            DogSounds.GRUMPY_BARK2,
                            DogSounds.GRUMPY_BARK3,
                            DogSounds.PUGLIN_BARK2,
                            DogSounds.PUGLIN_BARK3,
                            DogSounds.SAD_BARK1
                        );
                        var soundToPlay = sounds.get(dog.getRandom().nextInt(sounds.size())).get();
                        dog.level().playSound(null, dog.blockPosition(), soundToPlay, dog.getSoundSource(), 1.0F, 1.0F);

                        Component dogNameComponent = Component.literal("[" + dogName + "]").withStyle(ChatFormatting.GOLD);
                        Component responseComponent = Component.literal(" " + response);
                        player.sendSystemMessage(dogNameComponent.copy().append(responseComponent));
                    });
                });
                
                // We found the dog being talked to, so we can stop.
                return; 
            }
        }
    }
}