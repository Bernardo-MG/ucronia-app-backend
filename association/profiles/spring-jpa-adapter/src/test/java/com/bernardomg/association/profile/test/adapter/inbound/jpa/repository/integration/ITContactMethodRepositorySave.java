
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.association.profile.test.factory.ContactMethodEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ContactMethodRepository - save")
class ITContactMethodRepositorySave {

    @Autowired
    private ContactMethodRepository       repository;

    @Autowired
    private ContactMethodSpringRepository springRepository;

    @Test
    @DisplayName("When changing a contact method, then the contact method is persisted")
    @EmailContactMethod
    void testSave_Change_PersistedData() {
        final ContactMethod                 contactMethod;
        final Iterable<ContactMethodEntity> entities;

        // GIVEN
        contactMethod = ContactMethods.phoneChange();

        // WHEN
        repository.save(contactMethod);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactMethodEntities.phoneChange());
    }

    @Test
    @DisplayName("When there is a valid contact method, then the contact method is persisted")
    void testSave_PersistedData() {
        final ContactMethod                 contactMethod;
        final Iterable<ContactMethodEntity> entities;

        // GIVEN
        contactMethod = ContactMethods.email();

        // WHEN
        repository.save(contactMethod);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactMethodEntities.email());
    }

    @Test
    @DisplayName("When there is a valid contact method, then the created contact method is returned")
    void testSave_ReturnedData() {
        final ContactMethod contactMethod;
        final ContactMethod saved;

        // GIVEN
        contactMethod = ContactMethods.email();

        // WHEN
        saved = repository.save(contactMethod);

        // THEN
        Assertions.assertThat(saved)
            .as("contact method")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(ContactMethods.email());
    }

}
