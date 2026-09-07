/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.inbound.jpa.model;

import com.bernardomg.image.domain.model.Image;

public final class ImageEntityMapper {

    public static Image toDomain(final ImageEntity entity) {
        return new Image(entity.getNumber(), entity.getName(), entity.getDescription(), entity.getKey(),
            entity.getMediaType(), entity.getSize());
    }

    public static ImageEntity toEntity(final Image image) {
        final ImageEntity entity;

        entity = new ImageEntity();
        entity.setNumber(image.number());
        entity.setName(image.name());
        entity.setDescription(image.description());
        entity.setKey(image.key());
        entity.setMediaType(image.mediaType());
        entity.setSize(image.size());

        return entity;
    }

    private ImageEntityMapper() {
        super();
    }
}
