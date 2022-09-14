
package com.bernardomg.validation;

import java.util.Collection;

import com.bernardomg.mvc.error.model.Failure;

public interface ValidationRule<T> {

    public Collection<Failure> test(final T value);

}
