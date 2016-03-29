package com.evilcodes.landlord.commands;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.persistantData.Friend;
import com.evilcodes.landlord.persistantData.OwnedLand;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getOfflinePlayer;

/**
 * Created by jcdesimp on 2/18/15.
 * File edited by EvilCodes on 3/29/16.
 * Command for a player toi add a friend to all of their land at once.
 */
public class FriendAll implements LandlordCommand {

    private Landlord plugin;

    public FriendAll(Landlord plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        //is sender a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command can only be run by a player.");   //mess
        } else {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "usage: /land friendall <player>");  //mess
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("landlord.player.own")) {
                player.sendMessage(ChatColor.RED + "You do not have permission.");  //mess
                return true;
            }

            List<OwnedLand> pLand = plugin.getDatabase().find(OwnedLand.class).where().eq("ownerName", player.getUniqueId()).findList();
            OfflinePlayer possible = getOfflinePlayer(args[1]);
            if (!possible.hasPlayedBefore() && !possible.isOnline()) {
                player.sendMessage(ChatColor.RED + "That player is not recognized.");       //mess
                return true;
            }

            if (pLand.size() > 0) {
                for (OwnedLand l : pLand) {
                    l.addFriend(Friend.friendFromOfflinePlayer(getOfflinePlayer(args[1])));
                }

                plugin.getDatabase().save(pLand);


                player.sendMessage(ChatColor.GREEN + args[1] + " has been added as a friend to all of your land.");     //mess
                return true;
            } else {
                player.sendMessage(ChatColor.YELLOW + "You do not own any land!");        //mess
            }

        }
        return true;
    }

    @Override
    public String getHelpText(CommandSender sender) {

        //mess
        String usage = "/#{label} #{cmd} <player>"; // get the base usage string
        String desc = "Add friend to all your land.";   // get the description

        // return the constructed and colorized help string
        return Utils.helpString(usage, desc, getTriggers()[0].toLowerCase());

    }

    @Override
    public String[] getTriggers() {
        return new String[]{"friendall", "addfriendall"};
    }
}
