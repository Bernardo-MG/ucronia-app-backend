
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("File service - update metadata")
class TestFileServiceUpdateMetadata {

    @Mock
    private ContentKeyGenerator contentKeyGenerator;

    @Mock
    private ContentPolicy       contentPolicy;

    @Mock
    private ContentRepository   contentRepository;

    @Mock
    private FileRepository      repository;

    @InjectMocks
    private DefaultFileService  service;

    @Test
    @DisplayName("When updating file metadata, content is not persisted")
    void testUpdateMetadata() {
        final File updated;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(repository.save(any(File.class))).willReturn(Files.publicAccess());

        // WHEN
        updated = service.updateMetadata(Files.publicAccess());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Files.publicAccess());
    }

}
