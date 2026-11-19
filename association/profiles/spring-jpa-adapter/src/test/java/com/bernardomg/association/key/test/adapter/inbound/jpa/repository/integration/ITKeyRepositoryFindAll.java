
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyRepository - findAll")
class ITKeyRepositoryFindAll {

    @Autowired
    private KeyRepository repository;

    @Test
    @DisplayName("When key data is provided, then all keys are returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindAll() {
        final Page<Key> keys;

        // WHEN
        keys = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(keys.content())
            .as("When key data is provided, then all keys are returned")
            .containsExactly(Keys.available());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Key> keys;

        // WHEN
        keys = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(keys.content())
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

}
