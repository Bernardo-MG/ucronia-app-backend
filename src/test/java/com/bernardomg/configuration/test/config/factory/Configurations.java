
package com.bernardomg.configuration.test.config.factory;

import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.domain.model.Configuration;

public final class Configurations {

    public static final Configuration amount() {
        return Configuration.builder()
            .withCode(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("1.0")
            .withType(ConfigurationConstants.NUMBER_TYPE)
            .build();
    }

    public static final Configuration first() {
        return Configuration.builder()
            .withCode("a")
            .withValue(ConfigurationConstants.STRING_VALUE)
            .withType(ConfigurationConstants.STRING_TYPE)
            .build();
    }

    public static final Configuration floatValue() {
        return Configuration.builder()
            .withCode(ConfigurationConstants.CODE)
            .withValue("10.1")
            .withType(ConfigurationConstants.NUMBER_TYPE)
            .build();
    }

    public static final Configuration intValue() {
        return Configuration.builder()
            .withCode(ConfigurationConstants.CODE)
            .withValue(ConfigurationConstants.NUMBER_VALUE)
            .withType(ConfigurationConstants.NUMBER_TYPE)
            .build();
    }

    public static final Configuration second() {
        return Configuration.builder()
            .withCode("b")
            .withValue(ConfigurationConstants.STRING_VALUE)
            .withType(ConfigurationConstants.STRING_TYPE)
            .build();
    }

    public static final Configuration stringValue() {
        return Configuration.builder()
            .withCode(ConfigurationConstants.CODE)
            .withValue(ConfigurationConstants.STRING_VALUE)
            .withType(ConfigurationConstants.STRING_TYPE)
            .build();
    }

    public static final Configuration valid() {
        return Configuration.builder()
            .withCode(ConfigurationConstants.CODE)
            .withValue(ConfigurationConstants.STRING_VALUE)
            .withType(ConfigurationConstants.STRING_TYPE)
            .build();
    }

    private Configurations() {
        super();
    }

}
