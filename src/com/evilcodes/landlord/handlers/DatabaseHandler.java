package com.evilcodes.landlord.handlers;

import com.evilcodes.landlord.Landlord;
import com.evilcodes.landlord.utils.DatabaseInterface;
import com.evilcodes.landlord.utils.MySQLCore;

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
            mysql= false;
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

    /********************** MYSQL **********************/

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
        //TODO
    }

    private static void disconnectMysql() {
        if (databaseInterface.checkConnection()) {
            //TODO Save everything
            databaseInterface.close();
        }
    }

    /********************** FLATFILE **********************/

    private static void setupFlatFile() {

    }



}
