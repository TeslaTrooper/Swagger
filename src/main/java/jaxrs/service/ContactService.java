package jaxrs.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.ResponseHeader;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Swagger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jaxrs.model.ContactHandler;
import jaxrs.model.beans.ContactBean;
import jaxrs.model.beans.ContactMinimal;

@Path("contacts")
@Api(value = "Contact operations", protocols = "ZZZ")
public class ContactService implements ReaderListener {

	private final ApiDefinition apiDefinition;
	private final ContactHandler contactHandler;

	public ContactService() {
		apiDefinition = new ApiDefinition();
		contactHandler = new ContactHandler();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
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
	public Response createNewContact(@ApiParam(value = "Contact information as json format",
	required = true, name = "test") final ContactBean contact) {
		final String contactId = contactHandler.createNewContact(contact);

		if (contactId == null) {
			return Response.status(405).build();
		}

		return Response.status(200).header("contact-id", contactId).build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetches an existing contact")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given contact was fetched successfully.",
					response = ContactBean.class),
					@ApiResponse(code = 404, message = "Contact does not exist!") })
	public Response getContact(@ApiParam(value = "The id for the requested contact.",
	required = true) @PathParam("id") final String contactId) {
		final ContactBean contact = contactHandler.getContact(contactId);

		if (contact == null) {
			return Response.status(404).build();
		}

		return Response.status(200).entity(contact).build();
	}

	@PUT
	@Path("{id}/{attrib}/{newValue}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Alters an existing contact",
	notes = "The value for attribute has to be a valid attribue from contacts.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given contact was altered successfully."),
			@ApiResponse(code = 405, message = "Invalid input for either attribute, or value."),
			@ApiResponse(code = 404, message = "Contact does not exist!") })
	public Response alterContact(@ApiParam(value = "The id for the requested contact.",
	required = true, name = "id") @PathParam("id") final String contactId,
	@ApiParam(value = "The attribute to change for this contact.", required = true,
	name = "attrib") @PathParam("attrib") final String attrib, @ApiParam(
			value = "The new value for the given attribute.", required = true,
			name = "newValue") @PathParam("newValue") final String value) {
		final int result = contactHandler.alterContact(contactId, attrib, value);

		if (result == -1) {
			return Response.status(405).build();
		}
		if (result == 0) {
			return Response.status(404).build();
		}

		return Response.status(200).build();
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Deletes an existing contact", authorizations = { @Authorization(
			value = ApiDefinition.SCHEME) })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Given contact was deleted successfully."),
			@ApiResponse(code = 404, message = "Contact does not exist."),
			@ApiResponse(code = 403, message = "Wrong key for api_key.") })
	public Response deleteContacta(@ApiParam(value = "The id for the contact to delete.",
	required = true) @PathParam("id") final String contactId,
	@Context final HttpServletRequest r) {
		final boolean result = contactHandler.deleteContact(contactId);
		final String header = r.getHeader("api_key");

		if ((header == null) || !header.equals("special_key")) {
			return Response.status(403).build();
		}

		return result ? Response.status(200).build() : Response.status(404).build();
	}

	@GET
	@Path("/list")
	@ApiOperation(value = "Fetches all existing contacts")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contacts were fetched successfully.",
					response = ContactMinimal.class, responseContainer = "List"),
					@ApiResponse(code = 404, message = "No contacts found.") })
	public Response getList() {
		final List<ContactMinimal> contacts = contactHandler.getList();

		return contacts != null ? Response.status(200).entity(contacts).build() : Response.status(
				404).build();
	}

	@Override
	public void beforeScan(final Reader reader, final Swagger swagger) {
	}

	@Override
	public void afterScan(final Reader reader, final Swagger swagger) {
		apiDefinition.afterScan(reader, swagger);
	}

}
