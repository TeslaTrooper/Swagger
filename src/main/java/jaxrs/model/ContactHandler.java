package jaxrs.model;

import java.util.UUID;

import javax.ws.rs.core.Response;

import jaxrs.model.beans.Contact;
import jaxrs.model.beans.ContactBean;
import jaxrs.persistence.DBConnector;

public class ContactHandler {

	public final DBConnector db;

	public ContactHandler() {
		db = new DBConnector();

		db.createTable();
	}

	public Response createNewContact(final ContactBean contact) {
		if ((contact == null) || (contact.getEmail() == null) || (contact.getLastName() == null)
				|| (contact.getName() == null) || (contact.getPhoneNumber() == null)) {
			return Response.status(405).build();
		}

		final String id = UUID.randomUUID().toString();

		final Contact c = new Contact();
		c.setId(id);
		c.setName(contact.getName());
		c.setLastName(contact.getLastName());
		c.setPhoneNumber(contact.getPhoneNumber());
		c.setEmail(contact.getEmail());

		db.insert(c);

		return Response.status(200).header("contact-id", id).build();
	}

	public Response getContact(final String contactId) {
		final Contact c = db.selectContact(contactId);
		if (c == null) {
			return Response.status(404).build();
		}

		return Response.status(200).entity(c).build();
	}

	public Response alterContact(final ContactBean contact) {
		if (db.alterContact(contact) > 0) {
			return Response.status(200).build();
		}

		return Response.status(404).build();
	}

	public Response deleteContact(final String contactId) {
		if (db.deleteContact(contactId) > 0) {
			return Response.status(200).build();
		}

		return Response.status(404).build();
	}
}
