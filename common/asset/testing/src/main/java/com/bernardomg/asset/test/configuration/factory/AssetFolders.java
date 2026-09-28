
package com.bernardomg.asset.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.AssetFolder;

public final class AssetFolders {

    public static AssetFolder child() {
        return new AssetFolder(AssetFolderConstants.CHILD_NUMBER, AssetFolderConstants.CHILD_NAME,
            Optional.of(AssetFolderConstants.NUMBER));
    }

    public static AssetFolder nameChange() {
        return new AssetFolder(AssetFolderConstants.NUMBER, AssetFolderConstants.ALTERNATIVE_NAME, Optional.empty());
    }

    public static AssetFolder parent() {
        return new AssetFolder(AssetFolderConstants.PARENT_NUMBER, "Parent", Optional.empty());
    }

    public static AssetFolder toCreate() {
        return new AssetFolder(-1L, AssetFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder valid() {
        return new AssetFolder(AssetFolderConstants.NUMBER, AssetFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder withParent() {
        return new AssetFolder(AssetFolderConstants.NUMBER, AssetFolderConstants.NAME,
            Optional.of(AssetFolderConstants.PARENT_NUMBER));
    }

    private AssetFolders() {
        super();
    }

}
