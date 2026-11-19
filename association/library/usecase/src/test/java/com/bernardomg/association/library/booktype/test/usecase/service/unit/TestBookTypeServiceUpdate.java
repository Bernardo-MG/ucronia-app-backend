
package com.bernardomg.association.library.booktype.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookTypeService - update")
class TestBookTypeServiceUpdate {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When there is a book type with an empty name, then an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable action;
        final BookType         bookType;

        // GIVEN
        bookType = BookTypes.emptyName();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(bookType);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a book type with a name existing for another user, then an exception is thrown")
    void testUpdate_ExistsForAnother() {
        final BookType         bookType;
        final ThrowingCallable action;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.existsByNameForAnother(BookTypeConstants.NAME, BookTypeConstants.NUMBER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(bookType);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("existing", "name", BookTypeConstants.NAME));
    }

    @Test
    @DisplayName("When there is a not existing book type, then an exception is thrown")
    void testUpdate_NotExisting() {
        final BookType         bookType;
        final ThrowingCallable action;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(bookType);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing book type, then an exception is thrown")
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When there is a valid book type, then the book type is persisted")
    void testUpdate_PersistedData() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(bookType);

        // THEN
        verify(bookTypeRepository).save(BookTypes.valid());
    }

    @Test
    @DisplayName("When there is a valid book type, then the created book type is returned")
    void testUpdate_ReturnedData() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        given(bookTypeRepository.save(BookTypes.valid())).willReturn(BookTypes.valid());

        // WHEN
        created = service.update(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid book type, then the created book type is returned")
            .isEqualTo(BookTypes.valid());
    }

}
