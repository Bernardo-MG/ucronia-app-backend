
package com.bernardomg.image.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderCreationDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderUpdateDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImagePageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.model.ImageDtoMapper;
import com.bernardomg.image.adapter.outbound.rest.model.ImageFolderDtoMapper;
import com.bernardomg.image.adapter.outbound.rest.security.ImageReadAuthorizer;
import com.bernardomg.image.usecase.service.ImageFolderService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class ImageFolderController implements ImageFolderApi {

    private final ImageReadAuthorizer authorizer;

    private final ImageFolderService  service;

    public ImageFolderController(final ImageFolderService imageFolderService,
            final ImageReadAuthorizer imageReadAuthorizer) {
        service = Objects.requireNonNull(imageFolderService);
        authorizer = Objects.requireNonNull(imageReadAuthorizer);
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.CREATE)
    public ResponseEntity<ImageFolderDto> createImageFolder(final ImageFolderCreationDto request) {
        final AssetFolder    created;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        created = service.create(new AssetFolder(-1L, request.getName(), parentNumber));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ImageFolderDtoMapper.toDto(created));
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.DELETE)
    public ResponseEntity<ImageFolderDto> deleteImageFolder(final Long number) {
        return ResponseEntity.ok(ImageFolderDtoMapper.toDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImageFolderDto> getImageFolder(final Long number) {
        return ResponseEntity.ok(ImageFolderDtoMapper.toDto(service.getOne(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<List<ImageFolderDto>> getImageFolders() {
        return ResponseEntity.ok(service.getAll()
            .stream()
            .map(ImageFolderDtoMapper::toDto)
            .toList());
    }

    @Override
    @Unsecured
    public ResponseEntity<ImagePageResponseDto> getImagesInFolder(final Long folderNumber, final Integer page,
            final Integer size, final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> images;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivateImages()) {
            images = service.getImages(folderNumber, pagination, WebSorting.toSorting(sort));
        } else {
            images = service.getPublicImages(folderNumber, pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(images));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImagePageResponseDto> getRootImages(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> images;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivateImages()) {
            images = service.getRootImages(pagination, WebSorting.toSorting(sort));
        } else {
            images = service.getPublicRootImages(pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(images));
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> moveImageToFolder(final Long folderNumber, final Long imageNumber) {
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(service.moveImage(imageNumber, folderNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> moveImageToRoot(final Long imageNumber) {
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(service.moveImageToRoot(imageNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.UPDATE)
    public ResponseEntity<ImageFolderDto> updateImageFolder(final Long number, final ImageFolderUpdateDto request) {
        final AssetFolder    folder;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        folder = new AssetFolder(number, request.getName(), parentNumber);
        return ResponseEntity.ok(ImageFolderDtoMapper.toDto(service.update(folder)));
    }

}
