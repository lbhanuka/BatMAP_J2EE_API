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
//import javax.ws.rs.Consumes;


@Path("/userservice")
public class User {

	  @Path("/test")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/test
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public String testMethode() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String username = "Bhanuka";
		String password = "root";
		jsonObject.put("username", username); 
		jsonObject.put("password", password);

		//String result = "@Produces(\"application/json\")\n\n" + jsonObject;
		return "{\"userId\": 1,\"id\": 1,\"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\": \"quia et suscipitsuscipit recusandae consequuntur expedita et cumreprehenderit molestiae ut ut quas totamnostrum rerum est autem sunt rem eveniet architecto\"}";
	  }
	  
	  @Path("getpassword/{username}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/getpassword/bhanuka
	  @GET
	  @Produces("application/json")
	  public JSONObject getPassword(@PathParam("username") String username) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String password = "root";
		jsonObject.put("Password", password); 

		//String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return jsonObject;
	  }

	  @Path("getdetails/{username}")
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getDetails(@PathParam("username") String username) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String userDetails = "details from database";
		jsonObject.put("userDetails", userDetails); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
	  

	  @Path("/getuser/{email}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/test1
	  @GET
	  @Produces("application/json; charset=UTF-8")
	  public Response getUser(@PathParam("email") String email) throws ClassNotFoundException, SQLException, JSONException{
	        DBConnection mydb = DBConnection.getDbCon();
        	JSONObject jsonObject = new JSONObject();
	        if(mydb!=null){
                String sql = "SELECT * FROM user WHERE email = ?";
                String [] parms = {email};
	            ResultSet rs = mydb.query(sql,parms);
	            if(rs.next()){
	        		String first_name = rs.getString("first_name");
	        		String last_name = rs.getString("last_name");
	        		String user_email = rs.getString("email");
	        		String password = rs.getString("password");
	        		String institute = rs.getString("institute");
	        		jsonObject.put("first_name", first_name); 
	        		jsonObject.put("last_name", last_name); 
	        		jsonObject.put("user_email", user_email); 
	        		jsonObject.put("password", password); 
	        		jsonObject.put("institute", institute); 
	        		jsonObject.put("success", true);

	            }else {
	            	jsonObject.put("success", false);
	            }
	        }else{
            	jsonObject.put("success", false);
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



}
