
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ContactMethodRepository - findAll")
class ITContactMethodRepositoryFindAllPaginated {

    @Autowired
    private ContactMethodRepository repository;

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Page<ContactMethod> contactMethods;
        final Pagination          pagination;
        final Sorting             sorting;
        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contactMethods = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(contactMethods)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("contacts")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a contact method, then it is returned")
    @EmailContactMethod
    void testFindAll_Single() {
        final Page<ContactMethod> contactMethods;
        final Pagination          pagination;
        final Sorting             sorting;
        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contactMethods = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(contactMethods)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("contacts")
            .containsExactly(ContactMethods.email());
    }

}
