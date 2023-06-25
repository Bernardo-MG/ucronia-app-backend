
package com.bernardomg.association.fee.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.mapper.FeeMapper;
import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.fee.validation.CreateFeeValidator;
import com.bernardomg.association.fee.validation.UpdateFeeValidator;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.validation.Validator;

/**
 * Default implementation of the fee service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
public final class DefaultFeeService implements FeeService {

    private final FeeRepository        feeRepository;

    private final FeeMapper            mapper;

    private final MemberFeeRepository  memberFeeRepository;

    private final MemberRepository     memberRepository;

    private final Validator<FeeCreate> validatorCreate;

    private final Validator<FeeUpdate> validatorUpdate;

    public DefaultFeeService(final FeeRepository feeRepo, final MemberFeeRepository memberFeeRepo,
            final MemberRepository memberRepo, final FeeMapper mpper) {
        super();

        feeRepository = feeRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        mapper = mpper;

        // TODO: Test validation
        validatorCreate = new CreateFeeValidator(memberRepository);
        validatorUpdate = new UpdateFeeValidator(memberRepository);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:CREATE')")
    public final MemberFee create(final FeeCreate request) {
        final PersistentFee entity;
        final PersistentFee created;

        validatorCreate.validate(request);

        entity = mapper.toEntity(request);

        created = feeRepository.save(entity);

        // TODO: Doesn't return names
        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:DELETE')")
    public final Boolean delete(final Long id) {
        feeRepository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
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
    public final Optional<MemberFee> getOne(final Long id) {
        final Optional<PersistentMemberFee> found;
        final Optional<MemberFee>           result;

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
    @PreAuthorize("hasAuthority('FEE:UPDATE')")
    public final MemberFee update(final Long id, final FeeUpdate form) {
        final PersistentFee entity;
        final PersistentFee created;

        validatorUpdate.validate(form);

        entity = mapper.toEntity(form);
        entity.setId(id);

        created = feeRepository.save(entity);

        // TODO: Doesn't return names
        return mapper.toDto(created);
    }

}
