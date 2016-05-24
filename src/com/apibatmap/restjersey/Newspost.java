package com.apibatmap.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/newsservice")
public class Newspost {
	  
	  @Path("getfiveposts/{startingNumber}")
	  //use the link below to test if this works!
	  //http://localhost:8080/BatMAP/crunchify/newsservice/getfiveposts/1
	  @GET
	  @Produces("application/json")
	  public static Response getFivePosts(@PathParam("startingNumber") int startingNumber) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String newsPostDetails = "News post details from DB";
		jsonObject.put("newsPostDetails", newsPostDetails); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

}
