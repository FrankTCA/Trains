package org.infotoast.trains;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;

public class EventsListener implements org.bukkit.event.Listener {
    private static ArrayList<PlayerClickMinecart> playersActiveChain;
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerEntityInteract(PlayerInteractEntityEvent evt) {
        Entity minecartClicked = evt.getRightClicked();
        if (Common.isMinecartType(minecartClicked.getType())) {
            if (evt.getPlayer().getItemOnCursor().getType().equals(Material.CHAIN)) {
                if (playersActiveChain.contains(evt.getPlayer().getUniqueId())) {
                    Trains.links.newLink(playersActiveChain.get(playersActiveChain.indexOf(evt.getPlayer().getUniqueId())).getEntityUUID(), minecartClicked.getUniqueId());
                    evt.getPlayer().sendMessage("§6§b[Trains]§r §5Minecarts linked!");
                } else {
                    playersActiveChain.add(new PlayerClickMinecart(evt.getPlayer(), minecartClicked));
                    evt.getPlayer().sendMessage("§6§b[Trains]§r §5One minecart linked! Click the chain on the second minecart.");
                }
                evt.setCancelled(true);
            }
        } else {
            if (playersActiveChain.contains(evt.getPlayer().getUniqueId())) {
                playersActiveChain.remove(evt.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityMove(EntityMoveEvent evt) {
        if (Common.isMinecartType(evt.getEntityType())) {
            if (Trains.links.minecartIsLinked(evt.getEntity())) {
                Entity otherMinecart = Trains.links.getLinkingPartner(evt.getEntity());
                otherMinecart.teleport(evt.getFrom());
            }
        }
    }
}
