package com.evilcodes.landlord;

import com.evilcodes.landlord.commands.LandlordCommandExecutor;
import com.evilcodes.landlord.handlers.DatabaseHandler;
import com.evilcodes.landlord.handlers.DependencyHandler;
import com.evilcodes.landlord.handlers.FileHandler;
import com.evilcodes.landlord.landFlags.*;
import com.evilcodes.landlord.landManagement.FlagManager;
import com.evilcodes.landlord.landManagement.ViewManager;
import com.evilcodes.landlord.landMap.MapManager;
import com.evilcodes.landlord.utils.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Main plugin class for Landlord
 */
public class Landlord extends JavaPlugin {

    //private static MyDatabase database;
    private static Landlord plugin;
    private MapManager mapManager = new MapManager();
    private FlagManager flagManager;
    private ViewManager manageViewManager;
    private LandAlerter pListen;

    public static Landlord getInstance() {
        return Landlord.plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        //listner = new LandListener();
        //getServer().getPluginManager().registerEvents(new LandListener(this), this);
        flagManager = new FlagManager(this);
        manageViewManager = new ViewManager();
        getServer().getPluginManager().registerEvents(mapManager, this);


        // Check for config / messages file
        FileHandler.init();


        // Registering Alert Listener
        pListen = new LandAlerter();
        if (getConfig().getBoolean("options.showLandAlerts", true)) {
            getServer().getPluginManager().registerEvents(pListen, this);
        }


        getLogger().info("Created by EvilCodes and Jcdesimp!");


        //Plugin Metrics
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
            getLogger().warning("Could not connect to mcstats to send the plugin metrics.");
        }

        // Command Executor
        getCommand("landlord").setExecutor(new LandlordCommandExecutor(this));

        // Check dependencies
        DependencyHandler.init();

        // Database creation, configuration, and maintenance.
        DatabaseHandler.init();


        //Register default flags
        if (FileHandler.getConfig().getBoolean("enabled-flags.build")) {
            flagManager.registerFlag(new Build());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.harmAnimals")) {
            flagManager.registerFlag(new HarmAnimals());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.useContainers")) {
            flagManager.registerFlag(new UseContainers());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.tntDamage")) {
            flagManager.registerFlag(new TntDamage());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.useRedstone")) {
            flagManager.registerFlag(new UseRedstone());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.openDoor")) {
            flagManager.registerFlag(new OpenDoor());
        }
        if (FileHandler.getConfig().getBoolean("enabled-flags.pvp")) {
            flagManager.registerFlag(new PVP());
        }
        //TODO
        //flagManager.registerFlag(new OpenDoorDUPE1());
        //flagManager.registerFlag(new OpenDoorDUPE2());
        //flagManager.registerFlag(new OpenDoorDUPE3());


    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " v. " + getDescription().getVersion() + " has been disabled!");
        DatabaseHandler.terminate();
        mapManager.removeAllMaps();
        manageViewManager.deactivateAll();
        pListen.clearPtrack();
    }

    public FlagManager getFlagManager() {
        return flagManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public ViewManager getManageViewManager() {
        return manageViewManager;
    }


}
