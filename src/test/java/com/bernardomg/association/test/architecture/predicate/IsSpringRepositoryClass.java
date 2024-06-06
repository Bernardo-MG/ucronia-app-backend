
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class IsSpringRepositoryClass extends DescribedPredicate<JavaClass> {

    public IsSpringRepositoryClass() {
        super("Spring repository classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAssignableTo(JpaRepository.class);
    }

}
