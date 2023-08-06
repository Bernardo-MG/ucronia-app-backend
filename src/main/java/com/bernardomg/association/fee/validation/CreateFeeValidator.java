
package com.bernardomg.association.fee.validation;

import java.util.Collection;

import com.bernardomg.association.fee.model.request.FeePayment;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<FeePayment> {

    private final MemberRepository memberRepository;

    public CreateFeeValidator(final MemberRepository memberRepo) {
        super();

        memberRepository = memberRepo;
    }

    @Override
    protected final void checkRules(final FeePayment fee, final Collection<FieldFailure> failures) {
        final Long   uniqueDates;
        final Long   duplicates;
        FieldFailure failure;

        // Verify the member exists
        if (!memberRepository.existsById(fee.getMemberId())) {
            log.error("Found no member with id {}", fee.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", fee.getMemberId());
            failures.add(failure);
        }

        // Verify the dates have no duplicates
        uniqueDates = fee.getFeeDates()
            .stream()
            .distinct()
            .count();
        if (uniqueDates < fee.getFeeDates()
            .size()) {
            duplicates = (fee.getFeeDates()
                .size() - uniqueDates) + 1;
            log.error("Received {} fees, but {} are duplicates", fee.getFeeDates()
                .size(), duplicates);
            failure = FieldFailure.of("feeDates", "duplicated", duplicates);
            failures.add(failure);
        }
    }

}
