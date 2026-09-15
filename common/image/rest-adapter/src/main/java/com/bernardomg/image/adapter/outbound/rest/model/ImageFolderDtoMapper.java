/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.model;

import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderDto;
import com.bernardomg.image.domain.model.ImageFolder;

public final class ImageFolderDtoMapper {

    public static ImageFolderDto toDto(final ImageFolder folder) {
        return new ImageFolderDto().number(folder.number())
            .name(folder.name())
            .parentNumber(folder.parentNumber()
                .orElse(null));
    }

    private ImageFolderDtoMapper() {
        super();
    }

}
