
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

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookTypeService - create")
class TestBookTypeServiceCreate {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When there is a book type with an empty name, then an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable action;
        final BookType         bookType;

        // GIVEN
        bookType = BookTypes.emptyName();

        // WHEN
        action = () -> service.create(bookType);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is a book type with an existing name, then an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable action;
        final BookType         bookType;

        // GIVEN
        bookType = BookTypes.toCreate();

        given(bookTypeRepository.existsByName(BookTypeConstants.NAME)).willReturn(true);

        // WHEN
        action = () -> service.create(bookType);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("existing", "name", BookTypeConstants.NAME));
    }

    @Test
    @DisplayName("When there is a book type with padded name, then the book is persisted")
    void testCreate_Padded_PersistedData() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.padded();

        // WHEN
        service.create(bookType);

        // THEN
        verify(bookTypeRepository).save(BookTypes.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book type, then the book is persisted")
    void testCreate_PersistedData() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.toCreate();

        // WHEN
        service.create(bookType);

        // THEN
        verify(bookTypeRepository).save(BookTypes.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book type, then the created book is returned")
    void testCreate_ReturnedData() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.toCreate();

        given(bookTypeRepository.save(BookTypes.toCreate())).willReturn(BookTypes.valid());

        // WHEN
        created = service.create(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid book type, then the created book is returned")
            .isEqualTo(BookTypes.valid());
    }

}
