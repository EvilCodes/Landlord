package com.evilcodes.landlord.landManagement;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.persistantData.LandFlagPerm;

import java.util.HashMap;

/**
 * File created by jcdesimp on 4/11/14.
 * File edited by EvilCodes on 3/29/16.
 */
public class FlagManager {

    private HashMap<String, Landflag> registeredFlags;
    private Landlord plugin;

    public FlagManager(Landlord plugin) {
        this.registeredFlags = new HashMap<String, Landflag>();
        this.plugin = plugin;
    }

    public HashMap<String, Landflag> getRegisteredFlags() {
        return registeredFlags;
    }

    public boolean registerFlag(Landflag f) {
        if (registeredFlags.containsKey(f.getClass().getSimpleName())) {
            plugin.getLogger().warning("Could not register flag \"" + f.getClass().getSimpleName() + "\" because a flag is already registered with that name!");
            f.setUniqueName(f.getClass().getSimpleName());

            return false;
        }
        LandFlagPerm lfp = plugin.getDatabase().find(LandFlagPerm.class).where().eq("identifier", f.getClass().getSimpleName()).findUnique();
        if (lfp == null) {
            plugin.getLogger().info("Registering new land flag: " + f.getClass().getSimpleName());
            lfp = LandFlagPerm.flagPermFromData(f.getClass().getSimpleName(), plugin.getDatabase().find(LandFlagPerm.class).findRowCount() + 1);
            plugin.getDatabase().save(lfp);
        }
        f.setPermSlot(lfp.getPermSlot());
        try {
            plugin.getServer().getPluginManager().registerEvents(f, plugin);
            registeredFlags.put(f.getClass().getSimpleName(), f);
        } catch (Exception e) {
            plugin.getLogger().warning("Error occured while registering flag \"" + f.getClass().getSimpleName() + "\":");
            e.printStackTrace();
            return false;
        }

        plugin.getLogger().info("Registered flag: " + f.getClass().getSimpleName());
        return true;


    }

    public int numStoredPerms() {
        return plugin.getDatabase().find(LandFlagPerm.class).findList().size();
    }

}

