
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.domain.filter.ActivityFilter;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleActivity;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleActivityOutOfOrder;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleDayOutOfOrderActivity;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.SingleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - findAll")
class ITActivityRepositoryFindAllWithFilter {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("When there is a single activity, then it returns all the activities")
    @SingleDayActivity
    void testFindAll() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When there is a single activity, then it returns all the activities")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.singleDay());
    }

    @Test
    @DisplayName("When there are multiple activites, then it returns all the activities")
    @MultipleActivity
    void testFindAll_Multiple() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When there are multiple activites, then it returns all the activities")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(10L, Month.JANUARY),
                Activities.forNumberAndMonth(11L, Month.JANUARY), Activities.forNumberAndMonth(12L, Month.JANUARY),
                Activities.forNumberAndMonth(13L, Month.JANUARY), Activities.forNumberAndMonth(14L, Month.JANUARY));
    }

    @Test
    @DisplayName("When there are multiple activites out of order, then it returns all the activities ordered")
    @MultipleActivityOutOfOrder
    void testFindAll_Multiple_OutOfOrder() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When there are multiple activites out of order, then it returns all the activities ordered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Activities.forNumberAndMonth(10L, Month.JANUARY),
                Activities.forNumberAndMonth(11L, Month.JANUARY), Activities.forNumberAndMonth(12L, Month.JANUARY),
                Activities.forNumberAndMonth(13L, Month.JANUARY), Activities.forNumberAndMonth(14L, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is an existing activity having multiple days, then it is returned")
    @MultipleDayActivity
    void testFindAll_MultipleDays() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;
        final ActivityDate   date1;
        final ActivityDate   date2;
        final ActivityDate   date3;
        final ActivityDate   date4;
        final ActivityDate   date5;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        date1 = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);
        date2 = new ActivityDate(CalendarDateConstants.START.plus(1L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(1L, ChronoUnit.DAYS));
        date3 = new ActivityDate(CalendarDateConstants.START.plus(2L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(2L, ChronoUnit.DAYS));
        date4 = new ActivityDate(CalendarDateConstants.START.plus(3L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(3L, ChronoUnit.DAYS));
        date5 = new ActivityDate(CalendarDateConstants.START.plus(4L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(4L, ChronoUnit.DAYS));

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(activities)
                .as("When there is an existing activity having multiple days, then it is returned")
                .extracting(Page::content)
                .asInstanceOf(InstanceOfAssertFactories.LIST)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
                .containsExactly(Activities.multipleDay());
            Assertions.assertThat(activities.content())
                .as("When there is an existing activity having multiple days, then it is returned")
                .first()
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("When there is an existing activity having multiple days our of order, then it is returned in order")
    @MultipleDayOutOfOrderActivity
    void testFindAll_MultipleDays_OutOfOrder() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;
        final ActivityDate   date1;
        final ActivityDate   date2;
        final ActivityDate   date3;
        final ActivityDate   date4;
        final ActivityDate   date5;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        date1 = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);
        date2 = new ActivityDate(CalendarDateConstants.START.plus(1L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(1L, ChronoUnit.DAYS));
        date3 = new ActivityDate(CalendarDateConstants.START.plus(2L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(2L, ChronoUnit.DAYS));
        date4 = new ActivityDate(CalendarDateConstants.START.plus(3L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(3L, ChronoUnit.DAYS));
        date5 = new ActivityDate(CalendarDateConstants.START.plus(4L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(4L, ChronoUnit.DAYS));

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(activities)
                .as("When there is an existing activity having multiple days our of order, then it is returned in order")
                .extracting(Page::content)
                .asInstanceOf(InstanceOfAssertFactories.LIST)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
                .containsExactly(Activities.multipleDay());
            Assertions.assertThat(activities.content())
                .as("When there is an existing activity having multiple days our of order, then it is returned in order")
                .first()
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testFindAll_NoData() {
        final Page<Activity> activities;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        // WHEN
        activities = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .as("When there is no data, then it returns nothing")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
