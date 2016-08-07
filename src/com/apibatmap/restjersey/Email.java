package com.apibatmap.restjersey;

/**
 * Created by lahiru on 8/6/2016.
 */

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.apibatmap.restjersey.dao.EmailDao;
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


@Path("/emailservice")
public class Email {
    private static final String sysadmin = "batmap.uoc.official@gmail.com" ;

    @POST
    @Path("/contactus")
    @Consumes("application/json")
    @Produces("application/json")
    public Response contactUs(String st) throws MessagingException {
        JSONObject jsonReq = new JSONObject(st);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonMsg = new JSONObject();

        String cn = jsonReq.getString("contact_name");
        String ce = jsonReq.getString("contact_email");
        String cp = jsonReq.getString("contact_phone");
        String cm = jsonReq.getString("contact_message");


        jsonMsg.put("recipient",sysadmin);
        jsonMsg.put("subject", cn+" wants to contact you");
        jsonMsg.put("body", cn+"'s details are below.<br><strong>Name: </strong>"+cn
                +"<br><strong>Email: </strong>"+ ce
                +"<br><strong>Phone number: </strong>"+ cp
                +"<br><strong>Message: </strong>"+cm+"<br>"
        );

        EmailDao ed = new EmailDao(jsonMsg);

        if(ed.sendMail()){
            jsonObject.put("flag",true);
        }else {
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
    @Path("/contactus")
    @Consumes("*/*")
    public Response contactUsPre(){
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
