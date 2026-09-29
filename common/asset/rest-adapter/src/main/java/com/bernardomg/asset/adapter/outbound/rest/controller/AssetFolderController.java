
package com.bernardomg.asset.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.asset.adapter.outbound.rest.dto.AssetFolderCreationDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetFolderDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetFolderUpdateDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetPageResponseDto;
import com.bernardomg.asset.adapter.outbound.rest.dto.AssetResponseDto;
import com.bernardomg.asset.adapter.outbound.rest.model.AssetDtoMapper;
import com.bernardomg.asset.adapter.outbound.rest.model.AssetFolderDtoMapper;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.AssetFolder;
import com.bernardomg.asset.usecase.service.AssetFolderService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.framework.security.access.annotation.Unsecured;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class AssetFolderController implements AssetFolderApi {

    private final AssetReadAuthorizer authorizer;

    private final AssetFolderService  service;

    public AssetFolderController(final AssetFolderService assetFolderService,
            @Qualifier("assetReadAuthorizer") final AssetReadAuthorizer assetReadAuthorizer) {
        service = Objects.requireNonNull(assetFolderService);
        authorizer = Objects.requireNonNull(assetReadAuthorizer);
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSET", action = Actions.CREATE)
    public ResponseEntity<AssetFolderDto> createAssetFolder(final AssetFolderCreationDto request) {
        final AssetFolder    created;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        created = service.create(new AssetFolder(-1L, request.getName(), parentNumber));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AssetFolderDtoMapper.toDto(created));
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSET", action = Actions.DELETE)
    public ResponseEntity<AssetFolderDto> deleteAssetFolder(final Long number) {
        return ResponseEntity.ok(AssetFolderDtoMapper.toDto(service.delete(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<AssetFolderDto> getAssetFolder(final Long number) {
        return ResponseEntity.ok(AssetFolderDtoMapper.toDto(service.getOne(number)));
    }

    @Override
    @Unsecured
    public ResponseEntity<List<AssetFolderDto>> getAssetFolders() {
        return ResponseEntity.ok(service.getAll()
            .stream()
            .map(AssetFolderDtoMapper::toDto)
            .toList());
    }

    @Override
    @Unsecured
    public ResponseEntity<AssetPageResponseDto> getAssetsInFolder(final Long folderNumber, final Integer page,
            final Integer size, final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> assets;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivate()) {
            assets = service.getAssets(folderNumber, pagination, WebSorting.toSorting(sort));
        } else {
            assets = service.getPublicAssets(folderNumber, pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(AssetDtoMapper.toResponseDto(assets));
    }

    @Override
    @Unsecured
    public ResponseEntity<AssetPageResponseDto> getRootAssets(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination  pagination;
        final Page<Asset> assets;

        pagination = new Pagination(page, size);
        if (authorizer.canReadPrivate()) {
            assets = service.getRootAssets(pagination, WebSorting.toSorting(sort));
        } else {
            assets = service.getPublicRootAssets(pagination, WebSorting.toSorting(sort));
        }

        return ResponseEntity.ok(AssetDtoMapper.toResponseDto(assets));
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSET", action = Actions.UPDATE)
    public ResponseEntity<AssetResponseDto> moveAssetToFolder(final Long folderNumber, final Long assetNumber) {
        return ResponseEntity.ok(AssetDtoMapper.toResponseDto(service.moveAsset(assetNumber, folderNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSET", action = Actions.UPDATE)
    public ResponseEntity<AssetResponseDto> moveAssetToRoot(final Long assetNumber) {
        return ResponseEntity.ok(AssetDtoMapper.toResponseDto(service.moveAssetToRoot(assetNumber)));
    }

    @Override
    @RequireResourceAuthorization(resource = "ASSET", action = Actions.UPDATE)
    public ResponseEntity<AssetFolderDto> updateAssetFolder(final Long number, final AssetFolderUpdateDto request) {
        final AssetFolder    folder;
        final Optional<Long> parentNumber;

        parentNumber = Optional.ofNullable(request.getParentNumber());
        folder = new AssetFolder(number, request.getName(), parentNumber);
        return ResponseEntity.ok(AssetFolderDtoMapper.toDto(service.update(folder)));
    }

}
