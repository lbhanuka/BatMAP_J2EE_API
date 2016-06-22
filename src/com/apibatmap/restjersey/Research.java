package com.apibatmap.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.apibatmap.restjersey.dao.ResearchDao;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

@Path("/researchservice")
public class Research {
	  
	@Path("search/{searchString}")
	@GET
	@Produces("application/json")
	public static Response getFivePosts(@PathParam("searchString") String searchString) throws JSONException {
		JSONObject jsonObject = new JSONObject();

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

	@Path("getall")
	@GET
	@Produces("application/json")
	public Response getAll() throws SQLException, ClassNotFoundException {

		ResearchDao rd = new ResearchDao();
		JSONObject jsonObject = rd.getAllResearch();
		if(jsonObject.getJSONArray("allResearchList").length()==0){
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





}
