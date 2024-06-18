
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is annotated with JPA annotations. This is delegated to {@link IsJpaAnnotation}, which will check
 * the annotations in the class.
 */
public final class IsJpaAnnotatedClass extends DescribedPredicate<JavaClass> {

    private final IsJpaAnnotation isJpaAnnotation = new IsJpaAnnotation();

    public IsJpaAnnotatedClass() {
        super("JPA classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isMetaAnnotatedWith(isJpaAnnotation);
    }

}
