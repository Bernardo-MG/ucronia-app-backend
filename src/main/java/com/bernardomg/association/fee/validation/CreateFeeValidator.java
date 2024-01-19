
package com.bernardomg.association.fee.validation;

import java.util.Collection;

import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.infra.jpa.repository.MemberFeeRepository;
import com.bernardomg.association.member.infra.jpa.model.MemberEntity;
import com.bernardomg.association.member.infra.jpa.repository.MemberSpringRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<FeePayment> {

    private final MemberFeeRepository    memberFeeRepository;

    private final MemberSpringRepository memberRepository;

    public CreateFeeValidator(final MemberSpringRepository memberRepo, final MemberFeeRepository memberFeeRepo) {
        super();

        memberRepository = memberRepo;
        memberFeeRepository = memberFeeRepo;
    }

    @Override
    protected final void checkRules(final FeePayment payment, final Collection<FieldFailure> failures) {
        final Long         uniqueDates;
        final int          totalDates;
        final Long         existing;
        final Long         duplicates;
        final MemberEntity member;
        FieldFailure       failure;

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
        member = memberRepository.findByNumber(payment.getMember()
            .getNumber())
            .get();
        // TODO: use a single query
        existing = payment.getFeeDates()
            .stream()
            .filter(date -> memberFeeRepository.existsByMemberIdAndDateAndPaid(member.getId(), date, true))
            .count();
        if (existing > 0) {
            failure = FieldFailure.of("feeDates[]", "existing", existing);
            failures.add(failure);
        }
    }

}
