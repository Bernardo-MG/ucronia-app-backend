
package com.bernardomg.association.configuration.test.source.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.bernardomg.association.configuration.usecase.PersistentConfigurationSource;
import com.bernardomg.configuration.test.data.annotation.StringConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentConfigurationSource - Errors")
public class PersistentConfigurationSourceErrorIT {

    @Autowired
    private PersistentConfigurationSource source;

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Getting the float for a string value throws an exception")
    @StringConfiguration
    void testGetFloat_String() {
        final ThrowingCallable execution;

        execution = () -> source.getFloat("key");

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(NumberFormatException.class);
    }

}
