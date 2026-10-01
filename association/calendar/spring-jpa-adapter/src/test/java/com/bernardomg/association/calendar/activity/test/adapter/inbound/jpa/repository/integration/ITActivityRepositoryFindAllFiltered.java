
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.domain.filter.ActivityFilter;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - find all with filter")
@MultipleActivity
class ITActivityRepositoryFindAllFiltered {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("With a starting bound, it returns activities ending on or after it")
    void testFindAll_From() {
        final ActivityFilter filter;
        final Page<Activity> activities;

        // GIVEN
        filter = new ActivityFilter(Optional.of(CalendarDateConstants.FROM), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(Activities.forNumberAndMonth(12L, Month.JANUARY),
                Activities.forNumberAndMonth(13L, Month.JANUARY), Activities.forNumberAndMonth(14L, Month.JANUARY));
    }

    @Test
    @DisplayName("With both bounds, it returns activities ending inside the interval")
    void testFindAll_FromAndTo() {
        final ActivityFilter filter;
        final Page<Activity> activities;

        // GIVEN
        filter = new ActivityFilter(Optional.of(CalendarDateConstants.FROM), Optional.of(CalendarDateConstants.TO));

        // WHEN
        activities = repository.findAll(filter, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(Activities.forNumberAndMonth(12L, Month.JANUARY),
                Activities.forNumberAndMonth(13L, Month.JANUARY));
    }

    @Test
    @DisplayName("Without bounds, it returns all activities")
    void testFindAll_NoBounds() {
        final ActivityFilter filter;
        final Page<Activity> activities;

        // GIVEN
        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(Activities.forNumberAndMonth(10L, Month.JANUARY),
                Activities.forNumberAndMonth(11L, Month.JANUARY), Activities.forNumberAndMonth(12L, Month.JANUARY),
                Activities.forNumberAndMonth(13L, Month.JANUARY), Activities.forNumberAndMonth(14L, Month.JANUARY));
    }

    @Test
    @DisplayName("With an ending bound, it returns activities entirely ended before it")
    void testFindAll_To() {
        final ActivityFilter filter;
        final Page<Activity> activities;

        // GIVEN
        filter = new ActivityFilter(Optional.empty(), Optional.of(CalendarDateConstants.FROM));

        // WHEN
        activities = repository.findAll(filter, new Pagination(1, 20), Sorting.unsorted());

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(Activities.forNumberAndMonth(10L, Month.JANUARY),
                Activities.forNumberAndMonth(11L, Month.JANUARY));
    }

}
