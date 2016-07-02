package com.apibatmap.restjersey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/newsservice")
public class Newspost {
	  
	  @Path("getfiveposts/{startingNumber}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/crunchify/newsservice/getfiveposts/1
	  @GET
	  @Produces("application/json")
	  public static Response getFivePosts(@PathParam("startingNumber") int startingNumber) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String newsPostDetails = "News post details from DB";
		jsonObject.put("newsPostDetails", newsPostDetails); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	  @Path("/getfifteennews")
	  //http://localhost:8080/BatMAP_J2EE_API/newsservice/gettennews
	  @GET
	  @Produces("application/json")
	  public Response getTenNews() throws ClassNotFoundException, SQLException, JSONException{
	        DBConnection mydb = new DBConnection();
        	JSONArray jsonArray = new JSONArray();
	        if(mydb!=null){
				String[] parms={};
	            ResultSet rs = mydb.query("SELECT news_post.news_post_id, news_post.user_id, news_post.header, news_post.content, news_post.time, user.first_name, user.last_name FROM news_post INNER JOIN user ON news_post.user_id=user.user_id ORDER BY news_post_id DESC LIMIT 15",parms);
	            	while(rs.next()){

	                	JSONObject jsonObject = new JSONObject();

		        		String news_post_id = rs.getString("news_post_id");
		        		String user_id = rs.getString("user_id");
		        		String first_name = rs.getString("first_name");
		        		String last_name = rs.getString("last_name");
		        		String author = first_name+" "+last_name;
		        		String header = rs.getString("header");
		        		String content = rs.getString("content");
		        		Timestamp time = rs.getTimestamp("time");
		        		jsonObject.put("news_post_id", news_post_id);
		        		jsonObject.put("user_id", user_id);
		        		jsonObject.put("author", author);
		        		jsonObject.put("header", header);
		        		jsonObject.put("content", content);
		        		jsonObject.put("time", time);
		        		jsonObject.put("succsess", true);

		        		jsonArray.put(jsonObject);
	            	};


	        }else{
            	JSONObject jsonObject = new JSONObject();
            	jsonObject.put("succsess", false);
        		jsonArray.put(jsonObject);

	        }
    	    return Response
    	            .status(200)
    	            .header("Access-Control-Allow-Origin", "*")
    	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    	            .header("Access-Control-Allow-Credentials", "true")
    	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    	            .header("Access-Control-Max-Age", "1209600")
    	            .entity("{\"news\":"+jsonArray+"}")
    	            .build();
	    }


    @POST
    @Path("/addnews")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addnews(String st) throws SQLException, ClassNotFoundException {
        System.out.println("add news request received");

        JSONObject jsonReq = new JSONObject(st);
        String user_id = jsonReq.getString("user_id");
        String header = jsonReq.getString("header");
        String content = jsonReq.getString("content");

        JSONObject jsonObject = new JSONObject();
        DBConnection registerDB = new DBConnection();
        String sql = "INSERT INTO news_post(user_id,header,content) VALUES (?,?,?)";
        String[] parms = {user_id,header,content};
        int rs = registerDB.insert(sql, parms);
        if(rs>0){
            jsonObject.put("newsadded", true);
        }else {
            jsonObject.put("newsadded", false);
        }

        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(jsonObject.toString())
                .build();

    }

    @OPTIONS
    @Path("/addnews")
    @Consumes("*/*")
    public Response addNewsPre(){
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }



}
