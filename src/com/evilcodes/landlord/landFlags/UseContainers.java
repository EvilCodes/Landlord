package com.evilcodes.landlord.landFlags;

import com.evilcodes.landlord.landManagement.Landflag;
import com.evilcodes.landlord.persistantData.OwnedLand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * File created by jcdesimp on 4/16/14.
 * File edited by EvilCodes on 3/29/16.
 */
public class UseContainers extends Landflag {
 /*
  **********************
  * IMPORTANT!!!! Landlord will take care of registering
  * the listeners, all you need to do is register the
  * class with landlord's flagManager!!!
  **********************
  */

    /**
     * Constructor needs to be defined and properly call super()
     */
    public UseContainers() {
        super(
                "Use Containers",                                       //Display name (will be displayed to players)
                "Gives permission to use trap chests|" +
                        "chests, furnaces, anvils, hoppers,|" +           //Description (Lore of headerItem '|' will seperate lines of lore.)
                        "droppers, dispensers, beacons,|" +
                        "brewing stands, cauldrons,|" +
                        "and Jukeboxes.",
                new ItemStack(Material.CHEST),                      //Itemstack (represented in and manager)
                "Allowed Container Usage",                              //Text shown in manager for granted permission
                "can use containers.",                                  //Description in manager for granted permission (ex: Friendly players <desc>)
                "Denied Container Usage",                               //Text shown in manager for denied permission
                "cannot use containers."                                //Desciption in manager for denied permission (ex: Regular players <desc>)
        );
    }



    /*
     ******************************************************************************
     * ALL event handlers for this flag NEED to be defined inside this class!!!!!
     * REMEMBER! Do not register this class with bukkit, register with Landlord's
     * flag manager and landlord will register the event handlers.
     ******************************************************************************
     */


    /**
     * Event handler for block placements
     *
     * @param event that happened
     */


    /*
     *************************************
     * Of course u can register as many
     * event listeners as you need for
     * your flag to do it's job
     *************************************
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void useContainer(PlayerInteractEvent event) {

        String[] blockAccess = {"CHEST", "TRAPPED_CHEST", "BURNING_FURNACE", "FURNACE", "ANVIL", "DROPPER", "DISPENSER", "HOPPER", "BREWING_STAND", "SOIL", "BEACON", "JUKEBOX", "CAULDRON"};

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }
        //System.out.println(event.getAction().toString());
        //System.out.println(event.getClickedBlock().getType().toString());
        //System.out.println(event.getItem());
        if (!Arrays.asList(blockAccess).contains(event.getClickedBlock().getType().toString())) {
            return;
        }
        OwnedLand land = OwnedLand.getApplicableLand(event.getClickedBlock().getLocation());
        if (land == null) {
            return;
        }
        Player p = event.getPlayer();
        if (!land.hasPermTo(p, this)) {
            p.sendMessage(ChatColor.RED + "You are not allowed to use containers on this land.");
            event.setCancelled(true);
        }
    }


}
