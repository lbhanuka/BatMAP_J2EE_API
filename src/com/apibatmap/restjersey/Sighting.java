package com.apibatmap.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/sightingservice")
public class Sighting {
	  
	  @Path("getall/")
	  @GET
	  @Produces("application/json")
	  public static Response getFivePosts() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String allSightings = "Search result from DB";
		jsonObject.put("allSightings", allSightings); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

}
