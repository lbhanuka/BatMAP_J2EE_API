package com.apibatmap.restjersey;

import java.sql.*;

/**
 * Created by lahiru on 5/17/2016.
 */

public class DBConnection {
    private Connection connection;
    private String url = null;
    private String dbName = null;
    private String username = null;
    private String password = null;
    private String driver = null;

    public DBConnection() {
        this.connection = null;
        this.url = "jdbc:mysql://localhost:3306/";
        this.dbName = "batmap";
        this.driver = "com.mysql.jdbc.Driver";
        this.username = "root";
        this.password = "";

    }

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        if(connection == null){
            Class.forName(driver);
            connection = DriverManager.getConnection(url + dbName, username, password);
            System.out.println("Connection to db success!");
        }
        return connection;
    }



    public ResultSet query(String query, String[] parms) throws SQLException, ClassNotFoundException {
        getDbConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        if(parms.length!=0){
            for(int i=0;i<parms.length;i++){
                statement.setString(i + 1, parms[i]);
            }
        }
        ResultSet res = statement.executeQuery();
        return res;
    }



    public int insert(String insertQuery, String[] parms) throws SQLException, ClassNotFoundException {
        getDbConnection();
        PreparedStatement statement = connection.prepareStatement(insertQuery);
        if(parms.length!=0){
            for(int i=0;i<parms.length;i++){
                statement.setString(i+1,parms[i]);
            }
        }
        int result = statement.executeUpdate();
        return result;

    }

    public int update(String updateQuery, String[] parms) throws SQLException, ClassNotFoundException {
        getDbConnection();
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        if(parms.length!=0){
            for(int i=0;i<parms.length;i++){
                statement.setString(i+1,parms[i]);
            }
        }
        int result = statement.executeUpdate();

        return result;
    }

}



