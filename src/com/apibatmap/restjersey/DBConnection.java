package com.apibatmap.restjersey;

import java.sql.*;

/**
 * Created by lahiru on 5/17/2016.
 */

public final class DBConnection {
    public Connection conn;
    private PreparedStatement statement;
    public static DBConnection db;
    private DBConnection() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "batmap";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            this.conn = DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized DBConnection getDbCon() {
        if ( db == null ) {
            db = new DBConnection();
        }
        return db;
    }

    /**
     *
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */

    public ResultSet query(String query, String[] parms) throws SQLException{
        statement = db.conn.prepareStatement(query);
        if(parms.length!=0){
            for(int i=0;i<parms.length;i++){
                statement.setString(i+1,parms[i]);
            }
        }
        ResultSet res = statement.executeQuery();
        return res;
    }

    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */

    public int insert(String insertQuery, String[] parms) throws SQLException {
        statement = db.conn.prepareStatement(insertQuery);
        if(parms.length!=0){
            for(int i=0;i<parms.length;i++){
                statement.setString(i+1,parms[i]);
            }
        }
        int result = statement.executeUpdate();
        return result;

    }

}



