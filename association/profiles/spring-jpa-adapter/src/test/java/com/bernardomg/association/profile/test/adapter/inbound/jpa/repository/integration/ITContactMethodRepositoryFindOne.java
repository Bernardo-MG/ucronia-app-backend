
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ContactMethodRepository - findOne")
class ITContactMethodRepositoryFindOne {

    @Autowired
    private ContactMethodRepository repository;

    @Test
    @DisplayName("When there is a profile method, then it is returned")
    @EmailContactMethod
    void testFindOne() {
        final Optional<ContactMethod> contactOptional;

        // WHEN
        contactOptional = repository.findOne(ContactMethodConstants.NUMBER);

        // THEN
        Assertions.assertThat(contactOptional)
            .as("When there is a profile method, then it is returned")
            .contains(ContactMethods.email());
    }

    @Test
    @DisplayName("When there is no contact, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<ContactMethod> contactOptional;

        // WHEN
        contactOptional = repository.findOne(ContactMethodConstants.NUMBER);

        // THEN
        Assertions.assertThat(contactOptional)
            .as("When there is no contact, then nothing is returned")
            .isEmpty();
    }

}
