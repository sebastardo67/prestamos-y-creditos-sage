package main.java.org.sage.prestamos.creditos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {

    private ConnectionDb() {
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                CredentialsDb.URL_DB,
                CredentialsDb.USER_DB,
                CredentialsDb.PASS_DB
        );
    }
}