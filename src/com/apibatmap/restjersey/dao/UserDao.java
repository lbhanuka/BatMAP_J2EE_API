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

    public void getAllDetailsByEmail(String email){

    }

    public boolean isValidUser(String email,String salt){
        boolean flag = false;

        return flag;
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

    /**
     * get all account details - admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public JSONObject getAllAccounts() throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection getAllAccDB = new DBConnection();
        String sql = "SELECT * FROM user";
        String[] params = {};
        ResultSet rs =  getAllAccDB.query(sql, params);
        JSONArray allAccountsList = new JSONArray();
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
            allAccountsList.put(row);
        }
        jsonObject.put("allAccountList",allAccountsList);
        return jsonObject;
    }

    public JSONObject acceptSignupRequest(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject  = new JSONObject();
        this.email = jsonReq.getString("email");
        this.acc_status = "active";
        String sql = "UPDATE user SET acc_status = ? WHERE email = ? ";
        String[] params = {acc_status,email};
        DBConnection updateUsr = new DBConnection();
        int rs = updateUsr.update(sql, params);
        if(rs == 0){
            jsonObject.put("accepted",false);
        }else if(rs>0){
            jsonObject.put("accepted",true);
        }
        return jsonObject;
    }

    /**
     * Updates the account status (active,deactivated)
     * @param jsonReq
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public JSONObject manageAccount(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        this.email = jsonReq.getString("email");
        if(jsonReq.getString("type").trim().equals("act")){
            this.acc_status = "active";
            String sql = "UPDATE user SET acc_status = ? WHERE email = ? ";
            String[] params = {acc_status,email};
            DBConnection updateUsr = new DBConnection();
            int rs = updateUsr.update(sql, params);
            if(rs == 0){
                jsonObject.put("activated",false);
            }else if(rs>0){
                jsonObject.put("activated",true);
            }
        }else if(jsonReq.getString("type").trim().equals("deact")){
            this.acc_status = "deactivated";
            String sql = "UPDATE user SET acc_status = ? WHERE email = ? ";
            String[] params = {acc_status,email};
            DBConnection updateUsr = new DBConnection();
            int rs = updateUsr.update(sql, params);
            if(rs == 0){
                jsonObject.put("deactivated",false);
            }else if(rs>0){
                jsonObject.put("deactivated",true);
            }
        }

        return jsonObject;
    }


}
