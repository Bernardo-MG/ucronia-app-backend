
package com.bernardomg.association.activity.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.activity.TestApplication;
import com.bernardomg.association.activity.adapter.inbound.jpa.model.ActivityEntity;
import com.bernardomg.association.activity.adapter.inbound.jpa.repository.ActivitySpringRepository;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.test.configuration.factory.Activities;
import com.bernardomg.association.activity.test.factory.ActivityEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - save")
class ITActivityRepositorySave {

    @Autowired
    private ActivityRepository       repository;

    @Autowired
    private ActivitySpringRepository springRepository;

    @Test
    @DisplayName("Persists the data")
    void testSave_PersistedData() {
        final Iterable<ActivityEntity> activities;
        final Activity                 activity;

        // GIVEN
        activity = Activities.valid();

        // WHEN
        repository.save(activity);

        // THEN
        activities = springRepository.findAll();

        Assertions.assertThat(activities)
            .as("activities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(ActivityEntities.valid());
    }

    @Test
    @DisplayName("Returns the created data")
    void testSave_ReturnedData() {
        final Activity created;
        final Activity activity;

        // GIVEN
        activity = Activities.valid();

        // WHEN
        created = repository.save(activity);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Activities.valid());
    }

}
