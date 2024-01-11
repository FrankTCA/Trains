package org.infotoast.trains;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Common {
    private static final ArrayList<EntityType> minecarts = new ArrayList<EntityType>(Arrays.asList(EntityType.MINECART, EntityType.MINECART_CHEST, EntityType.MINECART_COMMAND, EntityType.MINECART_HOPPER, EntityType.MINECART_TNT, EntityType.MINECART_MOB_SPAWNER, EntityType.MINECART_FURNACE));
    private static final ArrayList<Material> rails = new ArrayList<>(Arrays.asList(Material.RAIL, Material.ACTIVATOR_RAIL, Material.POWERED_RAIL, Material.DETECTOR_RAIL));
    public static boolean isMinecartType(EntityType ent) {
        return minecarts.contains(ent);
    }

    public static Entity getEntityByUUID(UUID id) {
        return Bukkit.getEntity(id);
    }

    public static PlayerClickMinecart getPCMFromList(UUID id, ArrayList<PlayerClickMinecart> arrayList) {
        for (PlayerClickMinecart pcm : arrayList) {
            if (pcm.getPlayerUUID().equals(id)) {
                return pcm;
            }
        }
        return null;
    }

    public static boolean isRailType(Material mat) {
        return rails.contains(mat);
    }

    public static float[] yawToFacing(float yaw) {
        float[] result = radiusSwitch(yaw);
        if (result == null) {
            Trains.logger.warning("Weird yaw!");
            Trains.logger.warning("Yaw: " + yaw);
            return radiusSwitch(numClosestTo360(yaw));
        }
        return result;
    }

    private static float[] radiusSwitch(float yaw) {
        switch (Math.round(yaw)) {
            case 270:
                return new float[]{0f, 1f};
            case 90:
                return new float[]{1f, -1f};
            case 180:
                return new float[]{-1f, 1f};
            case 0:
                return new float[]{1f, 0f};
            default:
                return null;
        }
    }

    private static int numClosestTo360(float num) {
        float dividedByNinety = num / 90;
        switch (Math.round(dividedByNinety)) {
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                return 0;
        }
    }
}
