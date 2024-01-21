
package com.bernardomg.association.fee.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.domain.exception.MissingFeeIdException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.validation.CreateFeeValidator;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Slf4j
public final class DefaultFeeService implements FeeService {

    private final FeeRepository         feeRepository;

    private final MemberRepository      memberRepository;

    private final Validator<FeePayment> validatorPay;

    public DefaultFeeService(final FeeRepository feeRepo, final MemberRepository memberRepo) {
        super();

        feeRepository = feeRepo;
        memberRepository = memberRepo;

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(memberRepository, feeRepository);
    }

    @Override
    public final void delete(final long memberNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Deleting fee for {} in {}", memberNumber, date);

        memberExists = memberRepository.exists(memberNumber);
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        feeExists = feeRepository.exists(memberNumber, date);
        if (!feeExists) {
            throw new MissingFeeIdException(memberNumber + " " + date.toString());
        }

        feeRepository.delete(memberNumber, date);
    }

    @Override
    public final Iterable<Fee> getAll(final FeeQuery query, final Pageable pageable) {
        return feeRepository.findAll(query, pageable);
    }

    @Override
    public final Optional<Fee> getOne(final long memberNumber, final YearMonth date) {
        final boolean feeExists;
        final boolean memberExists;

        log.debug("Reading fee for {} in {}", memberNumber, date);

        memberExists = memberRepository.exists(memberNumber);
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(memberNumber);
        }

        feeExists = feeRepository.exists(memberNumber, date);
        if (!feeExists) {
            throw new MissingFeeIdException(memberNumber + " " + date.toString());
        }

        return feeRepository.findOne(memberNumber, date);
    }

    @Override
    public final Collection<Fee> payFees(final FeePayment payment) {
        final Collection<Fee>  fees;
        final Optional<Member> member;
        final boolean          memberExists;

        log.debug("Paying fees for {} in {}. Months paid: {}", payment.getMember()
            .getNumber(),
            payment.getTransaction()
                .getDate(),
            payment.getFeeDates());

        memberExists = memberRepository.exists(payment.getMember()
            .getNumber());
        if (!memberExists) {
            // TODO: Change exception
            throw new MissingMemberIdException(payment.getMember()
                .getNumber());
        }

        validatorPay.validate(payment);

        member = memberRepository.findOne(payment.getMember()
            .getNumber());
        fees = feeRepository.save(payment.getMember()
            .getNumber(), payment.getFeeDates());
        feeRepository.pay(member.get(), fees, payment.getTransaction()
            .getDate());

        return feeRepository.findAll(payment.getMember()
            .getNumber(), payment.getFeeDates());
    }

}
