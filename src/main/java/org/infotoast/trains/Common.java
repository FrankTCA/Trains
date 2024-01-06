package org.infotoast.trains;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Common {
    private static final ArrayList<EntityType> minecarts = new ArrayList<EntityType>(Arrays.asList(EntityType.MINECART, EntityType.MINECART_CHEST, EntityType.MINECART_COMMAND, EntityType.MINECART_HOPPER, EntityType.MINECART_TNT, EntityType.MINECART_MOB_SPAWNER, EntityType.MINECART_FURNACE));
    public static boolean isMinecartType(EntityType ent) {
        return minecarts.contains(ent);
    }

    public static Entity getEntityByUUID(UUID id) {
        return Bukkit.getEntity(id);
    }
}
