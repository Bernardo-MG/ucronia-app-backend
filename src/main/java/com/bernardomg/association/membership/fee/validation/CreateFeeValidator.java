
package com.bernardomg.association.membership.fee.validation;

import java.util.Collection;

import com.bernardomg.association.membership.fee.model.request.FeesPayment;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<FeesPayment> {

    private final FeeRepository    feeRepository;

    private final MemberRepository memberRepository;

    public CreateFeeValidator(final MemberRepository memberRepo, final FeeRepository feeRepo) {
        super();

        memberRepository = memberRepo;
        feeRepository = feeRepo;
    }

    @Override
    protected final void checkRules(final FeesPayment fee, final Collection<FieldFailure> failures) {
        final Long   uniqueDates;
        final int    totalDates;
        final Long   existing;
        final Long   duplicates;
        FieldFailure failure;

        // Verify the member exists
        if (!memberRepository.existsById(fee.getMemberId())) {
            log.error("Found no member with id {}", fee.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", fee.getMemberId());
            failures.add(failure);
        }

        // Verify there are no duplicated dates
        uniqueDates = fee.getFeeDates()
            .stream()
            .distinct()
            .count();
        totalDates = fee.getFeeDates()
            .size();
        if (uniqueDates < totalDates) {
            duplicates = (totalDates - uniqueDates);
            log.error("Received {} fees, but {} are duplicates", fee.getFeeDates()
                .size(), duplicates);
            failure = FieldFailure.of("feeDates[]", "duplicated", duplicates);
            failures.add(failure);
        }

        // Verify no date is already registered, unless it is not paid
        existing = fee.getFeeDates()
            .stream()
            .filter(date -> feeRepository.existsByMemberIdAndDateAndPaid(fee.getMemberId(), date, true))
            .count();
        if (existing > 0) {
            failure = FieldFailure.of("feeDates[]", "existing", existing);
            failures.add(failure);
        }
    }

}
