
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - get all public")
class TestFileServiceGetAllPublic {

    @Mock
    private ContentKeyGenerator contentKeyGenerator;

    @Mock
    private ContentPolicy       contentPolicy;

    @Mock
    private ContentRepository   contentRepository;

    @Mock
    private AssetRepository     repository;

    @InjectMocks
    private DefaultFileService  service;

    @Test
    @DisplayName("When getting public files, the requested page is returned")
    void testGetAllPublic() {
        final Page<Asset> existing;
        final Page<Asset> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(Files.publicAccess()), 10, 1, 1, 1, 1, true, true, sorting);
        given(repository.findAllPublic(pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getAllPublic(pagination, sorting);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(existing);
    }

    @Test
    @DisplayName("When getting public files and there is no data, the returned page is empty")
    void testGetAllPublic_Returned() {
        final Page<Asset> existing;
        final Page<Asset> result;
        final Pagination  pagination;
        final Sorting     sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 10, 1, 1, 1, 1, true, true, sorting);
        given(repository.findAllPublic(pagination, sorting)).willReturn(existing);

        // WHEN
        result = service.getAllPublic(pagination, sorting);

        // THEN
        Assertions.assertThat(result.content())
            .isEmpty();
    }

}
