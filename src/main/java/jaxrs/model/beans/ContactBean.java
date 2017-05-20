package jaxrs.model.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;

@ApiModel(value = "Contact",
		description = "Represents a structured description of all the data a contact can have.")
public class ContactBean {

	private String name;
	private String lastName;
	private String phoneNumber;
	private String email;

	@XmlElement(name = "name")
	@ApiModelProperty(value = "The name of this contact.", required = true, example = "John")
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@XmlElement(name = "lastName")
	@ApiModelProperty(value = "The last name of this contact.", required = true, example = "Doe")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name = "phoneNumber")
	@ApiModelProperty(value = "The phone number of this contact.", required = true)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@XmlElement(name = "email")
	@ApiModelProperty(value = "The email of this contact.", required = true,
			example = "johndoe@example.com")
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
