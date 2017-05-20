package jaxrs.service;

import io.swagger.jaxrs.Reader;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;

public class ApiDefinition {

	public static final String SCHEME = "api_key";

	public void afterScan(final Reader reader, final Swagger swagger) {
		swagger.securityDefinition(SCHEME, new ApiKeyAuthDefinition(SCHEME, In.HEADER));
	}

}
