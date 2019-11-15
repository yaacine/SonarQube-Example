package com.example.service;

import com.example.main.Home;
import com.example.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;


public class DatabaseService {

    private DatabaseService(){
        throw new IllegalStateException("Utility class");
    }
    static String errorMessage ="DataBase Error !";
    static String idCity = "idCity";
    static String description = "description";
    static String touristNumber = "touristNumber";
    static String name = "name";

    public static final Logger logger
            = Logger.getLogger(
            Home.class.getName());
    public static int addCity(Connection conn, City city) {
        PreparedStatement pstmt = null;
        int i = -1;
        try {
            String sql = "INSERT INTO City " + "VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, city.getIdCity());
            pstmt.setString(2, city.getName());
            pstmt.setInt(3, city.getTouristNumber());
            pstmt.setString(4, city.getDescription());
            i= pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            logger.log(Level.WARNING,errorMessage);

        } finally {
            try {
                if (pstmt != null) pstmt.close();


            } catch (SQLException se2) {
                logger.log(Level.WARNING,errorMessage);
            }
        }
        return i;
    }


   public static City getCity(Connection conn,int idCity) {
        PreparedStatement pstmt = null;
        City city = new City();
       ResultSet rs =null;
       try {

            String sql = "SELECT * FROM City where idCity=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,idCity);
            rs= pstmt.executeQuery();
            while (rs.next()) {
                city.setIdCity(rs.getInt(idCity));
                city.setName(rs.getString(name));
                city.setTouristNumber(rs.getInt(touristNumber));
                city.setDescription(rs.getString(description));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
           logger.log(Level.WARNING,errorMessage);
        } finally {
           closeEverything(pstmt,rs);
        }
        return  city;
    }


    public static List<City> getCities(Connection conn) {
        Statement stmt = null;
        List<City> cities = new ArrayList<>();
        ResultSet rs =null;
        try {

            String sql = "SELECT * FROM City";
            stmt = conn.createStatement();
            rs= stmt.executeQuery(sql);
            while (rs.next()) {
                City city = new City();
                city.setIdCity(rs.getInt(idCity));
                city.setName(rs.getString(name));
                city.setTouristNumber(rs.getInt(touristNumber));
                city.setDescription(rs.getString(description));
                cities.add(city);
            }
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            logger.log(Level.WARNING,errorMessage);
        } finally {
            closeEverything(stmt,rs);
        }
        return  cities;
    }
// New method

    public static City getCityByName(Connection conn,String name1) {
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        City city = new City();

        try{
            String sql = "SELECT * FROM City where name=?";
            stmt = conn.prepareStatement(sql) ;

            stmt.setString(1,name1);
            rs = stmt.executeQuery() ;

            while (rs.next()) {
                city.setIdCity(rs.getInt(idCity));
                city.setName(rs.getString(name));
                city.setTouristNumber(rs.getInt(touristNumber));
                city.setDescription(rs.getString(description));
            }

        } catch (SQLException se) {
            logger.log(Level.WARNING,errorMessage);

        } finally {
            closeEverything(stmt,rs);

        }
        return city;
    }

    private static void closeEverything(Statement stmt ,ResultSet rs){
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException se2) {
            logger.log(Level.WARNING,errorMessage);
        }
        try {
            if(rs!=null)rs.close();
        } catch (SQLException se2) {
            logger.log(Level.WARNING,errorMessage);
        }
    }


}
