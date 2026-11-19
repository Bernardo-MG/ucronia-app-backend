
package com.bernardomg.association.library.booktype.test.usecase.service.unit;

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

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookTypeService - getOne")
class TestBookTypeServiceGetOne {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When there is a book type, then it is returned")
    void testGetOne() {
        final Optional<BookType> bookType;

        // GIVEN
        given(bookTypeRepository.findOne(BookTypeConstants.NUMBER)).willReturn(Optional.of(BookTypes.valid()));

        // WHEN
        bookType = service.getOne(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(bookType)
            .as("When there is a book type, then it is returned")
            .contains(BookTypes.valid());
    }

    @Test
    @DisplayName("When there are no book types, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(bookTypeRepository.findOne(BookTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there are no book types, then an exception is thrown")
            .isInstanceOf(MissingBookTypeException.class);
    }

}
