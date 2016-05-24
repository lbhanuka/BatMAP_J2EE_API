package com.apibatmap.restjersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by lahiru on 5/17/2016.
 */
public final class DBConnection {

	public Connection conn;
    private Statement statement;
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
    public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }

}
