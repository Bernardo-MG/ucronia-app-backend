
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.data.repository.Repository;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is a repository, but not a Spring repository.
 */
public final class IsRepositoryNotSpringClass extends DescribedPredicate<JavaClass> {

    /**
     * TODO: careful when checking by package
     */
    private static final String    PACKAGE                 = ".repository";

    private final IsSyntheticClass syntheticClassPredicate = new IsSyntheticClass();

    public IsRepositoryNotSpringClass() {
        super("repository classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.getPackageName()
            .endsWith(PACKAGE)) && (!syntheticClassPredicate.test(javaClass))
                && (!javaClass.isAssignableTo(Repository.class));
    }

}
