package org.infotoast.trains;

import org.bukkit.entity.Entity;

import java.util.UUID;

public class MinecartLink {
    private UUID minecart1;
    private UUID minecart2;

    public MinecartLink(Entity m1, Entity m2) {
        minecart1 = m1.getUniqueId();
        minecart2 = m2.getUniqueId();
    }

    public MinecartLink(UUID m1, UUID m2) {
        minecart1 = m1;
        minecart2 = m2;
    }

    public MinecartLink(String str) {
        String[] arrStr = str.split(",");
        minecart1 = UUID.fromString(arrStr[0]);
        minecart2 = UUID.fromString(arrStr[1]);
    }

    public boolean minecartIsInLink(Entity m1) {
        return m1.getUniqueId().equals(minecart1) || m1.getUniqueId().equals(minecart2);
    }

    public UUID getLinkedMinecartUUID(Entity m1) {
        if (m1.getUniqueId().equals(minecart1)) {
            return minecart2;
        } else if (m1.getUniqueId().equals(minecart2)) {
            return minecart1;
        } else {
            throw new MinecartDoesNotExistException();
        }
    }

    public Entity getLinkedMinecart(Entity m1) {
        return Common.getEntityByUUID(getLinkedMinecartUUID(m1));
    }

    @Override
    public String toString() {
        return minecart1 + "," + minecart2;
    }
}
