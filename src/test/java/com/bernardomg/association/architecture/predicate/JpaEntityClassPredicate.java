
package com.bernardomg.association.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

import jakarta.persistence.Entity;

public final class JpaEntityClassPredicate extends DescribedPredicate<JavaClass> {

    public JpaEntityClassPredicate() {
        super("JPA entities classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAnnotatedWith(Entity.class);
    }

}