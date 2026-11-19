
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ContactMethodRepository - delete")
class ITContactMethodRepositoryDelete {

    @Autowired
    private ContactMethodRepository       repository;

    @Autowired
    private ContactMethodSpringRepository springrepository;

    @Test
    @DisplayName("When deleting a contact method, then it is deleted")
    @EmailContactMethod
    void testDelete() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springrepository.count())
            .as("When deleting a contact method, then it is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springrepository.count())
            .as("When there is no data, then nothing is deleted")
            .isZero();
    }

}
