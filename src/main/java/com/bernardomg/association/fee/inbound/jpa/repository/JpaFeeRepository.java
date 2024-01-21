
package com.bernardomg.association.fee.inbound.jpa.repository;

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

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.inbound.jpa.model.MemberFee;
import com.bernardomg.association.fee.inbound.jpa.model.MemberFeeEntity;
import com.bernardomg.association.fee.inbound.jpa.specification.MemberFeeSpecifications;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.infra.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.infra.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.transaction.infra.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.infra.inbound.jpa.repository.TransactionSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaFeeRepository implements FeeRepository {

    private final AssociationConfigurationSource configurationSource;

    private final FeePaymentSpringRepository     feePaymentRepository;

    private final FeeSpringRepository            feeRepository;

    private final MemberFeeSpringRepository      memberFeeRepository;

    private final MemberSpringRepository         memberRepository;

    private final MessageSource                  messageSource;

    private final TransactionSpringRepository    transactionRepository;

    public JpaFeeRepository(final FeeSpringRepository feeRepo, final MemberFeeSpringRepository memberFeeRepo,
            final MemberSpringRepository memberRepo, final FeePaymentSpringRepository feePaymentRepo,
            final TransactionSpringRepository transactionRepo, final AssociationConfigurationSource configurationSrc,
            final MessageSource messageSrc) {
        super();

        feeRepository = feeRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        feePaymentRepository = feePaymentRepo;
        transactionRepository = transactionRepo;
        configurationSource = configurationSrc;
        messageSource = messageSrc;
    }

    @Override
    public final void delete(final Long memberNumber, final YearMonth date) {
        final Optional<MemberEntity> member;
        final Optional<FeeEntity>    fee;

        member = memberRepository.findByNumber(memberNumber);
        fee = feeRepository.findOneByMemberIdAndDate(member.get()
            .getId(), date);

        feeRepository.deleteById(fee.get()
            .getId());
    }

    @Override
    public final boolean exists(final Long memberNumber, final YearMonth date) {
        return memberFeeRepository.existsByMemberNumberAndDate(memberNumber, date);
    }

    @Override
    public final boolean existsPaid(final Long memberNumber, final YearMonth date) {
        return memberFeeRepository.existsByMemberNumberAndDateAndPaid(memberNumber, date, true);
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        // TODO: Test reading with no name or surname

        log.debug("Reading fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDomain);
    }

    @Override
    public final Collection<Fee> findAll(final Long memberNumber, final Collection<YearMonth> feeDates) {
        return feeRepository.findAllByMemberNumberAndDateIn(memberNumber, feeDates)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Fee> findOne(final Long memberNumber, final YearMonth date) {
        final Optional<MemberFeeEntity> read;

        read = memberFeeRepository.findOneByMemberNumberAndDate(memberNumber, date);

        return read.map(this::toDomain);
    }

    @Override
    public final void pay(final Member member, final Collection<Fee> fees, final LocalDate payDate) {
        final TransactionEntity           transaction;
        final Float                       feeAmount;
        final String                      name;
        final String                      dates;
        final String                      message;
        final Object[]                    messageArguments;
        final Long                        index;
        final Iterable<FeePaymentEntity>  payments;
        final Collection<MemberFeeEntity> read;
        final Collection<YearMonth>       feeDates;

        feeDates = fees.stream()
            .map(Fee::getDate)
            .toList();

        // Calculate amount
        feeAmount = configurationSource.getFeeAmount() * feeDates.size();

        // Register transaction
        transaction = new TransactionEntity();
        transaction.setAmount(feeAmount);
        transaction.setDate(payDate);

        index = transactionRepository.findNextIndex();
        transaction.setIndex(index);

        name = member.getName()
            .getFullName();

        dates = feeDates.stream()
            .map(f -> messageSource.getMessage("fee.payment.month." + f.getMonthValue(), null,
                LocaleContextHolder.getLocale()) + " " + f.getYear())
            .collect(Collectors.joining(", "));

        messageArguments = List.of(name, dates)
            .toArray();
        message = messageSource.getMessage("fee.payment.message", messageArguments, LocaleContextHolder.getLocale());
        transaction.setDescription(message);

        transactionRepository.save(transaction);

        read = memberFeeRepository.findAllByMemberNumberAndDateIn(member.getNumber(), feeDates);

        // Register payments
        payments = read.stream()
            .map(MemberFeeEntity::getId)
            .map(id -> FeePaymentEntity.builder()
                .feeId(id)
                .transactionId(transaction.getId())
                .build())
            .toList();
        feePaymentRepository.saveAll(payments);

        transactionRepository.flush();
        feePaymentRepository.flush();
        memberFeeRepository.flush();
    }

    @Override
    public final Collection<Fee> save(final Long memberNumber, final Collection<YearMonth> feeDates) {
        final Collection<FeeEntity>          fees;
        final Function<YearMonth, FeeEntity> toPersistentFee;
        final Optional<MemberEntity>         member;
        final Collection<FeeEntity>          created;

        member = memberRepository.findByNumber(memberNumber);

        // Register fees
        toPersistentFee = (date) -> toEntity(member.get()
            .getId(), date);
        fees = feeDates.stream()
            .map(toPersistentFee)
            .toList();

        // Update fees on fees to update
        fees.stream()
            .forEach(this::loadId);

        created = feeRepository.saveAll(fees);

        // TODO: Why?
        feeRepository.flush();

        return created.stream()
            .map(this::toDomain)
            .toList();
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

    private final Fee toDomain(final FeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .date(entity.getDate())
            .member(member)
            .transaction(transaction)
            .build();
    }

    private final Fee toDomain(final MemberFee entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(entity.getMemberName())
            .number(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .index(entity.getTransactionIndex())
            .date(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .date(entity.getDate())
            .paid(entity.getPaid())
            .member(member)
            .transaction(transaction)
            .build();
    }

    private final Fee toDomain(final MemberFeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(entity.getFullName())
            .number(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .index(entity.getTransactionIndex())
            .date(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .date(entity.getDate())
            .paid(entity.getPaid())
            .member(member)
            .transaction(transaction)
            .build();
    }

    private final FeeEntity toEntity(final Long memberId, final YearMonth date) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(memberId);
        fee.setDate(date);

        return fee;
    }

}
