
package com.bernardomg.association.fee.usecase.validation;

import java.util.Collection;

import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<FeePayment> {

    private final FeeRepository    feeRepository;

    private final MemberRepository memberRepository;

    public CreateFeeValidator(final MemberRepository memberRepo, final FeeRepository feeRepo) {
        super();

        memberRepository = memberRepo;
        feeRepository = feeRepo;
    }

    @Override
    protected final void checkRules(final FeePayment payment, final Collection<FieldFailure> failures) {
        final Long   uniqueDates;
        final int    totalDates;
        final Long   existing;
        final Long   duplicates;
        final Member member;
        FieldFailure failure;

        // Verify there are no duplicated dates
        uniqueDates = payment.getFeeDates()
            .stream()
            .distinct()
            .count();
        totalDates = payment.getFeeDates()
            .size();
        if (uniqueDates < totalDates) {
            duplicates = (totalDates - uniqueDates);
            log.error("Received {} fees, but {} are duplicates", payment.getFeeDates()
                .size(), duplicates);
            failure = FieldFailure.of("feeDates[]", "duplicated", duplicates);
            failures.add(failure);
        }

        // Verify no date is already registered, unless it is not paid
        member = memberRepository.findOne(payment.getMember()
            .getNumber())
            .get();
        // TODO: use a single query
        existing = payment.getFeeDates()
            .stream()
            .filter(date -> feeRepository.existsPaid(member.getNumber(), date))
            .count();
        if (existing > 0) {
            failure = FieldFailure.of("feeDates[]", "existing", existing);
            failures.add(failure);
        }
    }

}
