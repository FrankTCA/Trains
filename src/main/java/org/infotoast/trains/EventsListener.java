package org.infotoast.trains;

import io.papermc.paper.entity.TeleportFlag;
import net.minecraft.world.entity.vehicle.Minecart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventsListener implements org.bukkit.event.Listener {
    private static ArrayList<PlayerClickMinecart> playersActiveChain = new ArrayList<>();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerEntityInteract(PlayerInteractAtEntityEvent evt) {
        Entity minecartClicked = evt.getRightClicked();
        if (Common.isMinecartType(minecartClicked.getType())) {
            if (evt.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CHAIN)) {
                PlayerClickMinecart activeChain = Common.getPCMFromList(evt.getPlayer().getUniqueId(), playersActiveChain);
                if (activeChain != null) {
                    Trains.links.newLink(activeChain.getEntityUUID(), minecartClicked.getUniqueId());
                    evt.getPlayer().sendMessage("§6§b[Trains]§r §5Minecarts linked!");
                    playersActiveChain.remove(activeChain);
                } else {
                    playersActiveChain.add(new PlayerClickMinecart(evt.getPlayer(), minecartClicked));
                    evt.getPlayer().sendMessage("§6§b[Trains]§r §5One minecart linked! Click the chain on the second minecart.");
                    Trains.logger.info(playersActiveChain.get(0).toString());
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
    public void onEntityMove(VehicleMoveEvent evt) throws NoSuchMethodException {
        if (Common.isMinecartType(evt.getVehicle().getType())) {
            if (Trains.links.minecartIsLinked(evt.getVehicle())) {
                ArrayList<Entity> otherMinecarts = Trains.links.getLinkingPartners(evt.getVehicle());
                for (Entity otherMinecart : otherMinecarts) {
                    if (otherMinecart == null) {
                        continue;
                    }
                    Location loc = evt.getVehicle().getLocation();
                    float yaw = evt.getVehicle().getYaw();
                    Location otherLoc = otherMinecart.getLocation();
                    evt.getVehicle().setRotation(otherMinecart.getYaw(), 0f);
                    float[] yawFacingFl = Common.yawToFacing(yaw);
                    otherLoc = loc.clone().add(yawFacingFl[0], 0, yawFacingFl[1]);
                    if (Common.isRailType(evt.getVehicle().getWorld().getType(loc))) {
                        //otherMinecart.setVelocity(otherLoc.toVector().subtract(otherMinecart.getLocation().toVector()).multiply(0.5));
                        if (Common.isRailType(otherLoc.getBlock().getType())) {
                            if (otherMinecart.getPassengers().isEmpty()) {
                                otherMinecart.teleport(otherLoc);
                            } else {
                                otherMinecart.teleport(otherLoc, TeleportFlag.EntityState.RETAIN_PASSENGERS);
                                /*try {
                                    Field minecartEntity = otherMinecart.getClass().getDeclaredField("");
                                    minecartEntity.setAccessible(true);
                                    net.minecraft.world.entity.Entity theEntity = (net.minecraft.world.entity.Entity) minecartEntity.get(minecartEntity);
                                    theEntity.moveTo(otherLoc.x(), otherLoc.y(), otherLoc.z());
                                } catch (NoSuchFieldException e) {
                                    throw new RuntimeException(e);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }*/

                            }
                        }
                        if (otherMinecart.getLocation().distance(evt.getVehicle().getLocation()) > 5) {
                            otherMinecart.teleport(evt.getVehicle());
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleCollide(VehicleEntityCollisionEvent evt) {
        if (Trains.links.minecartIsLinked(evt.getVehicle())) {
            evt.setCancelled(true);
        }
    }
}
