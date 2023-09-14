
package com.bernardomg.configuration.test.source.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.configuration.source.PersistentConfigurationSource;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentConfigurationSource")
public class PersistentConfigurationSourceIT {

    @Autowired
    private PersistentConfigurationSource source;

    @Test
    @DisplayName("Getting the float for an existing float returns its value as text")
    @Sql({ "/db/queries/configuration/float.sql" })
    void testGetFloat_Float() {
        final Float value;

        value = source.getFloat("key");

        Assertions.assertThat(value)
            .isEqualTo(10.1F);
    }

    @Test
    @DisplayName("Getting the float for an existing integer returns its value as text")
    @Sql({ "/db/queries/configuration/integer.sql" })
    void testGetFloat_Integer() {
        final Float value;

        value = source.getFloat("key");

        Assertions.assertThat(value)
            .isEqualTo(10F);
    }

    @Test
    @DisplayName("Getting the float for a not existing value returns zero")
    void testGetFloat_NotExisting() {
        final Float value;

        value = source.getFloat("key");

        Assertions.assertThat(value)
            .isEqualTo(0F);
    }

    @Test
    @DisplayName("Getting the string for an existing integer returns its value as text")
    @Sql({ "/db/queries/configuration/integer.sql" })
    void testGetString_Integer() {
        final String value;

        value = source.getString("key");

        Assertions.assertThat(value)
            .isEqualTo("10");
    }

    @Test
    @DisplayName("Getting the string for a not existing text returns an empty string")
    void testGetString_NotExisting() {
        final String value;

        value = source.getString("key");

        Assertions.assertThat(value)
            .isEmpty();
    }

    @Test
    @DisplayName("Getting the string for an existing text returns its value")
    @Sql({ "/db/queries/configuration/string.sql" })
    void testGetString_String() {
        final String value;

        value = source.getString("key");

        Assertions.assertThat(value)
            .isEqualTo("value");
    }

}
