package com.apibatmap.restjersey;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.apibatmap.restjersey.dao.UserDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/userservice")
public class User {

	  @Path("/search/{searchBy}/{searchTerm}")
	  @GET
	  @Produces("application/json")
	  public Response search(
              @PathParam("searchBy") String searchBy,
              @PathParam("searchTerm") String searchTerm ) throws JSONException, SQLException, ClassNotFoundException {

          UserDao ud  = new UserDao();
          JSONObject jsonObject = ud.search(searchBy,searchTerm);



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
          ResultSet rs = mydb.query(sql, parms);
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
    public Response signin(String st) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
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
    public Response signup(String st) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
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
    public Response updateProfile(String st) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
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
    public Response manageAccountPre(){
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
    @Path("/forgotpassstepone")
    @Consumes("application/json")
    @Produces("application/json")
    public Response forgotPassStepOne(String st) throws MessagingException, SQLException, ClassNotFoundException {
        JSONObject jsonReq = new JSONObject(st);
        JSONObject jsonObject = new JSONObject();

        UserDao ud = new UserDao();
        boolean flag = ud.forgotPassStepOne(jsonReq);
        if(flag){
            jsonObject.put("flag",true);
            boolean se = ud.forgotpassEmail();
            if(se){
                jsonObject.put("se",true);
            }else {
                jsonObject.put("se",false);
            }
        }else{
            jsonObject.put("flag",false);
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
    @Path("/forgotpassstepone")
    @Consumes("*/*")
    public Response forgotPassStepOnePre(){
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
    @Path("/forgotpasssteptwo")
    @Consumes("application/json")
    @Produces("application/json")
    public Response forgotPassStepTwo(String st) throws MessagingException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        JSONObject jsonReq = new JSONObject(st);
        UserDao ud = new UserDao();
        JSONObject jsonObject = ud.forgotPassStepTwo(jsonReq);

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
    @Path("/forgotpasssteptwo")
    @Consumes("*/*")
    public Response forgotPassStepTwoPre(){
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
