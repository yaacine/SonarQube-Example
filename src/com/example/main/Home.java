package com.example.main;

import com.example.model.City;
import com.example.service.DatabaseConnection;
import com.example.service.DatabaseService;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;

public class Home {

    // Create a Logger
   public static final Logger logger
            = Logger.getLogger(
            Home.class.getName());


    static String getCryptedUserName(){
        //decrypt userName

        String usr ="root";
        return usr;
    }

    static String getCryptedPassword(){
        //decrypt userName

        String mdp ="root";
        return mdp;

    }


    public static final String USER = getCryptedUserName();
    public static final String PASSWORD = getCryptedPassword();
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/citiesdb";

    public static void main(String args[]) {
        DatabaseConnection db = new DatabaseConnection(USER, PASSWORD, JDBC_DRIVER, DB_URL);
        Connection conn = db.connect();
        try {
            if (args.length > 0) {
                int id = Integer.parseInt(args[0]);
                String name = args[1];
                int numTourist = Integer.parseInt(args[2]);
                String desc = args[3];
                City city1 = new City(id, name, numTourist, desc);
                DatabaseService.addCity(conn, city1);
            }
        }
        catch (NumberFormatException e) {
            // log messages using log(Level level, String msg
            logger.log(Level.WARNING, "Arguments" + args[0] + args[2] + " must be an integer.");

        }
    }
}
