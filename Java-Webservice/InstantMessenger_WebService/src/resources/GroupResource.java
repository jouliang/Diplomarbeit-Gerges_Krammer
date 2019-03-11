package resources;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import javax.json.JsonArray;
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

import dao.DAO_Group;
import dao.DAO_Message;
import dao.DAO_User;
import data.Group;
import data.Message;
import data.User;

/**
 * 
 * @author Krammer & Gerges Here are the routes for the GroupRessource
 */
@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * This method creates an group
	 * 
	 * @param newGroup
	 * @return
	 */
	@POST
	@Path("/creategroup")
	public Response createGroup(JsonObject newGroup) {
		ArrayList<String> members = new ArrayList<String>();
		JsonArray jsonmember = newGroup.getJsonArray("groupMembers");
		if (jsonmember != null) {
			int len = jsonmember.size();
			for (int i = 0; i < len; i++) {
				members.add(jsonmember.get(i).toString());
			}
		}

		try {
			DAO_Group dao = DAO_Group.getDaoGroup();
			dao.insertGroup(newGroup.getString("groupName"), members);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Error();
		}

		return Response.ok(newGroup, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * This method gets all groups with their messages
	 * 
	 * @param uname
	 * @return
	 */
	@GET
	@Path("/gwithmforuser/{uname}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getGroupsWithMessagesForUser(@PathParam("uname") String uname) {
		HashMap<String, ArrayList<Message>> res = new HashMap<String, ArrayList<Message>>();

		HashSet<User> allUsers = new HashSet<User>();

		DAO_User daoU;

		try {
			daoU = DAO_User.getDaoUser();
			allUsers = daoU.getAllUsers();

			User theOne = null;

			for (User u : allUsers) {
				if (u.getUsername().equals(uname)) {
					theOne = u;
				}
			}

			HashSet<Group> allGroups = new HashSet<Group>();

			DAO_Group daoG = DAO_Group.getDaoGroup();
			allGroups = daoG.getAllGroups();

			HashSet<Message> allMsgs = new HashSet<Message>();

			DAO_Message daoM = DAO_Message.getDaoMessage();
			allMsgs = daoM.getAllMessages();

			for (Group g : allGroups) {
				if (g.getGroupMembers().contains(theOne.getUsername())) {
					ArrayList<Message> msgsforgroup = new ArrayList<Message>();
					for (Message m : allMsgs) {
						if (m.getReceiver().equals(g.getGroupName())) {
							msgsforgroup.add(m);
						}
					}
					res.put(g.getGroupName(), msgsforgroup);
				}
			}
		} catch (IOException | InterruptedException | ExecutionException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(res, MediaType.APPLICATION_JSON).build();
	}
}
