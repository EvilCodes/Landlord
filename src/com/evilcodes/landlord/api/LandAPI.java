package com.evilcodes.landlord.api;

import com.evilcodes.landlord.handlers.DatabaseHandler;

import java.util.UUID;

/**
 * Created by EvilCodes on 3/29/16.
 */
public class LandAPI {

    public static UUID getLandOwner(int x, int z, String world) {
        final String uuid = DatabaseHandler.getMysqlString("SELECT `owner_uuid` FROM " + DatabaseHandler.getMysqlTablePrefix() + "land WHERE `x` = " + x + " AND `z` = " + z + " AND `world_name` = '" + world + "';", "owner_uuid");
        return UUID.fromString(uuid);
    }
}
