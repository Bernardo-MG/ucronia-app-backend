
package com.bernardomg.file.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.file.domain.model.FileFolder;

public final class FileFolders {

    public static FileFolder child() {
        return new FileFolder(FileFolderConstants.CHILD_NUMBER, FileFolderConstants.CHILD_NAME,
            Optional.of(FileFolderConstants.NUMBER));
    }

    public static FileFolder nameChange() {
        return new FileFolder(FileFolderConstants.NUMBER, FileFolderConstants.ALTERNATIVE_NAME, Optional.empty());
    }

    public static FileFolder parent() {
        return new FileFolder(FileFolderConstants.PARENT_NUMBER, "Parent", Optional.empty());
    }

    public static FileFolder toCreate() {
        return new FileFolder(-1L, FileFolderConstants.NAME, Optional.empty());
    }

    public static FileFolder valid() {
        return new FileFolder(FileFolderConstants.NUMBER, FileFolderConstants.NAME, Optional.empty());
    }

    public static FileFolder withParent() {
        return new FileFolder(FileFolderConstants.NUMBER, FileFolderConstants.NAME,
            Optional.of(FileFolderConstants.PARENT_NUMBER));
    }

    private FileFolders() {
        super();
    }

}
