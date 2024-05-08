
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MemberFee;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MemberFeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.specification.MemberFeeSpecifications;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaFeeRepository implements FeeRepository {

    private final ActiveMemberSpringRepository activeMemberSpringRepository;

    private final FeePaymentSpringRepository   feePaymentSpringRepository;

    private final FeeSpringRepository          feeSpringRepository;

    private final MemberFeeSpringRepository    memberFeeSpringRepository;

    private final MemberSpringRepository       memberSpringRepository;

    private final TransactionSpringRepository  transactionSpringRepository;

    public JpaFeeRepository(final FeeSpringRepository feeSpringRepo,
            final MemberFeeSpringRepository memberFeeSpringRepo, final MemberSpringRepository memberSpringRepo,
            final ActiveMemberSpringRepository activeMemberSpringRepo,
            final FeePaymentSpringRepository feePaymentSpringRepo,
            final TransactionSpringRepository transactionSpringRepo) {
        super();

        feeSpringRepository = feeSpringRepo;
        memberFeeSpringRepository = memberFeeSpringRepo;
        memberSpringRepository = memberSpringRepo;
        activeMemberSpringRepository = activeMemberSpringRepo;
        feePaymentSpringRepository = feePaymentSpringRepo;
        transactionSpringRepository = transactionSpringRepo;
    }

    @Override
    public final void delete(final Long memberNumber, final YearMonth date) {
        final Optional<MemberEntity> member;

        log.debug("Deleting fee for member {} in date {}", memberNumber, date);

        member = memberSpringRepository.findByNumber(memberNumber);
        if (member.isPresent()) {
            feeSpringRepository.deleteByMemberIdAndDate(member.get()
                .getId(), date);

            log.debug("Deleted fee for member {} in date {}", memberNumber, date);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete fee for member {} in date {}, as the member doesn't exist", memberNumber, date);
        }
    }

    @Override
    public final boolean exists(final Long memberNumber, final YearMonth date) {
        final boolean exists;

        log.debug("checking a fee exists for member {} in date {}", memberNumber, date);

        exists = memberFeeSpringRepository.existsByMemberNumberAndDate(memberNumber, date);

        log.debug("Fee exists for member {} in date {}: {}", memberNumber, date, exists);

        return exists;
    }

    @Override
    public final boolean existsPaid(final Long memberNumber, final YearMonth date) {
        final boolean exists;

        log.debug("checking a paid fee exists for member {} in date {}", memberNumber, date);

        // TODO: the boolean is not needed
        exists = memberFeeSpringRepository.existsByMemberNumberAndDateAndPaid(memberNumber, date, true);

        log.debug("Paid fee exists for member {} in date {}: {}", memberNumber, date, exists);

        return exists;
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        final Iterable<Fee>                            found;
        // TODO: Test reading with no name or surname

        log.debug("Finding all fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeSpringRepository.findAll(pageable);
        } else {
            page = memberFeeSpringRepository.findAll(spec.get(), pageable);
        }

        found = page.map(this::toDomain);

        log.debug("Found all fees with sample {} and pagination {}: {}", query, pageable, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForActiveMembers(final Year year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for active members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY);
        end = YearMonth.of(year.getValue(), Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberSpringRepository.findAllActiveIdsInRange(validStart, validEnd);

        found = memberFeeSpringRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for active members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForInactiveMembers(final Year year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for inactive members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY);
        end = YearMonth.of(year.getValue(), Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberSpringRepository.findAllInactiveIds(validStart, validEnd);

        found = memberFeeSpringRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for inactive members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Iterable<Fee> findAllForMember(final Long memberNumber, final Pageable pageable) {
        final Page<MemberFeeEntity> page;
        final Iterable<Fee>         found;

        log.debug("Finding all fees for member {} and pagination {}", memberNumber, pageable);

        page = memberFeeSpringRepository.findAllByMemberNumber(memberNumber, pageable);

        found = page.map(this::toDomain);

        log.debug("Found all fees for member {} and pagination {}: {}", memberNumber, pageable, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForMemberInDates(final Long memberNumber,
            final Collection<YearMonth> feeDates) {
        final Collection<Fee> fees;

        log.debug("Finding all fees for member {} in dates {}", memberNumber, feeDates);

        fees = feeSpringRepository.findAllByMemberNumberAndDateIn(memberNumber, feeDates)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for member {} in dates {}: {}", memberNumber, feeDates, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllForPreviousMonth() {
        final YearMonth       previousMonth;
        final Collection<Fee> fees;

        log.debug("Finding all fees for the previous month");

        previousMonth = YearMonth.now()
            .minusMonths(1);

        fees = memberFeeSpringRepository.findAllByDate(previousMonth)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for the previous month: {}", fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInMonth(final YearMonth date) {
        final Collection<Fee> fees;

        log.debug("Finding all fees in month {}", date);

        fees = memberFeeSpringRepository.findAllByDate(date)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees in month {}: {}", date, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInYear(final Year year, final Sort sort) {
        final YearMonth       start;
        final YearMonth       end;
        final Collection<Fee> fees;

        log.debug("Finding all fees in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY);
        end = YearMonth.of(year.getValue(), Month.DECEMBER);

        fees = memberFeeSpringRepository.findAllInRange(start, end, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees in year {}: {}", year, fees);

        return fees;
    }

    @Override
    public final Optional<Fee> findOne(final Long memberNumber, final YearMonth date) {
        final Optional<MemberFeeEntity> read;
        final Optional<Fee>             found;

        log.debug("Finding fee for member {} in date {}", memberNumber, date);

        read = memberFeeSpringRepository.findByMemberNumberAndDate(memberNumber, date);

        found = read.map(this::toDomain);

        log.debug("Found fee for member {} in date {}: {}", memberNumber, date, found);

        return found;
    }

    @Override
    public final FeeCalendarYearsRange findRange() {
        final Collection<Integer>   years;
        final FeeCalendarYearsRange range;

        log.debug("Finding fees range");

        years = memberFeeSpringRepository.findYears();
        range = FeeCalendarYearsRange.builder()
            .withYears(years)
            .build();

        log.debug("Found fees range: {}", range);

        return range;
    }

    @Override
    public final void pay(final Member member, final Collection<Fee> fees, final Transaction transaction) {
        final TransactionEntity           transactionEntity;
        final Iterable<FeePaymentEntity>  payments;
        final Collection<MemberFeeEntity> read;
        final Collection<YearMonth>       feeDates;

        log.debug("Paying fees for {}, using fees {} and transaction {}", member.getNumber(), fees, transaction);

        feeDates = fees.stream()
            .map(Fee::getDate)
            .toList();

        transactionEntity = transactionSpringRepository.findByIndex(transaction.getIndex())
            .get();
        read = memberFeeSpringRepository.findAllByMemberNumberAndDateIn(member.getNumber(), feeDates);

        // Register payments
        payments = read.stream()
            .map(MemberFeeEntity::getId)
            .map(id -> FeePaymentEntity.builder()
                .withFeeId(id)
                .withTransactionId(transactionEntity.getId())
                .build())
            .toList();
        feePaymentSpringRepository.saveAll(payments);

        transactionSpringRepository.flush();
        feePaymentSpringRepository.flush();
        memberFeeSpringRepository.flush();

        log.debug("Paid fees for {}, using fees {} and transaction {}", member.getNumber(), fees, transaction);
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;
        final Collection<Fee>       saved;

        log.debug("Saving fees {}", fees);

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        entities.forEach(this::loadId);
        saved = feeSpringRepository.saveAll(entities)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Saved fees {}", fees);

        return saved;
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        // TODO: optimize to use a single query
        read = feeSpringRepository.findByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final Fee toDomain(final FeeEntity entity) {
        final FeeMember              feeMember;
        final FeeTransaction         feeTransaction;
        final Optional<MemberEntity> member;
        final String                 name;

        member = memberSpringRepository.findById(entity.getMemberId());
        if (member.isEmpty()) {
            feeMember = FeeMember.builder()
                .build();
        } else {
            name = (member.get()
                .getPerson()
                .getName() + " "
                    + member.get()
                        .getPerson()
                        .getSurname()).trim();
            feeMember = FeeMember.builder()
                .withFullName(name)
                .withNumber(member.get()
                    .getPerson()
                    .getNumber())
                .build();
        }

        feeTransaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withMember(feeMember)
            .withTransaction(feeTransaction)
            .build();
    }

    private final Fee toDomain(final MemberFee entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(entity.getMemberName())
            .withNumber(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    private final Fee toDomain(final MemberFeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(entity.getFullName())
            .withNumber(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    private final FeeEntity toEntity(final Fee fee) {
        final long id;

        id = memberSpringRepository.findByNumber(fee.getMember()
            .getNumber())
            .get()
            .getId();
        return FeeEntity.builder()
            .withMemberId(id)
            .withDate(fee.getDate())
            .build();
    }

}
