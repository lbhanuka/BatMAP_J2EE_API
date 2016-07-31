package com.apibatmap.restjersey;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

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
        DBConnection mydb = new DBConnection();
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

	  @Path("/getone/{sighting_id}")
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getOneSighting(@PathParam("sighting_id") String sighting_id) throws ClassNotFoundException, SQLException, JSONException{
          DBConnection mydb = new DBConnection();
        	JSONArray jsonArray = new JSONArray();
	        if(mydb!=null){
	        	String sql = "SELECT sighting.user_id, first_name, last_name, institute, longitude, latitude, sighting.species_id, species_name, colour_code, count, approval, date, time, sighting.location_id, location FROM sighting INNER JOIN user ON sighting.user_id=user.user_id INNER JOIN species ON sighting.species_id=species.species_id INNER JOIN sighting_location ON sighting.location_id=sighting_location.location_id WHERE sighting.sighting_id = ?;";
	            String[] parms = {sighting_id};
	            ResultSet rs = mydb.query(sql,parms);
	            	if(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
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
    	            .entity("{\"sighting\":"+jsonArray+"}")
    	            .build();
	    }
	  

	  @Path("/getall/{email}")
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getAllUserSightings(@PathParam("email") String email) throws ClassNotFoundException, SQLException, JSONException{
          DBConnection mydb = new DBConnection();
        	JSONArray jsonArray = new JSONArray();
	        if(mydb!=null){
	        	String sql = "SELECT sighting.user_id, sighting_id, first_name, email, last_name, institute, longitude, latitude, sighting.species_id, species_name, colour_code, count, approval, date, time, sighting.location_id, location FROM sighting INNER JOIN user ON sighting.user_id=user.user_id INNER JOIN species ON sighting.species_id=species.species_id INNER JOIN sighting_location ON sighting.location_id=sighting_location.location_id WHERE email = ?;";
	            String[] parms = {email};
	            ResultSet rs = mydb.query(sql,parms);
	            	while(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
		        		String user_id = rs.getString("user_id");
		        		String sighting_id = rs.getString("sighting_id");
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
    	            .entity("{\"usersightings\":"+jsonArray+"}")
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
			String user_id = "";
			
	        DBConnection mydb = new DBConnection();
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
            
        	String sql4 = "SELECT user_id FROM user WHERE email = ?";
        	String param4 = newSighting.getString("user");
            String[] parms4 = {param4};
            ResultSet rs4 = mydb.query(sql4,parms4);
            if(rs4.next()){
        		user_id = String.valueOf(rs4.getInt("user_id")); 
            }else {
            	jsonObject.put("succsess", false);
            }
            
            String date = newSighting.getString("date");
            date = date.substring(0, 10);
            String time = newSighting.getString("time");
            String count = newSighting.getString("count");
            String lat = String.valueOf(newSighting.getDouble("lat"));
            String lng = String.valueOf(newSighting.getDouble("lng"));
            String comment = newSighting.getString("comments");

        	String sqlMain = "INSERT INTO  `sighting` (`user_id` ,`longitude` ,`latitude` ,`species_id` ,`count` ,`date` ,`time` ,`location_id` ,`comment`)VALUES (?,  ?,  ?,  ?,  ?, ?,  ?,  ?,  ?);";
            String[] parmsMain = {user_id,lng,lat,species_id,count,date,time,location_id,comment};
            mydb.insert(sqlMain,parmsMain);

            
        	String last_id = null;
            String sql3 = "SELECT `sighting`.`sighting_id` FROM  `sighting` ORDER BY  `sighting`.`sighting_id` DESC LIMIT 0 , 1";
            String[] parms3 = {};
            ResultSet rs = mydb.query(sql3,parms3);
            if(rs.next()){
            	last_id = rs.getString("sighting_id");
            }
			result = "{\"Record_id\": \"" + last_id + "\"}";
			
        	String sql5 = "INSERT INTO  `admin_notifications` (`notification_type_id`,`event_id`)VALUES (?, ?);";
            String[] parms5 = {"2",last_id};
            mydb.insert(sql5,parms5);
			
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
		        DBConnection mydb = new DBConnection();
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
		  
		  
			/**
			 * Upload a File
			 * @throws SQLException 
			 * @throws ClassNotFoundException 
			 */
			private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/home/bhanuka/Pictures/sightings/";

			@POST
			@Path("/upload")
			@Consumes(MediaType.MULTIPART_FORM_DATA)
			public Response uploadFile(
					@FormDataParam("file") InputStream fileInputStream,
					@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws ClassNotFoundException, SQLException {
				
		        DBConnection mydb = new DBConnection();
		        String file_name;
	        	
		        String last_sighting_id = null;
	            String sql3 = "SELECT `sighting`.`sighting_id` FROM  `sighting` ORDER BY  `sighting`.`sighting_id` DESC LIMIT 0 , 1";
	            String[] parms3 = {};
	            ResultSet rs = mydb.query(sql3,parms3);
	            if(rs.next()){
	            	last_sighting_id = rs.getString("sighting_id");
	            }
	            
	        	String sql6 = "LOCK TABLES `sighting_image` WRITE;";
	            String[] parms6 = {};
	            mydb.insert(sql6,parms6); 
	            
	        	String sql4 = "INSERT INTO  `sighting_image` (`sighting_id`)VALUES (?);";
	            String[] parms4 = {last_sighting_id};
	            mydb.insert(sql4,parms4); 
	            
		        String last_image_id = null;
	            String sql5 = "SELECT `sighting_image`.`image_path` FROM  `sighting_image` ORDER BY  `sighting_image`.`image_path` DESC LIMIT 0 , 1";
	            String[] parms5 = {};
	            ResultSet rs5 = mydb.query(sql5,parms5);
	            if(rs5.next()){
	            	last_image_id = rs5.getString("image_path");
	            }
	            
	        	String sql7 = "UNLOCK TABLES;";
	            String[] parms7 = {};
	            mydb.insert(sql7,parms7); 
	            
	            file_name = last_image_id;
	            String filePath = SERVER_UPLOAD_LOCATION_FOLDER	+ last_image_id + ".png";

				// save the file to the server
				saveFile(fileInputStream, filePath);

	        	//String sql2 = "UPDATE  `batmap`.`species` SET  `main_picture` = ? WHERE  `species`.`species_id` =?;";
	            //String[] parms2 = {(file_name+".png"),file_name};
	            //mydb.insert(sql2,parms2);
	            System.out.println("got last id as " + last_image_id);

				String output = "New Species recorded successfully.";

				return Response
						.status(200)
						.header("Access-Control-Allow-Origin", "*")
			            .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name, X-Auth-Token")
			            .header("Access-Control-Allow-Credentials", "true")
			            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
			            .header("Access-Control-Max-Age", "1209600")
						.entity(output).build();

			}
			
			// save uploaded file to a defined location on the server
			private void saveFile(InputStream uploadedInputStream,
					String serverLocation) {

				try {
					OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
					int read = 0;
					byte[] bytes = new byte[1024];

					outpuStream = new FileOutputStream(new File(serverLocation));
					while ((read = uploadedInputStream.read(bytes)) != -1) {
						outpuStream.write(bytes, 0, read);
					}
					outpuStream.flush();
					outpuStream.close();
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
			
			private static final String FILE_PATH = "/home/bhanuka/Pictures/sightings/";

			@GET
			@Path("/getimage/{image_id}")
			@Produces("image/png")
			public Response getImage(@PathParam("image_id") String image_id) throws IOException, ClassNotFoundException, SQLException {
	            String image_name = image_id;
	            
				String filename = FILE_PATH + image_name +".png";
				File imageFile = new File(filename);
				BufferedImage image = ImageIO.read(imageFile);

			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ImageIO.write(image, "png", baos);
			    byte[] imageData = baos.toByteArray();

			    // uncomment line below to send non-streamed
			    return Response.ok(imageData).build();

			    // uncomment line below to send streamed
			    //return Response.ok(new ByteArrayInputStream(imageData)).build();

			}
			
			@GET
			@Path("/getimageids/{sighting_id}")
			@Produces("application/json; charset=UTF-8")
			public Response getImageIds(@PathParam("sighting_id") String sighting_id) throws IOException, ClassNotFoundException, SQLException {
				DBConnection mydb = new DBConnection();
		      	JSONArray jsonArray = new JSONArray();
		        String sql = "SELECT image_path FROM sighting_image WHERE sighting_id = ?;";
	            String[] parms = {sighting_id};
	            ResultSet rs = mydb.query(sql,parms);
	            
            	while(rs.next()){                	
	        		jsonArray.put(rs.getInt("image_path"));
            	};

          	    return Response
          	            .status(200)
          	            .header("Access-Control-Allow-Origin", "*")
          	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
          	            .header("Access-Control-Allow-Credentials", "true")
          	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
          	            .header("Access-Control-Max-Age", "1209600")
          	            .entity("{\"imageIds\":"+jsonArray+"}")
          	            .build();
			}
			
			@GET
			@Path("/remove/{sighting_id}")
			@Produces("application/json; charset=UTF-8")
			public Response removeSighting(@PathParam("sighting_id") String sighting_id) throws IOException, ClassNotFoundException, SQLException {
				DBConnection mydb = new DBConnection();
		        String sql1 = "DELETE FROM `sighting` WHERE `sighting`.`sighting_id` = ?";
		        String sql2 = "DELETE FROM `sighting_image` WHERE `sighting_image`.`sighting_id` = ?";
	            String[] parms = {sighting_id};
	            mydb.insert(sql2,parms);
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
			@Path("/accept/{sighting_id}")
			@Produces("application/json; charset=UTF-8")
			public Response acceptSighting(@PathParam("sighting_id") String sighting_id) throws IOException, ClassNotFoundException, SQLException {
				DBConnection mydb = new DBConnection();
		        String sql = "UPDATE  `sighting` SET  `approval` =  'approved' WHERE  `sighting`.`sighting_id` = ?";
	            String[] parms = {sighting_id};
	            mydb.insert(sql,parms);


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
			@Path("/updateSpecies/{sighting_id}/{species_id}")
			@Produces("application/json; charset=UTF-8")
			public Response updateSpecies(@PathParam("sighting_id") String sighting_id, @PathParam("species_id") String species_id) throws IOException, ClassNotFoundException, SQLException {
				DBConnection mydb = new DBConnection();
		        String sql = "UPDATE  `sighting` SET  `sighting`.`species_id` = ?  WHERE  `sighting`.`sighting_id` = ?";
	            String[] parms = {species_id,sighting_id};
	            mydb.insert(sql,parms);


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
			@Path("/removeimage/{image_id}")
			@Produces("application/json; charset=UTF-8")
			public Response removeImage(@PathParam("image_id") String image_id) throws IOException, ClassNotFoundException, SQLException {
				DBConnection mydb = new DBConnection();
		        String sql = "DELETE FROM `sighting_image` WHERE `sighting_image`.`image_path` = ?";
	            String[] parms = {image_id};
	            mydb.insert(sql,parms);


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
			
}

