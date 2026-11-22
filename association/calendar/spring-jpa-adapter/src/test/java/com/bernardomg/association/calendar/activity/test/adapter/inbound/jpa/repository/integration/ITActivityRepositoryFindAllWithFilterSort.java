
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.PropertyReferenceException;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.domain.filter.ActivityFilter;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - findAll")
@MultipleActivity
class ITActivityRepositoryFindAllWithFilterSort {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by description, then it returns the ordered data")
    void testFindAll_Description_Asc() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When sorting in ascending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(10, Month.JANUARY),
                Activities.forNumberAndMonth(11, Month.JANUARY), Activities.forNumberAndMonth(12, Month.JANUARY),
                Activities.forNumberAndMonth(13, Month.JANUARY), Activities.forNumberAndMonth(14, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in descending order by description, then it returns the ordered data")
    void testFindAll_Description_Desc() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.DESC)));

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When sorting in descending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(14, Month.JANUARY),
                Activities.forNumberAndMonth(13, Month.JANUARY), Activities.forNumberAndMonth(12, Month.JANUARY),
                Activities.forNumberAndMonth(11, Month.JANUARY), Activities.forNumberAndMonth(10, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in ascending order by title, then it returns the ordered data")
    void testFindAll_Title_Asc() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("title", Sorting.Direction.ASC)));

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When sorting in ascending order by title, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(10, Month.JANUARY),
                Activities.forNumberAndMonth(11, Month.JANUARY), Activities.forNumberAndMonth(12, Month.JANUARY),
                Activities.forNumberAndMonth(13, Month.JANUARY), Activities.forNumberAndMonth(14, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in descending order by title, then it returns the ordered data")
    void testFindAll_Title_Desc() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("title", Sorting.Direction.DESC)));

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When sorting in descending order by title, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(14, Month.JANUARY),
                Activities.forNumberAndMonth(13, Month.JANUARY), Activities.forNumberAndMonth(12, Month.JANUARY),
                Activities.forNumberAndMonth(11, Month.JANUARY), Activities.forNumberAndMonth(10, Month.JANUARY));
    }

    @Test
    @DisplayName("When ordering by a nonexistent field, then an error is generated")
    void testGetAll_NotExisting() {
        final Pagination       pagination;
        final Sorting          sorting;
        final ThrowingCallable action;
        final ActivityFilter   filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        action = () -> repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Ordering by a not existing field generates an error")
            .isInstanceOf(PropertyReferenceException.class);
    }

}
