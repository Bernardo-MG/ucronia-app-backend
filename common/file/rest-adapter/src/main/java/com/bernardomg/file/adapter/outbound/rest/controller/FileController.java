
package com.bernardomg.file.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bernardomg.asset.adapter.outbound.rest.model.ContentDtoMapper;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.file.adapter.outbound.rest.dto.FileMetadataUpdateDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FilePageResponseDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileResponseDto;
import com.bernardomg.file.adapter.outbound.rest.model.FileDtoMapper;
import com.bernardomg.file.usecase.service.FileService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class FileController implements FileApi {

    private final AssetReadAuthorizer authorizer;

    private final FileService        service;

    public FileController(final FileService fileService,
            @Qualifier("fileReadAuthorizer") final AssetReadAuthorizer fileReadAuthorizer) {
        service = Objects.requireNonNull(fileService);
        authorizer = Objects.requireNonNull(fileReadAuthorizer);

        // TODO: why is it returning ResponseEntity?
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.CREATE)
    public ResponseEntity<FileResponseDto> createFile(final String name, final String description,
            final MultipartFile file, final Boolean publicAccess) {
        final Content         content;
        final FileResponseDto response;
        final Asset           createdFile;

        content = ContentDtoMapper.toContent(file);
        createdFile = new Asset(-1L, name, description, "", content.mediaType(), content.size(),
            !Boolean.FALSE.equals(publicAccess), Optional.empty());
        response = FileDtoMapper.toResponseDto(service.create(createdFile, content));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.DELETE)
    public ResponseEntity<FileResponseDto> deleteFile(final Long number) {
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<FilePageResponseDto> getAllFiles(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Sorting     sorting;
        final Page<Asset> files;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        if (authorizer.canReadPrivate()) {
            files = service.getAll(pagination, sorting);
        } else {
            files = service.getAllPublic(pagination, sorting);
        }
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(files));
    }

    @Override
    @Unsecured
    public ResponseEntity<FileResponseDto> getFile(final Long number) {
        final Asset file;

        file = service.getOne(number);
        authorizer.checkCanRead(file);
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(file));
    }

    @Override
    @Unsecured
    public ResponseEntity<Resource> getFileContent(final Long number) {
        final Asset                    file;
        final ResponseEntity<Resource> attachmentResponse;
        final ResponseEntity<Resource> response;

        file = service.getOne(number);
        authorizer.checkCanRead(file);
        attachmentResponse = ContentDtoMapper.toAttachment(service.getContent(number), file.name());

        if (file.publicAccess()) {
            response = attachmentResponse;
        } else {
            response = ResponseEntity.status(attachmentResponse.getStatusCode())
                .headers(attachmentResponse.getHeaders())
                .header(HttpHeaders.CACHE_CONTROL, "private, no-store")
                .body(attachmentResponse.getBody());
        }

        return response;
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.UPDATE)
    public ResponseEntity<FileResponseDto> updateFile(final Long number, final String name, final String description,
            final Boolean publicAccess, final MultipartFile file) {
        final Content         content;
        final FileResponseDto response;

        content = ContentDtoMapper.toContent(file);
        response = FileDtoMapper.toResponseDto(service.update(new Asset(number, name, description, "",
            content.mediaType(), content.size(), publicAccess, Optional.empty()), content));
        return ResponseEntity.ok(response);
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.UPDATE)
    public ResponseEntity<FileResponseDto> updateFileMetadata(final Long number,
            final FileMetadataUpdateDto fileMetadataUpdateDto) {
        final Asset updated;
        final Asset file;

        file = FileDtoMapper.toDomain(number, fileMetadataUpdateDto);
        updated = service.updateMetadata(file);
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(updated));
    }

}
