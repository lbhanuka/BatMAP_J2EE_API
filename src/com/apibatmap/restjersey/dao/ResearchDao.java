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
}
