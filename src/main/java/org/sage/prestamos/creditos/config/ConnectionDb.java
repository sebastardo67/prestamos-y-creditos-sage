/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.org.sage.prestamos.creditos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import main.java.org.sage.prestamos.creditos.config.CredentialsDb;
import java.sql.SQLException;
/**
 *
 * @author informatica
 */
public class ConnectionDb {
    private static Connection connection;
    //El constructor deve ser privado, esto se para
    //evitar que se creen instancias de esta clase 
    private ConnectionDb(){}
        //metodo
        public static Connection getconnectionDataBase() throws SQLException{
            if (connection == null || connection.isClosed()){
         connection = DriverManager.getConnection(CredentialsDb.URL_DB, CredentialsDb.USER_DB, CredentialsDb.PASS_DB);
        }
            return connection;
    }
        
}
