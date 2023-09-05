
package com.bernardomg.configuration.test.source.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.configuration.source.PersistentConfigurationSource;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentConfigurationSource - Errors")
public class PersistentConfigurationSourceErrorIT {

    @Autowired
    private PersistentConfigurationSource source;

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Getting the float for a string value throws an exception")
    @Sql({ "/db/queries/configuration/string.sql" })
    void testGetFloat_String() {
        final ThrowingCallable execution;

        execution = () -> source.getFloat("key");

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(NumberFormatException.class);
    }

}
