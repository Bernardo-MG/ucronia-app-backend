
package com.bernardomg.image.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bernardomg.asset.adapter.outbound.rest.model.ContentDtoMapper;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.usecase.service.AssetService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageMetadataUpdateDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImagePageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.model.ImageDtoMapper;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class ImageController implements ImageApi {

    private final AssetReadAuthorizer authorizer;

    private final AssetService        service;

    public ImageController(@Qualifier("imageService") final AssetService imageService,
            final AssetReadAuthorizer imageReadAuthorizer) {
        service = Objects.requireNonNull(imageService);
        authorizer = Objects.requireNonNull(imageReadAuthorizer);

        // TODO: why is it returning ResponseEntity?
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSETS", action = Actions.CREATE)
    public ResponseEntity<ImageResponseDto> createImage(final String name, final String description,
            final Boolean publicAccess, final MultipartFile file) {
        final Content          content;
        final ImageResponseDto response;
        final Asset            image;

        content = ContentDtoMapper.toContent(file);
        image = new Asset(-1L, name, description, "", content.mediaType(), content.size(),
            !Boolean.FALSE.equals(publicAccess), Optional.empty());
        response = ImageDtoMapper.toResponseDto(service.create(image, content));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSETS", action = Actions.DELETE)
    public ResponseEntity<ImageResponseDto> deleteImage(final Long number) {
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImagePageResponseDto> getAllImages(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Sorting     sorting;
        final Page<Asset> images;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        if (authorizer.canReadPrivate()) {
            images = service.getAll(pagination, sorting);
        } else {
            images = service.getAllPublic(pagination, sorting);
        }
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(images));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImageResponseDto> getImage(final Long number) {
        final Asset image;

        image = service.getOne(number);
        authorizer.checkCanRead(image);
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(image));
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSETS", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> updateImage(final Long number, final String name, final String description,
            final Boolean publicAccess, final MultipartFile file) {
        final Content          content;
        final ImageResponseDto response;

        content = ContentDtoMapper.toContent(file);
        response = ImageDtoMapper.toResponseDto(service.update(new Asset(number, name, description, "",
            content.mediaType(), content.size(), publicAccess, Optional.empty()), content));
        return ResponseEntity.ok(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSETS", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> updateImageMetadata(final Long number,
            final ImageMetadataUpdateDto imageMetadataUpdateDto) {
        final Asset updated;
        final Asset image;

        image = ImageDtoMapper.toDomain(number, imageMetadataUpdateDto);
        updated = service.updateMetadata(image);
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(updated));
    }

}
