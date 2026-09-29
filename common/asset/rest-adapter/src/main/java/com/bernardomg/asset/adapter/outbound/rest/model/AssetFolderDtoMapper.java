/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.adapter.outbound.rest.model;

import com.bernardomg.asset.adapter.outbound.rest.dto.AssetFolderDto;
import com.bernardomg.asset.domain.model.AssetFolder;

public final class AssetFolderDtoMapper {

    public static AssetFolderDto toDto(final AssetFolder folder) {
        return new AssetFolderDto().number(folder.number())
            .name(folder.name())
            .parentNumber(folder.parentNumber()
                .orElse(null));
    }

    private AssetFolderDtoMapper() {
        super();
    }

}
