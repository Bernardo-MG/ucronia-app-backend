/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */
package com.bernardomg.image.adapter.outbound.rest.model;

import com.bernardomg.image.adapter.outbound.rest.dto.ImageDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImagePageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.image.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.image.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class ImageDtoMapper {
    public static ImageResponseDto toResponseDto(final Image image) {
        return new ImageResponseDto().content(new ImageDto().number(image.number()).name(image.name())
            .description(image.description()).mediaType(image.mediaType()).size(image.size()));
    }

    public static ImagePageResponseDto toResponseDto(final Page<Image> page) {
        final SortingDto sorting = new SortingDto().properties(page.sort().properties().stream()
            .map(ImageDtoMapper::toDto).toList());
        return new ImagePageResponseDto().content(page.content().stream().map(ImageDtoMapper::toDto).toList())
            .size(page.size()).page(page.page()).totalElements(page.totalElements()).totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage()).first(page.first()).last(page.last()).sort(sorting);
    }

    private static ImageDto toDto(final Image image) {
        return new ImageDto().number(image.number()).name(image.name()).description(image.description())
            .mediaType(image.mediaType()).size(image.size());
    }

    private static PropertyDto toDto(final Property property) {
        final DirectionEnum direction = property.direction() == Direction.ASC ? DirectionEnum.ASC : DirectionEnum.DESC;
        return new PropertyDto().name(property.name()).direction(direction);
    }
    private ImageDtoMapper() {
        super();
    }
}
