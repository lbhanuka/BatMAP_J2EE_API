package com.apibatmap.restjersey;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



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
	  public Response getAllSpeciesLess() throws ClassNotFoundException, SQLException, JSONException{
        DBConnection mydb = new DBConnection();
      	JSONArray jsonArray = new JSONArray();
	        	String sql = "SELECT species_id, species_name, colour_code FROM species;";
	            String[] parms = {};
	            ResultSet rs = mydb.query(sql,parms);
	            	while(rs.next()){
	            		
	                	JSONObject jsonObject = new JSONObject();
	                	
		        		int species_id = rs.getInt("species_id");
		        		String species_name = rs.getString("species_name");
		        		String colour_code = rs.getString("colour_code");

		        		jsonObject.put("species_id", species_id); 
		        		jsonObject.put("species_name", species_name);  
		        		jsonObject.put("colour_code", colour_code);  
		        		jsonObject.put("show", false);  
		        		
		        		jsonArray.put(jsonObject);
	            	};

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
	  

	  @Path("/getall/medium")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/species/getall/less
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getAllSpeciesMedium() throws ClassNotFoundException, SQLException, JSONException{
        DBConnection mydb = new DBConnection();
      	JSONArray jsonArray = new JSONArray();
	        	String sql = "SELECT species_id, species_name, description, main_picture FROM species;";
	            String[] parms = {};
	            ResultSet rs = mydb.query(sql,parms);
	            	while(rs.next()){
	            		
	            		if(rs.getInt("species_id")!=7){ //to avoid species "unidentified"
		                	JSONObject jsonObject = new JSONObject();
		                	
			        		int species_id = rs.getInt("species_id");
			        		String species_name = rs.getString("species_name");
			        		String description = rs.getString("description");
	
			        		jsonObject.put("species_id", species_id); 
			        		jsonObject.put("species_name", species_name);  
			        		jsonObject.put("description", description);  
			        		
			        		jsonArray.put(jsonObject);
	            		}else{}
	            	};

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
	  
		private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/home/bhanuka/Pictures/species/";

		/**
		 * Upload a File
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
		 */

		@POST
		@Path("/upload")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response uploadFile(
				@FormDataParam("file") InputStream fileInputStream,
				@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws ClassNotFoundException, SQLException {
			
	        DBConnection mydb = new DBConnection();
	        String file_name = "error";
        	String sql = "SELECT `species`.`species_id` FROM  `species` ORDER BY  `species`.`species_id` DESC LIMIT 0 , 1";
            String[] parms = {};
            ResultSet rs = mydb.query(sql,parms);
            if(rs.next()){
            	file_name = rs.getString("species_id");
            }
            
			String filePath = SERVER_UPLOAD_LOCATION_FOLDER	+ file_name + ".png";

			// save the file to the server
			saveFile(fileInputStream, filePath);

        	String sql2 = "UPDATE  `batmap`.`species` SET  `main_picture` = ? WHERE  `species`.`species_id` =?;";
            String[] parms2 = {(file_name+".png"),file_name};
            mydb.insert(sql2,parms2);
            
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
		
		private static final String FILE_PATH = "/home/bhanuka/Pictures/species/";

		@GET
		@Path("/getimage/{species_id}")
		@Produces("image/png")
		public Response getImage(@PathParam("species_id") String species_id) throws IOException, ClassNotFoundException, SQLException {
            String image_name = "default.png";
			DBConnection mydb = new DBConnection();
	        String sql = "SELECT main_picture FROM species WHERE species_id = ?;";
            String[] parms = {species_id};
            ResultSet rs = mydb.query(sql,parms);
            if(rs.next()){
                image_name = rs.getString("main_picture");
            }
            
			String filename = FILE_PATH + image_name;
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
		public Response postNewSpecies(String species) throws ClassNotFoundException, SQLException, JSONException{
			
			JSONObject newSpecies = new JSONObject(species); 
	        DBConnection mydb = new DBConnection();
            
            String species_name = newSpecies.getString("species_name");
            String description = newSpecies.getString("description");
            String color = newSpecies.getString("color");

        	String sql = "INSERT INTO  `species` (`species_name` ,`description` ,`colour_code`)VALUES (?,  ?,  ?);";
            String[] parms = {species_name,description,color};
            mydb.insert(sql,parms);

			String result = "{\"Record entered\": \"" + "DONE" + "\"}";
			
			return Response 	
					.status(200)
					.header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Acsess-Control-Max-Age", "1209600")
		            .entity(result).build();
		}
	  
}
