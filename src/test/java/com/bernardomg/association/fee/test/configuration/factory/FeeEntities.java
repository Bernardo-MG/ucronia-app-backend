
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.ZoneOffset;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.member.test.configuration.factory.MemberProfileEntities;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;

public final class FeeEntities {

    public static final FeeEntity currentMonth() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity currentMonthAlternative() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.alternative());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity nextMonth() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.NEXT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity nextYear() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.NEXT_YEAR_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity notPaid() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.withEmail());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(false);
        return entity;
    }

    public static final FeeEntity paid() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.withEmail());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.februaryFee());
        return entity;
    }

    public static final FeeEntity paidAtDate(final Instant date) {
        final FeeEntity         entity;
        final FeeTypeEntity     feeType;
        final TransactionEntity transaction;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        transaction = TransactionEntities.februaryFee();
        transaction.setDate(date);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.withEmail());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(transaction);
        return entity;
    }

    public static final FeeEntity paidMultiple() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        return entity;
    }

    public static final FeeEntity paidMultipleAtNextDate() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.NEXT_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesStartYear());
        return entity;
    }

    public static final FeeEntity paidMultipleFirstNextYear() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.FIRST_NEXT_YEAR_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        return entity;
    }

    public static final FeeEntity paidMultipleLastInYear() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.LAST_YEAR_DATE.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.multipleFeesEndYear());
        return entity;
    }

    public static final FeeEntity paidWithIndex(final long index) {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        entity.setPaid(true);
        entity.setTransaction(TransactionEntities.index(index));
        return entity;
    }

    public static final FeeEntity previousMonth() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity previousYear() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.PREVIOUS_YEAR_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity twoMonthsBack() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.TWO_MONTHS_BACK.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    public static final FeeEntity twoYearsBack() {
        final FeeEntity     entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new FeeEntity();
        entity.setFeeType(feeType);
        entity.setMember(MemberProfileEntities.active());
        entity.setMemberId(1L);
        entity.setMonth(FeeConstants.TWO_YEARS_BACK.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return entity;
    }

    private FeeEntities() {
        super();
    }

}
