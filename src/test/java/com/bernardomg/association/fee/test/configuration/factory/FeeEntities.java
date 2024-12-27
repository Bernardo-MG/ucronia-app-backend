
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;

public final class FeeEntities {

    public static final FeeEntity atDate() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.DATE)
            .build();
    }

    public static final FeeEntity currentMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static final FeeEntity currentMonthAlternative() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.alternative())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static final FeeEntity firstNextYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .build();
    }

    public static final FeeEntity lastInYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.LAST_YEAR_DATE)
            .build();
    }

    public static final FeeEntity nextDate() {
        return FeeEntity.builder()
            .withId(2L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_DATE)
            .build();
    }

    public static final FeeEntity nextMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_MONTH)
            .build();
    }

    public static final FeeEntity nextYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_YEAR_MONTH)
            .build();
    }

    public static final FeeEntity paidWithIndex(final long index) {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .withTransactionId(index)
            .build();
    }

    public static final FeeEntity previousMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final FeeEntity previousYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.PREVIOUS_YEAR_MONTH)
            .build();
    }

    public static final FeeEntity twoMonthsBack() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.TWO_MONTHS_BACK)
            .build();
    }

    public static final FeeEntity twoYearsBack() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.TWO_YEARS_BACK)
            .build();
    }

    private FeeEntities() {
        super();
    }

}
