package com.apibatmap.restjersey;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

@Path("/sightingservice")
public class Sighting {
	  	  

	  @Path("/getall")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/test1
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getUser() throws ClassNotFoundException, SQLException, JSONException{
	        DBConnection mydb = DBConnection.getDbCon();
        	JSONArray jsonArray = new JSONArray();
	        if(mydb!=null){
	            ResultSet rs = mydb.query("SELECT * FROM sighting");
	            	while(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
		        		String sighting_id = rs.getString("sighting_id");
		        		String user_id = rs.getString("user_id");
		        		float longitude = rs.getFloat("longitude");
		        		float latitude = rs.getFloat("latitude");
		        		String species_id = rs.getString("species_id");
		        		String count = rs.getString("count");
		        		jsonObject.put("sighting_id", sighting_id); 
		        		jsonObject.put("user_id", user_id); 
		        		jsonObject.put("longitude", longitude); 
		        		jsonObject.put("latitude", latitude); 
		        		jsonObject.put("species_id", species_id); 
		        		jsonObject.put("count", count); 
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
    	            .entity("{\"allsightings\":"+jsonArray+"}")
    	            .build();
	    }

}
