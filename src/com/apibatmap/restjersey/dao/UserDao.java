package com.apibatmap.restjersey.dao;

import com.apibatmap.restjersey.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lahiru on 6/15/2016.
 */
public class UserDao {
    private String user_id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String institute;
    private String user_type;
    private String acc_status;

    /**
     * constructor no parameter
     */
    public UserDao() {
        this.user_id = null;
        this.email = null;
        this.password = null;
        this.first_name = null;
        this.last_name = null;
        this.institute = null;
        this.user_type = null;
        this.acc_status = null;
    }

    public void getAllByEmail(String email){

    }



    /**
     * update user profile
     * @param jsonReq
     * @return jsonObject
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public JSONObject updateProfile(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        this.email = jsonReq.getString("email");
        this.password = jsonReq.getString("password");
        this.first_name = jsonReq.getString("first_name");
        this.last_name = jsonReq.getString("last_name");
        this.institute = jsonReq.getString("institute");

        String sql = "UPDATE user SET password = ?, first_name = ?, last_name = ?, institute = ? WHERE email = ? ";
        String[] params = {password,first_name,last_name,institute,email};
        DBConnection updateUsr = new DBConnection();
        JSONObject jsonObject = new JSONObject();
        int rs = updateUsr.update(sql, params);
        if(rs>0){
            jsonObject.put("updated",true);
        }else if(rs==0) {
            jsonObject.put("updated",false);
        }
        return jsonObject;
    }

    /**
     * get pending sign up request by admin..
     * @return json
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public JSONObject getPendingList() throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection pendingDB = new DBConnection();
        String accstatus = "pending";
        String sql = "SELECT * FROM user WHERE acc_status = ?";
        String[] params = {accstatus};
        ResultSet rs =  pendingDB.query(sql, params);
        JSONArray pendingList = new JSONArray();
        while (rs.next()){
            JSONObject row = new JSONObject();
            row.put("user_id", rs.getString("user_id"));
            row.put("email",rs.getString("email"));
            row.put("password",rs.getString("password"));
            row.put("first_name",rs.getString("first_name"));
            row.put("last_name",rs.getString("last_name"));
            row.put("institute",rs.getString("institute"));
            row.put("user_type", rs.getString("user_type"));
            row.put("acc_status",rs.getString("acc_status"));
            pendingList.put(row);
        }
        jsonObject.put("pendingList", pendingList);
        return jsonObject;
    }


}
