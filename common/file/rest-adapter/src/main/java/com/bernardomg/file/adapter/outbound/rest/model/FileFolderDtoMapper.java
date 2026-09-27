/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.adapter.outbound.rest.model;

import com.bernardomg.file.adapter.outbound.rest.dto.FileFolderDto;
import com.bernardomg.file.domain.model.FileFolder;

public final class FileFolderDtoMapper {

    public static FileFolderDto toDto(final FileFolder folder) {
        return new FileFolderDto().number(folder.number())
            .name(folder.name())
            .parentNumber(folder.parentNumber()
                .orElse(null));
    }

    private FileFolderDtoMapper() {
        super();
    }

}
