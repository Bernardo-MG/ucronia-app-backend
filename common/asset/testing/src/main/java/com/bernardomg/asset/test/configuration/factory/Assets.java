/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.asset.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.Asset;

public final class Assets {

    public static Asset change() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION,
            AssetConstants.CHANGE_KEY, AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length);
    }

    public static Asset nameChange() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.ALTERNATIVE_NAME, AssetConstants.DESCRIPTION,
            AssetConstants.KEY, AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length);
    }

    public static Asset patch() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION, "", "", 0);
    }

    public static Asset privateAccess() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION, AssetConstants.KEY,
            AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length, false, Optional.empty());
    }

    public static Asset privateAccessInFolder() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION, AssetConstants.KEY,
            AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length, false, Optional.of(AssetFolderConstants.NUMBER));
    }

    public static Asset publicAccess() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION, AssetConstants.KEY,
            AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length, true, Optional.empty());
    }

    public static Asset publicAccessInFolder() {
        return new Asset(AssetConstants.NUMBER, AssetConstants.NAME, AssetConstants.DESCRIPTION, AssetConstants.KEY,
            AssetConstants.PDF_MEDIA_TYPE, AssetConstants.DATA.length, true, Optional.of(AssetFolderConstants.NUMBER));
    }

    private Assets() {
        super();
    }
}
