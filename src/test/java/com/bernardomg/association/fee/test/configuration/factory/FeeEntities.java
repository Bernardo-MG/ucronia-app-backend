
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.LocalDate;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

public final class FeeEntities {

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

    public static final FeeEntity paid() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withTransaction(TransactionEntities.februaryFee())
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidAtDate(final LocalDate date) {
        TransactionEntity transaction;

        transaction = TransactionEntities.februaryFee();
        transaction.setDate(date);
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withTransaction(transaction)
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidMultiple() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withTransaction(TransactionEntities.multipleFeesStartYear())
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidMultipleAtNextDate() {
        return FeeEntity.builder()
            .withId(2L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.NEXT_DATE)
            .withPaid(true)
            .withTransaction(TransactionEntities.multipleFeesStartYear())
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidMultipleFirstNextYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .withPaid(true)
            .withTransaction(TransactionEntities.multipleFeesEndYear())
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidMultipleLastInYear() {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.LAST_YEAR_DATE)
            .withPaid(true)
            .withTransaction(TransactionEntities.multipleFeesEndYear())
            .withTransactionId(TransactionConstants.ID)
            .build();
    }

    public static final FeeEntity paidWithIndex(final long index) {
        return FeeEntity.builder()
            .withId(1L)
            .withPerson(PersonEntities.membershipActive())
            .withPersonId(1L)
            .withDate(FeeConstants.CURRENT_MONTH)
            .withPaid(true)
            .withTransaction(TransactionEntities.index(index))
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
