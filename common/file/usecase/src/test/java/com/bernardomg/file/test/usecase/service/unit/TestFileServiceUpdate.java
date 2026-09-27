
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

import com.bernardomg.content.domain.key.ContentKeyGenerator;
import com.bernardomg.content.domain.model.Content;
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.file.domain.exception.FileAlreadyExistsException;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.Contents;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("File service - update")
class TestFileServiceUpdate {

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
    @DisplayName("When updating an file, metadata and content are persisted")
    void testUpdate() {
        final Content content;
        final File    updated;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.CHANGE_KEY);
        given(repository.save(any(File.class))).willReturn(Files.publicAccess());
        content = Contents.file();

        // WHEN
        updated = service.update(Files.publicAccess(), content);

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Files.publicAccess());
        then(contentRepository).should()
            .save(FileConstants.CHANGE_KEY, content);
        then(contentRepository).should()
            .delete(FileConstants.KEY);
    }

    @Test
    @DisplayName("When old content deletion fails, the update still succeeds")
    void testUpdate_ContentDeletionFailureIsIgnored() {
        final File updated;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.CHANGE_KEY);
        given(repository.save(any(File.class))).willReturn(Files.publicAccess());
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(FileConstants.KEY);

        // WHEN
        updated = service.update(Files.publicAccess(), Contents.file());

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(Files.publicAccess());
    }

    @Test
    @DisplayName("When updating an file with an existing name, an exception is thrown")
    void testUpdate_DuplicateName() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(repository.existsByNameAndFolder(FileConstants.NAME, null, FileConstants.NUMBER)).willReturn(true);

        // WHEN
        callable = () -> service.update(Files.publicAccess(), Contents.file());

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(FileAlreadyExistsException.class);
    }

    @Test
    @DisplayName("When update persistence fails, the replacement content is deleted")
    void testUpdate_PersistenceFailureDeletesReplacement() {
        final ThrowingCallable callable;
        final RuntimeException failure;

        // GIVEN
        failure = new RuntimeException("Persistence failed");
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.CHANGE_KEY);
        willThrow(failure).given(repository)
            .save(any(File.class));

        // WHEN
        callable = () -> service.update(Files.publicAccess(), Contents.file());

        // THEN
        Assertions.assertThatThrownBy(callable)
            .isSameAs(failure);
        then(contentRepository).should()
            .delete(FileConstants.CHANGE_KEY);
    }

    @Test
    @DisplayName("When updating an file, metadata references the replacement content")
    void testUpdate_PersistsReplacementKey() {

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        given(contentKeyGenerator.generate("files")).willReturn(FileConstants.CHANGE_KEY);
        given(repository.save(any(File.class))).willReturn(Files.publicAccess());

        // WHEN
        service.update(Files.publicAccess(), Contents.file());

        // THEN
        then(repository).should()
            .save(Files.change());
    }

}
