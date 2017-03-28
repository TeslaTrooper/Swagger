package jaxrs.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

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

	private ContactHandler contactHandler;

	public ContactService() {
		contactHandler = new ContactHandler();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a new contacts",
			notes = "Every field of Contacts has to be declared")
	@ApiResponses(
			value = {
					@ApiResponse(
							code = 200,
							message = "Given contact was created successfully.",
							responseHeaders = @ResponseHeader(
									name = "contact-id",
									description = "The corresponding id, which can be used to access this contact",
									response = String.class)),
					@ApiResponse(code = 405,
									message = "Given contact has one or more fields with value null.") })
	public Response createNewContact(final Contact contact) {
		return contactHandler.createNewContact(contact);
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContact(@PathParam("id") final String contactId) {
		return contactHandler.getContact(contactId);
	}

	public void alterContact(final String contactId) {
		contactHandler.alterContact(contactId);
	}

	public void deleteContact(final String contactId) {
		contactHandler.deleteContact(contactId);
	}

}
