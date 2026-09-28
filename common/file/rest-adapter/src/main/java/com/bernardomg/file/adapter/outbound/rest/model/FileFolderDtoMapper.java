/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.adapter.outbound.rest.model;

import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.file.adapter.outbound.rest.dto.FileFolderDto;

public final class FileFolderDtoMapper {

    public static FileFolderDto toDto(final AssetFolder folder) {
        return new FileFolderDto().number(folder.number())
            .name(folder.name())
            .parentNumber(folder.parentNumber()
                .orElse(null));
    }

    private FileFolderDtoMapper() {
        super();
    }

}
