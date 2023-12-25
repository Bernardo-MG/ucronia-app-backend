
package com.bernardomg.association.membership.fee.service;

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
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.model.request.FeesPayment;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.fee.validation.CreateFeeValidator;
import com.bernardomg.association.membership.fee.validation.UpdateFeeValidator;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.MissingIdException;
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
        validatorPay = new CreateFeeValidator(memberRepository, feeRepository);
        validatorUpdate = new UpdateFeeValidator(memberRepository);
    }

    @Override
    public final void delete(final Long memberId, final YearMonth date) {
        final Optional<FeeEntity> fee;

        log.debug("Deleting fee for {} in {}", memberId, date);

        fee = feeRepository.findOneByMemberIdAndDate(memberId, date);

        if (fee.isEmpty()) {
            // TODO: use more concrete exception
            throw new MissingIdException("fee", memberId + " " + date.toString());
        }

        feeRepository.deleteById(fee.get()
            .getId());
    }

    @Override
    public final Iterable<MemberFee> getAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        // TODO: Test repository
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
    public final Optional<MemberFee> getOne(final long id) {
        final Optional<MemberFeeEntity> found;
        final Optional<MemberFee>       result;

        log.debug("Reading fee with id {}", id);

        if (!feeRepository.existsById(id)) {
            throw new MissingIdException("fee", id);
        }

        found = memberFeeRepository.findById(id);

        if (found.isPresent()) {
            result = found.map(this::toDto);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Collection<? extends MemberFee> payFees(final FeesPayment payment) {
        final Collection<FeeEntity> fees;
        final Collection<Long>      ids;

        log.debug("Paying fees for member with id {}. Months paid: {}", payment.getMemberId(), payment.getFeeDates());

        validatorPay.validate(payment);

        registerTransaction(payment);
        fees = registerFees(payment);

        // Read fees to return names
        feeRepository.flush();
        ids = fees.stream()
            .map(FeeEntity::getId)
            .toList();
        return readAll(ids);
    }

    @Override
    public final MemberFee update(final long id, final FeeUpdate fee) {
        final FeeEntity           entity;
        final FeeEntity           updated;
        final Optional<MemberFee> read;
        final MemberFee           result;

        log.debug("Updating fee with id {} using data {}", id, fee);

        if (!feeRepository.existsById(id)) {
            throw new MissingIdException("fee", id);
        }

        validatorUpdate.validate(fee);

        entity = toEntity(fee);
        entity.setId(id);

        updated = feeRepository.save(entity);

        // Read updated fee with name
        read = getOne(updated.getId());
        if (read.isPresent()) {
            result = read.get();
        } else {
            result = MemberFee.builder()
                .build();
        }

        return result;
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        read = feeRepository.findOneByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final List<MemberFee> readAll(final Collection<Long> ids) {
        final List<MemberFeeEntity> found;

        found = memberFeeRepository.findAllById(ids);

        return found.stream()
            .map(this::toDto)
            .toList();
    }

    private final Collection<FeeEntity> registerFees(final FeesPayment payment) {
        final Collection<FeeEntity>          fees;
        final Function<YearMonth, FeeEntity> toPersistentFee;

        // Register fees
        toPersistentFee = (date) -> toPersistentFee(payment.getMemberId(), date);
        fees = payment.getFeeDates()
            .stream()
            .map(toPersistentFee)
            .toList();

        // Update fees on fees to update
        fees.stream()
            .forEach(this::loadId);

        return feeRepository.saveAll(fees);
    }

    private final void registerTransaction(final FeesPayment payment) {
        final PersistentTransaction transaction;
        final Float                 feeAmount;
        final MemberEntity          member;
        final String                name;
        final String                dates;
        final String                message;
        final Object[]              messageArguments;

        validatorPay.validate(payment);

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * payment.getFeeDates()
            .size();

        // Register transaction
        transaction = new PersistentTransaction();
        transaction.setAmount(feeAmount);
        transaction.setDate(payment.getPaymentDate());

        member = memberRepository.findById(payment.getMemberId())
            .get();

        name = List.of(member.getName(), member.getSurname())
            .stream()
            .collect(Collectors.joining(" "))
            .trim();

        dates = payment.getFeeDates()
            .stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());
        transaction.setDescription(message);

        transactionRepository.save(transaction);
    }

    private final MemberFee toDto(final MemberFeeEntity entity) {
        return MemberFee.builder()
            .id(entity.getId())
            .memberId(entity.getMemberId())
            .memberName(entity.getMemberName())
            .date(entity.getDate())
            .paid(entity.getPaid())
            .build();
    }

    private final FeeEntity toEntity(final FeeUpdate update) {
        return FeeEntity.builder()
            .memberId(update.getMemberId())
            .date(update.getDate())
            .paid(update.getPaid())
            .build();
    }

    private final FeeEntity toPersistentFee(final Long memberId, final YearMonth date) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(memberId);
        fee.setDate(date);
        fee.setPaid(true);

        return fee;
    }

}
