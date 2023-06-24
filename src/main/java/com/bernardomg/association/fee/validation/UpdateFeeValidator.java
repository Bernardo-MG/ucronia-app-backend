
package com.bernardomg.association.fee.validation;

import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateFeeValidator extends AbstractValidator<FeeUpdate> {

    private final MemberRepository memberRepository;

    public UpdateFeeValidator(final MemberRepository memberRepo) {
        super();

        memberRepository = memberRepo;
    }

    @Override
    protected final void checkRules(final FeeUpdate fee) {
        FieldFailure failure;

        // Verify the member exists
        if (!memberRepository.existsById(fee.getMemberId())) {
            log.error("Found no member with id {}", fee.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", fee.getMemberId());
            addFailure(failure);
        }
    }

}
