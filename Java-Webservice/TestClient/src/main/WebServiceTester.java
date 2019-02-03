package main;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import data.*;

/**
 * @author Gabriel
 * This class represents a Tester for a Webservice
 */
public class WebServiceTester {

	private Client client;
	private String REST_SERVICE_URL = "http://localhost:8080/InstantMessenger_WebService/rest/users/createuser";
	private static final String SUCCESS_RESULT = "<result>success</result>";
	private static final String PASS = "pass";
	private static final String FAIL = "fail";

	private void init() {
		this.client = ClientBuilder.newClient();
	}

	public static void main(String[] args) {
		WebServiceTester tester = new WebServiceTester();
		// initialize the tester
		tester.init();
	
		// test add User Web Service Method
		tester.testCreateUser();
	
	}

	// Test: Add User 
	private void testCreateUser() {
		Form form = new Form();
		form.param("username", "uservontest");
		form.param("password", "mitneuempassort");
		form.param("initialLogin", "true");
		
		System.out.println("Makging call for create user");
		String callResult = client.target(REST_SERVICE_URL).request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

		String result = PASS;
		if (!SUCCESS_RESULT.equals(callResult)) {
			result = FAIL;
		}

		System.out.println("Test case name: testCreateUser, Result: " + result);
	}

	
}