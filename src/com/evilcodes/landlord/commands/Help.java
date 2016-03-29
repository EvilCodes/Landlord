package com.evilcodes.landlord.commands;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.LandlordCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

import static org.bukkit.util.NumberConversions.ceil;

/**
 * Created by jcdesimp on 2/19/15.
 * File edited by EvilCodes on 3/29/16.
 * Command to display help information to the player
 */
public class Help implements LandlordCommand {

    private Landlord plugin;
    private ArrayList<LandlordCommand> registeredCommands;


    public Help(Landlord plugin, LandlordCommandExecutor commandHandler) {
        this.plugin = plugin;
        this.registeredCommands = new ArrayList<LandlordCommand>();
    }


    public void addCommand(LandlordCommand lc) {
        registeredCommands.add(lc);
    }


    //mess - Pretty much this entire thing, generic help refactor as well from individual command help text
    @Override
    public boolean execute(CommandSender sender, String[] args, String label) {
        //check if page number is valid
        int pageNumber = 1;
        if (args.length > 1 && Arrays.asList(getTriggers()).contains(args[0])) {
            try {
                pageNumber = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                // Is not a number!
                sender.sendMessage(ChatColor.RED + "That is not a valid page number.");   //mess
                return true;
            }
        }

        // generate the help list

        String helpHeader = "--|| Landlord v#{version} Created by #{author} ||--";      //mess ready
        String aliases = "(Aliases: /landlord, land, /ll)";                             //mess ready

        // construct the header form the base strings
        String header = ChatColor.DARK_GREEN + helpHeader                                   // start out with the initial header
                .replace("#{version}", plugin.getDescription().getVersion())                // fill in the version
                .replace("#{author}", ChatColor.BLUE + "Jcdesimp" + ChatColor.DARK_GREEN)   // fill in the author name
                + '\n' + aliases;                                                           // add the aliases line

        ArrayList<String> helpList = new ArrayList<String>();

        // Get each help string
        for (LandlordCommand lc : registeredCommands) {
            String currCmd = lc.getHelpText(sender);
            if (currCmd != null) {  // make sure the help string isn't null (can happen if conditions aren't right)
                helpList.add(currCmd.replace("#{label}", label));
            }
        }

        /*helpList.add(ChatColor.DARK_AQUA+"/"+label + " help [page #]" + ChatColor.RESET + " - Show this help message.\n");
        String claim = ChatColor.DARK_AQUA+"/"+label + " claim (or "+"/"+label +" buy)" + ChatColor.RESET + " - Claim this chunk.\n";
        if(plugin.hasVault()){
            if(plugin.getvHandler().hasEconomy() && plugin.getConfig().getDouble("economy.buyPrice", 100.0)>0){
                claim += ChatColor.YELLOW+""+ChatColor.ITALIC+" Costs "+plugin.getvHandler().formatCash(plugin.getConfig().getDouble("economy.buyPrice", 100.0))+" to claim.\n";
            }
        }
        helpList.add(claim);
        String unclaim = ChatColor.DARK_AQUA+"/"+label + " unclaim [x,z] [world] (or "+"/"+label +" sell)" + ChatColor.RESET + " - Unclaim this chunk.\n";
        if (plugin.hasVault() && plugin.getvHandler().hasEconomy() && plugin.getConfig().getDouble("economy.sellPrice", 50.0) > 0) {
            if(plugin.getConfig().getBoolean("options.regenOnUnclaim",false)) {
                unclaim+=ChatColor.RED+""+ChatColor.ITALIC +" Regenerates Chunk!";
            }
            unclaim += ChatColor.YELLOW + "" + ChatColor.ITALIC + " Get " + plugin.getvHandler().formatCash(plugin.getConfig().getDouble("economy.sellPrice", 50.0)) + " per unclaim.\n";
        } else if(plugin.getConfig().getBoolean("options.regenOnUnclaim",false)) {
            unclaim+=ChatColor.RED+""+ChatColor.ITALIC +" Regenerates Chunk!\n";
        }
        helpList.add(unclaim);

        helpList.add(ChatColor.DARK_AQUA+"/"+label + " addfriend <player>" + ChatColor.RESET + " - Add friend to this land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " unfriend <player>" + ChatColor.RESET + " - Remove friend from this land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " friendall <player>" + ChatColor.RESET + " - Add friend to all your land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " unfriendall <player>" + ChatColor.RESET + " - Remove friend from all your land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " friends" + ChatColor.RESET + " - List friends of this land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " manage" + ChatColor.RESET + " - Manage permissions for this land.\n");
        helpList.add(ChatColor.DARK_AQUA+"/"+label + " list" + ChatColor.RESET + " - List all your owned land.\n");
        if(sender.hasPermission("landlord.player.map") && plugin.getConfig().getBoolean("options.enableMap",true)){
            helpList.add(ChatColor.DARK_AQUA+"/"+label + " map" + ChatColor.RESET + " - Toggle the land map.\n");
        }
        if(sender.hasPermission("landlord.player.info") && plugin.getConfig().getBoolean("options.enableMap",true)){
            helpList.add(ChatColor.DARK_AQUA+"/"+label + " info" + ChatColor.RESET + " - View info about this chunk.\n");
        }
        if(sender.hasPermission("landlord.admin.list")){
            helpList.add(ChatColor.DARK_AQUA+"/"+label + " listplayer <player>" + ChatColor.RESET + " - List land owned by another player.\n");
        }
        if(sender.hasPermission("landlord.admin.clearworld")){
            String clearHelp = ChatColor.DARK_AQUA+"/"+label + " clearworld <world> [player]" + ChatColor.RESET + " - Delete all land owned by a player in a world." +
                    " Delete all land of a world from console.\n";
            if(plugin.getConfig().getBoolean("options.regenOnUnclaim",false)){
                clearHelp += ChatColor.YELLOW+""+ChatColor.ITALIC+" Does not regenerate chunks.\n";
            }
            helpList.add(clearHelp);

        }
        if(sender.hasPermission("landlord.admin.reload")){
            helpList.add(ChatColor.DARK_AQUA+"/"+label + " reload" + ChatColor.RESET + " - Reloads the Landlord config file.\n");
        }*/
        //OwnedLand curr = myLand.get(0);

        //Amount to be displayed per page
        final int numPerPage = 5;

        int numPages = ceil((double) helpList.size() / (double) numPerPage);
        if (pageNumber > numPages) {
            sender.sendMessage(ChatColor.RED + "That is not a valid page number.");
            return true;
        }
        String pMsg = header;
        if (pageNumber == numPages) {
            for (int i = (numPerPage * pageNumber - numPerPage); i < helpList.size(); i++) {
                pMsg += '\n' + helpList.get(i);
            }
            pMsg += ChatColor.DARK_GREEN + "\n------------------------------";
        } else {
            for (int i = (numPerPage * pageNumber - numPerPage); i < (numPerPage * pageNumber); i++) {
                pMsg += '\n' + helpList.get(i);
            }
            pMsg += ChatColor.DARK_GREEN + "\n--- do" + ChatColor.YELLOW + " /" + label + " help " + (pageNumber + 1) + ChatColor.DARK_GREEN + " for next page ---";
        }

        sender.sendMessage(pMsg);
        return true;
    }

    @Override
    public String getHelpText(CommandSender sender) {
        //mess
        String usage = "/#{label} #{cmd} [page #]"; // get the base usage string
        String desc = "Show this help message.";   // get the description

        // return the constructed and colorized help string
        return Utils.helpString(usage, desc, getTriggers()[0].toLowerCase());
    }

    @Override
    public String[] getTriggers() {
        return new String[]{"help", "?"};
    }
}
