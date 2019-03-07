package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import dao.DAO_Group;
import dao.DAO_Message;
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
@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupResource {

	
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// http://localhost:8080/InstantMessenger_WebService/rest/groups/company
	@GET
	@Path("/company")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCompany() {
		return "works";
	}

	@GET
	@Path("/groups")
	public Response getEntry() throws IOException, InterruptedException, ExecutionException {
		HashSet<Group> allGroups = new HashSet<Group>();
		//allUsers.add(new User("name","sicher",true));
		//allUsers.add(new User("name2","sicher^2",false));
		
		DatabaseController dbinstance = DatabaseController.getDbController();
		DAO_Group dao = DAO_Group.getDaoGroup();
		allGroups = dao.getAllGroups();
		return Response.ok(allGroups, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/groupsforuser/{uname}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getEntriesForUser(@PathParam("uname") String uname) throws IOException, InterruptedException, ExecutionException, ParseException {
		HashMap<String, ArrayList<Message>> res = new HashMap<String, ArrayList<Message>>();
		DAO_User dao = DAO_User.getDaoUser();
		HashSet<User> allUsers = dao.getAllUsers();
		User theOne = null;
		
		for(User u: allUsers) {
			if(u.getUsername().equals(uname)) {
				theOne = u;
			}
		}
		
		HashSet<Group> allGroups = new HashSet<Group>();
		DatabaseController dbinstance = DatabaseController.getDbController();
		DAO_Group daoU = DAO_Group.getDaoGroup();
		allGroups = daoU.getAllGroups();
		
		
		DAO_Message daoM = DAO_Message.getDaoMessage();
		HashSet<Message> allMsgs = daoM.getAllMessages();
		
		//Gruppen durchsuchen
		for(Group g: allGroups) {
			//wenn der User in einer Gruppe vorkommt
			if(g.getGroupMembers().contains(theOne.getUsername())){
				ArrayList<Message> msgsforgroup = new ArrayList<Message>();
				for(Message m: allMsgs) {
					//Nachrichten, wo die gesuchte Gruppe Empfänger ist
					if(m.getReceiver().equals(g.getGroupName())) {
						msgsforgroup.add(m);
					}
				}
				res.put(g.getGroupName(), msgsforgroup);
			}
		}
		
		return Response.ok(res, MediaType.APPLICATION_JSON).build();
	}
}
