
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.ZoneOffset;

import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.member.test.configuration.factory.MemberContactEntities;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

public final class FeeEntities {

    public static final FeeEntity currentMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity currentMonthAlternative() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(ContactEntities.alternative());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity nextMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.NEXT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity nextYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.NEXT_YEAR_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity notPaid() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(false);
        return entity;
    }

    public static final FeeEntity paid() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.februaryFee());
        return entity;
    }

    public static final FeeEntity paidAtDate(final Instant date) {
        final TransactionEntity transaction = TransactionEntities.februaryFee();
        transaction.setDate(date);

        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(transaction);
        return entity;
    }

    public static final FeeEntity paidMultiple() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        return entity;
    }

    public static final FeeEntity paidMultipleAtNextDate() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.NEXT_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        return entity;
    }

    public static final FeeEntity paidMultipleFirstNextYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.FIRST_NEXT_YEAR_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        return entity;
    }

    public static final FeeEntity paidMultipleLastInYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.LAST_YEAR_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        return entity;
    }

    public static final FeeEntity paidWithIndex(final long index) {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.index(index));
        return entity;
    }

    public static final FeeEntity previousMonth() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity previousYear() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.PREVIOUS_YEAR_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity twoMonthsBack() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.TWO_MONTHS_BACK.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity twoYearsBack() {
        final FeeEntity entity = new FeeEntity();
        entity.setContact(MemberContactEntities.active());
        entity.setContactId(1L);
        entity.setDate(FeeConstants.TWO_YEARS_BACK.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    private FeeEntities() {
        super();
    }

}
