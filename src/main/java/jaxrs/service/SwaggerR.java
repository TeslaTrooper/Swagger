package jaxrs.service;

import io.swagger.jaxrs.ext.AbstractSwaggerExtension;
import io.swagger.jaxrs.ext.SwaggerExtension;
import io.swagger.models.parameters.FormParameter;
import io.swagger.models.parameters.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.glassfish.jersey.media.multipart.FormDataParam;

public class SwaggerR extends AbstractSwaggerExtension implements SwaggerExtension {

	@Override
	public List<Parameter> extractParameters(final List<Annotation> annotations, final Type type,
			final Set<Type> typesToSkip, final Iterator<SwaggerExtension> chain) {
		final List<Parameter> output = new ArrayList<>();

		if (shouldIgnoreClass(type.getClass())) {
			return new ArrayList<>();
		}

		for (final Annotation annotation : annotations) {
			if (annotation instanceof FormDataParam) {
				final FormDataParam fd = (FormDataParam) annotation;
				if (java.io.InputStream.class.equals(type)) {
					final Parameter param = new FormParameter().type("file").name(fd.value());
					output.add(param);
					return output;
				}
			}
		}
		if (chain.hasNext()) {
			return chain.next().extractParameters(annotations, type, typesToSkip, chain);
		}
		return null;
	}
}
