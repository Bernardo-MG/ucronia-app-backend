
package com.bernardomg.association.architecture.test;

import static com.bernardomg.association.architecture.config.CachedMethodPredicate.areCachedMethod;
import static com.bernardomg.association.architecture.config.CachingAnnotationPredicate.areCachingAnnotation;
import static com.bernardomg.association.architecture.config.ControllerClassPredicate.areControllerClasses;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.ProxyRules;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class CacheArchitectureRulesTest {

    @ArchTest
    static final ArchRule cache_configuration_should_be_outbound                 = classes().that()
        .haveSimpleNameEndingWith("Caches")
        .should()
        .resideInAPackage("..adapter.outbound.cache..")
        .because("caching should be configured on outbound layer");

    @ArchTest
    static final ArchRule classes_which_are_not_controllers_should_not_be_cached = methods().that()
        .areDeclaredInClassesThat(DescribedPredicate.not(areControllerClasses()))
        .should()
        .notBeAnnotatedWith(areCachingAnnotation())
        .because("caching should be applied only on controllers");

    @ArchTest
    static final ArchRule controllers_methods_should_be_cached                   = methods().that()
        .areDeclaredInClassesThat(areControllerClasses())
        .and()
        .arePublic()
        .should()
        .beAnnotatedWith(areCachingAnnotation())
        .because("controller methods should be cached");

    @ArchTest
    static final ArchRule no_direct_calls_to_cacheable_method                    = ProxyRules
        .no_classes_should_directly_call_other_methods_declared_in_the_same_class_that(are(areCachedMethod()));

}
