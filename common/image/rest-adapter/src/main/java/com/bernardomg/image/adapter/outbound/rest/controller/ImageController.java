
package com.bernardomg.image.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bernardomg.content.adapter.rest.ContentDtoMapper;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageMetadataUpdateDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImagePageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.dto.ImageResponseDto;
import com.bernardomg.image.adapter.outbound.rest.model.ImageDtoMapper;
import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.usecase.service.ImageService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class ImageController implements ImageApi {

    private final ImageService service;

    public ImageController(final ImageService imageService) {
        service = Objects.requireNonNull(imageService);

        // TODO: why is it returning ResponseEntity?
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.CREATE)
    public ResponseEntity<ImageResponseDto> createImage(final String name, final String description,
            final MultipartFile file) {
        final Content          content;
        final ImageResponseDto response;
        final Image            image;

        content = ContentDtoMapper.toContent(file);
        image = new Image(-1L, name, description, "", content.mediaType(), content.size());
        response = ImageDtoMapper.toResponseDto(service.create(image, content));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.DELETE)
    public ResponseEntity<ImageResponseDto> deleteImage(final Long number) {
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImagePageResponseDto> getAllImages(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Sorting     sorting;
        final Page<Image> images;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        images = service.getAll(pagination, sorting);
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(images));
    }

    @Override
    @Unsecured
    public ResponseEntity<ImageResponseDto> getImage(final Long number) {
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(service.getOne(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<Resource> getImageContent(final Long number) {
        return ContentDtoMapper.toInline(service.getContent(number));
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> updateImage(final Long number, final String name, final String description,
            final MultipartFile file) {
        final Content          content;
        final ImageResponseDto response;

        content = ContentDtoMapper.toContent(file);
        response = ImageDtoMapper.toResponseDto(
            service.update(new Image(number, name, description, "", content.mediaType(), content.size()), content));
        return ResponseEntity.ok(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "IMAGE", action = Actions.UPDATE)
    public ResponseEntity<ImageResponseDto> updateImageMetadata(final Long number,
            final ImageMetadataUpdateDto imageMetadataUpdateDto) {
        final Image updated;
        final Image image;

        image = ImageDtoMapper.toDomain(number, imageMetadataUpdateDto);
        updated = service.updateMetadata(image);
        return ResponseEntity.ok(ImageDtoMapper.toResponseDto(updated));
    }

}
