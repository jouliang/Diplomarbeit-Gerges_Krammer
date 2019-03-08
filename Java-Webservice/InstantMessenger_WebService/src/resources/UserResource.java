package resources;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.DAO_User;
import data.User;

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

		User u = new User(uname, pw, ilogin);

		try {
			// User created = EntryDAOImpl.getInstance().createEntry(e);

			// return Response.ok(created, MediaType.APPLICATION_JSON).build();
			return Response.ok(u, MediaType.APPLICATION_JSON).build();
		} catch (Exception e1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Creating User failed. " + e1.getMessage()).build();
		}
	}
	
	@GET
	@Path("/loginuser/{accountname}/{password}")
	public Response attemptLogin(@PathParam("accountname") String accountname, @PathParam("password") String password) throws IOException, InterruptedException, ExecutionException {
		boolean success = false;
		DAO_User dao = DAO_User.getDaoUser();
		HashSet<User> allUsers = dao.getAllUsers();
		
		for(User u: allUsers) {
			if(u.getUsername().equals(accountname) && u.getPassword().equals(password)) {
				success = true;
			}
		}
		
		return Response.ok(success, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/usernames")
	public Response getUsernames() throws IOException, InterruptedException, ExecutionException {
		HashSet<User> allUsers = new HashSet<User>();
		HashSet<String> usernames = new HashSet<String>();

		DAO_User dao = DAO_User.getDaoUser();
		allUsers = dao.getAllUsers();

		for (User u : allUsers) {
			usernames.add(u.getUsername());
		}

		return Response.ok(usernames, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/deleteaccount/{accountname}")
	public Response getEntriesForUser(@PathParam("accountname") String accountname)
			throws IOException, InterruptedException, ExecutionException {
		DAO_User dao = DAO_User.getDaoUser();
		// ohne try catch: Fehler bei DB Controller
		try {
			dao.deleteUser(accountname);
		} catch (Exception e) {

		}
		return Response.ok(accountname, MediaType.APPLICATION_JSON).build();
	}
}
