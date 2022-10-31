
package com.bernardomg.security.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoPrivilege;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.persistence.model.PersistentPrivilege;
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultPrivilegeService implements PrivilegeService {

    private final PrivilegeRepository repository;

    @Override
    public final Iterable<? extends Privilege> getAll(final Privilege sample, final Pageable pageable) {
        final PersistentPrivilege       entity;
        final List<Privilege>           dtos;
        final Page<PersistentPrivilege> read;

        entity = toEntity(sample);
        read = repository.findAll(Example.of(entity), pageable);
        dtos = read.stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(dtos, pageable, read::getTotalElements);
    }

    @Override
    public final Optional<? extends Privilege> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    private final Privilege toDto(final PersistentPrivilege entity) {
        final DtoPrivilege data;

        data = new DtoPrivilege();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentPrivilege toEntity(final Privilege data) {
        final PersistentPrivilege entity;

        entity = new PersistentPrivilege();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
