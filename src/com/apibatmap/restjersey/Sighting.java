package com.apibatmap.restjersey;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import javax.ws.rs.core.MediaType;

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
	        	String sql = "SELECT sighting_id, sighting.user_id, first_name, last_name, institute, longitude, latitude, sighting.species_id, species_name, colour_code, count, approval, date, time, sighting.location_id, location FROM sighting INNER JOIN user ON sighting.user_id=user.user_id INNER JOIN species ON sighting.species_id=species.species_id INNER JOIN sighting_location ON sighting.location_id=sighting_location.location_id;";
	            String[] parms = {};
	            ResultSet rs = mydb.query(sql,parms);
	            	while(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
		        		String sighting_id = rs.getString("sighting_id");
		        		String user_id = rs.getString("user_id");
		        		String first_name = rs.getString("first_name");
		        		String last_name = rs.getString("last_name");
		        		String institute = rs.getString("institute");
		        		float longitude = rs.getFloat("longitude");
		        		float latitude = rs.getFloat("latitude");
		        		String species_id = rs.getString("species_id");
		        		String species_name = rs.getString("species_name");
		        		String count = rs.getString("count");
		        		String date = rs.getString("date");
		        		String time = rs.getString("time");
		        		String approval = rs.getString("approval");
		        		String location_id = rs.getString("location_id");
		        		String location = rs.getString("location");
		        		String colour_code = rs.getString("colour_code");
		        		jsonObject.put("sighting_id", sighting_id); 
		        		jsonObject.put("user_id", user_id); 
		        		jsonObject.put("first_name", first_name); 
		        		jsonObject.put("last_name", last_name); 
		        		jsonObject.put("institute", institute); 
		        		jsonObject.put("longitude", longitude); 
		        		jsonObject.put("latitude", latitude); 
		        		jsonObject.put("species_id", species_id); 
		        		jsonObject.put("species_name", species_name); 
		        		jsonObject.put("count", count); 
		        		jsonObject.put("date", date); 
		        		jsonObject.put("time", time); 
		        		jsonObject.put("approval", approval); 
		        		jsonObject.put("location_id", location_id); 
		        		jsonObject.put("location", location);
		        		jsonObject.put("colour_code", colour_code);  
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
	  	

		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response postStudentRecord(String sighting) throws ClassNotFoundException, SQLException, JSONException{
			
			JSONObject newSighting = new JSONObject(sighting); 
			JSONObject jsonObject = new JSONObject();
			String species_id = "";
			String location_id = "";
			
	        DBConnection mydb = DBConnection.getDbCon();
        	String sql = "SELECT * FROM species WHERE species_name = ?";
        	String para1 = newSighting.getString("species");
            String[] parms = {para1};
            ResultSet rs1 = mydb.query(sql,parms);
            if(rs1.next()){
        		species_id = String.valueOf(rs1.getInt("species_id")); 
            }else {
            	jsonObject.put("succsess", false);
            }
			String result = "{\"Record entered\": \"" + "test" + "\"}";

        	String sql2 = "SELECT * FROM sighting_location WHERE location = ?";
        	String param2 = newSighting.getString("where");
            String[] parms2 = {param2};
            ResultSet rs2 = mydb.query(sql2,parms2);
            if(rs2.next()){
        		location_id = String.valueOf(rs2.getInt("location_id")); 
            }else {
            	jsonObject.put("succsess", false);
            }
            
            String date = newSighting.getString("date");
            date = date.substring(0, 10);
            String time = newSighting.getString("time");
            String count = newSighting.getString("count");
            String lat = String.valueOf(newSighting.getDouble("lat"));
            String lng = String.valueOf(newSighting.getDouble("lng"));
            String user = "1";
            String comment = newSighting.getString("comment");

        	String sqlMain = "INSERT INTO  `sighting` (`user_id` ,`longitude` ,`latitude` ,`species_id` ,`count` ,`date` ,`time` ,`location_id` ,`comment`)VALUES (?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?);";
            String[] parmsMain = {user,lng,lat,species_id,count,date,time,location_id,comment};
            int rsMain = mydb.insert(sqlMain,parmsMain);
            //if(rsMain.next()){

            //}else {
            	
            //}
			//String test = newSighting.getString("count");
			result = "{\"Record entered\": \"" + "DONE" + "\"}";
			
			return Response 	
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Acsess-Control-Max-Age", "1209600")
		            .entity(result).build();
		}
	  
		  @Path("/test")
		  //use the link below to test if this works!
		  //http://localhost:8080/BatMAP_J2EE_API/userservice/test
		  @GET
		  @Produces("application/json; charset=UTF-8")
		  public String testMethode() throws ClassNotFoundException, SQLException, JSONException {

				String result = "INIT";
		        DBConnection mydb = DBConnection.getDbCon();
	        	String sql1 = "SELECT * FROM sighting_location WHERE location = ?";
	            String[] parms2 = {"Other"};
	            ResultSet rs3 = mydb.query(sql1,parms2);
	            if(rs3.next()){
	        		int species_id = rs3.getInt("location_id"); 
	            	result = "answer is " + species_id;
	            }else {
	            	result = "ERROR!";
	            }

			//String result = "@Produces(\"application/json\")\n\n" + jsonObject;
			return result;
		  }
}
