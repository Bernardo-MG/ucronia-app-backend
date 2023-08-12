
package com.bernardomg.association.fee.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.mapper.FeeMapper;
import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.model.request.FeesPayment;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.fee.validation.CreateFeeValidator;
import com.bernardomg.association.fee.validation.UpdateFeeValidator;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
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

    private static final String          CACHE_CALENDAR = "fee_calendar";

    private static final String          CACHE_MULTIPLE = "fees";

    private static final String          CACHE_SINGLE   = "fee";

    private final FeeRepository          feeRepository;

    private final FeeMapper              mapper;

    private final MemberFeeRepository    memberFeeRepository;

    private final MemberRepository       memberRepository;

    private final TransactionRepository  transactionRepository;

    private final Validator<FeesPayment> validatorCreate;

    private final Validator<FeeUpdate>   validatorUpdate;

    public DefaultFeeService(final FeeRepository feeRepo, final TransactionRepository transactionRepo,
            final MemberFeeRepository memberFeeRepo, final MemberRepository memberRepo, final FeeMapper mpper) {
        super();

        feeRepository = feeRepo;
        transactionRepository = transactionRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        mapper = mpper;

        // TODO: Test validation
        validatorCreate = new CreateFeeValidator(memberRepository, feeRepository);
        validatorUpdate = new UpdateFeeValidator(memberRepository);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:DELETE')")
    @Caching(evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR }, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {
        if (!feeRepository.existsById(id)) {
            throw new InvalidIdException("fee", id);
        }

        feeRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<MemberFee> getAll(final FeeQuery request, final Pageable pageable) {
        final Page<PersistentMemberFee>                    page;
        final Optional<Specification<PersistentMemberFee>> spec;
        // TODO: Test repository
        // TODO: Test reading with no name or surname

        spec = MemberFeeSpecifications.fromRequest(request);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<MemberFee> getOne(final long id) {
        final Optional<PersistentMemberFee> found;
        final Optional<MemberFee>           result;

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
    @PreAuthorize("hasAuthority('FEE:CREATE')")
    @Caching(evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR, CACHE_SINGLE }, allEntries = true) })
    @Transactional
    public final Collection<? extends MemberFee> payFees(final FeesPayment payment) {
        final PersistentTransaction     transaction;
        final Collection<PersistentFee> fees;

        log.debug("Paying fees for member with id {}. Months paid: {}", payment.getMemberId(), payment.getFeeDates());

        validatorCreate.validate(payment);

        // Register transaction
        transaction = new PersistentTransaction();
        transaction.setAmount(payment.getAmount());
        transaction.setDate(payment.getPaymentDate());
        transaction.setDescription(payment.getDescription());

        // Register fees
        transactionRepository.save(transaction);

        fees = payment.getFeeDates()
            .stream()
            .map(date -> {
                final PersistentFee fee;

                fee = new PersistentFee();
                fee.setMemberId(payment.getMemberId());
                fee.setDate(date);
                fee.setPaid(true);

                return fee;
            })
            .toList();

        feeRepository.saveAll(fees);

        // TODO: Doesn't return names
        return fees.stream()
            .map(mapper::toDto)
            .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = { CACHE_MULTIPLE, CACHE_CALENDAR }, allEntries = true) })
    public final MemberFee update(final long id, final FeeUpdate form) {
        final PersistentFee entity;
        final PersistentFee created;

        if (!feeRepository.existsById(id)) {
            throw new InvalidIdException("fee", id);
        }

        validatorUpdate.validate(form);

        entity = mapper.toEntity(form);
        entity.setId(id);

        created = feeRepository.save(entity);

        // TODO: Doesn't return names
        return mapper.toDto(created);
    }

}
