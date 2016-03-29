package com.evilcodes.landlord.handlers;

import com.evilcodes.landlord.Landlord;

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

    public static void init() {
        final String databaseType = FileHandler.getConfig().getString("database.type");
        if (databaseType.equalsIgnoreCase("flatfile"))
            mysql = false;
        else if (databaseType.equalsIgnoreCase("mysql"))
            mysql = true;
        else {
            Landlord.getInstance().getLogger().warning("Could not read config property database.type. Setting to flatfile now.");
            mysql= false;
        }

        if (mysql) {
            setupMysql();
            connectMysql();
        }

    }

    public static void terminate() {
        if (mysql)
            disconnectMysql();
    }

    private static void setupMysql() {
        mysqlHost = FileHandler.getConfig().getString("database.host");
        mysqlDatabaseName = FileHandler.getConfig().getString("database.name");
        mysqlUsername = FileHandler.getConfig().getString("database.username");
        mysqlPassword = FileHandler.getConfig().getString("database.password");
        mysqlTablePrefix = FileHandler.getConfig().getString("database.prefix");
    }

    private static void connectMysql() {

    }

    private static void disconnectMysql() {
        
    }



}
