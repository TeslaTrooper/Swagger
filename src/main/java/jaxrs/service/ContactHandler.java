package jaxrs.service;

import java.util.UUID;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import jaxrs.model.Contact;

public class ContactHandler {

	public Response createNewContact(final Contact contact) {
		if ((contact.getEmail() == null) || (contact.getId() == null)
				|| (contact.getLastName() == null) || (contact.getName() == null)
				|| (contact.getPhoneNumber() == null)) {
			return Response.status(405).build();
		}

		final String id = UUID.randomUUID().toString();

		return Response.status(200).header("contact-id", id).build();
	}

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
