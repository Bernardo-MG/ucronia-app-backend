
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget.MethodCallTarget;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;

public class Predicates {

    public static final DescribedPredicate<MethodCallTarget> areCachedMethod() {
        return new IsSpringCachedMethod();
    }

    public static final DescribedPredicate<JavaAnnotation<?>> areCachingAnnotation() {
        return new IsSpringCacheAnnotation();
    }

    public static final DescribedPredicate<JavaClass> areConfigurationClasses() {
        return new IsSpringConfigurationClass();
    }

    public static final DescribedPredicate<JavaClass> areControllerClasses() {
        return new IsSpringControllerClass();
    }

    public static final DescribedPredicate<JavaAnnotation<?>> areJpaAnnotations() {
        return new IsJpaAnnotation();
    }

    public static final DescribedPredicate<JavaClass> areJpaEntitiesClasses() {
        return new IsJpaAnnotatedClass();
    }

    public static final DescribedPredicate<JavaClass> areRepositoryClasses() {
        return new IsRepositoryNotSpringClass();
    }

    public static final DescribedPredicate<JavaClass> areServiceClasses() {
        return new IsInServicePackage();
    }

    public static final DescribedPredicate<JavaClass> areSpringRepositoryClasses() {
        return new IsSpringRepositoryClass();
    }

    public static final DescribedPredicate<JavaClass> areValidatorClasses() {
        return new IsValidatorClass();
    }

}
