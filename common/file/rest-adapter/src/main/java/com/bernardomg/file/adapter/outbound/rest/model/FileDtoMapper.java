/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.file.adapter.outbound.rest.model;

import com.bernardomg.file.adapter.outbound.rest.dto.AuditDetailsDto;
import com.bernardomg.file.adapter.outbound.rest.dto.AuditUserDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileMetadataUpdateDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FilePageResponseDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileResponseDto;
import com.bernardomg.file.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.file.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.file.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class FileDtoMapper {

    public static final File toDomain(final long number, final FileMetadataUpdateDto change) {
        return new File(number, change.getName(), change.getDescription(), "", "", 0, change.getPublicAccess(),
            java.util.Optional.empty());
    }

    public static FileResponseDto toResponseDto(final File file) {
        return new FileResponseDto().content(new FileDto().number(file.number())
            .name(file.name())
            .description(file.description())
            .folderNumber(file.folderNumber()
                .orElse(null))
            .mediaType(file.mediaType())
            .size(file.size())
            .publicAccess(file.publicAccess())
            .audit(toDto(file.audit())));
    }

    public static FilePageResponseDto toResponseDto(final Page<File> page) {
        final SortingDto sorting = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FileDtoMapper::toDto)
            .toList());
        return new FilePageResponseDto().content(page.content()
            .stream()
            .map(FileDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sorting);
    }

    private static AuditDetailsDto toDto(final AuditDetails audit) {
        final AuditDetailsDto dto;

        if (audit == null) {
            dto = null;
        } else {
            dto = new AuditDetailsDto().createdAt(audit.createdAt())
                .createdBy(toDto(audit.createdBy()))
                .updatedAt(audit.updatedAt())
                .updatedBy(toDto(audit.updatedBy()));
        }

        return dto;
    }

    private static AuditUserDto toDto(final AuditUser user) {
        final AuditUserDto dto;

        if (user == null) {
            dto = null;
        } else {
            dto = new AuditUserDto().email(user.email())
                .username(user.username())
                .name(user.name());
        }
        return dto;
    }

    private static FileDto toDto(final File file) {
        return new FileDto().number(file.number())
            .name(file.name())
            .description(file.description())
            .folderNumber(file.folderNumber()
                .orElse(null))
            .mediaType(file.mediaType())
            .size(file.size())
            .publicAccess(file.publicAccess())
            .audit(toDto(file.audit()));
    }

    private static PropertyDto toDto(final Property property) {
        final DirectionEnum direction = property.direction() == Direction.ASC ? DirectionEnum.ASC : DirectionEnum.DESC;
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private FileDtoMapper() {
        super();
    }
}
