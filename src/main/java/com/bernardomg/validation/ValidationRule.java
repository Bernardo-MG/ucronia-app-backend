
package com.bernardomg.validation;

import java.util.Collection;

public interface ValidationRule<T> {

    public Collection<ValidationError> test(final T value);

}
