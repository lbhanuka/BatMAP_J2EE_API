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

    public void getAllDetailsByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email = ?";
        String[] params = {email};
        DBConnection getAllDB = new DBConnection();
        ResultSet rs = getAllDB.query(sql, params);
        if(rs.next()){
            this.user_id = rs.getString("user_id");
            this.email = email;
            this.password = rs.getString("password");
            this.first_name = rs.getString("first_name");
            this.last_name = rs.getString("last_name");
            this.institute = rs.getString("institute");
            this.user_type = rs.getString("user_type");
            this.acc_status = rs.getString("acc_status");
        }
    }

    public boolean isValidUser(String email,String salt){
        boolean flag = false;

        return flag;
    }

    public JSONObject signin(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        String email = jsonReq.getString("email");
        String password = jsonReq.getString("password");
        DBConnection logindb = new DBConnection();
        String sql2 = "SELECT * FROM user WHERE email = ? AND password = ?";
        String[] parms = {email,password};
        ResultSet rs = logindb.query(sql2,parms);
        if(rs.next()){
            jsonObject.put("email",rs.getString("email"));
            jsonObject.put("user_type",rs.getString("user_type"));
            jsonObject.put("acc_status",rs.getString("acc_status"));
            if(rs.getString("acc_status").equals("active")){
                jsonObject.put("signin", true);
            }else if(rs.getString("acc_status").equals("pending")){
                jsonObject.put("signin", false);
                jsonObject.put("status", "pending");
            }else if(rs.getString("acc_status").equals("deactivated")){
                jsonObject.put("signin", false);
                jsonObject.put("status", "deactivated");
            }
        }else {
            jsonObject.put("signin", false);
            jsonObject.put("cred", false);

        }
        return jsonObject;
    }
    public JSONObject signup(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        String email = jsonReq.getString("email");
        String password = jsonReq.getString("password");
        String confpassword = jsonReq.getString("confirmpassword");
        String first_name = jsonReq.getString("first_name");
        String last_name = jsonReq.getString("last_name");
        String institute = jsonReq.getString("institute");
        String user_type = "researcher";
        String acc_status = "pending";

        DBConnection checkUser = new DBConnection();
        String sql1 = "SELECT * FROM user WHERE email = ?";
        String parms1[] = {email};
        ResultSet rs1 = checkUser.query(sql1, parms1);
        if(rs1.next()){
            jsonObject.put("signup", false);
            jsonObject.put("userExists", true);
        }else if(password.trim().equals(confpassword.trim())) {
            jsonObject.put("userExists", false);
            DBConnection registerDB = new DBConnection();
            String sql2 = "INSERT INTO user(email,password,first_name,last_name,institute,user_type,acc_status) VALUES (?,?,?,?,?,?,?)";
            String[] parms2 = {email,password,first_name,last_name,institute,user_type,acc_status};
            int rs2 = registerDB.insert(sql2, parms2);
            if(rs2==0) {
                jsonObject.put("signup", false);
            }else  if (rs2>0) {
                jsonObject.put("signup", true);
            }

        }else {
            jsonObject.put("passwordNotEquals",true);
            jsonObject.put("signup", false);
        }
        return jsonObject;
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
    /**
     * getters and setters
     */

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getAcc_status() {
        return acc_status;
    }

    public void setAcc_status(String acc_status) {
        this.acc_status = acc_status;
    }

}
