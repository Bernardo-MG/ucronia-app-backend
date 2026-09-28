
package com.bernardomg.file.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.file.adapter.outbound.rest.dto.FileFolderCreationDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileFolderDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileFolderUpdateDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FilePageResponseDto;
import com.bernardomg.file.adapter.outbound.rest.dto.FileResponseDto;
import com.bernardomg.file.adapter.outbound.rest.model.FileDtoMapper;
import com.bernardomg.file.adapter.outbound.rest.model.FileFolderDtoMapper;
import com.bernardomg.file.adapter.outbound.rest.security.FileReadAuthorizer;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class FileFolderController implements FileFolderApi {

    private final FileReadAuthorizer authorizer;

    private final AssetFolderService  service;

    public FileFolderController(@Qualifier("fileFolderService") final AssetFolderService fileFolderService,
            final FileReadAuthorizer fileReadAuthorizer) {
        service = Objects.requireNonNull(fileFolderService);
        authorizer = Objects.requireNonNull(fileReadAuthorizer);
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.CREATE)
    public ResponseEntity<FileFolderDto> createFileFolder(final FileFolderCreationDto request) {
        final AssetFolder    created;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        created = service.create(new AssetFolder(-1L, request.getName(), parentNumber));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(FileFolderDtoMapper.toDto(created));
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.DELETE)
    public ResponseEntity<FileFolderDto> deleteFileFolder(final Long number) {
        return ResponseEntity.ok(FileFolderDtoMapper.toDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<FileFolderDto> getFileFolder(final Long number) {
        return ResponseEntity.ok(FileFolderDtoMapper.toDto(service.getOne(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<List<FileFolderDto>> getFileFolders() {
        return ResponseEntity.ok(service.getAll()
            .stream()
            .map(FileFolderDtoMapper::toDto)
            .toList());
    }

    @Override
    @Unsecured
    public ResponseEntity<FilePageResponseDto> getFilesInFolder(final Long folderNumber, final Integer page,
            final Integer size, final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> files;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivateFiles()) {
            files = service.getAssets(folderNumber, pagination, WebSorting.toSorting(sort));
        } else {
            files = service.getPublicAssets(folderNumber, pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(FileDtoMapper.toResponseDto(files));
    }

    @Override
    @Unsecured
    public ResponseEntity<FilePageResponseDto> getRootFiles(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> files;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivateFiles()) {
            files = service.getRootAssets(pagination, WebSorting.toSorting(sort));
        } else {
            files = service.getPublicRootAssets(pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(FileDtoMapper.toResponseDto(files));
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.UPDATE)
    public ResponseEntity<FileResponseDto> moveFileToFolder(final Long folderNumber, final Long fileNumber) {
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(service.moveAsset(fileNumber, folderNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.UPDATE)
    public ResponseEntity<FileResponseDto> moveFileToRoot(final Long fileNumber) {
        return ResponseEntity.ok(FileDtoMapper.toResponseDto(service.moveAssetToRoot(fileNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "FILE", action = Actions.UPDATE)
    public ResponseEntity<FileFolderDto> updateFileFolder(final Long number, final FileFolderUpdateDto request) {
        final AssetFolder    folder;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        folder = new AssetFolder(number, request.getName(), parentNumber);
        return ResponseEntity.ok(FileFolderDtoMapper.toDto(service.update(folder)));
    }

}
