package com.evilcodes.landlord.handlers;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.pluginHooks.VaultHandler;
import com.evilcodes.landlord.pluginHooks.WorldGuardHandler;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.Vault;
import org.bukkit.plugin.Plugin;

/**
 * Created by EvilCodes on 3/29/16.
 */
public class DependencyHandler {

    private static WorldGuardHandler worldGuardHandler;
    private static VaultHandler vaultHandler;

    public static void init() {
        checkWorldEdit();
        checkWorldGuard();
        checkVault();
    }

    private static boolean checkWorldEdit() {
        // WorldEdit Check
        final Plugin worldEdit = Landlord.getInstance().getServer().getPluginManager().getPlugin("WorldEdit");
        // WorldEdit may not be loaded

        //Maybe we do not need WorldEdit / WorldGuard because it's features are not needed
        if (worldEdit == null || !(worldEdit instanceof WorldEditPlugin) || !FileHandler.getConfig().getBoolean("worldguard.blockRegionClaim", true)) {
            Landlord.getInstance().getLogger().warning("WorldEdit not found, WorldGuard features disabled.");
            return false;
        } else {
            Landlord.getInstance().getLogger().info("WorldEdit found. WorldGuard features can work now.");
            return true;
        }
    }

    private static boolean checkWorldGuard() {
        // WorldGuard Check
        final Plugin worldGuard = Landlord.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        // WorldGuard may not be loaded

        //Maybe we do not need WorldGuard because it's features are not needed
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin) || !FileHandler.getConfig().getBoolean("worldguard.blockRegionClaim", true)) {
            Landlord.getInstance().getLogger().warning("WorldGuard not found, features disabled.");
            return false;
        } else {
            Landlord.getInstance().getLogger().info("WorldGuard found. Features can work now.");
            worldGuardHandler = new WorldGuardHandler(getWorldGuardPlugin());
            return true;
        }
    }

    private static boolean checkVault() {
        // Vault Check
        final Plugin vault = Landlord.getInstance().getServer().getPluginManager().getPlugin("Vault");
        // Vault may not be loaded

        //Maybe we do not need WorldGuard because it's features are not needed
        if (vault == null || !(vault instanceof Vault)) {
            Landlord.getInstance().getLogger().warning("Vault not found, economy features disabled.");
            return false;
        } else {
            Landlord.getInstance().getLogger().info("Vault found. Economy features can work now.");
            vaultHandler = new VaultHandler(getVaultPlugin());
            if (!vaultHandler.hasEconomy()) {
                Landlord.getInstance().getLogger().warning("This Vault version does not have economy features.");
                return false;
            } else
                return true;
        }
    }

    private static WorldGuardPlugin getWorldGuardPlugin() {
        Plugin worldGuard = Landlord.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldGuardPlugin) worldGuard;
    }

    private static Vault getVaultPlugin() {
        Plugin vault = Landlord.getInstance().getServer().getPluginManager().getPlugin("Vault");

        // Vault may not be loaded
        if (vault == null || !(vault instanceof Vault)) {
            return null; // Maybe you want throw an exception instead
        }
        return (Vault) vault;
    }

    public static VaultHandler getVaultHandler() {
        return DependencyHandler.vaultHandler;
    }

    public static WorldGuardHandler getWorldGuardHandler() {
        return DependencyHandler.worldGuardHandler;
    }

}
