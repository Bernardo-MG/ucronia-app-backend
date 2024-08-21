
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
import com.bernardomg.association.fee.domain.model.FeePerson;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaFeeRepository implements FeeRepository {

    private final FeePaymentSpringRepository  feePaymentSpringRepository;

    private final FeeSpringRepository         feeSpringRepository;

    private final MemberFeeSpringRepository   memberFeeSpringRepository;

    private final MemberSpringRepository      memberSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    private final TransactionSpringRepository transactionSpringRepository;

    public JpaFeeRepository(final FeeSpringRepository feeSpringRepo,
            final MemberFeeSpringRepository memberFeeSpringRepo, final PersonSpringRepository personSpringRepo,
            final MemberSpringRepository memberSpringRepo, final FeePaymentSpringRepository feePaymentSpringRepo,
            final TransactionSpringRepository transactionSpringRepo) {
        super();

        feeSpringRepository = feeSpringRepo;
        memberFeeSpringRepository = memberFeeSpringRepo;
        personSpringRepository = personSpringRepo;
        memberSpringRepository = memberSpringRepo;
        feePaymentSpringRepository = feePaymentSpringRepo;
        transactionSpringRepository = transactionSpringRepo;
    }

    @Override
    public final void delete(final Long number, final YearMonth date) {
        final Optional<PersonEntity> person;

        log.debug("Deleting fee for member {} in date {}", number, date);

        person = personSpringRepository.findByNumber(number);
        if (person.isPresent()) {
            feeSpringRepository.deleteByPersonIdAndDate(person.get()
                .getId(), date);

            log.debug("Deleted fee for member {} in date {}", number, date);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete fee for member {} in date {}, as the member doesn't exist", number, date);
        }
    }

    @Override
    public final boolean exists(final Long number, final YearMonth date) {
        final boolean exists;

        log.debug("checking a fee exists for member {} in date {}", number, date);

        exists = memberFeeSpringRepository.existsByPersonNumberAndDate(number, date);

        log.debug("Fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final boolean existsPaid(final Long number, final YearMonth date) {
        final boolean exists;

        log.debug("checking a paid fee exists for member {} in date {}", number, date);

        // TODO: the boolean is not needed
        exists = memberFeeSpringRepository.existsByPersonNumberAndDateAndPaid(number, date, true);

        log.debug("Paid fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        final Iterable<Fee>                            found;
        // TODO: Test reading with no first or last name

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
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for active members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY);
        end = YearMonth.of(year.getValue(), Month.DECEMBER);

        foundIds = memberSpringRepository.findAllActivePersonIds();

        log.debug("Active members: {}", foundIds);

        found = memberFeeSpringRepository.findAllInRangeForPersonsIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for active members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForInactiveMembers(final Year year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for inactive members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY);
        end = YearMonth.of(year.getValue(), Month.DECEMBER);

        foundIds = memberSpringRepository.findAllInactivePersonIds();

        log.debug("Inactive members: {}", foundIds);

        found = memberFeeSpringRepository.findAllInRangeForPersonsIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for inactive members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Iterable<Fee> findAllForMember(final Long number, final Pageable pageable) {
        final Iterable<Fee> found;

        log.debug("Finding all fees for member {} and pagination {}", number, pageable);

        found = memberFeeSpringRepository.findAllByPersonNumber(number, pageable)
            .map(this::toDomain);

        log.debug("Found all fees for member {} and pagination {}: {}", number, pageable, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForMemberInDates(final Long number, final Collection<YearMonth> feeDates) {
        final Collection<Fee> fees;

        log.debug("Finding all fees for member {} in dates {}", number, feeDates);

        fees = feeSpringRepository.findAllByPersonNumberAndDateIn(number, feeDates)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for member {} in dates {}: {}", number, feeDates, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllForPreviousMonth() {
        final YearMonth       previousMonth;
        final Collection<Fee> fees;

        previousMonth = YearMonth.now()
            .minusMonths(1);

        log.debug("Finding all fees for the previous month: {}", previousMonth);

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
    public final Optional<Fee> findOne(final Long number, final YearMonth date) {
        final Optional<Fee> found;

        log.debug("Finding fee for member {} in date {}", number, date);

        found = memberFeeSpringRepository.findByPersonNumberAndDate(number, date)
            .map(this::toDomain);

        log.debug("Found fee for member {} in date {}: {}", number, date, found);

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
    public final void pay(final Person person, final Collection<Fee> fees, final Transaction transaction) {
        final long                        transactionId;
        final Iterable<FeePaymentEntity>  payments;
        final Collection<MemberFeeEntity> read;
        final Collection<YearMonth>       feeDates;

        log.debug("Paying fees for {}, using fees {} and transaction {}", person.getNumber(), fees, transaction);

        feeDates = fees.stream()
            .map(Fee::getDate)
            .toList();

        // TODO: just return the id
        transactionId = transactionSpringRepository.findByIndex(transaction.getIndex())
            .get()
            .getId();
        read = memberFeeSpringRepository.findAllByPersonNumberAndDateIn(person.getNumber(), feeDates);

        // Register payments
        payments = read.stream()
            .map(MemberFeeEntity::getId)
            .map(id -> FeePaymentEntity.builder()
                .withFeeId(id)
                .withTransactionId(transactionId)
                .build())
            .toList();
        feePaymentSpringRepository.saveAll(payments);

        transactionSpringRepository.flush();
        feePaymentSpringRepository.flush();
        memberFeeSpringRepository.flush();

        log.debug("Paid fees for {}, using fees {} and transaction {}", person.getNumber(), fees, transaction);
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;
        final Collection<Fee>       saved;

        log.debug("Saving fees {}", fees);

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        // TODO: use map
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
        read = feeSpringRepository.findByPersonIdAndDate(fee.getPerson()
            .getId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final Fee toDomain(final FeeEntity entity) {
        final FeePerson      feePerson;
        final FeeTransaction feeTransaction;
        final String         name;

        name = (entity.getPerson()
            .getFirstName() + " "
                + entity.getPerson()
                    .getLastName()).trim();
        feePerson = FeePerson.builder()
            .withFullName(name)
            .withNumber(entity.getPerson()
                .getNumber())
            .build();

        feeTransaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPerson(feePerson)
            .withTransaction(feeTransaction)
            .build();
    }

    private final Fee toDomain(final MemberFee entity) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(entity.getPersonName())
            .withNumber(entity.getPersonNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withPerson(person)
            .withTransaction(transaction)
            .build();
    }

    private final Fee toDomain(final MemberFeeEntity entity) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(entity.getFullName())
            .withNumber(entity.getPersonNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withPerson(person)
            .withTransaction(transaction)
            .build();
    }

    private final FeeEntity toEntity(final Fee fee) {
        final PersonEntity person;

        person = personSpringRepository.findByNumber(fee.getPerson()
            .getNumber())
            .get();
        return FeeEntity.builder()
            .withPerson(person)
            .withDate(fee.getDate())
            .build();
    }

}
