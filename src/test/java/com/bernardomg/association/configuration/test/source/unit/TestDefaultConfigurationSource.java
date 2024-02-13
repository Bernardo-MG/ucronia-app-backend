
package com.bernardomg.association.configuration.test.source.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.test.config.factory.ConfigurationConstants;
import com.bernardomg.association.configuration.test.config.factory.Configurations;
import com.bernardomg.association.configuration.usecase.source.DefaultConfigurationSource;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultConfigurationSource")
public class TestDefaultConfigurationSource {

    @Mock
    private ConfigurationRepository    configurationRepository;

    @InjectMocks
    private DefaultConfigurationSource source;

    @Test
    @DisplayName("Getting the float for an existing float returns its value")
    void testGetFloat_Float() {
        final Float value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.floatValue()));

        // WHEN
        value = source.getFloat(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEqualTo(10.1F);
    }

    @Test
    @DisplayName("Getting the float for an existing integer returns its value")
    void testGetFloat_Integer() {
        final Float value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.intValue()));

        // WHEN
        value = source.getFloat(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEqualTo(10F);
    }

    @Test
    @DisplayName("Getting the float for a not existing value returns zero")
    void testGetFloat_NotExisting() {
        final Float value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY)).willReturn(Optional.empty());

        // WHEN
        value = source.getFloat(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEqualTo(0F);
    }

    @Test
    @DisplayName("Getting the float for a string value throws an exception")
    void testGetFloat_String() {
        final ThrowingCallable execution;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.stringValue()));

        // WHEN
        execution = () -> source.getFloat("key");

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("Getting the string for an existing integer returns its value as text")
    void testGetString_Integer() {
        final String value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.intValue()));

        // WHEN
        value = source.getString(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEqualTo("10");
    }

    @Test
    @DisplayName("Getting the string for a not existing text returns an empty string")
    void testGetString_NotExisting() {
        final String value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY)).willReturn(Optional.empty());

        // WHEN
        value = source.getString(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEmpty();
    }

    @Test
    @DisplayName("Getting the string for an existing text returns its value")
    void testGetString_String() {
        final String value;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.stringValue()));

        // WHEN
        value = source.getString(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(value)
            .isEqualTo("value");
    }

}
