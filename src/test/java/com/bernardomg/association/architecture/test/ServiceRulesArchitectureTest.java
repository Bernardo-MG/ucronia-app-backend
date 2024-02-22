
package com.bernardomg.association.architecture.test;

import static com.bernardomg.association.architecture.config.ServiceClassPredicate.areServiceClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class ServiceRulesArchitectureTest {

    @ArchTest
    public static final ArchRule services_should_be_suffixed = classes().that(areServiceClasses())
        .should()
        .haveSimpleNameEndingWith("Service");

}
