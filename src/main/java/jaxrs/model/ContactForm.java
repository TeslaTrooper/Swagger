package jaxrs.model;

import jaxrs.model.beans.ContactBean;

import org.glassfish.jersey.media.multipart.FormDataParam;

public class ContactForm {
	@FormDataParam("body")
	private ContactBean contact;

	public ContactBean getContact() {
		return contact;
	}

	public void setContact(final ContactBean contact) {
		this.contact = contact;
	}

}
