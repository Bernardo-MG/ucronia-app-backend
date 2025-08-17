
package com.bernardomg.association.library.author.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface AuthorService {

    public Author create(final Author author);

    public void delete(final Long number);

    public Page<Author> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<Author> getOne(final Long number);

    public Author update(final Author author);

}
