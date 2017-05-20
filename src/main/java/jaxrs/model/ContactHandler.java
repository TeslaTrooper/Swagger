package jaxrs.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import jaxrs.model.beans.ContactBean;
import jaxrs.model.beans.ContactMinimal;
import jaxrs.persistence.DBConnector;

public class ContactHandler {

	public final DBConnector db;

	public ContactHandler() {
		db = new DBConnector();

		db.createTable();
	}

	public String createNewContact(final ContactBean contact) {
		if ((contact == null) || (contact.getEmail() == null) || (contact.getLastName() == null)
				|| (contact.getName() == null) || (contact.getPhoneNumber() == null)) {
			return null;
		}

		final String id = UUID.randomUUID().toString();

		final ContactBean c = new ContactBean();
		c.setName(contact.getName());
		c.setLastName(contact.getLastName());
		c.setPhoneNumber(contact.getPhoneNumber());
		c.setEmail(contact.getEmail());

		return db.insert(id, c) > 0 ? id : null;
	}

	public ContactBean getContact(final String contactId) {
		return db.selectContact(contactId);
	}

	public int alterContact(final String contactId, final String attrib, final String value) {
		Field f;
		try {
			f = ContactBean.class.getDeclaredField(attrib);
		} catch (final Exception e) {
			return -1;
		}

		return db.alterContact(contactId, f, value);
	}

	public boolean deleteContact(final String contactId) {
		return db.deleteContact(contactId) > 0;
	}

	public List<ContactMinimal> getList() {
		return db.getList();
	}
}
