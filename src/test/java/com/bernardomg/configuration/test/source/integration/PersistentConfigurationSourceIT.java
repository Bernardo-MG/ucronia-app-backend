
package com.bernardomg.configuration.test.source.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.configuration.source.PersistentConfigurationSource;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentConfigurationSource")
public class PersistentConfigurationSourceIT {

    @Autowired
    private PersistentConfigurationSource source;

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Reading the value for an existing key returns its value")
    @Sql({ "/db/queries/configuration/single.sql" })
    void testGetConfiguration_Existing() {
        final String property;

        property = source.getConfiguration("key");

        Assertions.assertThat(property)
            .isEqualTo("value");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Reading the value for a not existing key returns nothing")
    void testGetConfiguration_NotExisting() {
        final String property;

        property = source.getConfiguration("key");

        Assertions.assertThat(property)
            .isEmpty();
    }

}
