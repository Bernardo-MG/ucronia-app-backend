
package com.bernardomg.association.calendar.activity.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.activity.domain.filter.ActivityFilter;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.usecase.service.DefaultActivityService;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - getAll")
class TestScheduledGameServiceGetAll {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAll() {
        final Page<Activity> activities;
        final Page<Activity> existing;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        existing = new Page<>(List.of(Activities.singleDay()), 0, 0, 0, 0, 0, false, false, sorting);
        given(activityRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        activities = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("Activities")
            .containsExactly(Activities.singleDay());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAll_NoData() {
        final Page<Activity> activities;
        final Page<Activity> existing;
        final Pagination     pagination;
        final Sorting        sorting;
        final ActivityFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        filter = new ActivityFilter(Optional.empty(), Optional.empty());

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(activityRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        activities = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(activities)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("Activities")
            .isEmpty();
    }

}
