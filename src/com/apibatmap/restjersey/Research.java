package com.apibatmap.restjersey;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.apibatmap.restjersey.dao.ResearchDao;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/researchservice")
public class Research {

	@Path("/search/{searchTerm}")
	@GET
	@Produces("application/json")
	public Response searchResearch(@PathParam("searchTerm") String searchTerm) throws JSONException, SQLException, ClassNotFoundException {
		ResearchDao rd = new ResearchDao();
		JSONObject jsonObject = rd.searchResearch(searchTerm);
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
     * getting research by giving research id
     * @param research_id
     * @return
     * @throws JSONException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
	@Path("research/{research_id}")
	@GET
	@Produces("application/json")
	public Response getResearchById(@PathParam("research_id") String research_id) throws JSONException, SQLException, ClassNotFoundException {
        ResearchDao rd = new ResearchDao();
		JSONObject jsonObject = rd.getResearchById(research_id);
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
     * getting all the research data
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
	@Path("/getall")
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


    @Path("research/{research_id}/comments")
    @GET
    @Produces("application/json")
    public Response getCommentsByResearch(@PathParam("research_id") String research_id) throws SQLException, ClassNotFoundException {
        ResearchDao rd = new ResearchDao();
        JSONObject jsonObject = rd.getCommentsByResId(research_id);
        if(jsonObject.getJSONArray("commentsList").length()==0){
            jsonObject.put("flag",false);
        }else {
            jsonObject.put("flag",true);
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

    @Path("/research/{research_id}/replies")
    @GET
    @Produces("application/json")
    public Response getRepliesByResearch(@PathParam("research_id") String research_id) throws SQLException, ClassNotFoundException {
        ResearchDao rd = new ResearchDao();
        JSONObject jsonObject =  rd.getRepliesByResearch(research_id);
        if(jsonObject.getJSONArray("repliesList").length()==0){
            jsonObject.put("flag",false);
        }else {
            jsonObject.put("flag",true);
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
     * addding new research data
     * @param st
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
	@POST
	@Path("/addnew")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addNewResearch(String st) throws SQLException, ClassNotFoundException {
		JSONObject jsonReq = new JSONObject(st);
		ResearchDao rd = new ResearchDao();
		JSONObject jsonObject = rd.addNewResearch(jsonReq);

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
	@Path("/addnew")
	@Consumes("*/*")
	public Response addNewResearchPre(){
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
     * adding new comment for a research
     * @return
     */

	@POST
    @Path("/research/comment")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addCommentForResearch(String st) throws SQLException, ClassNotFoundException {
		JSONObject jsonReq = new JSONObject(st);
		ResearchDao rd = new ResearchDao();
		boolean flag  = rd.addCommentForResearch(jsonReq);
        JSONObject jsonObject = new JSONObject();
		if(flag){
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
    @Path("/research/comment")
    @Consumes("*/*")
    public Response addCommentForResearchPre(){
        return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600")
                .build();
    }

    @OPTIONS
    @Path("/")
    @Consumes("*/*")
    public Response addReplyForCommentPre(){
        return Response
				.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, authorization, Content-Type, X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600")
                .build();
    }

	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/D:/batmapAPI_host/";

	/**
	 * Upload a File
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@POST
	@Path("/addnewupload")
	@Consumes("multipart/form-data")
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws ClassNotFoundException, SQLException {

		DBConnection mydb = new DBConnection();
		String file_name = "error";
		String sql = "SELECT research_id FROM research ORDER BY research_id DESC LIMIT 0 , 1";
		String[] parms = {};
		ResultSet rs = mydb.query(sql,parms);
		if(rs.next()){
			file_name = rs.getString("research_id");
			System.out.println(file_name);
		}

		String filePath = SERVER_UPLOAD_LOCATION_FOLDER	+ file_name + ".pdf";

		// save the file to the server
		saveFile(fileInputStream, filePath);

		String sql2 = "UPDATE research SET file_path = ? WHERE research_id = ?";
		String[] parms2 = {filePath,file_name};
		mydb.update(sql2, parms2);

		String output = "New research recorded successfully.";

		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600")
				.entity(output).build();

	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
						  String serverLocation) {

		try {
			OutputStream outputStream;
			int read = 0;
			byte[] bytes = new byte[1024];

			outputStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@OPTIONS
	@Path("/addnewupload")
	@Consumes("*/*")
	public Response uploadFilePre(){
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
