package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import controller.DatabaseController;
import dao.DAO_User;

import java.text.ParseException;
import java.util.List;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.*;


// maps the resource to the URL 
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// http://localhost:8080/InstantMessenger_WebService/rest/users/company
	@GET
	@Path("/company")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCompany() {
		return "works";
	}

	@GET
	@Path("/users")
	public Response getEntry() throws IOException, InterruptedException, ExecutionException {
		DAO_User dao = DAO_User.getDaoUser();
		HashSet<User> allUsers = dao.getAllUsers();
		
		return Response.ok(allUsers, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/createuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEntry(JsonObject data) throws ParseException {
		if (!data.containsKey("username") || !data.containsKey("password")) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Username and password required").build();
		}

		String uname = data.getString("username");
		String pw = data.getString("password");
		boolean ilogin = data.getBoolean("initialLogin");
		
		if (uname.length() <= 0) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Username can not be empty").build();
		}

		User u = new User(uname,pw,ilogin);

		try {
		//	User created = EntryDAOImpl.getInstance().createEntry(e);

			//return Response.ok(created, MediaType.APPLICATION_JSON).build();
			return Response.ok(u, MediaType.APPLICATION_JSON).build();
		} catch (Exception e1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Creating User failed. " + e1.getMessage()).build();
		}
	}


}
