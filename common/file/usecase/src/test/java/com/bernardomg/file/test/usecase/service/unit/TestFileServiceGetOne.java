
package com.bernardomg.file.test.usecase.service.unit;

import static com.bernardomg.asset.domain.model.AssetType.FILE;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.exception.AssetNotExistingException;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service")
class TestFileServiceGetOne {

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
    @DisplayName("When getting an file, its metadata is returned")
    void testGetOne() {
        final Asset result;

        // GIVEN
        given(repository.findOne(FILE, FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));

        // WHEN
        result = service.getOne(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(Files.publicAccess());
    }

    @Test
    @DisplayName("When getting a missing file, not found is raised")
    void testGetOne_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(FILE, FileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.getOne(FileConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetNotExistingException.class);
    }

}
