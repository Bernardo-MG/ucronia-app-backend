
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.LocalDate;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

public final class FeeEntities {

    public static final FeeEntity currentMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH);
        return entity;
    }

    public static final FeeEntity currentMonthAlternative() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.alternative());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH);
        return entity;
    }

    public static final FeeEntity nextMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.NEXT_MONTH);
        return entity;
    }

    public static final FeeEntity nextYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.NEXT_YEAR_MONTH);
        return entity;
    }

    public static final FeeEntity paid() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.DATE);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.februaryFee());
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidAtDate(final LocalDate date) {
        final TransactionEntity transaction = TransactionEntities.februaryFee();
        transaction.setDate(date);

        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.DATE);
        entity.setPaid(true);
        entity.setTransaction(transaction);
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidMultiple() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.DATE);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidMultipleAtNextDate() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.NEXT_DATE);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidMultipleFirstNextYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.FIRST_NEXT_YEAR_DATE);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidMultipleLastInYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.LAST_YEAR_DATE);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        entity.setTransactionId(TransactionConstants.ID);
        return entity;
    }

    public static final FeeEntity paidWithIndex(final long index) {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH);
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.index(index));
        entity.setTransactionId(index);
        return entity;
    }

    public static final FeeEntity previousMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.PREVIOUS_MONTH);
        return entity;
    }

    public static final FeeEntity previousYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.PREVIOUS_YEAR_MONTH);
        return entity;
    }

    public static final FeeEntity twoMonthsBack() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.TWO_MONTHS_BACK);
        return entity;
    }

    public static final FeeEntity twoYearsBack() {
        final FeeEntity entity = new FeeEntity();
        entity.setPerson(PersonEntities.membershipActive());
        entity.setPersonId(1L);
        entity.setDate(FeeConstants.TWO_YEARS_BACK);
        return entity;
    }

    private FeeEntities() {
        super();
    }

}
