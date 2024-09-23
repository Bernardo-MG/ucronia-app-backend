
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;

public final class FeeEntities {

    public static FeeEntity atDate() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.DATE)
            .build();
    }

    public static FeeEntity currentMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity currentMonthAlternative() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.alternative())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity firstNextYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .build();
    }

    public static FeeEntity lastInYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.LAST_YEAR_DATE)
            .build();
    }

    public static FeeEntity nextDate() {
        return FeeEntity.builder()
            .withId(2L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_DATE)
            .build();
    }

    public static FeeEntity nextMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_MONTH)
            .build();
    }

    public static FeeEntity nextYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_YEAR_MONTH)
            .build();
    }

    public static FeeEntity previousMonth() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.PREVIOUS_MONTH)
            .build();
    }

    public static FeeEntity previousYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.PREVIOUS_YEAR_MONTH)
            .build();
    }

    public static FeeEntity twoMonthsBack() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.TWO_MONTHS_BACK)
            .build();
    }

    public static FeeEntity twoYearsBack() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.valid())
            .withPersonId(1L)
            .withDate(FeeConstants.TWO_YEARS_BACK)
            .build();
    }

    private FeeEntities() {
        super();
    }

}
