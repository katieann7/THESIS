package com.two.factor.authentication.data.connectionhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ConnectionHelper class connects to an Oracle database.
 */
public class ConnectionHelper {
    /** The connection URL. */
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:rogate";
    /** The Oracle driver. */
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    /** The username used to connect to the database. */
    public static final String username = "system";
    /** The password used to connect to the database. */
    public static final String password = "Changeme0";


    /**
     * This method gets the connection from an Oracle database instance.
     * */
    public static Connection getConnection() throws RuntimeException {
        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            return DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Error has occurred. Cannot create a database instance." + ex.getMessage());
        } catch (SQLException ex) {
            throw new RuntimeException("Error has occurred. Cannot connect to the database." + ex.getMessage());
        }
    }
}
