
package com.bernardomg.image.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.AssetFolder;

public final class ImageFolders {

    public static AssetFolder child() {
        return new AssetFolder(ImageFolderConstants.CHILD_NUMBER, ImageFolderConstants.CHILD_NAME,
            Optional.of(ImageFolderConstants.NUMBER));
    }

    public static AssetFolder nameChange() {
        return new AssetFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.ALTERNATIVE_NAME, Optional.empty());
    }

    public static AssetFolder parent() {
        return new AssetFolder(ImageFolderConstants.PARENT_NUMBER, "Parent", Optional.empty());
    }

    public static AssetFolder toCreate() {
        return new AssetFolder(-1L, ImageFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder valid() {
        return new AssetFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder withParent() {
        return new AssetFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.NAME,
            Optional.of(ImageFolderConstants.PARENT_NUMBER));
    }

    private ImageFolders() {
        super();
    }

}
