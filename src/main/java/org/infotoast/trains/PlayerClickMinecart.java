package org.infotoast.trains;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerClickMinecart {
    private UUID playerID;
    private UUID entityID;

    public PlayerClickMinecart(Player player, Entity minecart) {
        playerID = player.getUniqueId();
        entityID = minecart.getUniqueId();
    }

    public boolean hasPlayer(Player pl) {
        return pl.getUniqueId().equals(playerID);
    }

    public UUID getPlayerUUID() {
        return playerID;
    }

    public UUID getEntityUUID() {
        return entityID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerClickMinecart) {
            PlayerClickMinecart pcm = (PlayerClickMinecart)obj;
            return pcm.getPlayerUUID().equals(playerID);
        } else if (obj instanceof UUID) {
            return obj.equals(playerID);
        }
        return false;
    }
}
