
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.file.test.configuration.factory.Contents;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - get content")
class TestFileServiceGetContent {

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
    @DisplayName("When getting file content, it is loaded from storage")
    void testGetContent() throws IOException {
        final Content content;
        final Content existing;

        // GIVEN
        existing = Contents.file();
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(contentRepository.getOne(FileConstants.KEY)).willReturn(existing);

        // WHEN
        content = service.getContent(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(content.data()
            .readAllBytes())
            .containsExactly(FileConstants.DATA);
    }

}
