package com.apibatmap.restjersey;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/notification")
public class Notification {

	  @Path("/getallforuser/{email}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/species/getall/less
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getAllSpeciesLess( @PathParam("email") String email ) throws ClassNotFoundException, SQLException, JSONException{
	    boolean isAdmin = false;
	    boolean isResearcher = false;
	    String user_type;
	    String user_id;
	    String admin = "administrator";
	    String researcher = "researcher";
		DBConnection mydb = new DBConnection();
      	JSONArray jsonArray = new JSONArray();
        
        String sql2 = "SELECT user_id,user_type FROM user WHERE email = ?;";
        String[] parms2 = {email};
        ResultSet rs2 = mydb.query(sql2,parms2);
        
        //identifing the user type, isAdmin or isResearcher
    	if(rs2.next()){ 
    		user_type = rs2.getString("user_type");
    		user_id = rs2.getString("user_id");
	        //System.out.println(rs2.getString("user_type"));
    		if(admin.equals(user_type)){
    			isAdmin = true;
    			isResearcher = true;
          	}else if(researcher.equals(user_type)){
          		isResearcher = true;
          	};
    	}else{
    		user_type = "Invalid User";
     		user_id = "0";

    	};

    	//generating response according to identified user type
    	if(isAdmin){
        	String sql = "SELECT notification_id, notification_type, time, event_id, admin_notifications.notification_type_id FROM admin_notifications INNER JOIN notification_type ON admin_notifications.notification_type_id=notification_type.notification_type_id;";
    	    String[] parms = {};
    	    ResultSet rs = mydb.query(sql,parms);
    	    while(rs.next()){
    	    	JSONObject jsonObject = new JSONObject();
    	        int notification_id = rs.getInt("notification_id");
    		    String notification_type = rs.getString("notification_type");
    		    int notification_type_id = rs.getInt("notification_type_id");
    		    int event_id = rs.getInt("event_id");
    		    String time = rs.getString("time");

    		    jsonObject.put("notification_id", notification_id); 
    		    jsonObject.put("event_id", event_id); 
    		    jsonObject.put("notification_type", notification_type);  
    		    jsonObject.put("notification_type_id", notification_type_id);  
    		    jsonObject.put("time", time);  
    		    jsonObject.put("table", "admin_notifications"); 
    		    jsonArray.put(jsonObject);
    	    };
    	};

    	if(isResearcher){
        	String sql = "SELECT notification_id, notification_type, time, event_id, user_notifications.notification_type_id FROM user_notifications INNER JOIN notification_type ON user_notifications.notification_type_id=notification_type.notification_type_id WHERE user_id = ?;";
    	    String[] parms = {user_id};
    	    ResultSet rs = mydb.query(sql,parms);
    	    while(rs.next()){
    	    	JSONObject jsonObject = new JSONObject();
    	        int notification_id = rs.getInt("notification_id");
    		    String notification_type = rs.getString("notification_type");
    		    int notification_type_id = rs.getInt("notification_type_id");
    		    int event_id = rs.getInt("event_id");
    		    String time = rs.getString("time");

    		    jsonObject.put("notification_id", notification_id); 
    		    jsonObject.put("notification_type", notification_type);  
    		    jsonObject.put("notification_type_id", notification_type_id);  
    		    jsonObject.put("time", time); 
    		    jsonObject.put("event_id", event_id); 
    		    jsonObject.put("table", "user_notifications"); 
    		    jsonArray.put(jsonObject);
    	    };
    	};

  	    return Response
  	            .status(200)
  	            .header("Access-Control-Allow-Origin", "*")
  	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
  	            .header("Access-Control-Allow-Credentials", "true")
  	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
  	            .header("Access-Control-Max-Age", "1209600")
  	            .entity("{\"notifications\":"+jsonArray+"}")
  	            .build();
	    }

	  	@OPTIONS
		@Consumes("*/*")
		public Response responseForPreflight(){
			return Response 	
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name, X-Auth-Token")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600")
		            .build();
		}
	  	
		@GET
		@Path("/remove/{table}/{notification_id}/{notification_type_id}")
		@Produces("application/json; charset=UTF-8")
		public Response removeSighting(@PathParam("notification_id") String notification_id, @PathParam("notification_type_id") String notification_type_id, @PathParam("table") String table) throws IOException, ClassNotFoundException, SQLException {
			DBConnection mydb = new DBConnection();
	        //System.out.println(notification_id);
	        //System.out.println(table);
	        String sql1 = "DELETE FROM " + table + " WHERE `notification_id` = ? AND `notification_type_id` = ?";
	        //System.out.println(table);
            String[] parms = {notification_id,notification_type_id};
            mydb.insert(sql1,parms);


      	    return Response
      	            .status(200)
      	            .header("Access-Control-Allow-Origin", "*")
      	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
      	            .header("Access-Control-Allow-Credentials", "true")
      	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
      	            .header("Access-Control-Max-Age", "1209600")
      	            .entity("{\"status\":"+ "\"done\"" +"}")
      	            .build();
		}
		
		@GET
		@Path("/getnotification/{event_id}/{notification_type_id}")
		@Produces("application/json; charset=UTF-8")
		public Response getNotificationId(@PathParam("event_id") String event_id, @PathParam("notification_type_id") String notification_type_id) throws IOException, ClassNotFoundException, SQLException {
			int notification_id = 0;
			DBConnection mydb = new DBConnection();
	        String sql1 = "SELECT notification_id FROM admin_notifications WHERE `event_id` = ? AND `notification_type_id` = ?";
            String[] parms = {event_id,notification_type_id};
    	    ResultSet rs = mydb.query(sql1,parms);
    	    if(rs.next()){
    	        notification_id = rs.getInt("notification_id");
    	    };

      	    return Response
      	            .status(200)
      	            .header("Access-Control-Allow-Origin", "*")
      	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
      	            .header("Access-Control-Allow-Credentials", "true")
      	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
      	            .header("Access-Control-Max-Age", "1209600")
      	            .entity("{\"notification_id\":"+ notification_id +"}")
      	            .build();
		}
		
}
