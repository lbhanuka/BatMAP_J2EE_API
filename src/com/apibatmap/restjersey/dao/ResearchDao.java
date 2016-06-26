package com.apibatmap.restjersey.dao;

import com.apibatmap.restjersey.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lahiru on 6/19/2016.
 */
public class ResearchDao {
    private String research_id;
    private String user_id;
    private String title;
    private String description;
    private String time;
    private String updated;
    private String last_update;

    public ResearchDao() {
        this.research_id = null;
        this.user_id = null;
        this.title = null;
        this.description = null;
        this.time = null;
        this.updated = null;
        this.last_update = null;
    }

    public JSONObject getAllResearch() throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection getAll = new DBConnection();
        String sql = "SELECT * FROM research INNER JOIN user ON research.user_id = user.user_id";
        String[] params = {};
        ResultSet rs = getAll.query(sql, params);
        JSONArray allResearchList = new JSONArray();
        while (rs.next()){
            JSONObject row = new JSONObject();
            row.put("research_id", rs.getString("research_id"));
            row.put("user_email",rs.getString("email"));
            row.put("title",rs.getString("title"));
            row.put("description",rs.getString("description"));
            row.put("time",rs.getString("time"));
            row.put("updated",rs.getString("updated"));
            row.put("last_update",rs.getString("last_update"));
            allResearchList.put(row);
        }
        jsonObject.put("allResearchList",allResearchList);
        return jsonObject;
    }

    public JSONObject addNewResearch(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection resDB = new DBConnection();
        UserDao ud = new UserDao();
        ud.getAllDetailsByEmail(jsonReq.getString("userEmail"));
        String user_id = ud.getUser_id();
        String title = jsonReq.getString("resTitle");
        String description = jsonReq.getString("resDescription");
        String sql = "INSERT INTO research(user_id,title,description) VALUES (?,?,?)";
        String[] parms = {user_id,title,description};
        int rs = resDB.insert(sql, parms);
        if(rs==0) {
            jsonObject.put("addRes", false);
        }else  if (rs>0) {
            jsonObject.put("addRes", true);
        }

        return jsonObject;
    }

    public JSONObject getResearchById(String research_id) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection rDB = new DBConnection();
        String sql = "SELECT * from research INNER JOIN user ON research.user_id = user.user_id WHERE research_id = ?";
        String[] params = {research_id};
        ResultSet rs = rDB.query(sql, params);
        if(rs.next()){
            jsonObject.put("flag",true);
            jsonObject.put("research_id",rs.getString("research_id"));
            jsonObject.put("user_email",rs.getString("email"));
            jsonObject.put("res_title",rs.getString("title"));
            jsonObject.put("res_desc",rs.getString("description"));
            jsonObject.put("created_date",rs.getDate("time"));
            jsonObject.put("created_time",rs.getTime("time"));
        }else{
            jsonObject.put("flag",false);
        }

        return jsonObject;
    }

    public JSONObject getCommentsByResId(String research_id) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection rDB = new DBConnection();
        String sql = "SELECT * from research_comment INNER JOIN user ON research_comment.user_id = user.user_id WHERE research_id = ?";
        String[] params = {research_id};
        ResultSet rs = rDB.query(sql, params);
        JSONArray commentsList = new JSONArray();
        while (rs.next()){
            JSONObject row = new JSONObject();
            row.put("research_id",rs.getString("research_id"));
            row.put("comment_id",rs.getString("research_id"));
            row.put("user_email",rs.getString("email"));
            row.put("com_content",rs.getString("content"));
            commentsList.put(row);
        }
        jsonObject.put("commentsList",commentsList);
        return jsonObject;
    }

}
