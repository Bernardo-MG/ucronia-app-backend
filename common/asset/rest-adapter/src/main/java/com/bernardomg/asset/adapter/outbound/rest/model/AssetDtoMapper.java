/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.asset.adapter.outbound.rest.model;

import com.bernardomg.asset.adapter.outbound.rest.dto.AssetDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetPageResponseDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetResponseDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AuditDetailsDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AuditUserDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.asset.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class AssetDtoMapper {

    public static AssetResponseDto toResponseDto(final Asset asset) {
        return new AssetResponseDto().content(new AssetDto().number(asset.number())
            .name(asset.name())
            .description(asset.description())
            .folderNumber(asset.folderNumber()
                .orElse(null))
            .mediaType(asset.mediaType())
            .size(asset.size())
            .publicAccess(asset.publicAccess())
            .audit(toDto(asset.audit())));
    }

    public static AssetPageResponseDto toResponseDto(final Page<Asset> page) {
        final SortingDto sorting = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(AssetDtoMapper::toDto)
            .toList());
        return new AssetPageResponseDto().content(page.content()
            .stream()
            .map(AssetDtoMapper::toDto)
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

    private static AssetDto toDto(final Asset asset) {
        return new AssetDto().number(asset.number())
            .name(asset.name())
            .description(asset.description())
            .folderNumber(asset.folderNumber()
                .orElse(null))
            .mediaType(asset.mediaType())
            .size(asset.size())
            .publicAccess(asset.publicAccess())
            .audit(toDto(asset.audit()));
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

    private static PropertyDto toDto(final Property property) {
        final DirectionEnum direction = property.direction() == Direction.ASC ? DirectionEnum.ASC : DirectionEnum.DESC;
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private AssetDtoMapper() {
        super();
    }
}
