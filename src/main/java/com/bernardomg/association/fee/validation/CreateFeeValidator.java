
package com.bernardomg.association.fee.validation;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<FeeCreate> {

    private final MemberRepository memberRepository;

    public CreateFeeValidator(final MemberRepository memberRepo) {
        super();

        memberRepository = memberRepo;
    }

    @Override
    protected final void checkRules(final FeeCreate fee) {
        FieldFailure failure;

        // Verify the member exists
        if (!memberRepository.existsById(fee.getMemberId())) {
            log.error("Found no member with id {}", fee.getMemberId());
            failure = FieldFailure.of("memberId", "notExists", fee.getMemberId());
            addFailure(failure);
        }
    }

}
