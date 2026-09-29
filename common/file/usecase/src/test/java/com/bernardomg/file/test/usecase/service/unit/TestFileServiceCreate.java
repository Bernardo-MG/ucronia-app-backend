
package com.bernardomg.file.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.asset.domain.exception.AssetAlreadyExistsException;
import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.asset.domain.repository.AssetRepository;
import com.bernardomg.asset.domain.key.ContentKeyGenerator;
import com.bernardomg.asset.domain.model.Content;
import com.bernardomg.asset.domain.policy.ContentPolicy;
import com.bernardomg.asset.domain.repository.ContentRepository;
import com.bernardomg.file.test.configuration.factory.Contents;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Asset service - create")
class TestFileServiceCreate {

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
    @DisplayName("When creating an file with an existing name, conflict is raised")
    void testCreate_Existing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.existsByNameAndFolder(FileConstants.NAME, null)).willReturn(true);

        // WHEN
        callable = () -> service.create(Files.publicAccess(), Contents.file());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(AssetAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When creating an file, the name is checked in its folder")
    void testCreate_NameCheckedInFolder() {
        final Long  folderNumber;
        final Asset file;

        // GIVEN
        folderNumber = 2L;
        file = new Asset(FileConstants.NUMBER, FileConstants.NAME, FileConstants.DESCRIPTION, FileConstants.KEY,
            FileConstants.PDF_MEDIA_TYPE, FileConstants.DATA.length, Optional.of(folderNumber));
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.KEY);
        given(repository.save(any(Asset.class))).willReturn(file);

        // WHEN
        service.create(file, Contents.file());

        // THEN
        then(repository).should()
            .existsByNameAndFolder(FileConstants.NAME, folderNumber);
    }

    @Test
    @DisplayName("When creating an file, the content should be persisted")
    void testCreate_PersistContent() {
        final Content content;

        // GIVEN
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.KEY);
        given(repository.save(any(Asset.class))).willReturn(Files.publicAccess());
        content = Contents.file();

        service.create(Files.publicAccess(), content);

        // THEN
        then(contentRepository).should()
            .save(FileConstants.KEY, content);
    }

    @Test
    @DisplayName("When metadata persistence fails, the uploaded content is deleted")
    void testCreate_PersistenceFailureDeletesContent() {
        final ThrowingCallable callable;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.KEY);
        willThrow(failure).given(repository)
            .save(any(Asset.class));

        // WHEN
        callable = () -> service.create(Files.publicAccess(), Contents.file());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isSameAs(failure);
        then(contentRepository).should()
            .delete(FileConstants.KEY);
    }

    @Test
    @DisplayName("When creating an file, the correct file is returned")
    void testCreate_Returned() {
        final Content content;
        final Asset   created;

        // GIVEN
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.KEY);
        given(repository.save(any(Asset.class))).willReturn(Files.publicAccess());
        content = Contents.file();

        // WHEN
        created = service.create(Files.publicAccess(), content);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Files.publicAccess());
    }

}
