
package com.bernardomg.association.fee.usecase.validation;

import java.util.Collection;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateFeeValidator extends AbstractValidator<Collection<Fee>> {

    private final FeeRepository    feeRepository;

    private final MemberRepository memberRepository;

    public CreateFeeValidator(final MemberRepository memberRepo, final FeeRepository feeRepo) {
        super();

        memberRepository = memberRepo;
        feeRepository = feeRepo;
    }

    @Override
    protected final void checkRules(final Collection<Fee> fees, final Collection<FieldFailure> failures) {
        final long   uniqueDates;
        final int    totalDates;
        final long   existing;
        final long   duplicates;
        final long   number;
        final Member member;
        FieldFailure failure;

        // Verify there are no duplicated dates
        uniqueDates = fees.stream()
            .map(Fee::getDate)
            .distinct()
            .count();
        totalDates = fees.size();
        if (uniqueDates < totalDates) {
            duplicates = (totalDates - uniqueDates);
            log.error("Received {} fee dates, but {} are duplicates", totalDates, duplicates);
            failure = FieldFailure.of("feeDates[]", "duplicated", duplicates);
            failures.add(failure);
        }

        // Verify no date is already registered, unless it is not paid
        if (!fees.isEmpty()) {
            number = fees.iterator()
                .next()
                .getMember()
                .getNumber();
            member = memberRepository.findOne(number)
                .get();
            // TODO: use a single query
            existing = fees.stream()
                .map(Fee::getDate)
                .filter(date -> feeRepository.existsPaid(member.getNumber(), date))
                .count();
            if (existing > 0) {
                failure = FieldFailure.of("feeDates[]", "existing", existing);
                failures.add(failure);
            }
        }
    }

}
