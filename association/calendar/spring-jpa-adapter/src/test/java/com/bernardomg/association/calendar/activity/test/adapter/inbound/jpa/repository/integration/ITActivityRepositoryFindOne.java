
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

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
@DisplayName("ActivityRepository - findOne")
class ITActivityRepositoryFindOne {

    @Autowired
    private ActivityRepository repository;

    @Test
    @DisplayName("When there is an existing activity, then it is returned")
    @SingleDayActivity
    void testFindOne() {
        final Optional<Activity> activity;

        // WHEN
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activity)
            .as("file")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an existing activity, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Activities.singleDay()));
    }

    @Test
    @DisplayName("When there is an existing activity having multiple days, then it is returned")
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
                .as("file")
                .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                    .as("When there is an existing activity having multiple days, then it is returned")
                    .usingRecursiveComparison()
                    .ignoringFields("audit")
                    .isEqualTo(Activities.multipleDay()));
            Assertions.assertThat(activity.get())
                .as("When there is an existing activity having multiple days, then it is returned")
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("When there is an existing activity having multiple days our of order, then it is returned in order")
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
                .as("file")
                .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                    .as("When there is an existing activity having multiple days our of order, then it is returned in order")
                    .usingRecursiveComparison()
                    .ignoringFields("audit")
                    .isEqualTo(Activities.multipleDay()));
            Assertions.assertThat(activity.get())
                .as("When there is an existing activity having multiple days our of order, then it is returned in order")
                .extracting(Activity::dates)
                .asInstanceOf(InstanceOfAssertFactories.SET)
                .containsExactly(date1, date2, date3, date4, date5);
        });
    }

    @Test
    @DisplayName("When there is no activity, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Activity> activity;

        // WHEN
        activity = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activity)
            .as("When there is no activity, then nothing is returned")
            .isEmpty();
    }

}
