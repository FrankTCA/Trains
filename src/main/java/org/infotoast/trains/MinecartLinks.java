package org.infotoast.trains;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.UUID;

public class MinecartLinks {
    private ArrayList<MinecartLink> linkDB = new ArrayList<>();

    public void newLink(Entity minecart1, Entity minecart2) {
        linkDB.add(new MinecartLink(minecart1, minecart2));
    }

    public void newLink(UUID minecart1, UUID minecart2) {
        linkDB.add(new MinecartLink(minecart1, minecart2));
    }

    private void newLinkFromString(String str) {
        linkDB.add(new MinecartLink(str));
    }

    protected static MinecartLinks getFromUncompressedStringDB(String str) {
        MinecartLinks mclinks = new MinecartLinks();
        String[] splits = str.split("/");
        for (String s : splits) {
            mclinks.newLinkFromString(s);
        }
        return mclinks;
    }

    public boolean minecartIsLinked(Entity m1) {
        for (MinecartLink link : linkDB) {
            if (link.minecartIsInLink(m1)) {
                return true;
            }
        }
        return false;
    }

    public UUID getLinkingPartnerUUID(Entity m1) {
        for (MinecartLink link : linkDB) {
            if (link.minecartIsInLink(m1)) {
                return link.getLinkedMinecartUUID(m1);
            }
        }
        return null;
    }

    public Entity getLinkingPartner(Entity m1) {
        for (MinecartLink link : linkDB) {
            if (link.minecartIsInLink(m1)) {
                return link.getLinkedMinecart(m1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String fullString = "";
        for (MinecartLink link : linkDB) {
            fullString += link + "/";
        }
        fullString = fullString.substring(0, fullString.length()-1);
        return fullString;
    }
}
