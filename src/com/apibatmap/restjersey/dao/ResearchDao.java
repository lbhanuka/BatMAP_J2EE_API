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
            row.put("created_date",rs.getDate("time"));
            row.put("created_time",rs.getTime("time"));
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
        String sql = "SELECT * from research_comment INNER JOIN user ON research_comment.user_id = user.user_id WHERE research_id = ? ORDER BY comment_id ASC";
        String[] params = {research_id};
        ResultSet rs = rDB.query(sql, params);
        JSONArray commentsList = new JSONArray();
        while (rs.next()){
            JSONObject row = new JSONObject();
            row.put("research_id",rs.getString("research_id"));
            row.put("comment_id",rs.getString("comment_id"));
            row.put("user_email",rs.getString("email"));
            row.put("com_content", rs.getString("content"));
            row.put("created_date", rs.getDate("time"));
            row.put("created_time",rs.getTime("time"));
            commentsList.put(row);
        }
        jsonObject.put("commentsList",commentsList);
        return jsonObject;
    }

    public JSONObject getRepliesByResearch(String research_id) throws SQLException, ClassNotFoundException {
        JSONObject jsonObject = new JSONObject();
        DBConnection rDB = new DBConnection();
        String sql = "SELECT user.*,research_comment_reply.* from research_comment_reply INNER JOIN user ON research_comment_reply.user_id = user.user_id INNER JOIN research_comment ON research_comment_reply.comment_id = research_comment.comment_id WHERE research_id = ?";
        String[] params = {research_id};
        ResultSet rs = rDB.query(sql, params);
        JSONArray repliesList = new JSONArray();
        while (rs.next()){
            JSONObject row = new JSONObject();
            row.put("comment_id",rs.getString("comment_id"));
            row.put("reply_id",rs.getString("reply_id"));
            row.put("rep_user_email",rs.getString("email"));
            row.put("rep_content",rs.getString("content"));
            repliesList.put(row);
        }
        jsonObject.put("repliesList",repliesList);
        return jsonObject;
    }

    public boolean addCommentForResearch(JSONObject jsonReq) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        UserDao ud = new UserDao();
        String research_id = jsonReq.getString("research_id");
        ud.getAllDetailsByEmail(jsonReq.getString("user_email"));
        String user_id = ud.getUser_id();
        String content = jsonReq.getString("content");
        DBConnection rDB = new DBConnection();
        String sql = "INSERT INTO research_comment(research_id,user_id,content) VALUES (?,?,?)";
        String[] params = {research_id,user_id,content};
        int rs = rDB.insert(sql,params);
        if(rs>0){
            flag = true;
        }
        return flag;
    }

    public String getLastResearchId() throws SQLException, ClassNotFoundException {
        DBConnection mydb = new DBConnection();
        String file_name = "error";
        String sql = "SELECT research_id FROM research ORDER BY research_id DESC LIMIT 0 , 1";
        String[] parms = {};
        ResultSet rs = mydb.query(sql, parms);
        if(rs.next()){
            file_name = rs.getString("research_id");
//            System.out.println(file_name);
        }
        return file_name;
    }

    public int updateResearchFilePath(String file_name) throws SQLException, ClassNotFoundException {
        int flag = 0;
        DBConnection db = new DBConnection();
        String sql2 = "UPDATE research SET file_path = ? WHERE research_id = ?";
        String[] parms2 = {(file_name+".pdf"),file_name};
        flag = db.update(sql2, parms2);
        return flag;
    }

}
