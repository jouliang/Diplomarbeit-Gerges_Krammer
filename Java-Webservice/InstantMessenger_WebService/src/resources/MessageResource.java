package resources;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import dao.DAO_Message;
import java.text.ParseException;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import data.*;

// maps the resource to the URL 
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// http://localhost:8080/InstantMessenger_WebService/rest/messages/company
	@GET
	@Path("/company")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCompany() {
		return "works";
	}

	@GET
	@Path("/messages")
	public Response getEntry() throws IOException, InterruptedException, ExecutionException, ParseException {
		HashSet<Message> allMsgs = new HashSet<Message>();

		DAO_Message dao = DAO_Message.getDaoMessage();
		allMsgs = dao.getAllMessages();

		return Response.ok(allMsgs, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/createmessage")
	public Response create(JsonObject newMessage) {

		DAO_Message dao;

		try {
			dao = DAO_Message.getDaoMessage();
			dao.insertMessage(newMessage.getString("messageContent"), newMessage.getString("receiver"),
					newMessage.getString("sender"), new Date());
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(newMessage, MediaType.APPLICATION_JSON).build();
	}
}
