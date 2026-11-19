
package com.bernardomg.association.library.booktype.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookTypeService - delete")
class TestBookTypeServiceDelete {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When deleting a book type, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(bookTypeRepository.findOne(BookTypeConstants.NUMBER)).willReturn(Optional.of(BookTypes.valid()));

        // WHEN
        service.delete(BookTypeConstants.NUMBER);

        // THEN
        verify(bookTypeRepository).delete(BookTypeConstants.NUMBER);
    }

    @Test
    @DisplayName("When the book type doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookTypeRepository.findOne(BookTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the book type doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingBookTypeException.class);
    }

}
