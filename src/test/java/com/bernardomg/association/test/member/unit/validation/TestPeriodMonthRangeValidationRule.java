
package com.bernardomg.association.test.member.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.memberperiod.model.DtoMemberPeriod;
import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.association.memberperiod.validation.rule.PeriodMonthRangeValidationRule;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

@DisplayName("Period month range validation rule")
public class TestPeriodMonthRangeValidationRule {

    private final ValidationRule<MemberPeriod> validator = new PeriodMonthRangeValidationRule();

    public TestPeriodMonthRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<ValidationError> error;
        final DtoMemberPeriod             period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(13);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.memberPeriod.invalidEndMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<ValidationError> error;
        final DtoMemberPeriod             period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(0);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.memberPeriod.invalidEndMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Accepts full year")
    public final void testValidator_FullYear() throws Exception {
        final Collection<ValidationError> error;
        final DtoMemberPeriod             period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(12);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertEquals(0, error.size());
    }

    @Test
    @DisplayName("Rejects the starting month when it is above limits")
    public final void testValidator_StartMonthAbove() throws Exception {
        final Collection<ValidationError> error;
        final DtoMemberPeriod             period;

        period = new DtoMemberPeriod();
        period.setStartMonth(13);
        period.setStartYear(2020);
        period.setEndMonth(12);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.memberPeriod.invalidStartMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Rejects the starting month when it is below limits")
    public final void testValidator_StartMonthBelow() throws Exception {
        final Collection<ValidationError> error;
        final DtoMemberPeriod             period;

        period = new DtoMemberPeriod();
        period.setStartMonth(0);
        period.setStartYear(2020);
        period.setEndMonth(12);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.memberPeriod.invalidStartMonth", error.iterator()
            .next()
            .getError());
    }

}
