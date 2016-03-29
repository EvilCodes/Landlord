package com.evilcodes.landlord.handlers;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.utils.DatabaseInterface;
import com.evilcodes.landlord.utils.MySQLCore;
import sun.rmi.log.LogHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by EvilCodes on 3/29/16.
 */
public class DatabaseHandler {

    private static boolean mysql = false;
    private static String mysqlHost = "localhost";
    private static String mysqlDatabaseName = "Landlord";
    private static String mysqlUsername = "root";
    private static String mysqlPassword = "pass";
    private static String mysqlTablePrefix = "ll_";
    private static DatabaseInterface databaseInterface;

    public static void init() {
        final String databaseType = FileHandler.getConfig().getString("database.type");
        if (databaseType.equalsIgnoreCase("flatfile"))
            mysql = false;
        else if (databaseType.equalsIgnoreCase("mysql"))
            mysql = true;
        else {
            Landlord.getInstance().getLogger().warning("Could not read config property database.type. Setting to flatfile now.");
            mysql = false;
        }

        if (mysql) {
            setupMysql();
            connectMysql();
        } else {
            setupFlatFile();
        }
    }

    public static void terminate() {
        if (mysql)
            disconnectMysql();
    }

    public static String getMysqlTablePrefix() {
        return mysqlTablePrefix;
    }

    /**********************
     * MYSQL
     **********************/

    private static void setupMysql() {
        mysqlHost = FileHandler.getConfig().getString("database.host");
        mysqlDatabaseName = FileHandler.getConfig().getString("database.name");
        mysqlUsername = FileHandler.getConfig().getString("database.username");
        mysqlPassword = FileHandler.getConfig().getString("database.password");
        mysqlTablePrefix = FileHandler.getConfig().getString("database.prefix");
    }

    private static void connectMysql() {
        databaseInterface = new MySQLCore(mysqlHost, mysqlDatabaseName, mysqlUsername, mysqlPassword);
        if (!databaseInterface.checkConnection()) {
            Landlord.getInstance().getLogger().warning("Could not connect to database: " + mysqlDatabaseName + " on " + mysqlHost);
            Landlord.getInstance().getLogger().warning("Falling back to flatfile now.");
            mysql = false;
            setupFlatFile();
        } else {
            checkMysqlTables();
        }
    }

    private static void checkMysqlTables() {
        final String version = "CREATE TABLE IF NOT EXISTS `" + mysqlTablePrefix + "version` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `identifier` varchar(255) NOT NULL," +
                "  `string_data` varchar(255) NOT NULL," +
                "  `int_data` int(11) NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
        final String land = "CREATE TABLE IF NOT EXISTS `" + mysqlTablePrefix + "land` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `owner_uuid` varchar(64) NOT NULL," +
                "  `x` int(11) NOT NULL," +
                "  `z` int(11) NOT NULL," +
                "  `world_name` varchar(255) NOT NULL," +
                "  `permissions` varchar(255) NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
        final String friend = "CREATE TABLE IF NOT EXISTS `" + mysqlTablePrefix + "friend` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `uuid` varchar(64) NOT NULL," +
                "  `owned_land_id` int(11) NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  KEY `ix_" + mysqlTablePrefix + "friend_" + mysqlTablePrefix + "land_1` (`owned_land_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";

        final String flagperm = "CREATE TABLE IF NOT EXISTS `" + mysqlTablePrefix + "flagperm` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `identifier` varchar(255) NOT NULL," +
                "  `perm_slot` int(11) NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";

        final String flagperminsert = "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (1,'Build',1);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (2,'HarmAnimals',2);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (3,'UseContainers',3);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (4,'TntDamage',4);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (5,'UseRedstone',5);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (6,'OpenDoor',6);" +
                "INSERT INTO `" + mysqlTablePrefix + "flagperm` VALUES (7,'PVP',7);" +
                "CREATE INDEX ix_" + mysqlTablePrefix + "friend_" + mysqlTablePrefix + "land_1 on " + mysqlTablePrefix + "friend (owned_land_id);";

        databaseInterface.execute(version);
        databaseInterface.execute(land);
        databaseInterface.execute(friend);
        databaseInterface.execute(flagperm);
        databaseInterface.execute(flagperminsert);
    }


    public static String getMysqlString(String query, String key) {
        String result = "Not found";
        if (MySQLCore.mysqlExists(query)) {
            final ResultSet res = databaseInterface.select(query);
            if (res != null) {
                try {
                    while (res.next()) {
                        try {
                            result = res.getString(key);
                        } catch (Exception ex) {
                            Landlord.getInstance().getLogger().warning("Could not read " + key + " from MySQL Database with query " + query);
                            ex.printStackTrace();
                        }
                    }
                } catch (SQLException ex) {
                    Landlord.getInstance().getLogger().warning(String.format("An Error occurred: %s", new Object[]{Integer.valueOf(ex.getErrorCode())}));
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    public static int getMysqlInt(String query, String key) {
        int result = 0;
        if (MySQLCore.mysqlExists(query)) {
            final ResultSet res = databaseInterface.select(query);
            if (res != null) {
                try {
                    while (res.next()) {
                        try {
                            result = res.getInt(key);
                        } catch (Exception ex) {
                            Landlord.getInstance().getLogger().warning("Could not read " + key + " from MySQL Database with query " + query);
                            ex.printStackTrace();
                        }
                    }
                } catch (SQLException ex) {
                    Landlord.getInstance().getLogger().warning(String.format("An Error occurred: %s", new Object[]{Integer.valueOf(ex.getErrorCode())}));
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    private static void disconnectMysql() {
        if (databaseInterface.checkConnection()) {
            //TODO Save everything
            databaseInterface.close();
        }
    }

    /**********************
     * FLATFILE
     **********************/

    private static void setupFlatFile() {

    }


}
