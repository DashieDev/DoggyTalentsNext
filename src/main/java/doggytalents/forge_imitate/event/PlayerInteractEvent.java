package doggytalents.forge_imitate.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PlayerInteractEvent {
    
    public static class EntityInteract extends Event {

        private Player interactor;
        private Entity target;
        private ItemStack stack = ItemStack.EMPTY;
        private InteractionResult cancelResult = InteractionResult.PASS;

        public EntityInteract(Player interact, Entity targ, ItemStack stack) {
            this.interactor = interact;
            this.target = targ;
            this.stack = stack;
        }

        public Player getEntity() {
            return this.interactor;
        }

        public Entity getTarget() {
            return this.target;
        }

        public Level getLevel() {
            return this.target.level();
        }

        public ItemStack getItemStack() {
            return this.stack;
        }

        public void setCancellationResult(InteractionResult result) {
            this.cancelResult = result;
        }

        public InteractionResult getCancelInteractionResult() {
            return this.cancelResult;
        }

    }

}
