
package com.bernardomg.file.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.asset.domain.model.AssetFolder;

public final class FileFolders {

    public static AssetFolder child() {
        return new AssetFolder(FileFolderConstants.CHILD_NUMBER, FileFolderConstants.CHILD_NAME,
            Optional.of(FileFolderConstants.NUMBER));
    }

    public static AssetFolder nameChange() {
        return new AssetFolder(FileFolderConstants.NUMBER, FileFolderConstants.ALTERNATIVE_NAME, Optional.empty());
    }

    public static AssetFolder parent() {
        return new AssetFolder(FileFolderConstants.PARENT_NUMBER, "Parent", Optional.empty());
    }

    public static AssetFolder toCreate() {
        return new AssetFolder(-1L, FileFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder valid() {
        return new AssetFolder(FileFolderConstants.NUMBER, FileFolderConstants.NAME, Optional.empty());
    }

    public static AssetFolder withParent() {
        return new AssetFolder(FileFolderConstants.NUMBER, FileFolderConstants.NAME,
            Optional.of(FileFolderConstants.PARENT_NUMBER));
    }

    private FileFolders() {
        super();
    }

}
