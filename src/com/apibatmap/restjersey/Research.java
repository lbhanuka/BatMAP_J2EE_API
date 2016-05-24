package com.apibatmap.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/researchservice")
public class Research {
	  
	  @Path("search/{searchString}")
	  @GET
	  @Produces("application/json")
	  public static Response getFivePosts(@PathParam("searchString") String searchString) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		String searchResult = "Search result from DB";
		jsonObject.put("searchResult", searchResult); 

		String result = "@Produces(\"application/json\") \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }

}
