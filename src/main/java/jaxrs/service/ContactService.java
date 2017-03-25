package jaxrs.service;

import io.swagger.annotations.Api;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jaxrs.model.Contact;

@Path("contacts")
@Api(tags = "Contact manager")
public class ContactService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewContact(final Contact contact) {
		final String id = UUID.randomUUID().toString();

		return Response.status(200).header("contactId", id).build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContact(@PathParam("id") final String contactId) {
		final Contact c = new Contact();
		c.setId(contactId);
		c.setEmail("example@test.com");
		c.setName("Günther");
		c.setLastName("Müller");
		c.setPhoneNumber("+4912345678");

		return Response.status(200).entity(c).build();
	}

	public void alterContact(final String contactId) {

	}

	public void deleteContact(final String contactId) {

	}

}
