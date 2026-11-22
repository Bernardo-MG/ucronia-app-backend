
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.SingleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarInfoSpringRepository;
import com.bernardomg.association.calendar.test.factory.CalendarInfoEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - save")
class ITActivityRepositorySave {

    @Autowired
    private ActivityRepository           repository;

    @Autowired
    private CalendarInfoSpringRepository springRepository;

    @Test
    @DisplayName("When there is an activity with multiple days, then it is persisted")
    void testSave_MultipleDays_PersistedData() {
        final Iterable<CalendarInfoEntity> activities;
        final Activity                     activity;

        // GIVEN
        activity = Activities.multipleDay();

        // WHEN
        repository.save(activity);

        // THEN
        activities = springRepository.findAll();

        Assertions.assertThat(activities)
            .as("activities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "calendarDates.id", "types.id")
            .containsExactly(CalendarInfoEntities.publishedWithMultipleDays());
    }

    @Test
    @DisplayName("When there is an activity with multiple days, then it is returned")
    void testSave_MultipleDays_ReturnedData() {
        final Activity created;
        final Activity activity;

        // GIVEN
        activity = Activities.multipleDay();

        // WHEN
        created = repository.save(activity);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(activity);
    }

    @Test
    @DisplayName("When there is a valid activity, then it is persisted")
    void testSave_PersistedData() {
        final Iterable<CalendarInfoEntity> activities;
        final Activity                     activity;

        // GIVEN
        activity = Activities.singleDay();

        // WHEN
        repository.save(activity);

        // THEN
        activities = springRepository.findAll();

        Assertions.assertThat(activities)
            .as("activities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "calendarDates.id", "types.id")
            .containsExactly(CalendarInfoEntities.published());
    }

    @Test
    @DisplayName("When there is a valid activity, then it is returned")
    void testSave_ReturnedData() {
        final Activity created;
        final Activity activity;

        // GIVEN
        activity = Activities.singleDay();

        // WHEN
        created = repository.save(activity);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Activities.singleDay());
    }

    @Test
    @DisplayName("When changing the title, then the data is persisted")
    @SingleDayActivity
    void testSave_TitleChange_PersistedData() {
        final Iterable<CalendarInfoEntity> activities;
        final Activity                     activity;

        // GIVEN
        activity = Activities.titleChange();

        // WHEN
        repository.save(activity);

        // THEN
        activities = springRepository.findAll();

        Assertions.assertThat(activities)
            .as("activities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "calendarDates.id", "types.id")
            .containsExactly(CalendarInfoEntities.titleChangePublished());
    }

    @Test
    @DisplayName("When changing the title, then the data is returned")
    @SingleDayActivity
    void testSave_TitleChange_ReturnedData() {
        final Activity created;
        final Activity activity;

        // GIVEN
        activity = Activities.titleChange();

        // WHEN
        created = repository.save(activity);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Activities.titleChange());
    }

}
