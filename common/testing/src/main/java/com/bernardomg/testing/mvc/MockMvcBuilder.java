
package com.bernardomg.testing.mvc;

import java.util.Map;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class MockMvcBuilder {

    public static MappingJackson2HttpMessageConverter javaTimeJsonConverter() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    public static StandaloneMockMvcBuilder standalone(final Object controller) {
        return standalone(controller, Map.of());
    }

    public static StandaloneMockMvcBuilder standalone(final Object controller,
            final Map<String, String> validationProperties) {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationPropertyMap(validationProperties);
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        return MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator);
    }

    private MockMvcBuilder() {}
}
