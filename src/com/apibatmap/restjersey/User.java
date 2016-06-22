package com.apibatmap.restjersey;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.apibatmap.restjersey.dao.UserDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/userservice")
public class User {

	  @Path("/test")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/test
	  @GET
	  @Produces("application/json")
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
	  @Produces("application/json")
	  public Response getDetails(@PathParam("username") String username) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String userDetails = "details from database";
		jsonObject.put("userDetails", userDetails); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

    /**
     * get user details using email functionality
     * @param email
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws JSONException
     */

	  @Path("/getuser/{email}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP_J2EE_API/userservice/test1
	  @GET
	  @Produces("application/json")
	  public Response getUser(@PathParam("email") String email) throws ClassNotFoundException, SQLException, JSONException{
          DBConnection mydb = new DBConnection();
          JSONObject jsonObject = new JSONObject();

          String sql = "SELECT * FROM user WHERE email = ?";
          String [] parms = {email};
          ResultSet rs = mydb.query(sql,parms);
          if(rs.next()){
              String first_name = rs.getString("first_name");
              String last_name = rs.getString("last_name");
              String user_email = rs.getString("email");
              String password = rs.getString("password");
              String institute = rs.getString("institute");
              String user_type = rs.getString("user_type");
              jsonObject.put("first_name", first_name);
              jsonObject.put("last_name", last_name);
              jsonObject.put("email", user_email);
              jsonObject.put("password", password);
              jsonObject.put("institute", institute);
              jsonObject.put("user_type", user_type);
              jsonObject.put("getdetails", true);
          }else {
              jsonObject.put("getdetails", false);
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



    /**
     * Sigin in functionality
     * @param st
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */



    @POST
    @Path("/signin")
    @Consumes("application/json")
    @Produces("application/json")
    public Response signin(String st) throws SQLException, ClassNotFoundException {
        System.out.println("sign in request received");
        JSONObject jsonReq = new JSONObject(st);
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.signin(jsonReq);
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
    @Path("/signin")
    @Consumes("*/*")
    public Response signinPre(){
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }


    /**
     * Signup functionality
     * @param st
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    @POST
    @Path("/signup")
    @Consumes("application/json")
    @Produces("application/json")
    public Response signup(String st) throws SQLException, ClassNotFoundException {
        System.out.println("sign up request received");
        JSONObject jsonReq = new JSONObject(st);
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.signup(jsonReq);
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
    @Path("/signup")
    @Consumes("*/*")
    public Response signupPre(){
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    /**
     * update profile details
     * @return
     */

    @POST
    @Path("/updateprofile")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateProfile(String st) throws SQLException, ClassNotFoundException {
        System.out.println("update profile request received");
        JSONObject jsonReq = new JSONObject(st);
        String password = jsonReq.getString("password");
        String confpassword = jsonReq.getString("confirmpassword");
        JSONObject jsonObject;
        if(password.trim().equals(confpassword.trim())){
            UserDao ud = new UserDao();
            jsonObject = ud.updateProfile(jsonReq);
        }else {
            jsonObject = new JSONObject();
            jsonObject.put("passwordNtEquals",true);
            jsonObject.put("updated",false);
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
    @Path("/updateprofile")
    @Consumes("*/*")
    public Response updateProfilePre(){
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    /**
     * get pending signup accounts list - admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    @Path("/getpendinglist")
    @GET
    @Produces("application/json")
    public Response getPendingAcc() throws SQLException, ClassNotFoundException {
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.getPendingList();
        if(jsonObject.getJSONArray("pendingList").length()==0){
            jsonObject.put("pending",false);
        }else {
            jsonObject.put("pending",true);
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

    @Path("/getallaccountslist")
    @GET
    @Produces("application/json")
    public Response getAllAcc() throws SQLException, ClassNotFoundException {
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.getAllAccounts();
        if(jsonObject.getJSONArray("allAccountList").length()==0){
            jsonObject.put("getAll",false);
        }else {
            jsonObject.put("getAll",true);
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

    /**
     * accept signup request
     * @param st
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @POST
    @Path("/manageaccount")
    @Consumes("application/json")
    @Produces("application/json")
    public Response manageAccount(String st) throws SQLException, ClassNotFoundException {
        JSONObject jsonReq = new JSONObject(st);
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.manageAccount(jsonReq);

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
    @Path("/manageaccount")
    @Consumes("*/*")
    public Response acceptSignupReqPre(){
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }


    /**
     * -------------------------------------------------------------------
     */

    @POST
    @Path("/delete")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public void deleteUser(){

    }

    @POST
    @Path("/post")
    @Consumes("application/json")
    @Produces("application/json")
    public Response test(String st) {

        JSONObject jsonreq = new JSONObject(st);
        JSONObject jsonObject = new JSONObject();
        System.out.println("accessed");
        jsonObject.put("access", "ok");
        jsonObject.put("namefromreq", jsonreq.getString("name"));
        jsonObject.put("pwfromreq", jsonreq.getInt("password"));


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
    @Path("/post")
    @Consumes("*/*")
    public Response responseForPreflight(){
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
