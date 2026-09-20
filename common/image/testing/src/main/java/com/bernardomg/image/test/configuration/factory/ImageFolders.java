
package com.bernardomg.image.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.image.domain.model.ImageFolder;

public final class ImageFolders {

    public static ImageFolder child() {
        return new ImageFolder(ImageFolderConstants.CHILD_NUMBER, ImageFolderConstants.CHILD_NAME,
            Optional.of(ImageFolderConstants.NUMBER));
    }

    public static ImageFolder nameChange() {
        return new ImageFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.ALTERNATIVE_NAME, Optional.empty());
    }

    public static ImageFolder parent() {
        return new ImageFolder(ImageFolderConstants.PARENT_NUMBER, "Parent", Optional.empty());
    }

    public static ImageFolder toCreate() {
        return new ImageFolder(-1L, ImageFolderConstants.NAME, Optional.empty());
    }

    public static ImageFolder valid() {
        return new ImageFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.NAME, Optional.empty());
    }

    public static ImageFolder withParent() {
        return new ImageFolder(ImageFolderConstants.NUMBER, ImageFolderConstants.NAME,
            Optional.of(ImageFolderConstants.PARENT_NUMBER));
    }

    private ImageFolders() {
        super();
    }

}
