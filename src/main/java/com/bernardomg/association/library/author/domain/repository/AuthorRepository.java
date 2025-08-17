
package com.bernardomg.association.library.author.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface AuthorRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final Long number);

    public Page<Author> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<Author> findOne(final Long number);

    public Author save(final Author author);

}
