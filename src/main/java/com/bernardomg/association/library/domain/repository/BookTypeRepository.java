
package com.bernardomg.association.library.domain.repository;

import com.bernardomg.association.library.domain.model.BookType;

public interface BookTypeRepository {

    public boolean exists(final String name);

    public BookType save(final BookType book);

}
