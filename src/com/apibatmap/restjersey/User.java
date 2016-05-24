package com.apibatmap.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
//import com.group.db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;


@Path("/userservice")
public class User {

	  @Path("/test")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP/crunchify/userservice/test
	  @GET
	  @Produces("application/json")
	  public Response testMethode() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String username = "Bhanukaaa";
		String password = "root";
		jsonObject.put("username", username); 
		jsonObject.put("password", password);

		String result = "@Produces(\"application/json\")\n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	  
	  @Path("getpassword/{username}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP/crunchify/userservice/getpassword/bhanuka
	  @GET
	  @Produces("application/json")
	  public Response getPassword(@PathParam("username") String username) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String password = "root";
		jsonObject.put("Password", password); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

	  @Path("getdetails/{username}")
	  @GET
	  @Produces("application/json")
	  public Response getDetails(@PathParam("username") String username) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String userDetails = "details fron database";
		jsonObject.put("userDetails", userDetails); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	  

	  @Path("/test1")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP/crunchify/userservice/test1
	  @GET
	  @Produces("application/json")
	  public static String getDetails() throws ClassNotFoundException, SQLException {
	        String str;
	        DBConnection mydb = DBConnection.getDbCon();
	        if(mydb!=null){
	            ResultSet rs = mydb.query("SELECT * FROM user WHERE user_id = 1");
	            if(rs.next()){
	                str = rs.getString("user_name");
	            }else {
	                str = "else else";
	            }
	        }else{
	            str="else";
	        }
	        return str;
	    }

}
