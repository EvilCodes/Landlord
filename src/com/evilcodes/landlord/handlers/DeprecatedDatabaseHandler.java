package com.evilcodes.landlord.handlers;

/**
 * Created by EvilCodes on 3/29/16.
 */
public class DeprecatedDatabaseHandler {

    //TODO Get this working to support conversation from old db file to new db (file or mysql)

     /*
     * ***************************
     *       Database Stuff
     * ***************************
     */

    /*

    private void setupDatabase() {
        Configuration config = getConfig();

        database = new MyDatabase(this) {
            protected java.util.List<Class<?>> getDatabaseClasses() {
                List<Class<?>> list = new ArrayList<Class<?>>();
                list.add(OwnedLand.class);
                list.add(Friend.class);
                list.add(DBVersion.class);
                list.add(LandFlagPerm.class);

                return list;
            }

            ;
        };

        database.initializeDatabase(
                config.getString("database.driver", "org.sqlite.JDBC"),
                config.getString("database.url", "jdbc:sqlite:{DIR}{NAME}.db"),
                config.getString("database.username", "bukkit"),
                config.getString("database.password", "walrus"),
                config.getString("database.isolation", "SERIALIZABLE"),
                config.getBoolean("database.logging", false),
                config.getBoolean("database.rebuild", false)
        );

        config.set("database.rebuild", false);

    }
    */

    /*@Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(OwnedLand.class);
        list.add(Friend.class);
        //list.add(OwnedLand_Friend.class);
        return list;
    }*/

    /*

    @Override
    public EbeanServer getDatabase() {
        return database.getDatabase();
    }

    public void verifyDatabaseVersion() {
        int CURRENT_VERSION = 1;
        if (this.getDatabase().find(DBVersion.class).where().eq("identifier", "version").findUnique() == null) {
            //Convert Database
            this.getLogger().info("Starting OwnedLand conversion...");
            List<OwnedLand> allLand = plugin.getDatabase().find(OwnedLand.class).findList();
            for (OwnedLand l : allLand) {
                if (l.getOwnerName().length() < 32) {
                    //plugin.getLogger().info("Converting "+ l.getId() + "...");

                    */

                        /*
                         * *************************************
                         * mark for possible change    !!!!!!!!!
                         * *************************************
                         */
    /*
                    if (getOfflinePlayer(l.getOwnerName()).hasPlayedBefore()) {
                        //plugin.getLogger().info("Converting "+ l.getId() + "... Owner: "+l.getOwnerName());
                        l.setOwnerName(getOfflinePlayer(l.getOwnerName()).getUniqueId().toString());
                        plugin.getDatabase().save(l);

                    } else {
                        //plugin.getLogger().info("Deleting "+ l.getId() + "! Owner: "+l.getOwnerName());
                        plugin.getDatabase().delete(l);
                    }


                }
            }
            this.getLogger().info("Land Conversion completed!");

            this.getLogger().info("Starting Friend conversion...");
            List<Friend> allFriends = plugin.getDatabase().find(Friend.class).findList();
            for (Friend f : allFriends) {
                if (f.getPlayerName().length() < 32) {
                    //plugin.getLogger().info("Converting "+ l.getId() + "...");

                    */
                        /*
                         * *************************************
                         * mark for possible change    !!!!!!!!!
                         * *************************************
                         */

    /*
                    if (getOfflinePlayer(f.getPlayerName()).hasPlayedBefore()) {
                        //plugin.getLogger().info("Converting "+ f.getId() + "... Name: "+f.getPlayerName());
                        f.setPlayerName(getOfflinePlayer(f.getPlayerName()).getUniqueId().toString());
                        plugin.getDatabase().save(f);

                    } else {
                        //plugin.getLogger().info("Deleting "+ f.getId() + "! Name: "+f.getPlayerName());
                        plugin.getDatabase().delete(f);
                    }


                }
            }
            this.getLogger().info("Friend Conversion completed!");
            this.getLogger().info("Starting Permission conversion...");
            allLand = plugin.getDatabase().find(OwnedLand.class).findList();
            for (OwnedLand l : allLand) {
                if (l.getPermissions() != null) {


                    String[] currPerms = l.getPermissions().split("\\|");
                    String newPermString = "";
                    for (int i = 0; i < currPerms.length; i++) {
                        currPerms[i] = currPerms[i].substring(0, 3);
                        newPermString += Integer.toString(Integer.parseInt(currPerms[i], 2));
                        if (i < currPerms.length - 1) {
                            newPermString += "|";
                        }

                    }
                    l.setPermissions(newPermString);
                    plugin.getDatabase().save(l);
                }
            }
            //Entries for legacy flags
            this.getDatabase().save(LandFlagPerm.flagPermFromData("Build", 1));
            this.getDatabase().save(LandFlagPerm.flagPermFromData("HarmAnimals", 2));
            this.getDatabase().save(LandFlagPerm.flagPermFromData("UseContainers", 3));

            this.getLogger().info("Permission Conversion completed!");
            DBVersion vUpdate = new DBVersion();
            vUpdate.setIdentifier("version");
            vUpdate.setIntData(1);
            this.getDatabase().save(vUpdate);
        }
        int currVersion = this.getDatabase().find(DBVersion.class).where().eq("identifier", "version").findUnique().getIntData();
        if (currVersion < CURRENT_VERSION) {
            this.getLogger().info("Database outdated!");
        }

    }
    */
}
