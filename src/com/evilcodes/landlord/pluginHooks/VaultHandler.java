package com.evilcodes.landlord.pluginHooks;

import com.evilcodes.landlord.Landlord;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * File created by jcdesimp on 3/15/14.
 * File edited by EvilCodes on 3/29/16.
 */
public class VaultHandler {

    private Economy economy = null;

    public VaultHandler() {
        setupEconomy();
    }

    private boolean setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = Landlord.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null)
            this.economy = economyProvider.getProvider();

        return (this.economy != null);
    }

    public boolean hasEconomy() {
        if (this.economy == null)
            return false;
        else
            return true;
    }

    private boolean canAfford(Player p, double amt) {
        if (this.economy.hasAccount(p) && (this.economy.getBalance(p) >= amt))
            return true;
        else
            return false;
    }

    public boolean chargeCash(Player p, double amt) {
        if (canAfford(p, amt)) {
            economy.withdrawPlayer(p, amt);
            return true;
        } else {
            return false;
        }
    }

    public boolean giveCash(Player p, double amt) {
        economy.depositPlayer(p, amt);
        return true;
    }

    public String formatCash(double amt) {
        return economy.format(amt);
    }


}
