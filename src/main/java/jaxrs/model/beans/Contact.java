package jaxrs.model.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Contact")
public class Contact extends ContactBean {

	private String id;

	@ApiModelProperty(value = "Identifier for the contact.", required = true,
			example = "337cb0d8-8835-420c-8905-55a25ec19476")
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
