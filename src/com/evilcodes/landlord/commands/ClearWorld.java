package com.evilcodes.landlord.commands;

import com.evilcodes.landlord.Landlord;
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
 * Administration command to clear all land in a specified world.
 */
public class ClearWorld implements LandlordCommand {

    private Landlord plugin;

    public ClearWorld(Landlord plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        if(!sender.hasPermission("landlord.admin.clearworld")){
            sender.sendMessage(ChatColor.RED+"You do not have permission.");        //mess
            return true;
        }
        if(args.length > 1){
            List<OwnedLand> land;
            if(args.length > 2){
                /*
                 * *************************************
                 * mark for possible change    !!!!!!!!!
                 * *************************************
                 */
                OfflinePlayer possible = getOfflinePlayer(args[2]);
                if (!possible.hasPlayedBefore() && !possible.isOnline()) {
                    sender.sendMessage(ChatColor.RED+"That player is not recognized.");     //mess
                    return true;
                }
                land = plugin.getDatabase().find(OwnedLand.class).where().eq("ownerName",possible.getUniqueId().toString()).eq("worldName",args[1]).findList();
            } else {
                if(sender instanceof Player){
                    sender.sendMessage(ChatColor.RED+"You can only delete entire worlds from the console.");    //mess
                    return true;
                }
                land = plugin.getDatabase().find(OwnedLand.class).where().eq("worldName",args[1]).findList();
            }
            if(land.isEmpty()){
                sender.sendMessage(ChatColor.RED + "No land to remove.");       //mess
                return true;
            }

            plugin.getDatabase().delete(land);
            plugin.getMapManager().updateAll();
            sender.sendMessage(ChatColor.GREEN+"Land(s) deleted!");     //mess

        } else {
            sender.sendMessage(ChatColor.RED + "format: " + label + " clearworld <world> [<player>]");  //mess DUPE OF HELP, should probably make call to getHelpText
        }
        return true;
    }

    @Override
    public String getHelpText(CommandSender sender) {

        // only bother showing them this command if they have permission to do it.
        if(!sender.hasPermission("landlord.admin.clearworld")){
            return null;
        }


        //mess ready
        String usage = "/#{label} #{cmd} <world> [player]";             // get the base usage string
        String desc = "Delete all land owned by a player in a " +       // get the description
                "world. Delete all land of a world (console only).";
        String chunkWarning = "Does not regenerate chunks.";            // get the "chunks won't regen" warning


        String helpString = ""; // start building the help string


        // add the formatted string to it.
        helpString += Utils.helpString(usage, desc, getTriggers()[0].toLowerCase());

        // If chunk regen is on, warn them that bulk deletions will not regen
        if(plugin.getConfig().getBoolean("options.regenOnUnclaim",false)){  //conf
            helpString += ChatColor.YELLOW+" "+ChatColor.ITALIC+ chunkWarning;
        }

        // return the constructed and colorized help string
        return helpString;

    }

    @Override
    public String[] getTriggers() {
        return new String[]{"clearworld"};
    }
}
