package jaxrs.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import io.swagger.jaxrs.ext.SwaggerExtensions;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jaxrs.model.ContactHandler;
import jaxrs.model.beans.Contact;
import jaxrs.model.beans.ContactBean;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@Path("contacts")
@Api(tags = "Contact manager")
public class ContactService extends ResourceConfig {

	private ContactHandler contactHandler;

	public ContactService() {
		super(ContactService.class, MultiPartFeature.class);
		contactHandler = new ContactHandler();

		SwaggerExtensions.getExtensions().add(new SwaggerR());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a new contact",
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
	public Response createNewContact(final ContactBean contact) {
		return contactHandler.createNewContact(contact);
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetches an existing contact", response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given contact was fetched successfully.",
					response = Contact.class),
			@ApiResponse(code = 404, message = "Contact does not exist!") })
	public Response getContact(@ApiParam(value = "The id for the requested contact.",
			required = true) @PathParam("id") final String contactId) {
		return contactHandler.getContact(contactId);
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Alters an existing contact",
			notes = "The value for attribute has to be a valid value from contacts.")
	@ApiResponses(
			value = {
					@ApiResponse(
							code = 200,
							message = "Given contact was altered successfully.",
							responseHeaders = @ResponseHeader(
									name = "contact-id",
									description = "The corresponding id, which can be used to access this contact",
									response = String.class)),
					@ApiResponse(code = 405,
							message = "Invalid input for either attribute, or value."),
					@ApiResponse(code = 404, message = "Contact does not exist!") })
	public Response alterContact(
			@ApiParam(value = "The id for the requested contact.", required = true) @PathParam("id") final String contactId,
			@ApiParam(value = "The attribute to change for this contact.", required = true) @FormDataParam("body") final ContactBean contactForm) {
		return contactHandler.alterContact(contactForm);
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Deletes an existing contact")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given contact was deleted successfully."),
			@ApiResponse(code = 404, message = "Contact does not exist.") })
	public Response deleteContact(@ApiParam(value = "The id for the contact to delete.",
			required = true) @PathParam("id") final String contactId) {
		return contactHandler.deleteContact(contactId);
	}

}
