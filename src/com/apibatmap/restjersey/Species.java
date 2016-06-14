package com.apibatmap.restjersey;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/species")
public class Species {

	  @Path("/getall/less")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/species/getall/less
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getAllSpecies() throws ClassNotFoundException, SQLException, JSONException{
	    DBConnection mydb = DBConnection.getDbCon();
      	JSONArray jsonArray = new JSONArray();
	        if(mydb!=null){
	        	String sql = "SELECT species_id, species_name FROM species;";
	            String[] parms = {};
	            ResultSet rs = mydb.query(sql,parms);
	            	while(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
		        		int species_id = rs.getInt("species_id");
		        		String species_name = rs.getString("species_name");

		        		jsonObject.put("species_id", species_id); 
		        		jsonObject.put("species_name", species_name);  
		        		jsonObject.put("show", true);  
		        		
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
  	            .entity("{\"allspecies\":"+jsonArray+"}")
  	            .build();
	    }
}
