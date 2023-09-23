
package com.bernardomg.association.membership.fee.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.mapper.FeeMapper;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.model.request.FeesPayment;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.fee.validation.CreateFeeValidator;
import com.bernardomg.association.membership.fee.validation.UpdateFeeValidator;
import com.bernardomg.association.membership.member.persistence.model.PersistentMember;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.InvalidIdException;
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

    private static final String                  CACHE_CALENDAR = "fee_calendar";

    private static final String                  CACHE_MULTIPLE = "fees";

    private static final String                  CACHE_SINGLE   = "fee";

    private final AssociationConfigurationSource configurationSource;

    private final FeeRepository                  feeRepository;

    private final FeeMapper                      mapper;

    private final MemberFeeRepository            memberFeeRepository;

    private final MemberRepository               memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionRepository          transactionRepository;

    private final Validator<FeesPayment>         validatorPay;

    private final Validator<FeeUpdate>           validatorUpdate;

    public DefaultFeeService(final MessageSource msgSource, final FeeRepository feeRepo,
            final TransactionRepository transactionRepo, final MemberFeeRepository memberFeeRepo,
            final MemberRepository memberRepo, final FeeMapper mpper, final AssociationConfigurationSource confSource) {
        super();

        messageSource = msgSource;
        feeRepository = feeRepo;
        transactionRepository = transactionRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        mapper = mpper;
        configurationSource = confSource;

        // TODO: Test validation
        validatorPay = new CreateFeeValidator(memberRepository, feeRepository);
        validatorUpdate = new UpdateFeeValidator(memberRepository);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR }, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        log.debug("Deleting fee {}", id);

        if (!feeRepository.existsById(id)) {
            throw new InvalidIdException("fee", id);
        }

        feeRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<MemberFee> getAll(final FeeQuery query, final Pageable pageable) {
        final Page<PersistentMemberFee>                    page;
        final Optional<Specification<PersistentMemberFee>> spec;
        // TODO: Test repository
        // TODO: Test reading with no name or surname

        log.debug("Reading fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromRequest(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(mapper::toDto);
    }

    @Override
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<MemberFee> getOne(final long id) {
        final Optional<PersistentMemberFee> found;
        final Optional<MemberFee>           result;

        log.debug("Reading fee with id {}", id);

        if (!feeRepository.existsById(id)) {
            throw new InvalidIdException("fee", id);
        }

        // TODO: Test repository
        // TODO: Test reading with no name or surname
        found = memberFeeRepository.findById(id);

        if (found.isPresent()) {
            result = found.map(mapper::toDto);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR, CACHE_SINGLE }, allEntries = true) })
    @Transactional
    public final Collection<? extends MemberFee> payFees(final FeesPayment payment) {
        final Collection<PersistentFee> fees;

        log.debug("Paying fees for member with id {}. Months paid: {}", payment.getMemberId(), payment.getFeeDates());

        validatorPay.validate(payment);

        registerTransaction(payment);
        fees = registerFees(payment);

        // TODO: Doesn't return names
        return fees.stream()
            .map(mapper::toDto)
            .toList();
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR }, allEntries = true) })
    public final MemberFee update(final long id, final FeeUpdate fee) {
        final PersistentFee entity;
        final PersistentFee updated;

        log.debug("Updating fee with id {} using data {}", id, fee);

        if (!feeRepository.existsById(id)) {
            throw new InvalidIdException("fee", id);
        }

        validatorUpdate.validate(fee);

        entity = mapper.toEntity(fee);
        entity.setId(id);

        updated = feeRepository.save(entity);

        // TODO: Doesn't return names
        return mapper.toDto(updated);
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

    private final Collection<PersistentFee> registerFees(final FeesPayment payment) {
        final Collection<PersistentFee>          fees;
        final Function<YearMonth, PersistentFee> toPersistentFee;

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
        final PersistentMember      member;
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

    private final PersistentFee toPersistentFee(final Long memberId, final YearMonth date) {
        final PersistentFee fee;

        fee = new PersistentFee();
        fee.setMemberId(memberId);
        fee.setDate(date);
        fee.setPaid(true);

        return fee;
    }

}