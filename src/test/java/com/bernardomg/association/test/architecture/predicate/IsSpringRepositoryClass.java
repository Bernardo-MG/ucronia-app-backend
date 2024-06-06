
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.data.repository.Repository;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is a Repository class. This is done by checking it extends the
 * {@link org.springframework.data.repository.Repository Repository} interface or applied the
 * {@link org.springframework.stereotype.Repository Repository} annotation.
 */
public final class IsSpringRepositoryClass extends DescribedPredicate<JavaClass> {

    public IsSpringRepositoryClass() {
        super("Spring repository classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAssignableTo(Repository.class)
                || javaClass.isMetaAnnotatedWith(org.springframework.stereotype.Repository.class);
    }

}
