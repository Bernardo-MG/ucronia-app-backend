
package com.bernardomg.asset.adapter.outbound.rest.controller;

import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.asset.adapter.outbound.rest.model.ContentDtoMapper;
import com.bernardomg.asset.adapter.outbound.rest.security.AssetReadAuthorizer;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.usecase.service.AssetContentService;
import com.bernardomg.framework.security.access.annotation.Unsecured;

@RestController
public class AssetContentController implements AssetContentApi {

    private final AssetReadAuthorizer authorizer;

    private final AssetContentService service;

    public AssetContentController(final AssetContentService assetContentService,
            final AssetReadAuthorizer assetReadAuthorizer) {
        service = Objects.requireNonNull(assetContentService);
        authorizer = Objects.requireNonNull(assetReadAuthorizer);
    }

    @Override
    @Unsecured
    public ResponseEntity<Resource> getAssetContent(final Long number, final Boolean download) {
        final Asset                    asset;
        final Content                  content;
        final ResponseEntity<Resource> contentResponse;
        final ResponseEntity<Resource> response;

        asset = service.getOne(number);
        authorizer.checkCanRead(asset);
        content = service.getContent(asset);
        if (Boolean.TRUE.equals(download)) {
            contentResponse = ContentDtoMapper.toAttachment(content, asset.name());
        } else {
            contentResponse = ContentDtoMapper.toInline(content);
        }

        if (asset.publicAccess()) {
            response = contentResponse;
        } else {
            response = ResponseEntity.status(contentResponse.getStatusCode())
                .headers(contentResponse.getHeaders())
                .header(HttpHeaders.CACHE_CONTROL, "private, no-store")
                .body(contentResponse.getBody());
        }

        return response;
    }

}
