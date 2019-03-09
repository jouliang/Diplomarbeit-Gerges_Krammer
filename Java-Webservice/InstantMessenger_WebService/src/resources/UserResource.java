package resources;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import dao.DAO_Group;
import dao.DAO_Message;
import dao.DAO_User;
import data.Group;
import data.Message;
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
	public Response createUser(JsonObject newUser) throws ParseException {

		try {
			DAO_User dao = DAO_User.getDaoUser();
			dao.insertUser(newUser.getString("username"), newUser.getString("password"));
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(newUser, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/loginuser/{accountname}/{password}")
	public Response checkUserLogin(@PathParam("accountname") String accountname,
			@PathParam("password") String password) {
		boolean success = false;

		HashSet<User> allUsers = null;
		try {
			DAO_User dao = DAO_User.getDaoUser();
			allUsers = dao.getAllUsers();
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (User u : allUsers) {
			if (u.getUsername().equals(accountname) && u.getPassword().equals(password)) {
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
	@Path("/usernameswmsgs")
	public Response getUsernamesWithMessages()
			throws IOException, InterruptedException, ExecutionException, ParseException {
		HashSet<User> allUsers = new HashSet<User>();
		HashSet<String> usernames = new HashSet<String>();
		HashMap<String, ArrayList<Message>> res = new HashMap<String, ArrayList<Message>>();

		DAO_User dao = DAO_User.getDaoUser();
		allUsers = dao.getAllUsers();

		DAO_Message daoM = DAO_Message.getDaoMessage();
		HashSet<Message> allMsgs = daoM.getAllMessages();

		for (User u : allUsers) {
			usernames.add(u.getUsername());
			ArrayList<Message> msgs = new ArrayList<Message>();
			for (Message m : allMsgs) {
				// Nachrichten, wo die gesuchte Gruppe Empfänger ist
				if (m.getSender().equals(u.getUsername())) {
					msgs.add(m);
				}
				if (m.getReceiver().equals(u.getUsername())) {
					msgs.add(m);
				}
			}
			res.put(u.getUsername(), msgs);
		}

		return Response.ok(res, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/updatepassword")
	public Response updatePassword(JsonObject userWithNewPassword) {

		try {
			DAO_User dao = DAO_User.getDaoUser();
			dao.updatePassword(userWithNewPassword.getString("username"), userWithNewPassword.getString("password"));
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(userWithNewPassword, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/updateusername")
	public Response updateUsername(JsonObject userWithNewName) throws ParseException {

		String oldUsername = userWithNewName.getString("oldUsername");
		String newUsername = userWithNewName.getString("newUsername");

		try {
			DAO_User daoUser = DAO_User.getDaoUser();
			DAO_Group daoGroup = DAO_Group.getDaoGroup();
			DAO_Message daoMessage = DAO_Message.getDaoMessage();

			HashSet<Group> allGroups = daoGroup.getAllGroups();
			ArrayList<String> groupMembers = new ArrayList<>();

			for (Group g : allGroups) {
				groupMembers = g.getGroupMembers();
				if (groupMembers.contains(oldUsername)) {
					int indexOfMemberInList = groupMembers.indexOf(oldUsername);
					daoGroup.updateGroupMember(g.getGroupName(), groupMembers, indexOfMemberInList, oldUsername,
							newUsername);
				}
			}

			HashSet<Message> allMessages = daoMessage.getAllMessages();

			for (Message m : allMessages) {
				if (m.getSender().equals(oldUsername)) {
					daoMessage.updateMessageSender(oldUsername, newUsername, m.getTransmittedTime());
				}

				if (m.getReceiver().equals(oldUsername)) {
					daoMessage.updateMessageReceiver(oldUsername, newUsername, m.getTransmittedTime());
				}
			}

			daoUser.updateUsername(oldUsername, newUsername);
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(userWithNewName, MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/deleteuser")
	public Response deleteUser(JsonObject user) {

		String username = user.getString("username");
		
		try {
			DAO_User daoUser = DAO_User.getDaoUser();
			DAO_Group daoGroup = DAO_Group.getDaoGroup();
			
			HashSet<Group> allGroups = daoGroup.getAllGroups();
			
			for(Group g : allGroups) {
				ArrayList<String> members = g.getGroupMembers();
				
				if(members.contains(username)) {
					int indexOfMemberInList = members.indexOf(username);
					daoGroup.deleteGroupmember(g.getGroupName(), members, indexOfMemberInList, username);
				}
			}
			
			daoUser.deleteUser(username);
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(user, MediaType.APPLICATION_JSON).build();
	}
}
