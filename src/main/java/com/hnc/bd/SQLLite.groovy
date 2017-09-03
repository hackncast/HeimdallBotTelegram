package com.hnc.bd

import java.sql.Connection
import java.sql.DriverManager

/**
 * Created by samuel on 18/07/17.
 */
public class SQLLite {

    private Connection connection = null;

    public SQLLite() {
        Class.forName("org.sqlite.JDBC");
        String userHome = System.getProperty("user.home");
        File dir = new File(userHome + "/.bd");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + userHome + "/.bd/heimdall.db");
    }

    Connection getConnection() {
        return connection
    }

    void close() {
        connection.close();
    }
}
