package doggytalents.common.event;

import org.antlr.v4.codegen.model.dbg;
import org.antlr.v4.parse.v3TreeGrammarException;

import doggytalents.DoggyAccessories;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.config.ConfigHandler.ServerConfig;
import doggytalents.common.entity.Dog;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IncapacitatedEventHandler {

    @SubscribeEvent
    public void onWolfOrDogDeath(LivingDeathEvent ev) {
        if (!ServerConfig.getConfig(ConfigHandler.SERVER.IMMORTAL_DOGS)) return;
        var e = ev.getEntity();
        //Can't save dog if dog is out of world
        if (ev.getSource().isBypassInvul()) return;
        if (e instanceof Dog d) { //TODO check based on var
            ev.setCanceled(true);
            d.setHealth(1);
            d.setMode(EnumMode.INCAPACITATED);
            //in Incapacitated Mode, the dog hunger is rendered by the negative of 
            //the maxIncapaciatedHunger's complement of the actuall hunger value.
            //At first the hunger will be zero, when the dog enters Incapacitated.
            //To exit incapacitated, the dog hunger have to reach maxIncapacitatedHunger.
            d.setDogHunger(0); //max : 3 days
            d.setIncapacitatedMutiplier(1);
            var owner = d.getOwner();
            if (owner != null) {
                var deathMsg = (String) ev.getSource().getLocalizedDeathMessage(d).getString();
                var msg = Component.literal(deathMsg);
                var genderStr = I18n.get(d.getGender().getUnlocalisedSubject());
                var msg005 = ". "
                    + genderStr.substring(0, 1).toUpperCase()
                    + genderStr.substring(1)
                    + " ";
                var msg01 = Component.translatable(
                    "dog.mode.incapacitated.msg.partition1",
                    Component.literal(msg005),
                    Component.translatable(EnumMode.INCAPACITATED.getUnlocalisedName())
                    .withStyle(
                        Style.EMPTY
                        .withBold(true)
                        .withColor(0xd60404)
                    )
                );
            
                msg.append(msg01);
                owner.sendSystemMessage(msg);
            }
            AccessoryInstance hurtLayer;
            if (ev.getSource().isFire()) {
                hurtLayer = DoggyAccessories.INCAPACITATED_BURN.get().getDefault();
            } else if (ev.getSource() == DamageSource.MAGIC) {
                hurtLayer = DoggyAccessories.INCAPACITATED_POISON.get().getDefault();
            } else {
                hurtLayer = DoggyAccessories.INCAPACITATED_BLOOD.get().getDefault();
            }
            
            if (hurtLayer != null) d.addAccessory(hurtLayer);
            if (d.isPassenger()) d.stopRiding();
            if (d.isVehicle()) {
                for (var x : d.getPassengers()) {
                    x.stopRiding();
                }
            }
        }
    }
}
