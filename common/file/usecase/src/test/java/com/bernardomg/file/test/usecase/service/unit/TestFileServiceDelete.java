
package com.bernardomg.file.test.usecase.service.unit;

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
import com.bernardomg.content.domain.policy.ContentPolicy;
import com.bernardomg.content.domain.repository.ContentRepository;
import com.bernardomg.file.domain.exception.FileNotExistingException;
import com.bernardomg.file.domain.model.File;
import com.bernardomg.file.domain.repository.FileRepository;
import com.bernardomg.file.test.configuration.factory.FileConstants;
import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.file.usecase.service.DefaultFileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("File service - delete")
class TestFileServiceDelete {

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
    @DisplayName("When deleting an file, the file is deleted")
    void testDelete() {
        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));

        // WHEN
        service.delete(FileConstants.NUMBER);

        // THEN
        then(contentRepository).should()
            .delete(FileConstants.KEY);
    }

    @Test
    @DisplayName("When content deletion fails, the metadata deletion still succeeds")
    void testDelete_ContentDeletionFailureIsIgnored() {

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        willThrow(new RuntimeException("S3 deletion failed")).given(contentRepository)
            .delete(FileConstants.KEY);

        // WHEN
        service.delete(FileConstants.NUMBER);

        // THEN
        then(repository).should()
            .delete(FileConstants.NUMBER);
    }

    @Test
    @DisplayName("When metadata deletion fails, the content is preserved")
    void testDelete_MetadataDeletionFailurePreservesContent() {
        final RuntimeException exception;
        final ThrowingCallable execution;

        // GIVEN
        exception = new RuntimeException("Database deletion failed");
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));
        willThrow(exception).given(repository)
            .delete(FileConstants.NUMBER);

        // WHEN
        execution = () -> service.delete(FileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isSameAs(exception);
        then(contentRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("When deleting a missing file, not found is raised")
    void testDelete_Missing() {
        final ThrowingCallable callable;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        callable = () -> service.delete(FileConstants.NUMBER);

        // WHEN + THEN
        Assertions.assertThatThrownBy(callable)
            .isInstanceOf(FileNotExistingException.class);
    }

    @Test
    @DisplayName("When deleting an file, the deleted file is returned")
    void testDelete_Returned() {
        final File deleted;

        // GIVEN
        given(repository.findOne(FileConstants.NUMBER)).willReturn(Optional.of(Files.publicAccess()));

        // WHEN
        deleted = service.delete(FileConstants.NUMBER);

        // THEN
        Assertions.assertThat(deleted)
            .isEqualTo(Files.publicAccess());
    }

}
