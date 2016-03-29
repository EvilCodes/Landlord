package com.evilcodes.landlord.utils;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by EvilCodes on 3/29/16.
 */
public abstract interface DatabaseInterface {

    public abstract Connection getConnection();

    public abstract Boolean checkConnection();

    public abstract void close();

    public abstract ResultSet select(String paramString);

    public abstract void insert(String paramString);

    public abstract void update(String paramString);

    public abstract void delete(String paramString);

    public abstract Boolean execute(String paramString);

    public abstract Boolean existsTable(String paramString);

    public abstract Boolean existsColumn(String paramString1, String paramString2);
}