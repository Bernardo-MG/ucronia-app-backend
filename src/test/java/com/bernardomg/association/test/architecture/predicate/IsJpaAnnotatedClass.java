
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class IsJpaAnnotatedClass extends DescribedPredicate<JavaClass> {

    public IsJpaAnnotatedClass() {
        super("JPA classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAnnotatedWith(new IsJpaAnnotation());
    }

}
