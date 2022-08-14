
package com.bernardomg.association.test.member.unit.validation;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.member.model.DtoMemberPeriod;
import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.association.member.validation.PeriodRangeOrderValidationRule;
import com.bernardomg.validation.ValidationError;
import com.bernardomg.validation.ValidationRule;

@DisplayName("Period range order validation rule")
public class TestPeriodRangeOrderValidationRule {

    private final ValidationRule<MemberPeriod> validator = new PeriodRangeOrderValidationRule();

    public TestPeriodRangeOrderValidationRule() {
        super();
    }

    @Test
    @DisplayName("Accepts a full year")
    public final void testValidator_FullYear() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(12);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertTrue(error.isEmpty());
    }

    @Test
    @DisplayName("Accepts a single month")
    public final void testValidator_SingleMonth() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(1);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertTrue(error.isEmpty());
    }

    @Test
    @DisplayName("Rejects a period in a single year where the starting month is after the end month")
    public final void testValidator_StartMonthAfterEnd() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(12);
        period.setStartYear(2020);
        period.setEndMonth(1);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertTrue(error.isPresent());
        Assertions.assertEquals("error.memberPeriod.startMonthAfterEndMonth", error.get()
            .getError());
    }

    @Test
    @DisplayName("Rejects a period in two years where the starting year is after the end year")
    public final void testValidator_StartYearAfterEnd() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2021);
        period.setEndMonth(12);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertTrue(error.isPresent());
        Assertions.assertEquals("error.memberPeriod.startYearAfterEndYear", error.get()
            .getError());
    }

    @Test
    @DisplayName("Rejects a period in two years  where the starting month is after the end month and  the starting year is after the end year")
    public final void testValidator_StartYearAfterEnd_StartMonthAfterEnd() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(12);
        period.setStartYear(2021);
        period.setEndMonth(1);
        period.setEndYear(2020);

        error = validator.test(period);

        Assertions.assertTrue(error.isPresent());
        Assertions.assertEquals("error.memberPeriod.startYearAfterEndYear", error.get()
            .getError());
    }

    @Test
    @DisplayName("Accepts two full years")
    public final void testValidator_TwoFullYears() throws Exception {
        final Optional<ValidationError> error;
        final DtoMemberPeriod           period;

        period = new DtoMemberPeriod();
        period.setStartMonth(1);
        period.setStartYear(2020);
        period.setEndMonth(12);
        period.setEndYear(2021);

        error = validator.test(period);

        Assertions.assertTrue(error.isEmpty());
    }

}
