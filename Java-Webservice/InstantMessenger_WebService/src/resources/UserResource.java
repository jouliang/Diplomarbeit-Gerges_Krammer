package resources;

import java.io.IOException;
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
 
/**
 * 
 * @author Krammer & Gerges
 * This is the ressource for the users
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * This method creates an user
	 * @param newUser
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/createuser")
	public Response createUser(JsonObject newUser) throws ParseException {
		String username = newUser.getString("username");
		boolean userNotExists = true;

		try {
			DAO_User dao = DAO_User.getDaoUser();

			userNotExists = isUserExists(username);

			if (userNotExists) {
				dao.insertUser(username, newUser.getString("password"));
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}

		return Response.ok(userNotExists, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * This method checks if an user is existing
	 * @param accountname
	 * @param password
	 * @return
	 */
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
			e.printStackTrace();
		}

		for (User u : allUsers) {
			if (u.getUsername().equals(accountname) && u.getPassword().equals(password)) {
				success = true;
			}
		}

		return Response.ok(success, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * This method gets alls usernames
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
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

	/**
	 * This method returns all users with their messages
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws ParseException
	 */
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

	/**
	 * This method updates the password of an user
	 */
	@POST
	@Path("/updatepassword")
	public Response updatePassword(JsonObject userWithNewPassword) {

		String username = userWithNewPassword.getString("username");
		String oldPassword = userWithNewPassword.getString("oldPassword");
		boolean isPasswordTrue = false;

		try {
			DAO_User dao = DAO_User.getDaoUser();
			isPasswordTrue = isPasswordExisting(username, oldPassword);
			if (isPasswordTrue) {
				dao.updatePassword(username, userWithNewPassword.getString("password"));
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}

		return Response.ok(isPasswordTrue, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * This method updates an username
	 * @param userWithNewName
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("/updateusername")
	public Response updateUsername(JsonObject userWithNewName) throws ParseException {

		String oldUsername = userWithNewName.getString("oldUsername");
		String newUsername = userWithNewName.getString("newUsername");
		boolean userNotExists = true;

		try {
			DAO_User daoUser = DAO_User.getDaoUser();
			DAO_Group daoGroup = DAO_Group.getDaoGroup();
			DAO_Message daoMessage = DAO_Message.getDaoMessage();

			userNotExists = isUserExists(newUsername);

			if (userNotExists) {
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
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}

		return Response.ok(userNotExists, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * This method deletes an user
	 * @param user
	 * @return
	 */
	@DELETE
	@Path("/deleteuser")
	public Response deleteUser(JsonObject user) {

		String username = user.getString("username");
		String password = user.getString("password");
		boolean isPasswordTrue = false;

		try {
			DAO_User daoUser = DAO_User.getDaoUser();
			DAO_Group daoGroup = DAO_Group.getDaoGroup();

			isPasswordTrue = isPasswordExisting(username, password);

			if (isPasswordTrue) {
				HashSet<Group> allGroups = daoGroup.getAllGroups();

				for (Group g : allGroups) {
					ArrayList<String> members = g.getGroupMembers();

					if (members.contains(username)) {
						int indexOfMemberInList = members.indexOf(username);
						daoGroup.deleteGroupmember(g.getGroupName(), members, indexOfMemberInList, username);
					}
				}

				daoUser.deleteUser(username);
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
		return Response.ok(isPasswordTrue, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * This method checks if a given user is already exists in the database
	 * 
	 * @param uName
	 * @return
	 */
	public boolean isUserExists(String uName) {
		boolean userNotExists = true;
		DAO_User dao;
		try {
			dao = DAO_User.getDaoUser();
			HashSet<User> allUsers = dao.getAllUsers();

			for (User u : allUsers) {
				if (u.getUsername().equals(uName)) {
					userNotExists = false;
					break;
				}
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return userNotExists;
	}
	
	/**
	 * This method checks if a given password is existing
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean isPasswordExisting(String username, String password) {
		boolean isPasswordTrue = false;
		try {
			DAO_User dao = DAO_User.getDaoUser();
			HashSet<User> allUsers = dao.getAllUsers();

			for (User u : allUsers) {
				if (u.getUsername().equals(username)) {
					if (u.getPassword().equals(password)) {
						isPasswordTrue = true;
					}
				}
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return isPasswordTrue;
	}
}
