/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderCreationDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageFolderUpdateDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImagePageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.model.ImageDtoMapper;
import com.bernardomg.image.adapter.outbound.rest.model.ImageFolderDtoMapper;
import com.bernardomg.image.domain.model.ImageFolder;
import com.bernardomg.image.usecase.service.ImageFolderService;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class ImageFolderController implements ImageFolderApi {

    private final ImageFolderService service;

    public ImageFolderController(final ImageFolderService imageFolderService) {
        service = Objects.requireNonNull(imageFolderService);
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.CREATE)
    public ResponseEntity<ImageFolderDto> createImageFolder(final ImageFolderCreationDto request) {
        final ImageFolder    created;
        final Optional<Long> parentNumber;

        if (request.getParentNumber()
            .isPresent()) {
            parentNumber = Optional.ofNullable(request.getParentNumber()
                .get());
        } else {
            parentNumber = Optional.empty();
        }

        created = service.create(new ImageFolder(-1L, request.getName(), parentNumber));
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
        return ResponseEntity.ok(ImageDtoMapper
            .toResponseDto(service.getImages(folderNumber, new Pagination(page, size), WebSorting.toSorting(sort))));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImagePageResponseDto> getRootImages(final Integer page, final Integer size,
            final List<String> sort) {
        return ResponseEntity.ok(ImageDtoMapper
            .toResponseDto(service.getRootImages(new Pagination(page, size), WebSorting.toSorting(sort))));
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
        final ImageFolder    folder;
        final Optional<Long> parentNumber;

        if (request.getParentNumber()
            .isPresent()) {
            parentNumber = Optional.ofNullable(request.getParentNumber()
                .get());
        } else {
            parentNumber = Optional.empty();
        }

        folder = new ImageFolder(number, request.getName(), parentNumber);
        return ResponseEntity.ok(ImageFolderDtoMapper.toDto(service.update(folder)));
    }

}
