
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ContactMethodRepository - existsByName")
class ITContactMethodRepositoryExistsByName {

    @Autowired
    private ContactMethodRepository repository;

    @Test
    @DisplayName("When there is an existing contact method name, then it exists")
    @EmailContactMethod
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.existsByName(ContactMethodConstants.EMAIL);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is a not existing contact method name, then it doesn't exists")
    @EmailContactMethod
    void testExists_AnotherName() {
        final boolean exists;

        // WHEN
        exists = repository.existsByName("abc");

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no contact method, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByName(ContactMethodConstants.EMAIL);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
