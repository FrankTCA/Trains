package org.infotoast.trains;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Trains extends JavaPlugin {

    public static Logger logger;
    public static MinecartLinks links;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        try {
            links = LinksFile.load();
        } catch (InvalidSaveFileException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(new EventsListener(), this);
        logger.info("Trains loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
