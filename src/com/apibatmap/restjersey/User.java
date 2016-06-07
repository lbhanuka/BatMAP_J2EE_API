package com.apibatmap.restjersey;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;



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
              jsonObject.put("first_name", first_name);
              jsonObject.put("last_name", last_name);
              jsonObject.put("user_email", user_email);
              jsonObject.put("password", password);
              jsonObject.put("institute", institute);
              jsonObject.put("success", true);
          }else {
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
        String email = jsonReq.getString("email");
        String password = jsonReq.getString("password");
        JSONObject jsonObject = new JSONObject();
        DBConnection logindb = new DBConnection();
        String sql2 = "SELECT * FROM user WHERE email = ? AND password = ?";
        String[] parms = {email,password};
        ResultSet rs = logindb.query(sql2,parms);
        if(rs.next()){
            jsonObject.put("signin", true);
        }else {
            jsonObject.put("signin", false);
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
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response signup(String st) throws SQLException, ClassNotFoundException {
        System.out.println("sign up request received");

        JSONObject jsonReq = new JSONObject(st);
        String email = jsonReq.getString("email");
        String password = jsonReq.getString("password");
        String confpassword = jsonReq.getString("confpassword");
        String first_name = jsonReq.getString("first_name");
        String last_name = jsonReq.getString("last_name");
        String institute = jsonReq.getString("institute");

        DBConnection checkUser = new DBConnection();
        String sql1 = "SELECT * FROM user WHERE email = ?";
        String parms1[] = {email};
        ResultSet rs1 = checkUser.query(sql1, parms1);
        JSONObject jsonObject = new JSONObject();
        if(rs1.next()){
            jsonObject.put("userExists", true);
        }else if(password.trim().equals(confpassword.trim())) {
            jsonObject.put("userExists", false);
            DBConnection registerDB = new DBConnection();
            String sql2 = "INSERT INTO user(email,password,first_name,last_name,institute) VALUES (?,?,?,?,?)";
            String[] parms2 = {email,password,first_name,last_name,institute};
            int rs2 = registerDB.insert(sql2, parms2);
            if(rs2==0){
                jsonObject.put("signup", false);
            }else  if (rs2>0){
                jsonObject.put("signup", true);
            }

        }else {
            jsonObject.put("passwordNotEquals",true);
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

    @POST
    @Path("/update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response updateUser(){
        JSONObject jsonObject = new JSONObject();

        String sql = "UPDATE user SET ";
        String[] parms = {};
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
