package com.test;

import com.example.model.City;
import com.example.service.DatabaseConnection;
import com.example.service.DatabaseService;
import org.junit.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DatabaseServiceTest {
    Connection conn;
    DatabaseConnection db;
    static final String USER = "sa";
    static final String PASS = "";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:test";

    @Before
    public  void setUp() throws Exception {
        db = new DatabaseConnection(USER,PASS,JDBC_DRIVER,DB_URL);
        conn = db.connect();
        db.createDb(conn);
    }

    @Test
    public void testAddCity() {
        City city = new City(1,"Alger",300000000,"Belle ville");
        int i = DatabaseService.addCity(conn,city);
        Assert.assertEquals(i,1);
    }

   @Test
    public void testGetCity() throws SQLException {
        City city = new City(1,"Alger",300000000,"Belle ville");
        DatabaseService.addCity(conn,city);
        City city2 = DatabaseService.getCity(conn,1);
        Assert.assertEquals(city2,city);
    }


    @Test
    public void testGetCities() throws SQLException {
        City city1 = new City(1,"Alger",300000000,"Belle ville");
        City city2 = new City(2,"Oran",300000000,"Belle ville");
        City city3 = new City(3,"Annaba",300000000,"Belle ville");
        City[] expectedCities = {city1,city2,city3};
        DatabaseService.addCity(conn,city1);
        DatabaseService.addCity(conn,city2);
        DatabaseService.addCity(conn,city3);
        List cities =  DatabaseService.getCities(conn);
        Assert.assertArrayEquals(cities.toArray(),expectedCities);
    }

    @After
    public  void tearDown() throws Exception {
        db.disconnect(conn);
    }

    @Test
    public void test_name_get(){

        City city2 = new City(1,"Oran",300000000,"Belle ville");
        DatabaseService.addCity(conn,city2);
        Assert.assertEquals(city2,DatabaseService.getCityByName(conn,city2.getName()) );
    }
    @Test(expected = Exception.class)
    public void coverage_add() throws NullPointerException{
        db.disconnect(null);
        db.createDb(null);
    }

    @Test
    public void exption_sql(){
        City city1 = new City(1,"Alger",300000000,"Belle ville");
        City city2 = new City(1,"Oran",300000000,"Belle ville");
        DatabaseService.addCity(conn,city1);
        DatabaseService.addCity(conn,city2);

        City[] expectedCities = {city1}; // because same id must not be accepted
        List cities =  DatabaseService.getCities(conn);
        Assert.assertArrayEquals(cities.toArray(),expectedCities);
    }

    @Test
    public void coverage_add_v2()  {
        db.createDb(null);
    }

    @Test
    public void overwrite_table_test(){
        db.createDb(conn);
    }




}