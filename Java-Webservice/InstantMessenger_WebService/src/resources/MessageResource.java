package resources;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.DAO_Message;

/**
 * 
 * @author Krammer & Gerges
 * This is the resource for the messages
 */
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * This method creates an message
	 * @param newMessage
	 * @return
	 */
	
	@POST
	@Path("/createmessage")
	public Response createMessage(JsonObject newMessage) {

		DAO_Message dao;

		try {
			dao = DAO_Message.getDaoMessage();
			dao.insertMessage(newMessage.getString("messageContent"), newMessage.getString("receiver"),
					newMessage.getString("sender"), new Date());
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}

		return Response.ok(newMessage, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Headers", "Content-Type")
		.header("Access-Control-Allow-Methods", "POST").build();
	}
}
