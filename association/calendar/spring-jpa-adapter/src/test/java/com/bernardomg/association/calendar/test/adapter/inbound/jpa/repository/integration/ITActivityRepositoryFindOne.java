/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

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
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.MultipleDayOutOfOrderActivity;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.SingleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - find one")
class ITActivityRepositoryFindOne {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("With an existing activity, it is returned")
    @SingleDayActivity
    void testFindOne() {
        final Optional<Activity> activity;

        // WHEN
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activity)
            .contains(Activities.singleDay());
    }

    @Test
    @DisplayName("With an existing activity having multiple days, it is returned")
    @MultipleDayActivity
    void testFindOne_MultipleDays() {
        final Optional<Activity> activity;
        final ActivityDate       date1;
        final ActivityDate       date2;
        final ActivityDate       date3;
        final ActivityDate       date4;
        final ActivityDate       date5;

        // GIVEN
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
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(activity)
                .contains(Activities.multipleDay());
            Assertions.assertThat(activity.get())
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("With an existing activity having multiple days our of order, it is returned in order")
    @MultipleDayOutOfOrderActivity
    void testFindOne_MultipleDays_OutOfOrder() {
        final Optional<Activity> activity;
        final ActivityDate       date1;
        final ActivityDate       date2;
        final ActivityDate       date3;
        final ActivityDate       date4;
        final ActivityDate       date5;

        // GIVEN
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
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(activity)
                .contains(Activities.multipleDay());
            Assertions.assertThat(activity.get())
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("With no activity, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Activity> activity;

        // WHEN
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activity)
            .isEmpty();
    }

}
