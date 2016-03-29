package com.evilcodes.landlord.handlers;

import com.evilcodes.landlord.Landlord;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by EvilCodes on 3/29/16.
 */
public class FileHandler {

    private static File configFile;
    private static File messagesFile;
    private static YamlConfiguration config;
    private static YamlConfiguration messages;

    /**
     * Set files and some variables
     */
    public static void init() {
        CheckForFiles();
    }

    /**
     * Set the configfile & messagesfile and load the files
     */
    private static void CheckForFiles() {
        FileHandler.configFile = new File(Landlord.getInstance().getDataFolder(), "config.yml");
        FileHandler.messagesFile = new File(Landlord.getInstance().getDataFolder(), "messages.yml");

        if (!FileHandler.configFile.exists()) {
            Landlord.getInstance().getLogger().info("No configfile found, creating...");
            setConfigs();
        }
        if (!FileHandler.messagesFile.exists()) {
            Landlord.getInstance().getLogger().warning("No messagesfile found, creating...");
            setConfigs();
        }
        LoadFiles();
    }

    /**
     * Save all files
     */
    public static void SaveFiles() {
        try {
            FileHandler.config.save(FileHandler.configFile);
            FileHandler.messages.save(FileHandler.messagesFile);
        } catch (Exception e) {
            Landlord.getInstance().getLogger().severe("Could not save configfiles!");
            e.printStackTrace();
        }
    }

    /**
     * Load all files and set the file variables
     */
    public static void LoadFiles() {
        try {
            if (FileHandler.config == null)
                FileHandler.configFile = new File(Landlord.getInstance().getDataFolder(), "config.yml");

            FileHandler.config = YamlConfiguration.loadConfiguration(FileHandler.configFile);

            if (FileHandler.messages == null)
                FileHandler.messagesFile = new File(Landlord.getInstance().getDataFolder(), "messages.yml");

            FileHandler.messages = YamlConfiguration.loadConfiguration(FileHandler.messagesFile);
        } catch (Exception e) {
            Landlord.getInstance().getLogger().warning("Could not load configfiles!");
            e.printStackTrace();
        }
    }

    /**
     * Set the file variables
     */
    public static void setConfigs() {
        FileHandler.config = YamlConfiguration.loadConfiguration(FileHandler.configFile);
        if (!FileHandler.configFile.exists())
            Landlord.getInstance().saveResource("config.yml", false);


        FileHandler.messages = YamlConfiguration.loadConfiguration(FileHandler.messagesFile);
        if (!FileHandler.messagesFile.exists())
            Landlord.getInstance().saveResource("messages.yml", false);

    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public static YamlConfiguration getMessages() {
        return messages;
    }
}
