
package com.bernardomg.association.library.domain.repository;

import com.bernardomg.association.library.domain.model.Author;

public interface AuthorRepository {

    public boolean exists(final String name);

    public Author save(final Author book);

}
