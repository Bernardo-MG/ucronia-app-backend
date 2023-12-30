
package com.bernardomg.association.membership.fee.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.exception.MissingFeeIdException;
import com.bernardomg.association.membership.fee.model.FeesPayment;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.fee.validation.CreateFeeValidator;
import com.bernardomg.association.membership.fee.validation.UpdateFeeValidator;
import com.bernardomg.association.membership.member.existence.MissingMemberIdException;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
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

    private final AssociationConfigurationSource configurationSource;

    private final FeeRepository                  feeRepository;

    private final MemberFeeRepository            memberFeeRepository;

    private final MemberRepository               memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionRepository          transactionRepository;

    private final Validator<FeesPayment>         validatorPay;

    private final Validator<FeeUpdate>           validatorUpdate;

    public DefaultFeeService(final MessageSource msgSource, final FeeRepository feeRepo,
            final TransactionRepository transactionRepo, final MemberFeeRepository memberFeeRepo,
            final MemberRepository memberRepo, final AssociationConfigurationSource confSource) {
        super();

        messageSource = msgSource;
        feeRepository = feeRepo;
        transactionRepository = transactionRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        configurationSource = confSource;

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(feeRepository);
        validatorUpdate = new UpdateFeeValidator(memberRepository);
    }

    @Override
    public final void delete(final long memberId, final YearMonth date) {
        final Optional<PersistentFee> fee;

        log.debug("Deleting fee for {} in {}", memberId, date);

        fee = feeRepository.findOneByMemberIdAndDate(memberId, date);

        if (fee.isEmpty()) {
            throw new MissingFeeIdException(memberId + " " + date.toString());
        }

        feeRepository.deleteById(fee.get()
            .getId());
    }

    @Override
    public final Iterable<MemberFee> getAll(final FeeQuery query, final Pageable pageable) {
        final Page<PersistentMemberFee>                    page;
        final Optional<Specification<PersistentMemberFee>> spec;
        // TODO: Test reading with no name or surname

        log.debug("Reading fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    public final Optional<MemberFee> getOne(final long memberId, final YearMonth date) {
        final Optional<PersistentMemberFee> found;
        final Optional<PersistentFee>       fee;

        log.debug("Reading fee for {} in {}", memberId, date);

        fee = feeRepository.findOneByMemberIdAndDate(memberId, date);

        if (fee.isEmpty()) {
            throw new MissingFeeIdException(memberId + " " + date.toString());
        }

        found = memberFeeRepository.findById(fee.get()
            .getId());

        return found.map(this::toDto);
    }

    @Override
    public final Collection<MemberFee> payFees(final long memberNumber, final LocalDate payDate,
            final Collection<YearMonth> feeDates) {
        final Collection<PersistentFee> fees;
        final Collection<Long>          ids;
        final FeesPayment               payment;

        log.debug("Paying fees for {} in {}. Months paid: {}", memberNumber, payDate, feeDates);

        if (!memberRepository.existsById(memberNumber)) {
            throw new MissingMemberIdException(memberNumber);
        }

        payment = FeesPayment.builder()
            .memberNumber(memberNumber)
            .feeDates(feeDates)
            .build();
        validatorPay.validate(payment);

        registerTransaction(memberNumber, payDate, feeDates);
        fees = registerFees(memberNumber, feeDates);

        // Read fees to return names
        feeRepository.flush();
        ids = fees.stream()
            .map(PersistentFee::getId)
            .toList();
        return readAll(ids);
    }

    @Override
    public final MemberFee update(final long memberId, final YearMonth date, final FeeUpdate fee) {
        final Optional<PersistentFee> found;
        final PersistentFee           entity;
        final PersistentFee           updated;
        final Optional<MemberFee>     read;
        final MemberFee               result;

        log.debug("Updating fee for {} in {} using data {}", memberId, date, fee);

        found = feeRepository.findOneByMemberIdAndDate(memberId, date);
        if (found.isEmpty()) {
            throw new MissingFeeIdException(memberId + " " + date.toString());
        }

        validatorUpdate.validate(fee);

        entity = toEntity(fee);
        entity.setId(found.get()
            .getId());

        updated = feeRepository.save(entity);

        // Read updated fee with name
        // FIXME: read directly from the repository
        read = getOne(updated.getMemberId(), updated.getDate());
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = MemberFee.builder()
                .build();
        }

        return result;
    }

    private final void loadId(final PersistentFee fee) {
        final Long                    id;
        final Optional<PersistentFee> read;

        read = feeRepository.findOneByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final List<MemberFee> readAll(final Collection<Long> ids) {
        final List<PersistentMemberFee> found;

        found = memberFeeRepository.findAllById(ids);

        return found.stream()
            .map(this::toDto)
            .toList();
    }

    private final Collection<PersistentFee> registerFees(final Long memberId, final Collection<YearMonth> feeDates) {
        final Collection<PersistentFee>          fees;
        final Function<YearMonth, PersistentFee> toPersistentFee;

        // Register fees
        toPersistentFee = (date) -> toPersistentFee(memberId, date);
        fees = feeDates.stream()
            .map(toPersistentFee)
            .toList();

        // Update fees on fees to update
        fees.stream()
            .forEach(this::loadId);

        return feeRepository.saveAll(fees);
    }

    private final void registerTransaction(final Long memberId, final LocalDate payDate,
            final Collection<YearMonth> feeDates) {
        final PersistentTransaction transaction;
        final Float                 feeAmount;
        final MemberEntity          member;
        final String                name;
        final String                dates;
        final String                message;
        final Object[]              messageArguments;
        final Long                  index;

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * feeDates.size();

        // Register transaction
        transaction = new PersistentTransaction();
        transaction.setAmount(feeAmount);
        transaction.setDate(payDate);

        index = transactionRepository.findNextIndex();
        transaction.setIndex(index);

        member = memberRepository.findById(memberId)
            .get();

        name = List.of(member.getName(), member.getSurname())
            .stream()
            .collect(Collectors.joining(" "))
            .trim();

        dates = feeDates.stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());
        transaction.setDescription(message);

        transactionRepository.save(transaction);
    }

    private final MemberFee toDto(final PersistentMemberFee entity) {
        return MemberFee.builder()
            .memberNumber(entity.getMemberId())
            .memberName(entity.getMemberName())
            .date(entity.getDate())
            .paid(entity.getPaid())
            .build();
    }

    private final PersistentFee toEntity(final FeeUpdate update) {
        return PersistentFee.builder()
            .memberId(update.getMemberId())
            .date(update.getDate())
            .paid(update.getPaid())
            .build();
    }

    private final PersistentFee toPersistentFee(final Long memberId, final YearMonth date) {
        final PersistentFee fee;

        fee = new PersistentFee();
        fee.setMemberId(memberId);
        fee.setDate(date);
        fee.setPaid(true);

        return fee;
    }

}
