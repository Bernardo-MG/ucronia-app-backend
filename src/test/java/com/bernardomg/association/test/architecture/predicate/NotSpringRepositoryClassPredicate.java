
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class NotSpringRepositoryClassPredicate extends DescribedPredicate<JavaClass> {

    private static final String           PACKAGE                 = ".repository";

    private final SyntheticClassPredicate syntheticClassPredicate = new SyntheticClassPredicate();

    public NotSpringRepositoryClassPredicate() {
        super("repository classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.getPackageName()
            .endsWith(PACKAGE)) && (!syntheticClassPredicate.test(javaClass))
                && (!javaClass.isAssignableTo(JpaRepository.class));
    }

}
