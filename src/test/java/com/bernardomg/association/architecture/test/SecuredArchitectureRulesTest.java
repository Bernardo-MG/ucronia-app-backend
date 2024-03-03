
package com.bernardomg.association.architecture.test;

import static com.bernardomg.association.architecture.config.ControllerClassPredicate.areControllerClasses;
import static com.bernardomg.association.architecture.config.ServiceClassPredicate.areServiceClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.bernardomg.security.access.RequireResourceAccess;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecuredArchitectureRulesTest {

    @ArchTest
    static final ArchRule controllers_methods_should_be_secured = methods().that()
        .areDeclaredInClassesThat(areControllerClasses())
        .and()
        .arePublic()
        .should()
        .beAnnotatedWith(RequireResourceAccess.class);

    @ArchTest
    static final ArchRule service_methods_should_not_be_secured = methods().that()
        .areDeclaredInClassesThat(areServiceClasses())
        .and()
        .arePublic()
        .should()
        .notBeAnnotatedWith(RequireResourceAccess.class);

}
