
package com.bernardomg.data.springframework;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

public final class SortingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public final Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String[]             propertiesParams;
        final Collection<Property> properties;

        propertiesParams = webRequest.getParameterValues("sort");
        if (propertiesParams != null) {
            properties = Arrays.stream(propertiesParams)
                .map(p -> p.split(","))
                .filter(p -> p.length >= 2)
                .map(this::toProperty)
                .toList();
        } else {
            properties = List.of();
        }

        return new Sorting(List.copyOf(properties));
    }

    @Override
    public final boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType()
            .equals(Sorting.class);
    }

    private final Property toProperty(final String[] parts) {
        String    propertyName;
        String    directionName;
        Direction direction;

        directionName = parts[1].trim()
            .toUpperCase();
        if ("desc".equalsIgnoreCase(directionName)) {
            direction = Direction.DESC;
        } else {
            direction = Direction.ASC;
        }

        propertyName = parts[0].trim();

        return new Property(propertyName, direction);
    }

}
