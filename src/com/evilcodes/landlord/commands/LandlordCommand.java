package com.evilcodes.landlord.commands;

import org.bukkit.command.CommandSender;

/**
 * Created by jcdesimp on 2/17/15.
 * File edited by EvilCodes on 3/29/16.
 * Interface for a player executable Landlord command
 */
public interface LandlordCommand {
    /**
     * Execute the command
     * @param sender who sent the command
     * @param args  array of arguments given with the command
     * @param label the actual command/alias that was entered.
     * @return boolean on whether or not success
     */
    public boolean execute(CommandSender sender, String[] args, String label);

    /**
     * Get this command help test
     * @return String that describes the command
     * @param sender of the command.
     */
    public String getHelpText(CommandSender sender);

    /**
     * Get the list of strings (non-case sensitive) that can trigger
     * this command. (command names, aliases, etc.)
     * @return the list of valid command trigger words
     */
    public String[] getTriggers();


}
