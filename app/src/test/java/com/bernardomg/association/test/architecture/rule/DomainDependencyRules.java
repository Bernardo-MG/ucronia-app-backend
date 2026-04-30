
package com.bernardomg.association.test.architecture.rule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

public class DomainDependencyRules {

    /**
     * Jackson classes are not imported.
     */
    @ArchTest
    static final ArchRule not_import_jackson_utils = noClasses().that()
        .resideInAnyPackage("com.bernardomg.association..domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("com.fasterxml.jackson..")
        .because("shouldn't use Jackson classes");

}
