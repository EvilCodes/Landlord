package com.evilcodes.landlord.landManagement;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * File created by jcdesimp on 4/11/14.
 * File edited by EvilCodes on 3/29/16.
 */
public abstract class Landflag implements Listener {
    //Data Fields
    private int permSlot;
    private String uniqueName;

    //Display Fields
    private String displayName;
    private String descrition;
    private ItemStack headerItem;
    private String allowedTitle;
    private String allowedText;
    private String deniedTitle;
    private String deniedText;

    protected Landflag(String displayName,
                       String description,
                       ItemStack headerItem,
                       String allowedTitle,
                       String allowedText,
                       String deniedTitle,
                       String deniedText) {
        this.displayName = displayName;
        this.descrition = description;
        this.headerItem = headerItem;
        this.allowedTitle = allowedTitle;
        this.allowedText = allowedText;
        this.deniedTitle = deniedTitle;
        this.deniedText = deniedText;
    }

    public int getPermSlot() {
        return permSlot;
    }

    public void setPermSlot(int permSlot) {
        this.permSlot = permSlot;
    }

    public String getDeniedText() {
        return deniedText;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return descrition;
    }

    public ItemStack getHeaderItem() {
        return headerItem;
    }

    public String getAllowedTitle() {
        return allowedTitle;
    }

    public String getAllowedText() {
        return allowedText;
    }

    public String getDeniedTitle() {
        return deniedTitle;
    }
}
