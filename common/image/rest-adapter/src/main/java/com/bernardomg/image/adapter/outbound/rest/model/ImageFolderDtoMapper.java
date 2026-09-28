/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.model;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderDto;

public final class ImageFolderDtoMapper {

    public static ImageFolderDto toDto(final AssetFolder folder) {
        return new ImageFolderDto().number(folder.number())
            .name(folder.name())
            .parentNumber(folder.parentNumber()
                .orElse(null));
    }

    private ImageFolderDtoMapper() {
        super();
    }

}
